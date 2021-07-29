package com.example.toolapp.services;

import com.example.toolapp.entities.RentalCharge;
import com.example.toolapp.entities.Tool;
import com.example.toolapp.models.RentalAgreement;
import com.example.toolapp.models.ToolCheckout;
import com.example.toolapp.repositories.RentalChargeRepository;
import com.example.toolapp.repositories.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CheckoutService {

    private ToolRepository toolRepository;
    private RentalChargeRepository rentalChargeRepository;

    @Autowired
    public CheckoutService(ToolRepository toolRepository, RentalChargeRepository rentalChargeRepository) {
        this.toolRepository = toolRepository;
        this.rentalChargeRepository = rentalChargeRepository;
    }

    public RentalAgreement checkout(ToolCheckout toolCheckout) {
        Tool tool = toolRepository.findByToolCode(toolCheckout.getToolCode());
        if (tool == null) {
            throw new CheckoutException("Invalid tool code: " + toolCheckout.getToolCode());
        }

        if (toolCheckout.getRentalDayCount() < 1) {
            throw new CheckoutException("Rental days must be >= to 1: " + toolCheckout.getRentalDayCount());
        }

        if (toolCheckout.getDiscountPercent() < 0 || toolCheckout.getDiscountPercent() > 100) {
            throw new CheckoutException("Discount percent must be in range 0 - 100: " + toolCheckout.getDiscountPercent());
        }

        if (toolCheckout.getCheckoutDate() == null) {
            throw new CheckoutException("Checkout date must be provided");
        }

        RentalCharge rentalCharge = rentalChargeRepository.findByToolTypeToolType(tool.getToolType().getToolType());
        if (rentalCharge == null) {
            throw new CheckoutException("Rental charge was not found for tool type: " + tool.getToolType().getToolType());
        }

        RentalAgreement rentalAgreement = new RentalAgreement();
        rentalAgreement.setToolCode(tool.getToolCode());
        rentalAgreement.setToolType(tool.getToolType().getToolType());
        rentalAgreement.setToolBrand(tool.getBrand());
        rentalAgreement.setRentalDays(toolCheckout.getRentalDayCount());
        rentalAgreement.setCheckoutDate(toolCheckout.getCheckoutDate());
        rentalAgreement.setDueDate(toolCheckout.getCheckoutDate().plusDays(toolCheckout.getRentalDayCount()));
        rentalAgreement.setDailyRentalCharge(rentalCharge.getDailyCharge());

        int chargeDays = new ChargeableDateCalculator().calculateChargeDays(rentalAgreement.getCheckoutDate(), rentalAgreement.getDueDate(), rentalCharge);
        rentalAgreement.setChargeDays(chargeDays);

        // Charge days X daily charge.  Resulting total rounded half up to cents.
        BigDecimal preDiscountCharge = rentalCharge.getDailyCharge().multiply(BigDecimal.valueOf(chargeDays)).setScale(2, RoundingMode.HALF_UP);
        rentalAgreement.setPreDiscountCharge(preDiscountCharge);

        rentalAgreement.setDiscountPercent(toolCheckout.getDiscountPercent());

        // Calculated from discount % and pre-discount charge.  Resulting amount rounded half up to cents.
        BigDecimal discountAmount = preDiscountCharge.multiply(BigDecimal.valueOf((double)toolCheckout.getDiscountPercent() / 100)).setScale(2, RoundingMode.HALF_UP);
        rentalAgreement.setDiscountAmount(discountAmount);

        rentalAgreement.setFinalCharge(preDiscountCharge.subtract(discountAmount));

        return rentalAgreement;
    }


}

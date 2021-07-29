package com.example.toolapp.services;

import com.example.toolapp.models.RentalAgreement;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Formats a rental agreement to the text format in specification.
 */
public class RentalAgreementFormatter {

    private RentalAgreement rentalAgreement;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private NumberFormat moneyFormat = DecimalFormat.getCurrencyInstance(Locale.US);

    public RentalAgreementFormatter(RentalAgreement rentalAgreement) {
        this.rentalAgreement = rentalAgreement;
    }

    public String format() {
        return "Tool Code: " + rentalAgreement.getToolCode() + "\n" +
                "Tool Type: " + rentalAgreement.getToolType() + "\n" +
                "Tool Brand: " + rentalAgreement.getToolBrand() + "\n" +
                "Rental Days: " + rentalAgreement.getRentalDays() + "\n" +
                "Checkout Date: " + dateFormatter.format(rentalAgreement.getCheckoutDate()) + "\n" +
                "Due Date: " + dateFormatter.format(rentalAgreement.getDueDate()) + "\n" +
                "Daily Rental Charge: " + moneyFormat.format(rentalAgreement.getDailyRentalCharge()) + "\n" +
                "Charge Days: " + rentalAgreement.getChargeDays() + "\n" +
                "Pre-discount Charge: " + moneyFormat.format(rentalAgreement.getPreDiscountCharge()) + "\n" +
                "Discount Percent: " + rentalAgreement.getDiscountPercent() + "%\n" +
                "Discount Amount: " + moneyFormat.format(rentalAgreement.getDiscountAmount()) + "\n" +
                "Final Charge: "  + moneyFormat.format(rentalAgreement.getFinalCharge()) + "\n";
    }
}

package com.example.toolapp.services;

import com.example.toolapp.models.RentalAgreement;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RentalAgreementFormatterTest {

    @Test
    public void testFormat() {
        RentalAgreement rentalAgreement = new RentalAgreement();
        rentalAgreement.setToolCode("LADW");
        rentalAgreement.setToolType("Ladder");
        rentalAgreement.setToolBrand("Werner");
        rentalAgreement.setRentalDays(3);
        rentalAgreement.setCheckoutDate(LocalDate.of(2020, Month.JULY, 02));
        rentalAgreement.setDueDate(LocalDate.of(2020, Month.JULY, 05));
        rentalAgreement.setDailyRentalCharge(BigDecimal.valueOf(1.99));
        rentalAgreement.setChargeDays(2);
        rentalAgreement.setPreDiscountCharge(BigDecimal.valueOf(3.98));
        rentalAgreement.setDiscountPercent(10);
        rentalAgreement.setDiscountAmount(BigDecimal.valueOf(0.40));
        rentalAgreement.setFinalCharge(BigDecimal.valueOf(3.58));

        String expectedFormat = """
            Tool Code: LADW
            Tool Type: Ladder
            Tool Brand: Werner
            Rental Days: 3
            Checkout Date: 07/02/2020
            Due Date: 07/05/2020
            Daily Rental Charge: $1.99
            Charge Days: 2
            Pre-discount Charge: $3.98
            Discount Percent: 10%
            Discount Amount: $0.40
            Final Charge: $3.58
            """;

        assertEquals(expectedFormat, new RentalAgreementFormatter(rentalAgreement).format());
    }
}

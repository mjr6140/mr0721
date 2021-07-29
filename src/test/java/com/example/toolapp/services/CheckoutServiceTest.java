package com.example.toolapp.services;

import com.example.toolapp.models.RentalAgreement;
import com.example.toolapp.models.ToolCheckout;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CheckoutServiceTest {

    @Autowired
    private CheckoutService checkoutService;

    @Test
    public void testCase1() {
        ToolCheckout toolCheckout = new ToolCheckout();
        toolCheckout.setToolCode("JAKR");
        toolCheckout.setCheckoutDate(LocalDate.parse("2015-09-03"));
        toolCheckout.setRentalDayCount(5);
        toolCheckout.setDiscountPercent(101);

        Exception exception = assertThrows(CheckoutException.class, () -> {
            checkoutService.checkout(toolCheckout);
        });

        String expectedMessage = "Discount percent must be in range 0 - 100: 101";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testCase2() {
        ToolCheckout toolCheckout = new ToolCheckout();
        toolCheckout.setToolCode("LADW");
        toolCheckout.setCheckoutDate(LocalDate.parse("2020-07-02"));
        toolCheckout.setRentalDayCount(3);
        toolCheckout.setDiscountPercent(10);

        RentalAgreement rentalAgreement = checkoutService.checkout(toolCheckout);
        System.out.println(new RentalAgreementFormatter(rentalAgreement).format());

        assertEquals("LADW", rentalAgreement.getToolCode());
        assertEquals("Ladder", rentalAgreement.getToolType());
        assertEquals("Werner", rentalAgreement.getToolBrand());
        assertEquals(3, rentalAgreement.getRentalDays());
        assertEquals(LocalDate.of(2020, Month.JULY, 2), rentalAgreement.getCheckoutDate());
        assertEquals(LocalDate.of(2020, Month.JULY, 5), rentalAgreement.getDueDate());
        assertEquals(0, BigDecimal.valueOf(1.99).compareTo(rentalAgreement.getDailyRentalCharge()));
        assertEquals(2, rentalAgreement.getChargeDays());
        assertEquals(0, BigDecimal.valueOf(3.98).compareTo(rentalAgreement.getPreDiscountCharge()));
        assertEquals(10, rentalAgreement.getDiscountPercent());
        assertEquals(0, BigDecimal.valueOf(0.40).compareTo(rentalAgreement.getDiscountAmount()));
        assertEquals(0, BigDecimal.valueOf(3.58).compareTo(rentalAgreement.getFinalCharge()));
    }

    @Test
    public void testCase3() {
        ToolCheckout toolCheckout = new ToolCheckout();
        toolCheckout.setToolCode("CHNS");
        toolCheckout.setCheckoutDate(LocalDate.parse("2015-07-02"));
        toolCheckout.setRentalDayCount(5);
        toolCheckout.setDiscountPercent(25);

        RentalAgreement rentalAgreement = checkoutService.checkout(toolCheckout);
        System.out.println(new RentalAgreementFormatter(rentalAgreement).format());

        assertEquals("CHNS", rentalAgreement.getToolCode());
        assertEquals("Chainsaw", rentalAgreement.getToolType());
        assertEquals("Stihl", rentalAgreement.getToolBrand());
        assertEquals(5, rentalAgreement.getRentalDays());
        assertEquals(LocalDate.of(2015, Month.JULY, 2), rentalAgreement.getCheckoutDate());
        assertEquals(LocalDate.of(2015, Month.JULY, 7), rentalAgreement.getDueDate());
        assertEquals(0, BigDecimal.valueOf(1.49).compareTo(rentalAgreement.getDailyRentalCharge()));
        assertEquals(3, rentalAgreement.getChargeDays());
        assertEquals(0, BigDecimal.valueOf(4.47).compareTo(rentalAgreement.getPreDiscountCharge()));
        assertEquals(25, rentalAgreement.getDiscountPercent());
        assertEquals(0, BigDecimal.valueOf(1.12).compareTo(rentalAgreement.getDiscountAmount()));
        assertEquals(0, BigDecimal.valueOf(3.35).compareTo(rentalAgreement.getFinalCharge()));
    }

    @Test
    public void testCase4() {
        ToolCheckout toolCheckout = new ToolCheckout();
        toolCheckout.setToolCode("JAKD");
        toolCheckout.setCheckoutDate(LocalDate.parse("2015-09-03"));
        toolCheckout.setRentalDayCount(6);
        toolCheckout.setDiscountPercent(0);

        RentalAgreement rentalAgreement = checkoutService.checkout(toolCheckout);
        System.out.println(new RentalAgreementFormatter(rentalAgreement).format());

        assertEquals("JAKD", rentalAgreement.getToolCode());
        assertEquals("Jackhammer", rentalAgreement.getToolType());
        assertEquals("DeWalt", rentalAgreement.getToolBrand());
        assertEquals(6, rentalAgreement.getRentalDays());
        assertEquals(LocalDate.of(2015, Month.SEPTEMBER, 3), rentalAgreement.getCheckoutDate());
        assertEquals(LocalDate.of(2015, Month.SEPTEMBER, 9), rentalAgreement.getDueDate());
        assertEquals(0, BigDecimal.valueOf(2.99).compareTo(rentalAgreement.getDailyRentalCharge()));
        assertEquals(3, rentalAgreement.getChargeDays());
        assertEquals(0, BigDecimal.valueOf(8.97).compareTo(rentalAgreement.getPreDiscountCharge()));
        assertEquals(0, rentalAgreement.getDiscountPercent());
        assertEquals(0, BigDecimal.valueOf(0).compareTo(rentalAgreement.getDiscountAmount()));
        assertEquals(0, BigDecimal.valueOf(8.97).compareTo(rentalAgreement.getFinalCharge()));
    }

    @Test
    public void testCase5() {
        ToolCheckout toolCheckout = new ToolCheckout();
        toolCheckout.setToolCode("JAKR");
        toolCheckout.setCheckoutDate(LocalDate.parse("2015-07-02"));
        toolCheckout.setRentalDayCount(9);
        toolCheckout.setDiscountPercent(0);

        RentalAgreement rentalAgreement = checkoutService.checkout(toolCheckout);
        System.out.println(new RentalAgreementFormatter(rentalAgreement).format());

        assertEquals("JAKR" +
                "", rentalAgreement.getToolCode());
        assertEquals("Jackhammer", rentalAgreement.getToolType());
        assertEquals("Ridgid", rentalAgreement.getToolBrand());
        assertEquals(9, rentalAgreement.getRentalDays());
        assertEquals(LocalDate.of(2015, Month.JULY, 2), rentalAgreement.getCheckoutDate());
        assertEquals(LocalDate.of(2015, Month.JULY, 11), rentalAgreement.getDueDate());
        assertEquals(0, BigDecimal.valueOf(2.99).compareTo(rentalAgreement.getDailyRentalCharge()));
        assertEquals(5, rentalAgreement.getChargeDays());
        assertEquals(0, BigDecimal.valueOf(14.95).compareTo(rentalAgreement.getPreDiscountCharge()));
        assertEquals(0, rentalAgreement.getDiscountPercent());
        assertEquals(0, BigDecimal.valueOf(0).compareTo(rentalAgreement.getDiscountAmount()));
        assertEquals(0, BigDecimal.valueOf(14.95).compareTo(rentalAgreement.getFinalCharge()));
    }

    @Test
    public void testCase6() {
        ToolCheckout toolCheckout = new ToolCheckout();
        toolCheckout.setToolCode("JAKR");
        toolCheckout.setCheckoutDate(LocalDate.parse("2020-07-02"));
        toolCheckout.setRentalDayCount(4);
        toolCheckout.setDiscountPercent(50);

        RentalAgreement rentalAgreement = checkoutService.checkout(toolCheckout);
        System.out.println(new RentalAgreementFormatter(rentalAgreement).format());

        assertEquals("JAKR" +
                "", rentalAgreement.getToolCode());
        assertEquals("Jackhammer", rentalAgreement.getToolType());
        assertEquals("Ridgid", rentalAgreement.getToolBrand());
        assertEquals(4, rentalAgreement.getRentalDays());
        assertEquals(LocalDate.of(2020, Month.JULY, 2), rentalAgreement.getCheckoutDate());
        assertEquals(LocalDate.of(2020, Month.JULY, 6), rentalAgreement.getDueDate());
        assertEquals(0, BigDecimal.valueOf(2.99).compareTo(rentalAgreement.getDailyRentalCharge()));
        assertEquals(1, rentalAgreement.getChargeDays());
        assertEquals(0, BigDecimal.valueOf(2.99).compareTo(rentalAgreement.getPreDiscountCharge()));
        assertEquals(50, rentalAgreement.getDiscountPercent());
        assertEquals(0, BigDecimal.valueOf(1.50).compareTo(rentalAgreement.getDiscountAmount()));
        assertEquals(0, BigDecimal.valueOf(1.49).compareTo(rentalAgreement.getFinalCharge()));
    }
}

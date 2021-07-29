package com.example.toolapp.services;

import com.example.toolapp.entities.RentalCharge;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChargeableDateCalculatorTest {

    @Test
    public void testIndependenceDay() {
        ChargeableDateCalculator chargeableDateCalculator = new ChargeableDateCalculator();

        LocalDate observedIndependenceDay2021 = chargeableDateCalculator.getObservedIndependenceDay(2021);
        assertEquals(observedIndependenceDay2021, LocalDate.of(2021, Month.JULY, 5));

        LocalDate observedIndependenceDay2020 = chargeableDateCalculator.getObservedIndependenceDay(2020);
        assertEquals(observedIndependenceDay2020, LocalDate.of(2020, Month.JULY, 3));

        LocalDate observedIndependenceDay2019 = chargeableDateCalculator.getObservedIndependenceDay(2019);
        assertEquals(observedIndependenceDay2019, LocalDate.of(2019, Month.JULY, 4));
    }

    @Test
    public void testLaborDay() {
        ChargeableDateCalculator chargeableDateCalculator = new ChargeableDateCalculator();

        LocalDate laborDay2021 = chargeableDateCalculator.getLaborDay(2021);
        assertEquals(laborDay2021, LocalDate.of(2021, Month.SEPTEMBER, 6));

        LocalDate laborDay2020 = chargeableDateCalculator.getLaborDay(2020);
        assertEquals(laborDay2020, LocalDate.of(2020, Month.SEPTEMBER, 7));

        LocalDate laborDay2014 = chargeableDateCalculator.getLaborDay(2014);
        assertEquals(laborDay2014, LocalDate.of(2014, Month.SEPTEMBER, 1));
    }

    @Test
    public void testGetHolidaysInRange() {
        ChargeableDateCalculator chargeableDateCalculator = new ChargeableDateCalculator();
        Set<LocalDate> holidays = chargeableDateCalculator.holidaysInRange(2020, 2021);
        assertEquals(4, holidays.size());
        assertTrue(holidays.contains(chargeableDateCalculator.getObservedIndependenceDay(2020)));
        assertTrue(holidays.contains(chargeableDateCalculator.getObservedIndependenceDay(2021)));
        assertTrue(holidays.contains(chargeableDateCalculator.getLaborDay(2020)));
        assertTrue(holidays.contains(chargeableDateCalculator.getLaborDay(2021)));
    }

    @Test
    public void testNumberOfDaysWeekdayOnly() {
        RentalCharge testCharge = new RentalCharge();
        testCharge.setHolidayCharge(false);
        testCharge.setWeekdayCharge(true);
        testCharge.setWeekendCharge(false);

        ChargeableDateCalculator chargeableDateCalculator = new ChargeableDateCalculator();
        int chargeDays = chargeableDateCalculator.calculateChargeDays(
                LocalDate.of(2021, Month.JULY, 1),
                LocalDate.of(2021, Month.JULY, 6),
                testCharge);

        // 7/1 -> not chargeable (first day, start from day after checkout)
        // 7/2 -> not chargeable (holiday)
        // 7/3 -> not chargeable (weekend)
        // 7/4 -> not chargeable (weekend)
        // 7/5 -> chargeable
        // 7/6 -> chargeable

        assertEquals(2, chargeDays);
    }

    @Test
    public void testNumberOfDaysWeekdayAndWeekend() {
        RentalCharge testCharge = new RentalCharge();
        testCharge.setHolidayCharge(false);
        testCharge.setWeekdayCharge(true);
        testCharge.setWeekendCharge(true);

        ChargeableDateCalculator chargeableDateCalculator = new ChargeableDateCalculator();
        int chargeDays = chargeableDateCalculator.calculateChargeDays(
                LocalDate.of(2021, Month.JULY, 1),
                LocalDate.of(2021, Month.JULY, 6),
                testCharge);

        // 7/1 -> not chargeable (first day, start from day after checkout)
        // 7/2 -> not chargeable (holiday)
        // 7/3 -> chargeable (weekend)
        // 7/4 -> chargeable (weekend)
        // 7/5 -> chargeable
        // 7/6 -> chargeable

        assertEquals(4, chargeDays);
    }

    @Test
    public void testNumberOfDaysAllChargeable() {
        RentalCharge testCharge = new RentalCharge();
        testCharge.setHolidayCharge(true);
        testCharge.setWeekdayCharge(true);
        testCharge.setWeekendCharge(true);

        ChargeableDateCalculator chargeableDateCalculator = new ChargeableDateCalculator();
        int chargeDays = chargeableDateCalculator.calculateChargeDays(
                LocalDate.of(2021, Month.JULY, 1),
                LocalDate.of(2021, Month.JULY, 6),
                testCharge);

        // 7/1 -> not chargeable (first day, start from day after checkout)
        // 7/2 -> chargeable (holiday)
        // 7/3 -> chargeable (weekend)
        // 7/4 -> chargeable (weekend)
        // 7/5 -> chargeable
        // 7/6 -> chargeable

        assertEquals(5, chargeDays);
    }
}

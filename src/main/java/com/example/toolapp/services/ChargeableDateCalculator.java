package com.example.toolapp.services;

import com.example.toolapp.entities.RentalCharge;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

import static java.time.temporal.TemporalAdjusters.firstInMonth;

/**
 * This class handles the logic for checking which dates are chargeable.
 *
 * Note that I've hardcoded the assumption from the spec that there are only two holidays observed: labor day and
 * independence day.
 */
public class ChargeableDateCalculator {

    /**
     * Calculate the number of chargeable days in a checkout range for a given tool.
     *
     * @param checkoutDate
     * @param dueDate
     * @param rentalCharge
     * @return
     */
    public int calculateChargeDays(LocalDate checkoutDate, LocalDate dueDate, RentalCharge rentalCharge) {
        int startYear = checkoutDate.getYear();
        int endYear = dueDate.getYear();
        Set<LocalDate> holidaysInRange = holidaysInRange(startYear, endYear);

        int chargeableDays = 0;

        // From day after checkout date through due date (inclusive)
        for (LocalDate date = checkoutDate.plusDays(1); date.isBefore(dueDate.plusDays(1)); date = date.plusDays(1)) {
            if (!rentalCharge.isHolidayCharge()) {
                // No holiday charge for this tool, skip holidays.
                if (holidaysInRange.contains(date)) {
                    continue;
                }
            }

            if (!rentalCharge.isWeekendCharge()) {
                // No weekend charge for this tool, skip weekends.
                DayOfWeek dayOfWeek = date.getDayOfWeek();
                if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                    continue;
                }
            }

            if (!rentalCharge.isWeekdayCharge()) {
                // No weekday charge for this tool, skip weekdays.
                DayOfWeek dayOfWeek = date.getDayOfWeek();
                if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                    continue;
                }
            }

            chargeableDays++;
        }

        return chargeableDays;
    }

    /**
     * Get the dates for observed holidays in the given range of years.
     *
     * @param startYear
     * @param endYear
     * @return all holidays that fall in those years.
     */
    public Set<LocalDate> holidaysInRange(int startYear, int endYear) {
        Set<LocalDate> holidays = new HashSet<>();
        for (int i = startYear; i <= endYear; i++) {
            holidays.add(getObservedIndependenceDay(i));
            holidays.add(getLaborDay(i));
        }

        return holidays;
    }

    /**
     * Get the observed independence day for the year.  If it falls on a Saturday this is the Friday before.  If it
     * falls on a Sunday this is the Monday after.  Otherwise it's July 4th.
     *
     * @param year the year
     * @return the observed day for independence day that year.
     */
    public LocalDate getObservedIndependenceDay(int year) {
        LocalDate independenceDay = LocalDate.of(year, Month.JULY, 4);
        DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY) {
            return independenceDay.minusDays(1);
        }
        if (dayOfWeek == DayOfWeek.SUNDAY) {
            return independenceDay.plusDays(1);
        }
        return independenceDay;
    }

    /**
     * Find labor day for a given year.  First Monday in September.
     *
     * @param year the year
     * @return the date for labor day that year
     */
    public LocalDate getLaborDay(int year) {
        LocalDate firstOfSeptember = LocalDate.of(year, Month.SEPTEMBER, 1);
        return firstOfSeptember.with(firstInMonth(DayOfWeek.MONDAY));
    }

}

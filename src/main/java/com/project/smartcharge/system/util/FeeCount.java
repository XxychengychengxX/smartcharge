//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.project.smartcharge.system.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FeeCount {
    public FeeCount() {
    }

    public static double calculateChargingCost(Date startTime, Date endTime, boolean isFastCharging) {
        double chargingRate;
        if (isFastCharging) {
            chargingRate = 30.0;
        } else {
            chargingRate = 7.0;
        }

        int timeSlot = MyDate.getTimeSlot(startTime);
        double unitPrice = getUnitPrice(timeSlot);
        long chargingTime = calculateChargingTimeInDate(startTime, endTime);
        double chargingCost = calculateChargingCost(chargingTime, chargingRate, unitPrice);
        return chargingCost ;
    }

    public static double calculateServiceCost(Date startTime, Date endTime, boolean isFastCharging) {
        double chargingRate;
        if (isFastCharging) {
            chargingRate = 30.0;
        } else {
            chargingRate = 7.0;
        }

        double servicePrice = getServicePrice();
        long chargingTime = calculateChargingTimeInDate(startTime, endTime);
        double serviceCost = calculateServiceCost(chargingTime, servicePrice, chargingRate);
        return serviceCost;
    }

    public static double calculateTotalCost(Date startTime, Date endTime, boolean isFastCharging) {
        double chargingRate;
        if (isFastCharging) {
            chargingRate = 30.0;
        } else {
            chargingRate = 7.0;
        }

        int timeSlot = MyDate.getTimeSlot(startTime);
        double unitPrice = getUnitPrice(timeSlot);
        double servicePrice = getServicePrice();
        long chargingTime = calculateChargingTimeInDate(startTime, endTime);
        double chargingCost = calculateChargingCost(chargingTime, chargingRate, unitPrice);
        double serviceCost = calculateServiceCost(chargingTime, servicePrice, chargingRate);
        double totalCost = chargingCost + serviceCost;
        return totalCost;
    }

    private static double calculateChargingCost(long chargingTime, double chargingRate, double unitPrice) {
        double chargingHours = (double)chargingTime / 60.0;
        double chargingCost = unitPrice * chargingRate * chargingHours;
        return chargingCost;
    }

    private static double calculateServiceCost(long chargingTime, double servicePrice, double chargingRate) {
        double serviceHours = (double)chargingTime / 60.0;
        double serviceCost = servicePrice * serviceHours * chargingRate;
        return serviceCost;
    }

    private static double getUnitPrice(int timeSlot) {
        double var10000;
        switch (timeSlot) {
            case 1:
                var10000 = 0.7;
                break;
            case 2:
                var10000 = 1.0;
                break;
            default:
                var10000 = 0.4;
        }

        return var10000;
    }

    private static double getServicePrice() {
        return 0.8;
    }

    private static long calculateChargingTimeInDate(Date startTime, Date endTime) {
        long duration = endTime.getTime() - startTime.getTime();
        return TimeUnit.MILLISECONDS.toMinutes(duration);
    }

    public static String calculateChargeDurationInString(boolean chargingMode, double chargingAmount) {
        double chargingRate = chargingMode ? 30.0 : 7.0;
        double chargingTime = chargingAmount / chargingRate ;
        int hours = (int)chargingTime;
        int minutes = (int)((chargingTime - (double)hours) * 60.0);
        String timeString = String.format("%02d:%02d", hours, minutes);
        return timeString;
    }

    public static double calculateChargingAmount(boolean chargingMode, String chargingDuration) {
        double fastChargingRate = 30.0;
        double slowChargingRate = 7.0;
        String[] parts = chargingDuration.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        double totalHours = (double)hours + (double)minutes / 60.0;
        double chargingRate = chargingMode ? fastChargingRate : slowChargingRate;
        double chargingAmount = totalHours * chargingRate;
        return chargingAmount;
    }
}

package com.project.smartcharge.system.util;

import org.junit.jupiter.api.Test;

import java.util.Date;

class FeeCountTest {

    @Test
    void calculateChargingCost() {
        Date date = new Date();
        System.out.println("FeeCount.calculateChargingCost(date,\n                new Date(date" +
                ".getTime() + 30 * 60 * 1000), true) = " + FeeCount.calculateChargingCost(new Date(date.getTime()+180*60*1000),
                new Date(date.getTime() + 240 * 60 * 1000), true));
    }

    @Test
    void calculateServiceCost() {
        Date date = new Date();
        System.out.println("FeeCount.calculateServiceCost(date,new Date(date.getTime()" +
                "+60*60*1000)) = " + FeeCount.calculateServiceCost(date,
                new Date(date.getTime() + 60 * 60 * 1000), true));

    }

    @Test
    void calculateTotalCost() {
        Date date = new Date();
        System.out.println("FeeCount.calculateTotalCost(date,new Date(date.getTime()" +
                "+60*60*1000)) = " + 1);
    }
}
package com.project.smartcharge.system.util;

import org.junit.jupiter.api.Test;

import java.util.Date;

class MyDateTest {

    @Test
    void addTimeInHour() {
        String s = "1221:43";
        String s1 = "03:28";
        System.out.println("MyDate.addTimeInHourString(s,s1) = " + MyDate.addTimeInHourString(s, s1));
    }

    @Test
    void addTimeInDate() {
        String s = "2023-05-28 04:18";
        String s1 = "04:30";
        System.out.println(MyDate.addTimeInDateFormatString(s, s1));
    }

    @Test
    void getTimeInterval() {
        System.out.println("MyDate.getTimeIntervalByDate(new Date(),new Date(System.currentTimeMillis()" +
                "+30*60*1000)) = " + MyDate.getTimeIntervalByDate(new Date(),
                new Date(System.currentTimeMillis() + 310 * 60 * 1000)));
    }

    @Test
    void getNeedTimeInString() {
        System.out.println("MyDate.getNeedTimeInString(new Date()) = " + MyDate.getNeedTimeInString(new Date()));
    }

    @Test
    void getNowTimeInString() {
        System.out.println("MyDate.getNowTimeInString() = " + MyDate.getNowTimeInString());
    }

    @Test
    void getNowTimeInDate() {
        System.out.println("MyDate.getNowTimeInDate() = " + MyDate.getNowTimeInDate());
    }

    @Test
    void getNowMinAndHour() {
        System.out.println("MyDate.getNowMinAndHour() = " + MyDate.getNowMinAndHour());
    }

    @Test
    void getTokenExpireTime() {
        System.out.println("MyDate.getTokenExpireTime() = " + MyDate.getTokenExpireTime());
    }


    @Test
    void getTimeSlot() {
        System.out.println("MyDate.getTimeSlot(new Date()) = " + MyDate.getTimeSlot(new Date()));
    }
}
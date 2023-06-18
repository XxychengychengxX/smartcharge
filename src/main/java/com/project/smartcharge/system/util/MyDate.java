/**
 * @author Valar Morghulis
 * @Date 2023/5/20
 */
package com.project.smartcharge.system.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 封装可能会用到的有关日期的请求
 */
public class MyDate {

    /**
     * 通过开始时间和和持续时间，判断当前系统时间是否到达了结束时间
     *
     * @param startTime 开始时间的日期对象
     * @param duration  持续时间长度
     * @return 未到达返回true，反之返回false
     */
    public static boolean isEndTimeReached(Date startTime, long duration) {
        Date currentTime = new Date();

        Date endTime = new Date(startTime.getTime() + duration);
        return currentTime.after(endTime);
    }

    /**
     * 获取给定时间段下个时间段的开始时间
     *
     * @param date 日期对象
     * @return 下个时间段的开始时间
     */
    public static Date getStartTimeOfNextSlot(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int timeSlot = getTimeSlot(date);

        switch (timeSlot) {
            case 1:
            case 3:
                calendar.set(Calendar.HOUR_OF_DAY, 18);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                break;
            case 2:
            case 4:
            case 5:
                calendar.set(Calendar.HOUR_OF_DAY, 21);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                break;
            case 6:
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 10);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                break;
            default:
                break;
        }

        return calendar.getTime();
    }

    /**
     * 获取对应时间段的结束时间
     *
     * @param date 日期对象
     * @return 对应时间段的结束时间
     */
    public static Date getEndTimeOfSlot(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int timeSlot = getTimeSlot(date);

        switch (timeSlot) {
            case 1, 3 -> {
                calendar.set(Calendar.HOUR_OF_DAY, 15);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
            }
            case 2, 4, 5 -> {
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
            }
            case 6 -> {
                calendar.set(Calendar.HOUR_OF_DAY, 7);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
            }
            default -> {
            }
        }

        return calendar.getTime();
    }

    /**
     * 输入两个HH:mm的字符串,返回他们的时间戳绝对值相加的结果，只计算小时，如10：30+20：40=31：10
     *
     * @param time1 一个HH:mm的字符串
     * @param time2 一个HH:mm的字符串
     * @return 相加后的HH:mm类型的字符串
     */
    public static String addTimeInHourString(String time1, String time2) {
        String[] parts1 = time1.split(":");
        String[] parts2 = time2.split(":");

        int hours1 = Integer.parseInt(parts1[0]);
        int minutes1 = Integer.parseInt(parts1[1]);
        int hours2 = Integer.parseInt(parts2[0]);
        int minutes2 = Integer.parseInt(parts2[1]);

        int totalHours = hours1 + hours2;
        int totalMinutes = minutes1 + minutes2;

        int finalHours = totalHours + totalMinutes / 60;
        int finalMinutes = totalMinutes % 60;

        return String.format("%02d:%02d", finalHours, finalMinutes);
    }

    /**
     * 计算一个字符串类型的日期和一个Date类型的日期相加后的字符串，包含日期的计算结果
     *
     * @param time1 yyyy-MM-dd HH:mm格式的字符串
     * @param time2 HH:mm格式的字符串
     * @return 相应的yyyy-MM-dd HH:mm格式日期字符串
     */
    public static String addTimeInDateFormatString(String time1, String time2) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date1 = dateFormat.parse(time1);

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            Date date2 = timeFormat.parse(time2);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);

            calendar.add(Calendar.HOUR_OF_DAY, date2.getHours());
            calendar.add(Calendar.MINUTE, date2.getMinutes());

            Date sumDate = calendar.getTime();

            return dateFormat.format(sumDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 传入两个yyyy-MM-dd HH:mm类型的字符串，计算出他们的时间间隔
     *
     * @param startTime 开始时间的字符串
     * @param endTime   结束时间的字符串
     * @return %d days, %02d:%02d格式的字符串
     */
    public static String getTimeIntervalByString(String startTime, String endTime) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            Date startDate = format.parse(startTime);
            Date endDate = format.parse(endTime);

            long intervalMillis = endDate.getTime() - startDate.getTime();

            long minutes = TimeUnit.MILLISECONDS.toMinutes(intervalMillis) % 60;
            long hours = TimeUnit.MILLISECONDS.toHours(intervalMillis) % 24;
            //long days = TimeUnit.MILLISECONDS.toDays(intervalMillis);

            //return String.format("%d days, %02d:%02d", days, hours, minutes);
            return String.format("%02d:%02d", hours, minutes);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 返回两个日期所间隔的时间的时间对象
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 间隔时间的HH:mm格式的字符串
     */
    public static String getTimeIntervalByDate(Date startDate, Date endDate) {
        long milliseconds = endDate.getTime() - startDate.getTime();
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;

        long hours = minutes / 60;
        minutes %= 60;

        return hours + ":" + minutes;
    }

    /**
     * 以yyyy-MM-dd HH:mm字符串形式获取所需时间
     *
     * @param date 传入的日期对象
     * @return 所需时间的字符串
     */
    public static String getNeedTimeInString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(date);
    }

    /**
     * 以对象形式获取时间
     *
     * @param dateString 传入yyyy-MM-dd HH:mm字符串形式的日期对象
     * @return 所需时间的字符串
     */
    public static Date getNeedTimeInDate(String dateString) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.parse(dateString);
    }

    /**
     * 以yyyy-MM-dd HH:mm字符串形式获取当前时间
     *
     * @return 当前时间对应字符串形式
     */
    public static String getNowTimeInString() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(date);
    }

    /**
     * 以对象方式获取当前时间
     *
     * @return 当前时间对应日期对象
     */
    public static Date getNowTimeInDate() {
        Date date = new Date();
        return date;
    }

    //只获取当前时间的小时和分钟
    public static String getNowMinAndHour() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(date);
    }

    /**
     * 返回token中两小时后的具体时间对象
     *
     * @return 当前时间两小时后的具体时间对象
     */
    public static Date getTokenExpireTime() {
        Date date = new Date();
        Date expireTime = new Date(date.getTime() + 7200000);
        return expireTime;
    }


    /**
     * 判断给定的日期在哪个时间段内
     *
     * @param date 给定的时间起点的Date对象
     * @return 对应在哪个时间段
     */
    public static int getTimeSlot(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        if (isWithinTimeRange(hour, minute, 10, 15, 0) || isWithinTimeRange(hour, minute, 18, 21,
                0)) {
            return 2;
        } else if (isWithinTimeRange(hour, minute, 7, 10, 0)
                || isWithinTimeRange(hour, minute, 15, 18, 0)
                || isWithinTimeRange(hour, minute, 21, 23, 0)) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 判断给定的时间在哪个时间段内
     *
     * @param hour      需判断传入的小时数
     * @param minute    需判断的分钟数
     * @param startHour 小时判断区间的开始
     * @param endHour   小时区间的结束
     * @param endMinute 分钟区间的结束
     * @return 如果在startHour:startMinute~endHour:endMinute区间中返回true,反之返回false
     */
    private static boolean isWithinTimeRange(int hour, int minute, int startHour, int endHour,
                                             int endMinute) {
        if (hour > startHour && hour < endHour) {
            return true;
        } else if (hour == startHour && minute >= 0) {
            return true;
        } else
            return hour == endHour && minute <= endMinute;
    }
}


package com.gupiao.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 时间处理通用类
 */
public class DateUtils {

    public static String DATE_FORMATE1 = "yyyy-MM";
    public static String DATE_FORMATE2 = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_FORMATE3 = "yyyy-MM-dd 00:00:00";
    public static String DATE_FORMATE4 = "yyyyMMdd";
    public static String DATE_FORMATE5 = "yyyy-MM-dd";


    /**
     * 将字符串转为date
     * @param date
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date converStringToDate(String date, String format) throws ParseException {
        SimpleDateFormat format1 = new SimpleDateFormat(format);
        return format1.parse(date);
    }

    /**
     * 将date转为字符串
     * @param date
     * @param format
     * @return
     */
    public static String converDateToString(Date date, String format){
        SimpleDateFormat format1 = new SimpleDateFormat(format);
        return format1.format(date);
    }

    public static String dateAddDays(String date,String format, Long days){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        LocalDate localDate = LocalDate.parse(date,dateTimeFormatter);
        localDate = localDate.plusDays(days);
        return localDate.format(dateTimeFormatter);
    }

    /**
     * 计算两个时间相差天数 t2-t1
     * @param startDate
     * @param endDate
     * @param formate
     * @return
     * @throws ParseException
     */
    public static int daysDiff(String startDate,String endDate,String formate) throws ParseException{

        SimpleDateFormat sdf=new SimpleDateFormat(formate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(startDate));
        long time1 = cal.getTimeInMillis();

        cal.setTime(sdf.parse(endDate));
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*60*60*24);

        return Integer.parseInt(String.valueOf(between_days));

    }


















    public static void main(String[] args) {
        BigDecimal a = BigDecimal.valueOf(1.1);
        BigDecimal b = BigDecimal.valueOf(2.2);
        BigDecimal c = BigDecimal.valueOf(3.3);
        BigDecimal d = BigDecimal.valueOf(1.1);
        System.out.println(a.compareTo(b));
        System.out.println(b.compareTo(a));
        System.out.println(a.compareTo(d));

    }
}

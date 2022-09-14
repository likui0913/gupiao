package com.gupiao.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间处理通用类
 */
public class DateUtils {

    public static String DATE_FORMATE1 = "yyyy-MM";
    public static String DATE_FORMATE2 = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_FORMATE3 = "yyyy-MM-dd 00:00:00";
    public static String DATE_FORMATE4 = "yyyyMMdd";


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

}

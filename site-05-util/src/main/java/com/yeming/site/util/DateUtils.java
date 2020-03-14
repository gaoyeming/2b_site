package com.yeming.site.util;

import com.yeming.site.util.constant.AllConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Function : 时间工具类
 * Date : 2016年3月31日 下午2:38:17
 * 如果是JDK8的应用，可以使用Instant代替Date，LocalDateTime代替Calendar，
 * DateTimeFormatter代替SimpleDateFormat，
 * 官方给出的解释：simple beautiful strong immutable thread-safe
 * @author yeming.gao
 */
public class DateUtils {
    private DateUtils(){}
    /**
     * 获取当前时间
     *
     * @return Instant
     */
    public static Instant getCurrentInstant() {
        return Instant.now();
    }

    /**
     * 获取当前时间
     *
     * @return Date
     */
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * 获取当前时间并指定格式
     *
     * @return String
     */
    public static String getCurrentDateToStr(String formatStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatStr);
        LocalDateTime now = LocalDateTime.now();
        return now.format(dateTimeFormatter);
    }

    /**
     * 将Date转换为指定格式
     *
     * @return String
     */
    public static String formatDateToStr(Date date , String formatStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatStr);
        // Date转换为LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * 将日期字符串转换为Date
     *
     * @return String
     */
    public static Date formatStrToDate(String dateStr , String formatStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.parse(dateStr);
    }

    /**
     * 比较两日期大小 date
     *
     * @param d1 日期1
     * @param d2 日期2
     * @return int
     */
    public static int compare(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);

        if (c1.after(c2)) {
            return AllConstants.Common.ONE;
        } else if (c1.before(c2)) {
            return AllConstants.Common.NEGATIVE_ONE;
        } else {
            return AllConstants.Common.ZERO;
        }
    }


    public static void main(String[] args) throws ParseException {
        System.out.println(getCurrentInstant());
        System.out.println(getCurrentDate());
        System.out.println(getCurrentDateToStr("yyyy-MM-dd HH:mm:ss"));
        System.out.println(formatDateToStr(new Date(),"yyyy-MM-dd"));
        System.out.println(formatStrToDate("20190811","yyyyMMdd"));
    }

}

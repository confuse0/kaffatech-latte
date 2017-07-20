
package com.kaffatech.latte.commons.toolkit.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zhen.ling
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static final long MAX_TIME = 9999999999L;

    public static final String MS = "yyyyMMddHHmmssSSS";
    public static final String SECOND = "yyyyMMddHHmmss";
    public static final String MINUTE = "yyyyMMddHHmm";
    public static final String HOUR = "yyyyMMdd";
    public static final String DAY = "yyyyMMdd";
    public static final String MONTH = "yyyyMM";
    public static final String YEAR = "yyyy";

    public static final String FMT_MS = "yyyy-MM-dd HH:mm:ss,SSS";
    public static final String FMT_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String FMT_MINUTE = "yyyy-MM-dd HH:mm";
    public static final String FMT_HOUR = "yyyy-MM-dd HH";
    public static final String FMT_DAY = "yyyy-MM-dd";
    public static final String FMT_MONTH = "yyyy-MM";

    public static int getOffsetDays(Date start, Date end) {
        long t = end.getTime() - start.getTime();
        t = t / (3600 * 24 * 1000);
        return (int) t;
    }

    public static long getOffsetSecond(Date start, Date end) {
        long second = end.getTime() - start.getTime();
        return second / 1000L;
    }

    public static Date now() {
        return new Date();
    }

    public static Date parseToDate(String str) {
        return parseToDate(str, SECOND);
    }

    public static Date parseToDate(String str, String patterns) {
        try {
            return DateUtils.parseDate(str, patterns);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String convertFormat(String day, String pattern,
                                       String toPattern) {
        if (StringUtils.isEmpty(day)) {
            return day;
        }
        day = StringUtils.trim(day);

        Date date;
        try {
            date = parseDate(day, pattern);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }

        return format(date, toPattern);
    }

    public static long getCurSecond() {
        return System.currentTimeMillis() / 1000L;
    }

    /**
     * 获得当月字符
     *
     * @return
     */
    public static String getCurMonthStr() {
        Date date = new Date();
        return getMonthStr(date);
    }

    /**
     * 获得月字符
     *
     * @param date
     * @return
     */
    public static String getMonthStr(Date date) {
        int month = DateUtils.toCalendar(date).get(Calendar.MONTH) + 1;
        return StringUtils.leftPad(String.valueOf(month), 2, "0");
    }

    /**
     * 获得多少毫秒以前的时间
     *
     * @param ms
     * @return
     */
    public static long getBeforeTime(long ms) {
        long beforeTime = System.currentTimeMillis() - ms;
        return beforeTime;
    }

    public static String format(String format) {
        return format(new Date(), format);
    }

    public static String format(Date date) {
        return format(date, SECOND);
    }

    public static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Long parseSec(String str, String pattern)
            throws ParseException {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        return parseMs(str, pattern) / 1000L;
    }

    public static Long parseMs(String str, String pattern)
            throws ParseException {
        if (StringUtils.isEmpty(str)) {
            return null;
        }

        Date date = parseDate(str, pattern);
        return date.getTime();
    }

    /**
     * @param time    秒绝对时间
     * @param pattern
     * @return
     */
    public static String formatBySec(Long time, String pattern) {
        if (time == null) {
            return null;
        }
        return format(time * 1000L, pattern);
    }

    /**
     * @param time    毫秒绝对时间
     * @param pattern
     * @return
     */
    public static String format(Long time, String pattern) {
        if (time == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date(time));
    }

    /**
     * 将数据库中的createtime和updatetime转换成指定格式的字符串
     *
     * @param time
     * @param pattern
     * @return
     */
    public static String convertLinuxTimeToString(Long time, String pattern) {
        Date date = new Date(time * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 用于比较当前时间是否在传入的开始和结束时间范围内
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @return boolean
     */
    public static boolean compareTimeRange(Date beginDate, Date endDate) {
        Date date = new Date();
        if (date.getTime() >= beginDate.getTime() && date.getTime() <= endDate.getTime()) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out
                .println(convertFormat("2015-05-18", "yyyy-MM-dd", "yyyyMMdd"));
        System.out.println(convertFormat("15:01", "HH:mm", "HHmm"));
    }

}

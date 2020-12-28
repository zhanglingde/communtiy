/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.ling.other.common.utils;


import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

/**
 * 日期处理
 *
 * @author liujiawan
 * @email sunlightcs@gmail.com
 * @date 2018年12月6日 2016年12月21日 下午12:53:33
 */
public class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_MONTH_PATTERN = "yyyy-MM";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时间格式(yyyy-MM-dd HH:mm)
     */
    public final static String DATE_TIME_NOWFEN_PATTERN = "yyyy-MM-dd HH:mm";

    /**
     * 时间格式( HH:mm)
     */
    public final static String DATE_TIME_PATTERN_H_M = "HH:mm";

    /**
     * 时间格式( yyMM)
     */
    public final static String DATE_TIME_SHORT_PATTERN = "yyMM";

    /**
     * cron date 转化
     */
    private static final String CRON_DATE_FORMAT = "ss mm HH dd MM ? yyyy";


    /**
     * 处理时分秒
     */
    public final static SimpleDateFormat SDF_DISPOSE = new SimpleDateFormat("yyyy-MM-dd");
    public final static SimpleDateFormat SDF_MONTH_DISPOSE = new SimpleDateFormat("yyyy-MM");
    public final static SimpleDateFormat SDFALL_DISPOSE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final static SimpleDateFormat SDHALF_DISPOSE = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    public final static SimpleDateFormat SDMONTH_DISPOSE = new SimpleDateFormat("MM-dd");
    public final static SimpleDateFormat SDHOUR_DISPOSE = new SimpleDateFormat("HH:mm");
    public final static SimpleDateFormat SDDAY_DISPOSE = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public final static SimpleDateFormat SDDAY_SIM_DISPOSE = new SimpleDateFormat("MM-dd HH:mm");
    public final static SimpleDateFormat SDF_COURSE_DISPOSE = new SimpleDateFormat("MM月dd日 HH:mm");

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date 日期
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date 日期
     * @param pattern 格式，如：DateUtils.DATE_TIME_PATTERN
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 字符串转换成日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期的格式，如：DateUtils.DATE_TIME_PATTERN
     */
    public static Date stringToDate(String strDate, String pattern) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.parseLocalDateTime(strDate).toDate();
    }

    /**
     * 根据周数，获取开始日期、结束日期
     *
     * @param week 周期 0本周，-1上周，-2上上周，1下周，2下下周
     * @return 返回date[0]开始日期、date[1]结束日期
     */
    public static Date[] getWeekStartAndEnd(int week) {
        DateTime dateTime = new DateTime();
        LocalDate date = new LocalDate(dateTime.plusWeeks(week));

        date = date.dayOfWeek().withMinimumValue();
        Date beginDate = date.toDate();
        Date endDate = date.plusDays(6).toDate();
        return new Date[] {beginDate, endDate};
    }

    /**
     * 对日期的【秒】进行加/减
     *
     * @param date 日期
     * @param seconds 秒数，负数为减
     * @return 加/减几秒后的日期
     */
    public static Date addDateSeconds(Date date, int seconds) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusSeconds(seconds).toDate();
    }

    /**
     * 对日期的【分钟】进行加/减
     *
     * @param date 日期
     * @param minutes 分钟数，负数为减
     * @return 加/减几分钟后的日期
     */
    public static Date addDateMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minutes).toDate();
    }

    /**
     * 对日期的【小时】进行加/减
     *
     * @param date 日期
     * @param hours 小时数，负数为减
     * @return 加/减几小时后的日期
     */
    public static Date addDateHours(Date date, int hours) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusHours(hours).toDate();
    }

    /**
     * 对日期的【天】进行加/减
     *
     * @param date 日期
     * @param days 天数，负数为减
     * @return 加/减几天后的日期
     */
    public static Date addDateDays(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }

    /**
     * 对日期的【周】进行加/减
     *
     * @param date 日期
     * @param weeks 周数，负数为减
     * @return 加/减几周后的日期
     */
    public static Date addDateWeeks(Date date, int weeks) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusWeeks(weeks).toDate();
    }

    /**
     * 对日期的【月】进行加/减
     *
     * @param date 日期
     * @param months 月数，负数为减
     * @return 加/减几月后的日期
     */
    public static Date addDateMonths(Date date, int months) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(months).toDate();
    }

    /**
     * 对日期的【年】进行加/减
     *
     * @param date 日期
     * @param years 年数，负数为减
     * @return 加/减几年后的日期
     */
    public static Date addDateYears(Date date, int years) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusYears(years).toDate();
    }


    /**
     * 获取指定时间 上一月
     *
     * @param date
     * @return
     */
    public static Date getLastMonth(Date date) {
        Calendar c = Calendar.getInstance();

        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        return c.getTime();
    }

    /**
     * 根据指定时间 获取 年
     *
     * @param date
     * @return
     */
    public static int getYearByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int year = cal.get(Calendar.YEAR);

        return year;
    }

    /**
     * 获取 月
     *
     * @param date
     * @return
     */
    public static int getMonthByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int month = cal.get(Calendar.MONTH) + 1;

        return month;
    }


    /**
     * 获取指定年月的 第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayByYearAndMonth(int year, int month) {

        try {
            Calendar cal = Calendar.getInstance();
            // 设置年份
            cal.set(Calendar.YEAR, year);
            // 设置月份
            cal.set(Calendar.MONTH, month - 1);

            // 获取某月最小天数
            int firstDay = cal.getMinimum(Calendar.DATE);
            // 设置日历中月份的最小天数
            cal.set(Calendar.DAY_OF_MONTH, firstDay);

            Date thisfirstDay = cal.getTime();
            thisfirstDay = SDF_DISPOSE.parse(SDF_DISPOSE.format(thisfirstDay));

            return thisfirstDay;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 获取指定年月的 最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayByYearAndMonth(int year, int month) {
        try {
            Calendar cal = Calendar.getInstance();
            // 设置年份
            cal.set(Calendar.YEAR, year);
            // 设置月份
            cal.set(Calendar.MONTH, month - 1);
            // 获取某月最大天数
            int lastDay = cal.getActualMaximum(Calendar.DATE);
            // 设置日历中月份的最大天数
            cal.set(Calendar.DAY_OF_MONTH, lastDay);

            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);

            Date thislastDay = cal.getTime();

            return thislastDay;

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取指定 日期 的前几天/后几天 的 开始 00:00:00
     *
     * @param date
     * @param dayNum
     * @return
     */
    public static Date getDayBegin(Date date, int dayNum) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, dayNum);
            Date thisWeekMonday = cal.getTime();
            thisWeekMonday = SDF_DISPOSE.parse(SDF_DISPOSE.format(thisWeekMonday));

            return thisWeekMonday;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取指定 日期 的前几天/后几天 的 开始 23:59:59
     *
     * @param date
     * @param dayNum
     * @return
     */
    public static Date getDayEnd(Date date, int dayNum) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, dayNum + 1);
            Date thisWeekMonday = cal.getTime();
            thisWeekMonday = SDF_DISPOSE.parse(SDF_DISPOSE.format(thisWeekMonday));

            cal.setTime(thisWeekMonday);
            cal.add(Calendar.SECOND, -1);
            thisWeekMonday = cal.getTime();

            return thisWeekMonday;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取指定日期的前一天 开始, 从 00:00:00 开始
     *
     * @param date
     * @return
     */
    public static Date getYesterdayBegin(Date date) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, -1);
            Date thisWeekMonday = cal.getTime();
            thisWeekMonday = SDF_DISPOSE.parse(SDF_DISPOSE.format(thisWeekMonday));

            return thisWeekMonday;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取指定日期的前一天 结束, 到 23:59:59
     *
     * @param date
     * @return
     */
    public static Date getYesterdayEnd(Date date) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            Date thisWeekMonday = cal.getTime();
            thisWeekMonday = SDF_DISPOSE.parse(SDF_DISPOSE.format(thisWeekMonday));

            cal.setTime(thisWeekMonday);
            cal.add(Calendar.SECOND, -1);
            thisWeekMonday = cal.getTime();

            return thisWeekMonday;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取指定日期的当天 开始, 从 00:00:00 开始
     *
     * @param date
     * @return
     */
    public static Date getNewBegin(Date date) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            Date thisWeekMonday = cal.getTime();
            thisWeekMonday = SDF_DISPOSE.parse(SDF_DISPOSE.format(thisWeekMonday));

            return thisWeekMonday;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取指定日期的当天 结束, 到 23:59:59
     *
     * @param date
     * @return
     */
    public static Date getNewEnd(Date date) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, 1);
            Date thisWeekMonday = cal.getTime();
            thisWeekMonday = SDF_DISPOSE.parse(SDF_DISPOSE.format(thisWeekMonday));

            cal.setTime(thisWeekMonday);
            cal.add(Calendar.SECOND, -1);
            thisWeekMonday = cal.getTime();

            return thisWeekMonday;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 本周一 日期
     *
     * @param date
     * @return
     */
    public static Date getThisWeekMonday(Date date) {
        try {

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            // 获得当前日期是一个星期的第几天
            int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
            if (1 == dayWeek) {
                cal.add(Calendar.DAY_OF_MONTH, -1);
            }
            // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
            cal.setFirstDayOfWeek(Calendar.MONDAY);

            // 获得当前日期是一个星期的第几天
            int day = cal.get(Calendar.DAY_OF_WEEK);
            // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
            cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);

            Date thisWeekMonday = cal.getTime();
            thisWeekMonday = SDF_DISPOSE.parse(SDF_DISPOSE.format(thisWeekMonday));

            return thisWeekMonday;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据周一 日期 获取周日 日期
     *
     * @param thisWeekMonday
     * @return
     */
    public static Date getThisWeekSunday(Date thisWeekMonday) {
        // 星期日
        Calendar cal = Calendar.getInstance();
        cal.setTime(thisWeekMonday);
        cal.add(Calendar.DATE, 7);
        cal.add(Calendar.SECOND, -1);
        Date thisWeekSunday = cal.getTime();
        return thisWeekSunday;
    }


    /**
     * 两个时间之间相差距离多少天
     *
     * @param one 时间参数 1：
     * @param two 时间参数 2：
     * @return 相差天数
     */
    public static long getDistanceDays(Date one, Date two) {
        long days = 0;
        long time1 = one.getTime();
        long time2 = two.getTime();
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        days = (diff / (1000 * 60 * 60 * 24));
        return days;
    }

    /**
     * 判断before是否end+hour的时间是否在之后，如果在返回true,否则返回false
     *
     * @param before
     * @param end
     * @param hour
     * @return
     */
    public static boolean getTimeBjAfterHour(Date before, Date end, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(end);
        cal.add(Calendar.HOUR, hour);//
        end = cal.getTime();
        return before.after(end);
    }

    /**
     * 获取指定日期的前七天, 不包含时分秒
     *
     * @param date
     * @return
     */
    public static List<String> getDistanceDays(Date date) {
        List<String> list = new ArrayList<>();
        try {
            String yesterday01 = DateUtils.SDF_DISPOSE.format(DateUtils.getYesterdayBegin(date));
            String yesterday02 = DateUtils.SDF_DISPOSE
                            .format(DateUtils.getYesterdayBegin(DateUtils.SDF_DISPOSE.parse(yesterday01)));
            String yesterday03 = DateUtils.SDF_DISPOSE
                            .format(DateUtils.getYesterdayBegin(DateUtils.SDF_DISPOSE.parse(yesterday02)));
            String yesterday04 = DateUtils.SDF_DISPOSE
                            .format(DateUtils.getYesterdayBegin(DateUtils.SDF_DISPOSE.parse(yesterday03)));
            String yesterday05 = DateUtils.SDF_DISPOSE
                            .format(DateUtils.getYesterdayBegin(DateUtils.SDF_DISPOSE.parse(yesterday04)));
            String yesterday06 = DateUtils.SDF_DISPOSE
                            .format(DateUtils.getYesterdayBegin(DateUtils.SDF_DISPOSE.parse(yesterday05)));
            String yesterday07 = DateUtils.SDF_DISPOSE
                            .format(DateUtils.getYesterdayBegin(DateUtils.SDF_DISPOSE.parse(yesterday06)));

            list.add(yesterday01);
            list.add(yesterday02);
            list.add(yesterday03);
            list.add(yesterday04);
            list.add(yesterday05);
            list.add(yesterday06);
            list.add(yesterday07);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 获取当前日期的过去几天时间集合, 不包含时分秒(包含当天)
     *
     * @param intervals
     * @return
     */
    public static List<String> getDays(int intervals) {
        List<String> list = new ArrayList<>();
        try {
            for (int i = intervals - 1; i >= 0; i--) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - i);
                String date = DateUtils.SDF_DISPOSE.format(calendar.getTime());
                list.add(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取当前日期的过去几天时间集合, 不包含时分秒(不包含当天)
     *
     * @param intervals
     * @return
     */
    public static List<String> getLastDays(Date time, int intervals) {
        List<String> list = new ArrayList<>();
        try {
            for (int i = intervals; i > 0; i--) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(time);
                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - i);
                String date = DateUtils.SDF_DISPOSE.format(calendar.getTime());
                list.add(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 获取当前年的所有月份集合
     *
     * @param date
     * @return
     */
    public static List<String> getMonthByYear(Date date) {
        List<String> list = new ArrayList<>();
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, 0);
            calendar.set(Calendar.DATE, 1);
            while (calendar.getTime().getTime() <= date.getTime()) {
                list.add(DateUtils.SDF_MONTH_DISPOSE.format(calendar.getTime()));
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取近n年月份集合
     *
     * @param date
     * @return
     */
    public static List<String> getMonthBetweenYear(Date date, Integer year) {

        List<String> list = new ArrayList<>();
        try {
            Calendar min = Calendar.getInstance();
            min.set(Calendar.YEAR, min.get(Calendar.YEAR) - year);
            Calendar now = Calendar.getInstance();
            now.setTime(date);
            while (min.getTime().before(now.getTime())) {
                list.add(DateUtils.SDF_MONTH_DISPOSE.format(now.getTime()));
                now.set(Calendar.MONTH, now.get(Calendar.MONTH) - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Date 得到 时间戳
     *
     * @param date
     * @return
     */
    public static long dateToUnixTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startDateStr = sdf.format(date);
        long time = (sdf.parse(startDateStr, new ParsePosition(0))).getTime() / 1000;
        return time;
    }

    /**
     * String 得到 时间戳
     *
     * @param dateStr
     * @return
     */
    public static long StrToUnixTime(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = (sdf.parse(dateStr, new ParsePosition(0))).getTime() / 1000;
        return time;
    }

    /**
     * 时间戳转为指定格式的时间字符串
     *
     * @param timestamp 时间戳字符串
     * @return
     */
    public static String timestampToDateStr(Long timestamp, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
        if (StringUtils.isNotEmpty(pattern)) {
            dateFormat = new SimpleDateFormat(pattern);
        }
        if (timestamp.compareTo(2000000000L) == -1) {
            // 转成毫秒
            timestamp = timestamp * 1000;
        }
        Date date = new Date(timestamp);
        String format = format(date, DATE_TIME_PATTERN);
        return format;
    }

    // 判断选择的日期是否是今天
    public static boolean isToday(Date time) {
        return isThisTime(time, "yyyy-MM-dd");
    }

    // 判断选择的日期是否是本周
    public static boolean isThisWeek(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(time);
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        if (paramWeek == currentWeek) {
            return true;
        }
        return false;
    }


    // 判断选择的日期是否是本月
    public static boolean isThisMonth(Date time) {
        return isThisTime(time, "yyyy-MM");
    }

    public static boolean isThisTime(Date time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(time);// 参数时间
        String now = sdf.format(new Date());// 当前时间
        if (param.equals(now)) {
            return true;
        }
        return false;
    }

    public static Integer getHours(Date now) {
        Instant instant = now.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalTime localTime = LocalDateTime.ofInstant(instant, zone).toLocalTime();
        return localTime.getHour();
    }

    /**
     * 获取当前时间的所有时间段
     *
     * @param hours
     * @return
     */
    public static List<String> getHoursList(Integer hours) {

        List<String> timeList = new ArrayList<>();

        for (int i = 0; i <= hours; i++) {
            StringBuilder time = new StringBuilder();
            if (i < 10) {
                time.append("0").append(i).append(":00 - ").append("0").append(i).append(":59");
            } else {
                time.append(i).append(":00 - ").append(i).append(":59");
            }
            timeList.add(time.toString());
        }
        return timeList;
    }

    /**
     * 秒数转为时分秒
     * 
     * @param second
     * @return
     */
    public static String secondToTimeStr(Long second) {
        if (second == null) {
            return "";
        }
        // 转换小时数
        long hours = second / 3600;
        // 剩余秒数
        second = second % 3600;
        // 转换分钟
        long minutes = second / 60;
        // 剩余秒数
        second = second % 60;
        return hours + "小时" + minutes + "分" + second + "秒";
    }

    /**
     * 获取近时间差
     *
     * @param date
     * @return
     */
    public static String getFriendlyTime(Date date) {
        Calendar now = Calendar.getInstance();
        Calendar edit = Calendar.getInstance();
        edit.setTime(date);

        // 当前时间单位
        int cYear = now.get(Calendar.YEAR);
        int cMonth = now.get(Calendar.MONTH);
        int cDate = now.get(Calendar.DAY_OF_MONTH);
        int cHour = now.get(Calendar.HOUR_OF_DAY);
        int cMinuter = now.get(Calendar.MINUTE);

        // 发布时间单位
        int sYear = edit.get(Calendar.YEAR);
        int sMonth = edit.get(Calendar.MONTH);
        int sDate = edit.get(Calendar.DAY_OF_MONTH);
        int sHour = edit.get(Calendar.HOUR_OF_DAY);
        int sMinuter = edit.get(Calendar.MINUTE);

        // 计算当前时间差
        if (cYear > sYear) {
            return SDF_DISPOSE.format(date);
        } else if (cMonth > sMonth) {
            return SDMONTH_DISPOSE.format(date);
        } else if (cDate > sDate) {
            return (cDate - sDate) > 7 ? SDMONTH_DISPOSE.format(date)
                            : (cDate - sDate) > 1 ? (cDate - sDate) + "天前" : "昨天 " + SDHOUR_DISPOSE.format(date);
        } else if (cHour > sHour) {
            return (cHour - sHour) + "小时前";
        } else if (cMinuter >= sMinuter) {
            return (cMinuter - sMinuter) < 1 ? "刚刚" : (cMinuter - sMinuter) + "分钟前";
        }
        return "";
    }

    /**
     * 根据生日获取年龄
     * 
     * @param birthday
     * @return
     */
    public static int getAgeByBirth(Date birthday) {
        // Calendar：日历
        /* 从Calendar对象中或得一个Date对象 */
        Calendar cal = Calendar.getInstance();
        /* 把出生日期放入Calendar类型的bir对象中，进行Calendar和Date类型进行转换 */
        Calendar bir = Calendar.getInstance();
        bir.setTime(birthday);
        /* 如果生日大于当前日期，则抛出异常：出生日期不能大于当前日期 */
        if (cal.before(birthday)) {
            throw new IllegalArgumentException("The birthday is before Now,It's unbelievable");
        }
        /* 取出当前年月日 */
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayNow = cal.get(Calendar.DAY_OF_MONTH);
        /* 取出出生年月日 */
        int yearBirth = bir.get(Calendar.YEAR);
        int monthBirth = bir.get(Calendar.MONTH);
        int dayBirth = bir.get(Calendar.DAY_OF_MONTH);
        /* 大概年龄是当前年减去出生年 */
        int age = yearNow - yearBirth;
        /* 如果出当前月小与出生月，或者当前月等于出生月但是当前日小于出生日，那么年龄age就减一岁 */
        if (monthNow < monthBirth || (monthNow == monthBirth && dayNow < dayBirth)) {
            age--;
        }
        return age;
    }

    /**
     * 获取连续签到时间
     * 
     * @param signInList
     * @return
     * @throws ParseException
     */
    public static int getContinuousSignInDay(List<Date> signInList) {
        // continuousDay 连续签到数
        int continuousDay = 1;
        try {
            boolean todaySignIn = false;
            if (signInList != null && signInList.size() > 0) {
                // today 当天日期
                Date today = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String todays = sdf.format(today);

                for (int i = 0; i < signInList.size(); i++) {
                    int intervalDay = distanceDay(sdf.parse(todays), DateUtils.getDayBegin(signInList.get(i), 0));
                    // 当天签到
                    if (intervalDay == 0 && i == 0) {
                        todaySignIn = true;
                    } else if (intervalDay == continuousDay) {
                        continuousDay++;
                    } else {
                        // 不连续，终止判断
                        break;
                    }
                }
            }
            if (!todaySignIn) {
                continuousDay--;
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return continuousDay;
    }

    // 判断当天日期 与以往签到日期相隔天数
    private static int distanceDay(Date largeDay, Date smallDay) {
        int day = (int) ((largeDay.getTime() - smallDay.getTime()) / (1000 * 60 * 60 * 24));
        return day;
    }

    public static void main(String[] args) {
        // yyyy-MM-dd HH:mm:ss
        try {
            // 测试星期
            Date date = new Date();
            System.out.println(LocalTime.now().getHour());
            Date thisWeekMonday = getThisWeekMonday(date);
            Date thisWeekSunday = getThisWeekSunday(thisWeekMonday);

            // System.out.println("本周一" + SDFALL_DISPOSE.format(thisWeekMonday));
            // System.out.println("本周日" + SDFALL_DISPOSE.format(thisWeekSunday));

            // 测试月份
            Date firstDayMonth = getFirstDayByYearAndMonth(getYearByDate(date), getMonthByDate(date));
            // System.out.println("本月开始" + SDFALL_DISPOSE.format(firstDayMonth));
            Date lastDayMonth = getLastDayByYearAndMonth(getYearByDate(date), getMonthByDate(date));
            // System.out.println("本月结束" + SDFALL_DISPOSE.format(lastDayMonth));

            // System.out.println("------------------------------------------------------------");
            Date dayBegin = getDayBegin(new Date(), -1);
            // System.out.println(dayBegin);
            // System.out.println(SDFALL_DISPOSE.format(dayBegin));
            Date dayEnd = getDayEnd(new Date(), -1);
            // System.out.println(dayEnd);
            // System.out.println(SDFALL_DISPOSE.format(dayEnd));

            Map<String, Object> allDataMap = new HashMap<>(16);
            allDataMap.put("beginTime", dayBegin);

            // System.out.println(allDataMap.get("beginTime"));
            // System.out.println(SDF_DISPOSE.format(allDataMap.get("beginTime")));
            // System.out.println("2019-07-20".length());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /***
     *
     * @param date 时间
     * @return cron类型的日期
     */
    public static String getCron(final Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        String formatTimeStr = "";
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /***
     *
     * @param cron Quartz cron的类型的日期
     * @return Date日期
     */

    public static Date getDate(final String cron) {


        if (cron == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        Date date = null;
        try {
            date = sdf.parse(cron);
        } catch (ParseException e) {
            return null;// 此处缺少异常处理,自己根据需要添加
        }
        return date;
    }

}

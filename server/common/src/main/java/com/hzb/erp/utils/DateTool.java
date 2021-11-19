package com.hzb.erp.utils;

import cn.hutool.core.date.ChineseDate;
import com.hzb.erp.common.enums.WeekdaysEnum;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

public class DateTool {

    /**
     * 设置时间格式
     */
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//    public static void main(String[] args) {
//        // 获取当前时间
//        Date date = new Date();
//        startWeek(date);
//        startMonth(date);
//        startYear(date);
//    }

    /**
     * 日期的加减方法
     * 用于在当前的天或者小时或者分钟或者月份的基础上加上或者减去若干小时，分钟，日，月
     *
     * @param currentDay 当前月份的某一天
     * @param day        (Calendar.DATE 天 Calendar.HOUR 小时 Calendar.MINUTE 分钟 Calendar.MONTH 月)需要加上的天数或者需要减去的天数，
     *                   例如：加上10天：(Calendar.DATE,10）减去十天：(Calendar.DATE,-10)
     * @return 返回加上或者减去的那个日期
     * Date dateResult = test.dayAddAndSub(Calendar.DATE, -1); //一个月中的某天
     * //Date dateResult = test.dayAddAndSub(Calendar.HOUR , -4); //小时
     * //Date dateResult = test.dayAddAndSub(Calendar.MINUTE , -60); //分钟
     * //Date dateResult = test.dayAddAndSub(Calendar.MONTH , -1); //月
     */
    public static Date dayAddAndSub(int currentDay, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(currentDay, day);
        Date startDate = calendar.getTime();
        return startDate;
    }

    // 获取本周开始时间
    public static void startWeek(Date date) {
        // 使用Calendar类进行时间的计算
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会计算到下一周去。
        // dayWeek值（1、2、3...）对应周日，周一，周二...
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayWeek == 1) {
            dayWeek = 7;
        } else {
            dayWeek -= 1;
        }
        System.out.println("前时间是本周的第几天:" + dayWeek); // 输出要当前时间是本周的第几天
        // 计算本周开始的时间
        cal.add(Calendar.DAY_OF_MONTH, 1 - dayWeek);
        Date startDate = cal.getTime();
        System.out.println("本周开始时间（周一）：" + sdf.format(startDate) + "==当前时间：" + sdf.format(date));
    }

    // 获取本月的开始日期
    public static LocalDate firstDayOfMonth(LocalDate localDate) {
        if (localDate == null) {
            localDate = LocalDate.now();
        }
        return localDate.with(TemporalAdjusters.firstDayOfMonth());
    }

    // 获取本月的结束日期
    public static LocalDate lastDayOfMonth(LocalDate localDate) {
        if (localDate == null) {
            localDate = LocalDate.now();
        }
        return localDate.with(TemporalAdjusters.lastDayOfMonth());
    }

    // 获取本年的开始日期
    public static LocalDate firstDayOfYear(LocalDate localDate) {
        if (localDate == null) {
            localDate = LocalDate.now();
        }
        return localDate.with(TemporalAdjusters.firstDayOfYear());
    }

    // 获取本年的结束日期
    public static LocalDate lastDayOfYear(LocalDate localDate) {
        if (localDate == null) {
            localDate = LocalDate.now();
        }
        return localDate.with(TemporalAdjusters.lastDayOfYear());
    }

    /**
     * 获取某一段时间内的每一天的日期 含首尾
     *
     * @param start
     * @param dEnd
     * @return
     */
    public static List<Date> findEveryDates(Date start, Date dEnd) {
        List<Date> res = new ArrayList<>();
        res.add(start);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(start);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            res.add(calBegin.getTime());
        }
        return res;
    }

    /**
     * 获取某一段时间内的每一天的日期 LocalDate版 含首尾
     *
     * @param start
     * @param end
     * @return
     */
    public static List<LocalDate> findEveryDates(LocalDate start, LocalDate end) {
        List<LocalDate> res = new ArrayList<>();
        res.add(start);
        while (end.isAfter(start)) {
            start = start.plusDays(1L);
            res.add(start);
        }
        return res;
    }

    /**
     * 通过生日获取年龄 周岁
     */
    public static Integer getAgeByBirthday(LocalDate birthday) {
        if (birthday == null) {
            return null;
        }
        LocalDate now = LocalDate.now();
        Period between = Period.between(birthday, now);
        return between.getYears();
    }

    /**
     * 判断是不是中国法定节假日 包含以下日期:
     * 一、元旦：1月1日至3日
     * 二、春节： 7天
     * 三、清明节：无
     * 四、劳动节：5月1日至5日
     * 五、端午节：无
     * 六、中秋节：无
     * 七、国庆节：10月1日至5日
     */
    public static Boolean isHoliday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int mon = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DATE);

        List<int[]> normalHoliday = new ArrayList<>();
        // 元旦
        normalHoliday.add(new int[]{1, 1});
        // 劳动节
        normalHoliday.add(new int[]{5, 1});
        normalHoliday.add(new int[]{5, 2});
        normalHoliday.add(new int[]{5, 3});
        normalHoliday.add(new int[]{5, 4});
        normalHoliday.add(new int[]{5, 5});
        // 国庆节
        normalHoliday.add(new int[]{10, 1});
        normalHoliday.add(new int[]{10, 2});
        normalHoliday.add(new int[]{10, 3});
        normalHoliday.add(new int[]{10, 4});
        normalHoliday.add(new int[]{10, 5});

        for (int[] normalItem : normalHoliday) {
            if (mon == normalItem[0] && day == normalItem[1]) {
                return true;
            }
        }

        cal.set(Calendar.HOUR_OF_DAY, +24);
        for (int i = 0; i < 7; i++) {
            ChineseDate theDate = new ChineseDate(cal.getTime());
            String dateName = theDate.getFestivals();
            cal.set(Calendar.HOUR_OF_DAY, -24);
            if (dateName.contains("春节")) {
                System.out.println("是春节");
                return true;
            }
        }
        return false;

    }

    /**
     * 日期转换 LocalDate -> Date
     */
    public static Date localDateToDate(LocalDate ldate) {
        return Date.from(ldate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 星期里的 1,2,3转行为一,二,三
     */
    public static String transWeekdayText(String weekdays) {
        if (weekdays == null) {
            return "";
        }
        List<String> list = Arrays.asList(weekdays.split(","));
        list = list.stream().distinct().collect(Collectors.toList());
        Collections.sort(list);

        if (list.size() == 7) {
            return "每天";
        }
        List<String> res = new ArrayList<>();
        for (String d : list) {
            WeekdaysEnum byCode = EnumTools.getByCode(Integer.parseInt(d), WeekdaysEnum.class);
            if (byCode != null) {
                res.add(byCode.getDist());
            }
        }
        return StringUtils.join(res, ",");
    }

    /**
    * 获取日期范围
    * */
    public static LocalDate[] startAndEndDateByRange(String rangeName) {
        LocalDate startData = null;
        LocalDate endData = null;
        LocalDate theDay = LocalDate.now();
        if ("本周".equals(rangeName)) {
            startData = theDay.with(DayOfWeek.MONDAY);
            endData = theDay.with(DayOfWeek.SUNDAY);
        } else if ("上周".equals(rangeName)) {
            theDay = theDay.minusWeeks(1L);
            startData = theDay.with(DayOfWeek.MONDAY);
            endData = theDay.with(DayOfWeek.SUNDAY);
        } else if ("本月".equals(rangeName)) {
            startData = theDay.with(TemporalAdjusters.firstDayOfMonth());
            endData = theDay.with(TemporalAdjusters.lastDayOfMonth());
        } else if ("上个月".equals(rangeName)) {
            theDay = theDay.minusMonths(1L);
            startData = theDay.with(TemporalAdjusters.firstDayOfMonth());
            endData = theDay.with(TemporalAdjusters.lastDayOfMonth());
        } else if ("下个月".equals(rangeName)) {
            theDay = theDay.plusMonths(1L);
            startData = theDay.with(TemporalAdjusters.firstDayOfMonth());
            endData = theDay.with(TemporalAdjusters.lastDayOfMonth());
        } else if ("本季度".equals(rangeName)) {

            Month month = theDay.getMonth();

            Month firstMonthOfQuarter = month.firstMonthOfQuarter();
            Month endMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 2);

            startData = LocalDate.of(theDay.getYear(), firstMonthOfQuarter, 1);
            endData = LocalDate.of(theDay.getYear(), endMonthOfQuarter, endMonthOfQuarter.length(theDay.isLeapYear()));

        } else if ("本年".equals(rangeName)) {
            startData = theDay.with(TemporalAdjusters.firstDayOfYear());
            endData = theDay.with(TemporalAdjusters.lastDayOfYear());
        }
        return new LocalDate[]{startData, endData};
    }
}

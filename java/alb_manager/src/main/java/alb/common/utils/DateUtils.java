package alb.common.utils;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

/**
 * Time tool class
 *
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static final String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * Gets the currentDateType of date
     *
     * @return Date() The current date
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * Get the current date, The default format isyyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Date path That year/month/day Such as2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * Date path That year/month/day Such as20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * The date string is converted to a date format
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Obtain the server startup time
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * Calculate two time differences
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // Get the millisecond time difference between the two times
        long diff = endDate.getTime() - nowDate.getTime();
        // Calculate the difference of days
        long day = diff / nd;
        // Calculate the difference in hours
        long hour = diff % nd / nh;
        // Calculate the difference in minutes
        long min = diff % nd % nh / nm;
        // Calculate the difference in seconds//The output
        // long sec = diff % nd % nh % nm / ns;
        return day + "day" + hour + "hours" + min + "minutes";
    }

    /**
     * @Date: 2020/6/6 10:35
     * @Description:The format time is specified appoint format
     */
    public static String formatDateToAppoint(Date date, Integer appoint) {
        StringBuilder resultDate = new StringBuilder();
        if (appoint == 1) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy·MM·dd");
            resultDate.append(dateFormat.format(date));
        }
        if (appoint == 2) {
            DateFormat dateFormat = new SimpleDateFormat("yyyyyearsMMmonthddday");
            resultDate.append(dateFormat.format(date));
        }
        if (appoint == 3) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            resultDate.append(dateFormat.format(date));
        }
        if (appoint == 4) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
            resultDate.append(dateFormat.format(date));
        }
        if (appoint == 5) {
            DateFormat dateFormat = new SimpleDateFormat("yyyyyearsMMmonthddday HH:00");
            resultDate.append(dateFormat.format(date));
        }
        if (appoint == 6) {
            DateFormat dateFormat = new SimpleDateFormat("yyyyyearsMMmonthddday HH:mm:ss");
            resultDate.append(dateFormat.format(date));
        }
        if (appoint == 7) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            resultDate.append(dateFormat.format(date));
        }
        return resultDate.toString();
    }

    /**
     * To calculate2The date difference between  How many years month day difference
     * Such as:2011-02-02 to  2017-03-02 Difference between 6years,1months,0day
     *
     * @param startDate YYYY-MM-DD
     * @param endDate   YYYY-MM-DD
     * @return years, month, day For example, 1,1,1
     */
    public static String dayComparePrecise(Date startDate, Date endDate) {
        String fromDate = formatDateToAppoint(startDate, 7);
        String toDate = formatDateToAppoint(endDate, 7);
        Period period = Period.between(LocalDate.parse(fromDate), LocalDate.parse(toDate));

        StringBuilder sb = new StringBuilder();
        if (period.getYears() > 0) {
            sb.append(period.getYears()).append("years");
        }
        if (period.getMonths() > 0) {
            sb.append(period.getMonths()).append("month");
        }
        if (period.getDays() > 0) {
            sb.append(period.getDays()).append("day");
        }
        return sb.toString();
    }


    /**
     * Gets the month of nearly a year
     *
     * @return
     */
    public static List<String> getInitMonthMapWithZero() {
        List<String> list = new ArrayList<String>();
        Calendar c = Calendar.getInstance();
        for (int i = 0; i < 12; i++) {
            int k = c.get(Calendar.YEAR);
            int j = c.get(Calendar.MONTH) + 1 - i;
            String date = "";
            if (j >= 1) {
                date = k + "-" + (j >= 10 ? "" : "0") + j;
            } else {
                int p = 11 - i;//Number of remaining cycles
                int m = c.get(Calendar.YEAR) - 1;
                int n = c.get(Calendar.MONTH) + 2 + p;
                date = m + "-" + (n >= 10 ? "" : "0") + n;
            }
            list.add(date);
        }
        Collections.reverse(list);
        return list;
    }

    private static  Date getDateAdd(int days){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -days);
        return c.getTime();
    }

    /**
     * To get close toNThe date of the day
     * @param days
     * @return
     */
    public static  List<String> getDaysBetween(int days){ //Date of last few days
        List<String> dayss = new ArrayList<>();
        Calendar start = Calendar.getInstance();
        start.setTime(getDateAdd(days));
        long startTIme = start.getTimeInMillis();
        Calendar end = Calendar.getInstance();
        end.setTime(new Date());
        Long endTime = end.getTimeInMillis();
        Long oneDay = 1000 * 60 * 60 * 24l;
        Long time = startTIme;
        while (time <= endTime) {
            Date d = new Date(time);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//            System.out.println(df.format(d));
            dayss.add(df.format(d));
            time += oneDay;
        }
        return dayss;
    }


    /**
     * According to the returnedweekKeyThe day of the week
     * @param weekKey
     * @return
     */
    public static String getWeekName(Integer weekKey){
        if (weekKey == 1){
            return "Sunday";
        }
        if (weekKey == 2){
            return "Monday";
        }if (weekKey == 3){
            return "Tuesday";
        }if (weekKey == 4){
            return "Wednesday";
        }if (weekKey == 5){
            return "Thursday";
        }if (weekKey == 6){
            return "Friday";
        }if (weekKey == 7){
            return "Saturday";
        }
        return null;
    }

    /**
     *  Create By Renbowen
     *  @Date: 2020/8/18 21:20
     *  @Description:Conversion time
     */
    public static String convertToTimeString(Date time) {
        Integer weekKey = DateUtil.dayOfWeek(time);
        // The current time
        Date nowDate = DateUtil.date();

        StringBuilder result = new StringBuilder();
        // If the current time is greater than the time required to convert
        if (nowDate.getTime() > time.getTime()){
            // Maximum time today
            Date todayMaxDate = DateUtil.endOfDay(nowDate);
            // Calculate the difference in seconds between the two times
            long betweenSecond = DateUtil.between(time, nowDate, DateUnit.SECOND);
            // The number of seconds left between the current time and today
            long maxBetweenSecond = DateUtil.between(time,todayMaxDate,DateUnit.SECOND);
            // The number of seconds left today
            long todayBetweenSecond = DateUtil.between(nowDate,todayMaxDate,DateUnit.SECOND);
            // If the seconds are less than a year
            if (betweenSecond < 31536000){
                // If the two times are different Less than The number of seconds left today
                if (todayBetweenSecond > maxBetweenSecond){
                    // If it's less than a minute
                    if (betweenSecond < 60){
                        result.append("just").append("(").append(getWeekName(weekKey)).append(")");
                    }else if (betweenSecond < 3600){
                        result.append(betweenSecond / 60).append("Minutes ago").append("(").append(getWeekName(weekKey)).append(")");
                    }else{
                        result.append(betweenSecond/3600).append("Hours before").append("(").append(getWeekName(weekKey)).append(")");
                    }
                }else {
                    // Gets the number of days between the two times  This method is accurate to the second under86400Seconds don't count as days
                    long betweenDay = compareDays(time,nowDate);
                    if (betweenDay == 1){
                        result.append("yesterday").append("(").append(getWeekName(weekKey)).append(")").append(DateUtil.format(time,"HH:mm:ss"));
                    }
                    else if (betweenDay == 2){
                        result.append("The day before yesterday").append("(").append(getWeekName(weekKey)).append(")").append(DateUtil.format(time,"HH:mm:ss"));
                    }
                    else if (betweenDay == 3){
                        result.append(betweenDay).append("Days ago,").append("(").append(getWeekName(weekKey)).append(")").append(DateUtil.format(time,"HH:mm:ss"));
                    }else {
                        result.append(DateUtil.format(time,"yyyy-MM-dd HH:mm:ss")).append("(").append(getWeekName(weekKey)).append(")");
                    }
                }
            }else {
                result.append(DateUtil.format(time,"yyyy-MM-dd HH:mm:ss")).append("(").append(getWeekName(weekKey)).append(")");
            }
        }
        // If the current time is less than or equal to the conversion time
        else {
            // Gets the number of days between the two times  This method is accurate to the second under86400Seconds don't count as days
            long betweenDay = compareDays(nowDate,time);
            if (betweenDay == 0){
                result.append("today").append("(").append(getWeekName(weekKey)).append(")").append(DateUtil.format(time,"HH:mm:ss"));
            }
            else if (betweenDay == 1){
                result.append("tomorrow").append("(").append(getWeekName(weekKey)).append(")").append(DateUtil.format(time,"HH:mm:ss"));
            }
            else if (betweenDay == 2){
                result.append("The day after tomorrow").append("(").append(getWeekName(weekKey)).append(")").append(DateUtil.format(time,"HH:mm:ss"));
            }else {
                result.append(DateUtil.format(time,"yyyy-MM-dd HH:mm:ss")).append("(").append(getWeekName(weekKey)).append(")");
            }
        }
        return result.toString();
    }


    /**
     *  @Date: 2020/8/18 21:10
     *  @Description: Calculate how many days apart the two dates are
     */
    public static Integer compareDays(Date date1, Date date2) {
        int day = 0;
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        int day1 = calendar1.get(Calendar.DAY_OF_YEAR);
        int day2 = calendar2.get(Calendar.DAY_OF_YEAR);
        int year1 = calendar1.get(Calendar.YEAR);
        int year2 = calendar2.get(Calendar.YEAR);
        if(year1 > year2) {
            int tempyear = year1;
            int tempday = day1;
            day1 = day2;
            day2 = tempday;
            year1 = year2;
            year2 = tempyear;
        }
        if (year1 == year2) {
            day = (day2-day1);
        } else {
            int DayCount = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    DayCount += 366;
                }else {
                    DayCount += 365;
                }
            }
            day = DayCount+(day2-day1);
        }
        return day;
    }

}

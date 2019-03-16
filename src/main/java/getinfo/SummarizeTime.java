package getinfo;

import java.time.*;
import java.time.LocalDate;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SummarizeTime {

    private static DateTimeFormatter dayTimePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("HH:mm:ss");

    /*
     *
     * SUM METHODS
     *
     */

    public static Integer adminTimesDurationTotal(String HlstatsURL, LocalDate startDay, LocalDate endDay) {

        List<LocalDateTime> adminTimes = getTimesListFromPeriod(HlstatsURL, startDay, endDay);

        Integer sumDuration = 0;
        if (!adminTimes.isEmpty()) {
        for (LocalDateTime t : adminTimes) {
            LocalTime at = t.toLocalTime();
//            int hourInteger = at.getHour();
//            int minuteInteger = at.getMinute();
//            int secondsInteger = at.getSecond();
//            //            String[] splitTimes = t.format(timePattern).split(":");
////
////            int hourInteger = Integer.parseInt(splitTimes[0]);
////            int minuteInteger = Integer.parseInt(splitTimes[1]);
////            int secondsInteger = Integer.parseInt(splitTimes[2]);
////
//            int hourToSeconds = hourInteger * 3600;
//            int minuteToSeconds = minuteInteger * 60;
////
//            sumDuration += secondsInteger + hourToSeconds + minuteToSeconds;
            sumDuration += at.toSecondOfDay();
        }
        } else {
            return 0;
        }
        return sumDuration;
    }

    public static String sumTimeString(String HlstatsURL, LocalDate startDay, LocalDate endDay) {

        int sum1 = adminTimesDurationTotal(HlstatsURL, startDay, endDay);

        if (sum1 > 0)
        {
            int days = sum1 / 86400;
            int hours = (sum1 % 86400) / 3600;
            int minutes = (sum1 % 3600) / 60;
            int seconds = sum1 % 60;

            String daysTime = (days + "d");
            String times = (hours + "h" + " " + minutes + "m" + " " + seconds + "s");
            String sumTime = daysTime + " " + times;

            return sumTime;
        }
        else {
            String sumTime = "The player hasn't played for the last 28 days or the player ID is wrong";

            return sumTime;
        }
    }

    /*
     *
     * ACQUIRED DATA LISTS METHODS
     *
     */

    private static List<LocalDateTime> getAdminDateTimes(String HlstatsURL) {
        List<LocalDate> adminDates = AdminTimeCollector.collectDates(HlstatsURL);
        List<LocalTime> adminTimes = AdminTimeCollector.collectTimes(HlstatsURL);

        List<LocalDateTime> adminDateTimes = new ArrayList<>();
        if(!adminDates.isEmpty())
        for (int i=0; i<adminDates.size(); i++) {
            adminDateTimes.add(LocalDateTime.of(adminDates.get(i), adminTimes.get(i)));
        }
        return adminDateTimes;
    }

    private static List<LocalDateTime> cutAdminDateTimesToPeriod(String HlstatsURL, LocalDate startDay, LocalDate endDay) {
        List<LocalDateTime> periodTimeDates = new ArrayList<>();
        List<LocalDateTime> adminDayTimes = getAdminDateTimes(HlstatsURL);

        Collections.reverse(adminDayTimes);
        for (LocalDateTime adt : adminDayTimes) {
            if (adt.toLocalDate().isAfter(startDay.minusDays(1)) && adt.toLocalDate().isBefore(endDay.plusDays(1))) {
                periodTimeDates.add(adt);
            }
        }
        return periodTimeDates;
    }

    private static List<LocalDate> getDaysListFromPeriod(LocalDate startDay, LocalDate endDay) {
        List<LocalDate> daysList = new ArrayList<>();
        LocalDate day = startDay;

        while (day.isBefore(endDay.plusDays(1))) {
            daysList.add(day);
            day = day.plusDays(1);
        }
        return daysList;
    }

    /*
     *
     * ACQUIRE FINAL DATA LISTS
     *
     */

    public static List<LocalDateTime> getTimesListFromPeriod(String HlstatsURL, LocalDate startDay, LocalDate endDay) {

        List<LocalDate> daysPeriod = getDaysListFromPeriod(startDay, endDay);
        List<LocalDateTime> adminTimeDates = cutAdminDateTimesToPeriod(HlstatsURL, startDay, endDay);
        List<LocalDateTime> fullAdminDateTime = new ArrayList<>();


        int count = 0;
        for (LocalDate dp : daysPeriod) {
            if (!adminTimeDates.isEmpty()) {
            if (dp.equals(adminTimeDates.get(count).toLocalDate())) {
                fullAdminDateTime.add(adminTimeDates.get(count));
                if (count!=adminTimeDates.size()-1) {
                    count += 1;
                }
            } else {
                LocalDateTime minimalAdminDateTime = LocalDateTime.of(dp, LocalTime.MIN);
                fullAdminDateTime.add(minimalAdminDateTime);
            }
            } else if (adminTimeDates.isEmpty() || adminTimeDates.size()==0){
                LocalDateTime minimalAdminDateTime = LocalDateTime.of(dp, LocalTime.MIN);
                fullAdminDateTime.add(minimalAdminDateTime);
            }
        }


        return fullAdminDateTime;
    }
}
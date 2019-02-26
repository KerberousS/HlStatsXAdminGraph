package getinfo;

import org.joda.time.format.DateTimeFormat;

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

    public static Duration adminTimesDurationTotal(String HlstatsURL, LocalDate startDay, LocalDate endDay) {

        List<Duration> adminTimes = getTimesListFromPeriod(HlstatsURL, startDay, endDay);
        Duration sumDuration = Duration.ofSeconds(0);

        for (Duration t : adminTimes) {
            sumDuration.plusSeconds(t.toSeconds());
            //            String[] splitTimes = t.format(timePattern).split(":");
//
//            int hourInteger = Integer.parseInt(splitTimes[0]);
//            int minuteInteger = Integer.parseInt(splitTimes[1]);
//            int secondsInteger = Integer.parseInt(splitTimes[2]);
//
//            int hourToSeconds = hourInteger * 3600;
//            int minuteToSeconds = minuteInteger * 60;
//
//            sum += secondsInteger + hourToSeconds + minuteToSeconds;
        }
        return sumDuration;
    }

    public static String durationToLocalTime(Duration duration) {

        String timeString = LocalTime.MIDNIGHT.plus(duration).format(timePattern);

        return timeString;
    }

    /*
     *
     * ACQUIRED DATA LISTS METHODS
     *
     */

    private static List<Duration> getAdminDaysListFromPeriod(String HlstatsURL, LocalDate startDay, LocalDate endDay) {
        List<LocalDate> adminDates = AdminTimeCollector.collectDates(HlstatsURL);
        List<Duration> adminTimes = AdminTimeCollector.collectTimes(HlstatsURL);
        Collections.reverse(adminDates);
        Collections.reverse(adminTimes);

        for (int i=0; i<adminDates.size(); i++) {
            LocalDate day = adminDates.get(i);
            Duration time = adminTimes.get(i);
            if (day.isAfter(startDay.minusDays(-1)) && day.isBefore(endDay.plusDays(1))) {

            }
        }
    }

    public static List<LocalDate> getDaysListFromPeriod(LocalDate startDay, LocalDate endDay) {
        List<LocalDate> daysList = new ArrayList<>();
        LocalDate day = startDay;

        while (day.isBefore(endDay)) {
            daysList.add(day);
            day = day.plusDays(1);
        }
        return daysList;
    }

    public static List<Duration> getTimesListFromPeriod(String HlstatsURL, LocalDate startDay, LocalDate endDay) {

        List<LocalDate> daysPeriod = getDaysListFromPeriod(startDay, endDay);
        List<LocalDate> adminDates = AdminTimeCollector.collectDates(HlstatsURL);
        List<Duration> adminTimes = AdminTimeCollector.collectTimes(HlstatsURL);
        List<Duration> indexedAdminTimes = new ArrayList<>();

        int count = adminDates.size()-1;
        for (int i=0; i<daysPeriod.size(); i++) {
            if (i<adminDates.size()) {
                LocalDate day = adminDates.get(count);
                Duration time = adminTimes.get(count);
                if (day.isAfter(startDay.minusDays(-1)) && day.isBefore(endDay.plusDays(1))) {
                    if (day.isEqual(daysPeriod.get(i))) {
                        indexedAdminTimes.add(i, time);
                        if (count!=0) {
                            count -= 1;
                        } else {
                            count = 0;
                        }
                    } else {
                        indexedAdminTimes.add(i, Duration.ofSeconds(0));
                    }
                }
                } else {
                indexedAdminTimes.add(i, Duration.ofSeconds(0));
            }
        }
        return indexedAdminTimes;
    }
}
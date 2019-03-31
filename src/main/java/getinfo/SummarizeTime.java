package getinfo;

import java.time.*;
import java.time.LocalDate;
import java.util.*;

public class SummarizeTime {

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
            String sumTime = "0d 0m 0s";

            return sumTime;
        }
    }

    /*
     *
     * ACQUIRED DATA LIST METHOD
     *
     */

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
        HashMap<LocalDate, LocalTime> adminTimeDates = AdminTimeCollector.getAdminDateTimes(HlstatsURL);
        List<LocalDateTime> fullAdminDateTime = new ArrayList<>();


        for (LocalDate dp : daysPeriod) {
            if (!adminTimeDates.isEmpty()) {
                if (adminTimeDates.containsKey(dp)) {
                    LocalTime adminTime = adminTimeDates.get(dp);
                    fullAdminDateTime.add(LocalDateTime.of(dp, adminTime));
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
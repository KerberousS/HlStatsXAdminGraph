package timecollector;

import java.time.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class SummarizeTime {

    /*
     *
     * SUM METHODS
     *
     */

    public static Integer adminTimesDurationTotal(List<LocalDateTime> adminTimes) {

        int sumDuration = 0;
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

    public static String sumTimeString(List<LocalDateTime> adminTimes) {

        int sum1 = adminTimesDurationTotal(adminTimes);

        if (sum1 > 0) {
            int days = sum1 / 86400;
            int hours = (sum1 % 86400) / 3600;
            int minutes = (sum1 % 3600) / 60;
            int seconds = sum1 % 60;

            return days + "d " + hours + "h " + minutes + "m " + seconds + "s";
        } else {
            return "0d 0h 0m 0s";
        }
    }

    /*
     *
     * ACQUIRED DATA LIST METHOD
     *
     */

//    private static List<LocalDate> getDaysListFromPeriod(LocalDate startDay, LocalDate endDay) {
//        List<LocalDate> daysList = new ArrayList<>();
//        LocalDate day = startDay;
//
//        while (day.isBefore(endDay.plusDays(1))) {
//            daysList.add(day);
//            day = day.plusDays(1);
//        }
//        return daysList;
//    }

    private static List<LocalDate> getDaysListFromPeriod(LocalDate startDay, LocalDate endDay) {
        return LocalDate.from(startDay).datesUntil(endDay).collect(Collectors.toList());
    }



    /*
     *
     * ACQUIRE FINAL DATA LISTS
     *
     */

    public static List<LocalDateTime> getTimesListFromPeriod(String HlstatsURL, LocalDate startDay, LocalDate endDay) {

        List<LocalDate> adminDaysPeriod = getDaysListFromPeriod(startDay, endDay);

        //If list has not been initialized, or possibly the day has changed, add days to static list
        if (!adminDaysPeriod.contains(startDay)) getDaysListFromPeriod(startDay, endDay);

        HashMap<LocalDate, LocalTime> adminTimeDates = AdminTimeCollector.getAdminDateTimes(HlstatsURL);
        List<LocalDateTime> fullAdminDateTime = new ArrayList<>(); //Create new list as the final time list (with empty/blank days)

        for (LocalDate dp : adminDaysPeriod) {
            if (adminTimeDates != null) {
                if (!adminTimeDates.isEmpty()) {
                    if (adminTimeDates.containsKey(dp)) {
                        LocalTime adminTime = adminTimeDates.get(dp);
                        fullAdminDateTime.add(LocalDateTime.of(dp, adminTime));
                    } else {
                        LocalDateTime minimalAdminDateTime = LocalDateTime.of(dp, LocalTime.MIN);
                        fullAdminDateTime.add(minimalAdminDateTime);
                    }
                    } else if (adminTimeDates.isEmpty()){
                        LocalDateTime minimalAdminDateTime = LocalDateTime.of(dp, LocalTime.MIN);
                        fullAdminDateTime.add(minimalAdminDateTime);
                    }
            }
        }
        return fullAdminDateTime;
        }

    /*
     *
     * ACQUIRE FINAL DATA LISTS
     *
     */

    public static List<LocalDateTime> cutTimesListToPeriod(List<LocalDateTime> adminsUncutDateTimeList, LocalDate startDay, LocalDate endDay) {
        List<LocalDateTime> cutAdminTimeList = new ArrayList<>();

        for (LocalDateTime at : adminsUncutDateTimeList) {
            if (at.isAfter(LocalDateTime.of(startDay, LocalTime.MAX)) && at.isBefore(LocalDateTime.of(endDay, LocalTime.MAX))) {
                cutAdminTimeList.add(at);
            }
        }
        return cutAdminTimeList;
    }
}
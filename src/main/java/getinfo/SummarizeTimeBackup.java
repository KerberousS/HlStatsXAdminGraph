//package getinfo;
//
//import org.joda.time.LocalDate;
//import org.joda.time.LocalDateTime;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class SummarizeTimeBackup {
//
//    private static int sum;
//
//    public static Integer adminTimesTotalSumInSeconds(String HlstatsURL) {
//
//        List<String> times = AdminTimeCollector.collectTimes(HlstatsURL);
//        sum = 0;
//
//        for (String t : times) {
//            String[] splitTimes = t.split(":");
//
//            int hourInteger = Integer.parseInt(splitTimes[0]);
//            int minuteInteger = Integer.parseInt(splitTimes[1]);
//            int secondsInteger = Integer.parseInt(splitTimes[2]);
//
//            int hourToSeconds = hourInteger * 3600;
//            int minuteToSeconds = minuteInteger * 60;
//
//            sum += secondsInteger + hourToSeconds + minuteToSeconds;
//        }
//        return sum;
//    }
//
//    private static List<Integer> timesToSecondsList(String HlstatsURL) {
//
//        List<Integer> timesListInSeconds = new ArrayList<>();
//        List<String> times = AdminTimeCollector.collectTimes(HlstatsURL);
//
//            for (String t : times) {
//                for (int index = 0; index>timesListInSeconds.size(); index++) {
//                    String[] splitTimes = t.split(":");
//
//                    int hourInteger = Integer.parseInt(splitTimes[0]);
//                    int minuteInteger = Integer.parseInt(splitTimes[1]);
//                    int secondsInteger = Integer.parseInt(splitTimes[2]);
//
//                    int hourToSeconds = hourInteger * 3600;
//                    int minuteToSeconds = minuteInteger * 60;
//
//                    sum = secondsInteger + hourToSeconds + minuteToSeconds;
//                    timesListInSeconds.add(index, sum);
//                }
//            }
//
//        return timesListInSeconds;
//    }
//
//    public static List<LocalDate> getDaysListFromPeriod(LocalDate startDay, LocalDate endDay) {
//
//        List<LocalDate> daysList = new ArrayList<>();
//
//        LocalDate day = new LocalDate(startDay.plusDays(1)); //Adding 1 day so current day is inclusive
//
//        while (day.isBefore(endDay)) {
//            daysList.add(day);
//            day.plusDays(1);
//        }
//        return daysList;
//    }
//
//    public static List<Integer> lastTimesList(String HlstatsURL, LocalDate startDay, LocalDate endDay) {
//
//        List<Integer> adminTimes = SummarizeTimeBackup.timesToSecondsList(HlstatsURL);
//        List<LocalDateTime> adminDates = ConvertList.convertStringdateTos(HlstatsURL);
//        List<LocalDate> selectedDays = getDaysListFromPeriod(startDay, endDay);
//
//        List<LocalDate> adminTimesList = new ArrayList();
//
//        for (LocalDate d : selectedDays) {
//            if(adminDates.equals(d)) {
//
//            }
//        }
//
//
//        return adminTimesList;
//    }
//
//    public static String adminTimesTotalSumInString(String HlstatsURL) {
//
//        int sum1 = SummarizeTimeBackup.adminTimesTotalSumInSeconds(HlstatsURL);
//
//        if (sum1 > 0)
//        {
//            int days = sum1 / 86400;
//            int hours = (sum1 % 86400) / 3600;
//            int minutes = (sum1 % 3600) / 60;
//            int seconds = sum1 % 60;
//
//            String daysTime = (days + "d");
//            String times = (hours + "h" + " " + minutes + "m" + " " + seconds + "s");
//            String sumTime = daysTime + " " + times;
//
//            return sumTime;
//        }
//        else {
////            String sumTime = "The player hasn't played for the last 28 days or the player ID is wrong";
//            String sumTime = "0d 0h 0s";
//            return sumTime;
//        }
//    }
//
//    private static List<LocalDate> adminDaysList (String HlstatsURL, LocalDate startDay, LocalDate endDay) {
//
//        List<LocalDate> daysList = ConvertList.convertStringdateTos(HlstatsURL);
//        LocalDate day = new LocalDate(startDay.plusDays(1)); //Adding 1 day so current day is inclusive
//
//        while (day.isBefore(endDay)) {
//            daysList.add(day);
//            day.plusDays(1);
//        }
//        return daysList;
//    }
//}
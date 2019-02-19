package getInfo;

import java.util.List;

public class SummarizeTime {

    public int sum;

    public static void main(String[] args) {
    }

    public int timesToSeconds(String adminHlstatsNumber) {
        AdminTimeCollector atc = new AdminTimeCollector();

            List<String> times = atc.collectTimes(adminHlstatsNumber);

            sum = 0;
            for (String t : times) {
                String[] splitTimes = t.split(":");

                int hourInteger = Integer.parseInt(splitTimes[0]);
                int minuteInteger = Integer.parseInt(splitTimes[1]);
                int secondsInteger = Integer.parseInt(splitTimes[2]);

                int hourToSeconds = hourInteger * 3600;
                int minuteToSeconds = minuteInteger * 60;

                sum += secondsInteger + hourToSeconds + minuteToSeconds;
            }
        return sum;
    }

    public String sumTimeString(String adminHlstatsNumber) {

        SummarizeTime sum = new SummarizeTime();
        int sum1 = sum.timesToSeconds(adminHlstatsNumber);

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
//            String sumTime = "The player hasn't played for the last 28 days or the player ID is wrong";
            //@TODO Add logging here
            String sumTime = "0d 0h 0s";
            return sumTime;
        }
    }
}
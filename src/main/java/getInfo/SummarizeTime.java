package getInfo;

import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SummarizeTime {

    public int sum;

    public static void main(String[] args) {
    }

    public int timesToSeconds(String HLSTATS_ADMIN_NUMBER) {
        AdminTimeCollector atc = new AdminTimeCollector();

        try {
            List<String> times = atc.collectTimes(HLSTATS_ADMIN_NUMBER);

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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sum;
    }

    public String sumTimeString(String HLSTATS_ADMIN_NUMBER) {

        SummarizeTime sum = new SummarizeTime();
        int sum1 = sum.timesToSeconds(HLSTATS_ADMIN_NUMBER);

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
}
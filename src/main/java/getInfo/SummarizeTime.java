package getInfo;

import javafx.scene.chart.XYChart;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    public List<Integer> timesToSecondsList(String adminHlstatsNumber) {
        AdminTimeCollector atc = new AdminTimeCollector();

        List<Integer> timesListInSeconds = new ArrayList<>();
        List<String> times = atc.collectTimes(adminHlstatsNumber);
        int count = 0;

            for (String t : times) {
                String[] splitTimes = t.split(":");

                int hourInteger = Integer.parseInt(splitTimes[0]);
                int minuteInteger = Integer.parseInt(splitTimes[1]);
                int secondsInteger = Integer.parseInt(splitTimes[2]);

                int hourToSeconds = hourInteger * 3600;
                int minuteToSeconds = minuteInteger * 60;

                sum = secondsInteger + hourToSeconds + minuteToSeconds;
                timesListInSeconds.add(count, sum);
                count += 1;
            }
        return timesListInSeconds;
    }

    public List<String> last28DaysList() {
        List<String> days = new ArrayList<>();
        int numberOfDays = 28;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -28);
        for (int i = 0; i<numberOfDays; i++) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
            days.add(sdf.format(cal.getTime()));
        }
        return days;
    }

    public List<Integer> last28TimesList(String adminHlstatsNumber) {
        SummarizeTime sum = new SummarizeTime();
        AdminTimeCollector atc = new AdminTimeCollector();
        List<Integer> times = sum.timesToSecondsList(adminHlstatsNumber);
        List<String> dates = atc.collectDates(adminHlstatsNumber);
        List<String> last28Days = last28DaysList();
        List<Integer> last28TimesList = new ArrayList();
        int count = times.size()-1;
        if (count!=0) {
            for (String d : last28Days) {
                for (int i = 0; i < last28Days.size(); i++) {
                    if (!d.equals(dates.get(count))) {
                        last28TimesList.add(i, 0);
                        break;
                    } else {
                        last28TimesList.add(i, times.get(count));
                        if (count != 0) {
                            count -= 1;
                        }
                        break;
                    }
                }
            }
        } else {
            for (int i = 0; i < last28Days.size(); i++) {
                last28TimesList.add(i, 0);
            }
        }
        return last28TimesList;
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
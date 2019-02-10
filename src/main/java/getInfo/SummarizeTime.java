package getInfo;

import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SummarizeTime {

    public long sum;

    public static void main(String[] args) {
        SummarizeTime sum = new SummarizeTime();
        sum.summarizeTimes();
    }
    public long summarizeTimes() {
        AdminTimeCollector atc = new AdminTimeCollector();
        try {
            List<String> times = atc.collectTimes();

            long sum = 0;
            for (int i=1; i < times.size(); i++) {
                Long l = Long.parseLong(times.get(i));
                sum += l;
                return sum;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(sum);
        return sum;
    }
}
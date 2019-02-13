package getInfo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConvertList {

    public static void main(String[] args) {
    }

    public List<String> joinDatesLists(String HLSTATS_ADMIN_NUMBER) {
        AdminTimeCollector atc = new AdminTimeCollector();

        List<String> dateTimes = new ArrayList<>();
        try {
            List<String> a = atc.collectTimes(HLSTATS_ADMIN_NUMBER);
            List<String> b = atc.collectDates(HLSTATS_ADMIN_NUMBER);

            for (int i = 0; i<a.size(); i++) {
                for (i = 0; i < b.size(); i++) {
                    String first = a.get(i);
                    String second = b.get(i);
                    String joined = String.join(" ", second, first);
                    dateTimes.add(joined);
                }
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        System.out.println(dateTimes);
            return dateTimes;
    }

    public List<LocalDateTime> convertStringToDates(String HLSTATS_ADMIN_NUMBER) {

        ConvertList conv = new ConvertList();
        List<String> dateStrings = conv.joinDatesLists(HLSTATS_ADMIN_NUMBER);
        List<LocalDateTime> dates = new ArrayList<>(32);

            DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (String d: dateStrings) {
                dates.add(LocalDateTime.parse(d, datePattern));
            }

        System.out.println(dates);
        return dates;
    }
}
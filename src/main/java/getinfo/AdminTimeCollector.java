package getinfo;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AdminTimeCollector {

    private static Logger log = Logger.getLogger(AdminTimeCollector.class.getName());

    private static Elements getHlstatsTimeRows(String HlstatsURL) {
        try {
            Document doc = Jsoup.connect(HlstatsURL).get();
            Elements timeRows = doc.select("td:nth-child(4)");

            if(timeRows != null) {
                timeRows.remove(0);
            }
            return timeRows;
        } catch (IOException e) {
            e.printStackTrace();
            log.info("Couldn't acquire time rows from the site, please check your connection or stacktrace");
            return null;
        }
    }

    private static Elements getHlstatsDateRows (String HlstatsURL) {
        try {
        Document doc = Jsoup.connect(HlstatsURL).get();
        Elements dateRows = doc.select("td:first-child");

            if(dateRows != null) {
                dateRows.remove(0);
            }
            return dateRows;
        } catch (IOException e) {
            e.printStackTrace();
            log.info("Couldn't acquire time rows from the site, please check your connection or stacktrace");
            return null;
        }
    }

        static List<LocalDate> collectDates(String HlstatsURL) {
            Elements dateElements = AdminTimeCollector.getHlstatsDateRows(HlstatsURL);

            List<LocalDate> adminDateList = new ArrayList<>();
            DateTimeFormatter dayPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for (Element date : dateElements) {
                String[] splitDateOpenHTMLTag = date.toString().split("\">");
                String[] splitDateCloseHTMLTag = splitDateOpenHTMLTag[1].split("</");

                String serverConnectionDate = splitDateCloseHTMLTag[0];
                adminDateList.add(LocalDate.parse(serverConnectionDate, dayPattern));
            }

            return adminDateList;
        }

            static List<Duration> collectTimes(String HlstatsURL) {
                Elements timeList = AdminTimeCollector.getHlstatsTimeRows(HlstatsURL);
                DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("HH:mm:ss");
                List<Duration> adminTimeList = new ArrayList<>();

                if (!timeList.isEmpty()) {
                    for (Element time : timeList) {
                        String[] splitTimeOpenHTMLTag = time.toString().split("nbsp;");
                        String[] splitTimeCloseHTMLTag = splitTimeOpenHTMLTag[1].split("h</");

                        /*
                        //Get days - Line above skips day, reasonably speaking a person cant have a day of time played during 1 day(?)
                        String[] splitDateFromTime = splitTimeCloseHTMLTag[0].split("d "); //1d 23:45:47
                        Long dayCount = Long.valueOf(splitDateFromTime[0]); // = 1
                        */

                        //Get times
                        String[] splitTimes = splitTimeCloseHTMLTag[0].split(":"); //23:45:47
                        Long hourCount = Long.valueOf(splitTimes[0]); //23
                        Long minuteCount = Long.valueOf(splitTimes[1]); //45
                        Long secondCount = Long.valueOf(splitTimes[2]); //47

                        //Add times to make duration
                        Duration serverConnectionTime = Duration.ofHours(hourCount).plusMinutes(minuteCount).plusSeconds(secondCount);
                        adminTimeList.add(serverConnectionTime);
                    }
                } else {
                    adminTimeList.add(Duration.ofSeconds(0)); //Add single no time element if the player hasnt played
                }

                return adminTimeList;
            }
}
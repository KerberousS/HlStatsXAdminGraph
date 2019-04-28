package getinfo;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

class AdminTimeCollector {

    private static Logger log = Logger.getLogger(AdminTimeCollector.class.getName());
    private static DateTimeFormatter dayPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("HH:mm:ss");

    static HashMap<LocalDate, LocalTime> getAdminDateTimes(String HlstatsURL) {
        try {
            Document doc = Jsoup.connect(HlstatsURL).get();
            Elements dateTimeRows = doc.select("td:first-child.bg1,td:nth-child(4).bg2");

            HashMap<LocalDate, LocalTime> dateTime = new HashMap<>();
            for (int i = 0; i < dateTimeRows.size(); i++) {
                //Get date
                String[] splitDateOpenHTMLTag = dateTimeRows.get(i).toString().split("\">");
                String[] splitDateCloseHTMLTag = splitDateOpenHTMLTag[1].split("</");
                String serverConnectionDate = splitDateCloseHTMLTag[0];

                //Get time
                String[] splitTimeOpenHTMLTag = dateTimeRows.get(i + 1).toString().split("nbsp;");
                String[] splitTimeCloseHTMLTag = splitTimeOpenHTMLTag[1].split("h</"); //23:45:47
                String serverConnectionTime = splitTimeCloseHTMLTag[0];

                dateTime.put(LocalDate.parse(serverConnectionDate, dayPattern), LocalTime.parse(serverConnectionTime, timePattern));
                i++;
            }
            return dateTime;
        } catch (IOException e) {
            e.printStackTrace();
            log.info("Couldn't acquire time rows from the site, please check your connection or stacktrace");
            return null;
        }
    }
}
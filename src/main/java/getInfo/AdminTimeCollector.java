package getInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminTimeCollector {

    public static final String HLSTATS_DEFAULT_URL = "http://hlstatsx.strefagier.com.pl/hlstats.php?mode=playersessions&player=";
    public static List<String> list = new ArrayList<String>();

    public static void main(String[] args) throws IOException {
    }

    public Elements acquireHlStatsTimes(String HLSTATS_ADMIN_NUMBER) throws IOException {
        Document doc = Jsoup.connect(HLSTATS_DEFAULT_URL + HLSTATS_ADMIN_NUMBER).get();
        //Elements hlStatsTimes = doc.select("td:contains(0d)");
        Elements hlStatsTimes = doc.select("td:nth-child(4)");


        try {
            hlStatsTimes.remove(0); //The first collected element is TAG HEADER, this line removes it from the list
        } catch (Exception e) {
            //Can't find any time for this player, either the ID is wrong or the Player hasn't played in the last 28 days
        }
        return hlStatsTimes;
    }
    public Elements acquireHlStatsDates(String HLSTATS_ADMIN_NUMBER) throws IOException {
        Document doc = Jsoup.connect(HLSTATS_DEFAULT_URL + HLSTATS_ADMIN_NUMBER).get();
        Elements hlStatsDates = doc.select("td:first-child");

        try {
            hlStatsDates.remove(0); //The first collected element is TAG HEADER, this line removes it from the list
        } catch (Exception e) {
            //Can't find any time for this player, either the ID is wrong or the Player hasn't played in the last 28 days
        }
        return hlStatsDates;
    }
        public List<String>  collectDates(String HLSTATS_ADMIN_NUMBER) throws IOException {
            AdminTimeCollector gat = new AdminTimeCollector();
            Elements dates = gat.acquireHlStatsDates(HLSTATS_ADMIN_NUMBER);

            List<String> adminDateList = new ArrayList<String>();

            for (Element date : dates) {
                String[] splitDateOpenHTMLTag = date.toString().split("\">");
                String[] splitDateCloseHTMLTag = splitDateOpenHTMLTag[1].split("</");

                String serverConnectionDate = splitDateCloseHTMLTag[0];

                adminDateList.add(serverConnectionDate);
            }

            return adminDateList;
        }

            public List<String> collectTimes(String HLSTATS_ADMIN_NUMBER) throws IOException {
                AdminTimeCollector gat = new AdminTimeCollector();
                Elements times = gat.acquireHlStatsTimes(HLSTATS_ADMIN_NUMBER);

                List<String> adminTimeList = new ArrayList<String>();

                for (Element time : times) {
                    String[] splitTimeOpenHTMLTag = time.toString().split("nbsp;");
                    String[] splitTimeCloseHTMLTag = splitTimeOpenHTMLTag[1].split("h</");

                    String serverConnectionTime = splitTimeCloseHTMLTag[0];

                    list.add(serverConnectionTime);
                    adminTimeList.add(serverConnectionTime);
                }

                return adminTimeList;
            }
}
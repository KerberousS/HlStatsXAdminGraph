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

    public Elements acquireHlStatsTimes(String adminHlstatsNumber) {
        Document doc = null;
        try {
            doc = Jsoup.connect(HLSTATS_DEFAULT_URL + adminHlstatsNumber).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Elements hlStatsTimes = doc.select("td:contains(0d)");
        Elements hlStatsTimes = doc.select("td:nth-child(4)");

        try {
            hlStatsTimes.remove(0); //The first collected element is TAG HEADER, this line removes it from the list
        } catch (Exception e) {
            //Can't find any time for this player, either the ID is wrong or the Player hasn't played in the last 28 days
        }
        return hlStatsTimes;
    }
    public Elements acquireHlStatsDates(String adminHlstatsNumber) {
        Document doc = null;
        try {
            doc = Jsoup.connect(HLSTATS_DEFAULT_URL + adminHlstatsNumber).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements hlStatsDates = doc.select("td:first-child");

        try {
            hlStatsDates.remove(0); //The first collected element is TAG HEADER, this line removes it from the list
        } catch (Exception e) {
            //Can't find any time for this player, either the ID is wrong or the Player hasn't played in the last 28 days
        }
        return hlStatsDates;
    }
        public List<String>  collectDates(String adminHlstatsNumber) {
            AdminTimeCollector gat = new AdminTimeCollector();
            Elements dates = gat.acquireHlStatsDates(adminHlstatsNumber);

            List<String> adminDateList = new ArrayList<String>();

            for (Element date : dates) {
                String[] splitDateOpenHTMLTag = date.toString().split("\">");
                String[] splitDateCloseHTMLTag = splitDateOpenHTMLTag[1].split("</");

                String serverConnectionDate = splitDateCloseHTMLTag[0];

                adminDateList.add(serverConnectionDate);
            }

            return adminDateList;
        }

            public List<String> collectTimes(String adminHlstatsNumber) {
                AdminTimeCollector gat = new AdminTimeCollector();
                Elements times = gat.acquireHlStatsTimes(adminHlstatsNumber);

                List<String> adminTimeList = new ArrayList<String>();

                if (!times.isEmpty()) {
                    for (Element time : times) {
                        String[] splitTimeOpenHTMLTag = time.toString().split("nbsp;");
                        String[] splitTimeCloseHTMLTag = splitTimeOpenHTMLTag[1].split("h</");

                        String serverConnectionTime = splitTimeCloseHTMLTag[0];

                        list.add(serverConnectionTime);
                        adminTimeList.add(serverConnectionTime);
                    }
                } else {
                    adminTimeList.add("00:00:00"); //Add single no time element if the player hasnt played
                }

                return adminTimeList;
            }
}
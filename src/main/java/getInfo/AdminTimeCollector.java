package getInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class getAdminTime {

    public static String adminLink = "http://hlstatsx.strefagier.com.pl/hlstats.php?mode=playersessions&player=755";
    public static Elements adminTimes;
    public static List<String> list = new ArrayList<String>();

    public static void main(String[] args) throws IOException {
        getAdminTime gat = new getAdminTime();
        gat.collectTimes();
    }
    public Elements acquireHlStatsTimes() throws IOException {
        Document doc = Jsoup.connect(adminLink).get();
        Elements hlStatsTimes = doc.select("td:contains(0d)");

        return hlStatsTimes;
    }
    public Elements acquireHlStatsDates() throws IOException {
        Document doc = Jsoup.connect(adminLink).get();
        Elements hlStatsDates = doc.select("td:contains(-)");

        return hlStatsDates;
    }
        public void collectTimes() throws IOException {
            getAdminTime gat = new getAdminTime();
            Elements times = gat.acquireHlStatsTimes();
            Elements dates = gat.acquireHlStatsDates();

            List<String> adminTimeList = new ArrayList<String>();
            List<String> adminDateList = new ArrayList<String>();

            for (Element date : dates) {
                String[] splitDateOpenHTMLTag = date.toString().split("\">");
                String[] splitDateCloseHTMLTag = splitDateOpenHTMLTag[1].split("</");

                String serverConnectionDate = splitDateCloseHTMLTag[0];

                //System.out.println(serverConnectionDate);
                adminDateList.add(serverConnectionDate);
            }

            for (Element time : times) {
               String[] splitTimeOpenHTMLTag = time.toString().split("nbsp;");
               String[] splitTimeCloseHTMLTag = splitTimeOpenHTMLTag[1].split("h</");
               String serverConnectionTime = splitTimeCloseHTMLTag[0];

               //System.out.println(serverConnectionTime);
               list.add(serverConnectionTime);
               adminTimeList.add(serverConnectionTime);
            }
            System.out.println(adminTimeList);
            System.out.println(adminDateList);
        }
    }

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
    public static String HLSTATS_ADMIN_NUMBER = "2195";
    public static String ADMIN_LINK_FINAL = HLSTATS_DEFAULT_URL + HLSTATS_ADMIN_NUMBER;
    public static List<String> list = new ArrayList<String>();

    public static void main(String[] args) throws IOException {
        AdminTimeCollector gat = new AdminTimeCollector();
        gat.collectTimes();
    }
    public Elements acquireHlStatsTimes() throws IOException {
        Document doc = Jsoup.connect(ADMIN_LINK_FINAL).get();
        //Elements hlStatsTimes = doc.select("td:contains(0d)");
        Elements hlStatsTimes = doc.select("td:nth-child(4)");

        hlStatsTimes.remove(0); // The first collected element is TAG HEADER, it should be removed
        return hlStatsTimes;
    }
    public Elements acquireHlStatsDates() throws IOException {
        Document doc = Jsoup.connect(ADMIN_LINK_FINAL).get();
        Elements hlStatsDates = doc.select("td:first-child"); //@FIXME Broken if there are minus "-" points

        hlStatsDates.remove(0); // The first collected element is TAG HEADER, it should be removed
        return hlStatsDates;
    }
        public List<String>  collectDates() throws IOException {
            AdminTimeCollector gat = new AdminTimeCollector();
            Elements dates = gat.acquireHlStatsDates();

            List<String> adminDateList = new ArrayList<String>();

            for (Element date : dates) {
                String[] splitDateOpenHTMLTag = date.toString().split("\">");
                String[] splitDateCloseHTMLTag = splitDateOpenHTMLTag[1].split("</");

                String serverConnectionDate = splitDateCloseHTMLTag[0];

                //System.out.println(serverConnectionDate);
                adminDateList.add(serverConnectionDate);
            }
            System.out.println(adminDateList);
            return adminDateList;
        }

            public List<String> collectTimes() throws IOException {
                AdminTimeCollector gat = new AdminTimeCollector();
                Elements times = gat.acquireHlStatsTimes();

                List<String> adminTimeList = new ArrayList<String>();

                for (Element time : times) {
                    String[] splitTimeOpenHTMLTag = time.toString().split("nbsp;");
                    String[] splitTimeCloseHTMLTag = splitTimeOpenHTMLTag[1].split("h</");

                    String serverConnectionTime = splitTimeCloseHTMLTag[0];

                    //System.out.println(serverConnectionTime);
                    list.add(serverConnectionTime);
                    adminTimeList.add(serverConnectionTime);
                }
                System.out.println(adminTimeList);
                return adminTimeList;
            }
        }
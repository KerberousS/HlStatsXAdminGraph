package getInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AdminTimeCollectorGetDate {

    public LocalDateTime adminTimeModify;

    public static void main(String[] args) {
        AdminTimeCollectorGetDate gatDate = new AdminTimeCollectorGetDate();
        gatDate.getNowDate();
    }
    public String getNowDate() {
        LocalDateTime a = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = a.format(formatter);
        System.out.println(formatDateTime);

        return formatDateTime;
    }
}
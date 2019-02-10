package getInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NowDate {

    public static void main(String[] args) {

    }
    public String getNowDate() {
        LocalDateTime a = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = a.format(formatter);
        System.out.println(formatDateTime);

        return formatDateTime;
    }
}
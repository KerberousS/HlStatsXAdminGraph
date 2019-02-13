import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class testJDBCConnection {

    @Test
    public void main() {
        String jdbcURL = "jdbc:postgresql://localhost:5432/Test";
        String user = "postgres";
        String password = "postgres";

        try {
            System.out.println("Connecting to: " + jdbcURL);

            Connection con = DriverManager.getConnection(jdbcURL, user, password);

            System.out.println("Connection successful");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

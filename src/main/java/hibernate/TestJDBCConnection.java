package hibernate;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJDBCConnection {

    public String TestConnection() {
        ConfigOperations config = new ConfigOperations();
        String jdbcURL = config.getDatabaseUrl();
        String user = config.getDatabaseUsername();
        String password = config.getDatabasePassword();

        String status;
        try {
            System.out.println("Connecting to: " + jdbcURL);

            Connection con = DriverManager.getConnection(jdbcURL, user, password);

            System.out.println("Connection successful");

            status = "DB Status: Connected";
        } catch (Exception e) {
            e.printStackTrace();
            status = "DB Status: Can't connect";
        }
        return status;
    }
}

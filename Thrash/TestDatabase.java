//package hibernate;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.util.List;
//
//import org.apache.log4j.Logger;
//
//public class TestDatabase {
//
//    public final static Logger logger = Logger.getLogger(TestDatabase.class);
//    private String testServerName = "Test Server Name";
//
//    public TestRecords {
//        System.out.println(".......Hibernate Crud Operations Example.......\n");
//
//
//        System.out.println("\n=======CREATE RECORDS=======\n");
//        DBOperations.createServerRecord(testServerName);
//
//        System.out.println("\n=======READ RECORDS=======\n");
//        List ListViewServers = DBOperations.displayRecords();
//        if(ListViewServers != null & ListViewServers.size() > 0) {
//            for(Object serverObj : ListViewServers) {
//                System.out.println(serverObj.toString());
//            }
//        }
//
//        System.out.println("\n=======UPDATE RECORDS=======\n");
////        int updateId = 1;
//        DBOperations.updateRecord(testServerName);
//        System.out.println("\n=======READ RECORDS AFTER UPDATION=======\n");
//        List updateServer = DBOperations.displayRecords();
//        if(updateServer != null & updateServer.size() > 0) {
//            for(Object serverObj : updateServer) {
//                System.out.println(serverObj.toString());
//            }
//        }
//
//        System.out.println("\n=======DELETE RECORD=======\n");
//        String deleteName = "Test server";
//        DBOperations.deleteRecord(deleteName);
//        System.out.println("\n=======READ RECORDS AFTER DELETION=======\n");
//        List deleteServerRecord = DBOperations.displayRecords();
//        for(Object serverObj : deleteServerRecord) {
//            System.out.println(serverObj.toString());
//        }
//
//        System.out.println("\n=======DELETE ALL RECORDS=======\n");
//        DBOperations.deleteAllRecords();
//        System.out.println("\n=======READ RECORDS AFTER ALL RECORDS DELETION=======");
//        List deleteAll = DBOperations.displayRecords();
//        if(deleteAll.size() == 0) {
//            System.out.println("\nNo Records Are Present In The Database Table!\n");
//        }
//        System.exit(0);
//    }
//    public String checkDBConnection() {
//        ConfigOperations config = new ConfigOperations();
//        String jdbcURL = config.getDatabaseUrl();
//        String user = config.getDatabaseUsername();
//        String password = config.getDatabasePassword();
//
//        String status = null;
//        try {
//            System.out.println("Connecting to: " + jdbcURL);
//
//            Connection con = DriverManager.getConnection(jdbcURL, user, password);
//
//            System.out.println("Connection successful");
//
//            status = "Connection successful";
//        } catch (Exception e) {
//            e.printStackTrace();
//            status = "An error has occured" + status;
//        }
//        return status;
//    }
//}
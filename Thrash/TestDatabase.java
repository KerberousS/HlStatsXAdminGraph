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
//        ServerOperations.createServerRecord(testServerName);
//
//        System.out.println("\n=======READ RECORDS=======\n");
//        List ListViewServers = ServerOperations.displayRecords();
//        if(ListViewServers != null & ListViewServers.size() > 0) {
//            for(Object serverObj : ListViewServers) {
//                System.out.println(serverObj.toString());
//            }
//        }
//
//        System.out.println("\n=======UPDATE RECORDS=======\n");
////        int updateId = 1;
//        ServerOperations.updateRecord(testServerName);
//        System.out.println("\n=======READ RECORDS AFTER UPDATION=======\n");
//        List updateServer = ServerOperations.displayRecords();
//        if(updateServer != null & updateServer.size() > 0) {
//            for(Object serverObj : updateServer) {
//                System.out.println(serverObj.toString());
//            }
//        }
//
//        System.out.println("\n=======DELETE RECORD=======\n");
//        String deleteName = "Test server";
//        ServerOperations.deleteRecord(deleteName);
//        System.out.println("\n=======READ RECORDS AFTER DELETION=======\n");
//        List deleteServerRecord = ServerOperations.displayRecords();
//        for(Object serverObj : deleteServerRecord) {
//            System.out.println(serverObj.toString());
//        }
//
//        System.out.println("\n=======DELETE ALL RECORDS=======\n");
//        ServerOperations.deleteAllRecords();
//        System.out.println("\n=======READ RECORDS AFTER ALL RECORDS DELETION=======");
//        List deleteAll = ServerOperations.displayRecords();
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
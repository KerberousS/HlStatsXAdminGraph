package com.jcg.hibernate.crud.operations;

import java.util.List;

import hibernate.Server;
import hibernate.ServerOperations;
import org.apache.log4j.Logger;

public class testDB {

    public final static Logger logger = Logger.getLogger(testDB.class);

    public static void main(String[] args) {
        System.out.println(".......Hibernate Crud Operations Example.......\n");

        String testServerName = "Test Server Name";

        System.out.println("\n=======CREATE RECORDS=======\n");
        ServerOperations.createServerRecord(1, testServerName);

        System.out.println("\n=======READ RECORDS=======\n");
        List ListViewServers = ServerOperations.displayRecords();
        if(ListViewServers != null & ListViewServers.size() > 0) {
            for(Object serverObj : ListViewServers) {
                System.out.println(serverObj.toString());
            }
        }

        System.out.println("\n=======UPDATE RECORDS=======\n");
        int updateId = 1;
        ServerOperations.updateRecord(updateId, testServerName);
        System.out.println("\n=======READ RECORDS AFTER UPDATION=======\n");
        List updateServer = ServerOperations.displayRecords();
        if(updateServer != null & updateServer.size() > 0) {
            for(Object serverObj : updateServer) {
                System.out.println(serverObj.toString());
            }
        }

        System.out.println("\n=======DELETE RECORD=======\n");
        int deleteId = 5;
        ServerOperations.deleteRecord(deleteId);
        System.out.println("\n=======READ RECORDS AFTER DELETION=======\n");
        List deleteServerRecord = ServerOperations.displayRecords();
        for(Object serverObj : deleteServerRecord) {
            System.out.println(serverObj.toString());
        }

        System.out.println("\n=======DELETE ALL RECORDS=======\n");
        ServerOperations.deleteAllRecords();
        System.out.println("\n=======READ RECORDS AFTER ALL RECORDS DELETION=======");
        List deleteAll = ServerOperations.displayRecords();
        if(deleteAll.size() == 0) {
            System.out.println("\nNo Records Are Present In The Database Table!\n");
        }
        System.exit(0);
    }
}
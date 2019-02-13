package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.List;

public class ServerOperations {

        static Session sessionObj;
        static SessionFactory sessionFactoryObj;

        // This Method Is Used To Create The Hibernate's SessionFactory Object
        private static SessionFactory buildSessionFactory() {
        // Creating Configuration Instance & Passing Hibernate Configuration File
        Configuration configObj = new Configuration();
        configObj.configure("hibernate.cfg.xml");

        // Since Hibernate Version 4.x, ServiceRegistry Is Being Used
        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();

        // Creating Hibernate SessionFactory Instance
        sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
        return sessionFactoryObj;
        }

            // Method 1: This Method Used To Create A New Server Record In The Database Table
        public static void createServerRecord(long serverID, String serverName) {
                long count = 0;
                Server serverObj = null;
                try {
                // Getting Session Object From SessionFactory
                sessionObj = buildSessionFactory().openSession();
                // Getting Transaction Object From Session Object
                sessionObj.beginTransaction();

                // Creating Transaction Entities
                for(int j = 101; j <= 105; j++) {
                count = count + 1;
                serverObj = new Server(serverID, serverName);
                serverObj.setServerID(count);
                serverObj.setServerName(serverName);
                sessionObj.save(serverObj);
                }

                // Committing The Transactions To The Database
                sessionObj.getTransaction().commit();
                System.out.println("\nSuccessfully Created '" + count + "' Records In The Database!\n");
                } catch(Exception sqlException) {
                if(null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                sessionObj.getTransaction().rollback();
                }
                sqlException.printStackTrace();
                } finally {
                if(sessionObj != null) {
                sessionObj.close();
                }
            }
        }

        // Method 2: This Method Is Used To Display The Records From The Database Table
        @SuppressWarnings("unchecked")
        public static List displayRecords() {
                List serversList = new ArrayList();
                try {
                // Getting Session Object From SessionFactory
                sessionObj = buildSessionFactory().openSession();
                // Getting Transaction Object From Session Object
                sessionObj.beginTransaction();

                serversList = sessionObj.createQuery("FROM SERVER").list();
                } catch(Exception sqlException) {
                if(null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                sessionObj.getTransaction().rollback();
                }
                sqlException.printStackTrace();
                } finally {
                if(sessionObj != null) {
                sessionObj.close();
                }
                }
                return serversList;
                }

        // Method 3: This Method Is Used To Update A Record In The Database Table
        public static void updateRecord(int server_id, String server_name) {
                try {
                // Getting Session Object From SessionFactory
                sessionObj = buildSessionFactory().openSession();
                // Getting Transaction Object From Session Object
                sessionObj.beginTransaction();

                // Creating Transaction Entity
                Server serObj = sessionObj.get(Server.class, server_name);
                serObj.setServerID(server_id);
                serObj.setServerName(server_name);

                // Committing The Transactions To The Database
                sessionObj.getTransaction().commit();
                System.out.println("\nServer of name ?= " + server_name + " Was Successfully Updated In The Database!\n");
                } catch(Exception sqlException) {
                if(null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                sessionObj.getTransaction().rollback();
                }
                sqlException.printStackTrace();
                } finally {
                if(sessionObj != null) {
                sessionObj.close();
                }
                }
        }

        // Method 4(a): This Method Is Used To Delete A Particular Record From The Database Table
        public static void deleteRecord(Integer serverId) {
                try {
                // Getting Session Object From SessionFactory
                sessionObj = buildSessionFactory().openSession();
                // Getting Transaction Object From Session Object
                sessionObj.beginTransaction();

                Server servObj = findRecordById(serverId);
                sessionObj.delete(servObj);

                // Committing The Transactions To The Database
                sessionObj.getTransaction().commit();
                System.out.println("\nServer With Id?= " + serverId + " Was Successfully Deleted From The Database!\n");
                } catch(Exception sqlException) {
                if(null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                sessionObj.getTransaction().rollback();
                }
                sqlException.printStackTrace();
                } finally {
                if(sessionObj != null) {
                sessionObj.close();
                }
            }
        }

            // Method 4(b): This Method To Find Particular Record In The Database Table
            public static Server findRecordById(Integer find_serverId) {
                    Server findserverObj = null;
                    try {
                    // Getting Session Object From SessionFactory
                    sessionObj = buildSessionFactory().openSession();
                    // Getting Transaction Object From Session Object
                    sessionObj.beginTransaction();

                    findserverObj = sessionObj.load(Server.class, find_serverId);
                    } catch(Exception sqlException) {
                    if(null != sessionObj.getTransaction()) {
                    System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                    sessionObj.getTransaction().rollback();
                    }
                    sqlException.printStackTrace();
                    }
                    return findserverObj;
                    }

            // Method 5: This Method Is Used To Delete All Records From The Database Table
            public static void deleteAllRecords() {
                    try {
                    // Getting Session Object From SessionFactory
                    sessionObj = buildSessionFactory().openSession();
                    // Getting Transaction Object From Session Object
                    sessionObj.beginTransaction();
            
                    Query queryObj = sessionObj.createQuery("DELETE FROM Server");
                    queryObj.executeUpdate();
            
                    // Committing The Transactions To The Database
                    sessionObj.getTransaction().commit();
                    System.out.println("\nSuccessfully Deleted All Records From The Database Table!\n");
                    } catch(Exception sqlException) {
                    if(null != sessionObj.getTransaction()) {
                    System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                    sessionObj.getTransaction().rollback();
                    }
                    sqlException.printStackTrace();
                    } finally {
                    if(sessionObj != null) {
                    sessionObj.close();
                    }
                    }
                    }
                    }
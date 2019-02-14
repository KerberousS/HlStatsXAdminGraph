package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.List;

public class ServerOperations {

        static Session sessionObj;
        static SessionFactory sessionFactory;

//        // This Method Is Used To Create The Hibernate's SessionFactory Object
////        private static SessionFactory buildSessionFactory() {
////        // Creating Configuration Instance & Passing Hibernate Configuration File
////        Configuration configObj = new Configuration()
////                .addPackage("getInfo.hibernate") //the fully qualified package name
////                .addAnnotatedClass(Admin.class)
////                .addAnnotatedClass(Server.class)
////                .addAnnotatedClass(Time.class)
////                .configure("hibernate.cfg.xml");
////
////        // Since Hibernate Version 4.x, ServiceRegistry Is Being Used
////        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();
////
////        // Creating Hibernate SessionFactory Instance
////        sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
////        return sessionFactoryObj;
////        }
        private static SessionFactory buildSessionFactory() {
           try {
                StandardServiceRegistry standardRegistry =
                        new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
                Metadata metaData =
                        new MetadataSources(standardRegistry).getMetadataBuilder().build();
                sessionFactory = metaData.getSessionFactoryBuilder().build();
        } catch (Throwable th) {
                System.err.println("Enitial SessionFactory creation failed" + th);
                throw new ExceptionInInitializerError(th);
        }
        return sessionFactory;
}

            // Method 1: This Method Used To Create A New Server Record In The Database Table
        public static void createServerRecord(String serverName) {
                long count = 0;
                Server serverObj = null;
                try {
                // Getting Session Object From SessionFactory
                sessionObj = buildSessionFactory().openSession();
                // Getting Transaction Object From Session Object
                sessionObj.beginTransaction();

                // Creating Transaction Entities
//                for(int j = 101; j <= 105; j++) {
//                count = count + 1;
                serverObj = new Server();
                serverObj.setServerName(serverName);
                sessionObj.save(serverObj);

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
                List<Server> serversList = new ArrayList();
                try {
                // Getting Session Object From SessionFactory
                sessionObj = buildSessionFactory().openSession();
                // Getting Transaction Object From Session Object
                sessionObj.beginTransaction();

                serversList = sessionObj.createQuery("SELECT S.serverName FROM Server S").getResultList();
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
        public static void updateRecord(String server_name) {
                try {
                // Getting Session Object From SessionFactory
                sessionObj = buildSessionFactory().openSession();
                // Getting Transaction Object From Session Object
                sessionObj.beginTransaction();

                // Creating Transaction Entity
                Server serObj = sessionObj.get(Server.class, server_name);
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
        public static void deleteRecord(String serverName) {
                try {
                // Getting Session Object From SessionFactory
                sessionObj = buildSessionFactory().openSession();
                // Getting Transaction Object From Session Object
                sessionObj.beginTransaction();

                Server servObj = findRecordByName(serverName);
                sessionObj.delete(servObj);

                // Committing The Transactions To The Database
                sessionObj.getTransaction().commit();
                System.out.println("\nServer With Name?= " + serverName + " Was Successfully Deleted From The Database!\n");
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
            public static Server findRecordByName(String find_serverName) {
                    Server findserverObj = null;
                    try {
                    // Getting Session Object From SessionFactory
                    sessionObj = buildSessionFactory().openSession();
                    // Getting Transaction Object From Session Object
                    sessionObj.beginTransaction();

                    findserverObj = sessionObj.load(Server.class, find_serverName);
                    } catch(Exception sqlException) {
                    if(null != sessionObj.getTransaction()) {
                    System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                    sessionObj.getTransaction().rollback();
                    }
                    sqlException.printStackTrace();
                    }
                    return findserverObj;
                    }

        // Method 4(c): This Method Is Used To Delete A Particular Record By Name From The Database Table
        public static void deleteRecordByName(String serverName) {
                try {
                        // Getting Session Object From SessionFactory
                        sessionObj = buildSessionFactory().openSession();
                        // Getting Transaction Object From Session Object
                        sessionObj.beginTransaction();

                        Query queryObj = sessionObj.createQuery("delete from Server where serverName=:name");
                        queryObj.setParameter("name", serverName);
                        queryObj.executeUpdate();

                        // Committing The Transactions To The Database
                        sessionObj.getTransaction().commit();
                        System.out.println("\nServer With Name?= " + serverName + " Was Successfully Deleted From The Database!\n");
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
package hibernate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.*;

public class AdminOperations {

        static Session sessionObj;
        static SessionFactory sessionFactory;

//        // This Method Is Used To Create The Hibernate's SessionFactory Object
////        private static SessionFactory buildSessionFactory() {
////        // Creating Configuration Instance & Passing Hibernate Configuration File
////        Configuration configObj = new Configuration()
////                .addPackage("getInfo.hibernate") //the fully qualified package name
////                .addAnnotatedClass(Admin.class)
////                .addAnnotatedClass(Admin.class)
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
                System.err.println("Iniitial SessionFactory creation failed" + th);
                throw new ExceptionInInitializerError(th);
        }
        return sessionFactory;
}

            // Method 1: This Method Used To Create A New Admin Record In The Database Table
        public static void createAdminRecord(String adminName, String adminLink, String adminColor, String adminServer) {
                long count = 0;
                Admin adminObj = null;
                try {
                // Getting Session Object From SessionFactory
                sessionObj = buildSessionFactory().openSession();
                // Getting Transaction Object From Session Object
                sessionObj.beginTransaction();

                // Creating Transaction Entities
//                for(int j = 101; j <= 105; j++) {
//                count = count + 1;
                adminObj = new Admin();
                adminObj.setAdminName(adminName);
                adminObj.setAdminLink(adminLink);
                adminObj.setAdminColor(adminColor);
                adminObj.setAdminServer(adminServer);
                sessionObj.save(adminObj);

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
        public static List displayRecords(String adminServer) {
                List<Admin> adminsList = new ArrayList();
                try {
                // Getting Session Object From SessionFactory
                sessionObj = buildSessionFactory().openSession();
                // Getting Transaction Object From Session Object
                sessionObj.beginTransaction();

                StringBuilder sqlQuery = new StringBuilder("SELECT A.adminName FROM Admin A ")
                        .append("WHERE A.adminServer = :server ");

                Query queryObj = sessionObj.createQuery(sqlQuery.toString());
                queryObj.setParameter("server", adminServer);

                adminsList = queryObj.getResultList();
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
                return adminsList;
                }

//        public static String displayFullRecords(String adminServer) {
//
//
//                String adminName = null;
//                String adminLink = null;
//                String adminColor = null;
//
//                try {
//                        // Getting Session Object From SessionFactory
//                        sessionObj = buildSessionFactory().openSession();
//                        // Getting Transaction Object From Session Object
//                        sessionObj.beginTransaction();
//
//                        StringBuilder sqlQuery = new StringBuilder("SELECT A.adminName, A.adminLink, A.adminColor FROM Admin A ")
//                                .append("WHERE A.adminServer = :server ");
//
//                        Query queryObj = sessionObj.createQuery(sqlQuery.toString());
//                        queryObj.setParameter("server", adminServer);
//
//                        List<Object[]> adminsList = queryObj.list();
//                        for (Object[] a: adminsList) {
//                                adminName = (String)a[0];
//                                adminLink = (String)a[1];
//                                adminColor = (String)a[2];
//                        }
//                } catch(Exception sqlException) {
//                        if(null != sessionObj.getTransaction()) {
//                                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
//                                sessionObj.getTransaction().rollback();
//                        }
//                        sqlException.printStackTrace();
//                } finally {
//                        if(sessionObj != null) {
//                                sessionObj.close();
//                        }
//                }
//                return adminName;
//        }

        public static ObservableList<Admin> displayFullRecords(String adminServer) {
                ObservableList<Admin> list = null;
                try {
                        // Getting Session Object From SessionFactory
                        sessionObj = buildSessionFactory().openSession();
                        // Getting Transaction Object From Session Object
                        sessionObj.beginTransaction();

                        StringBuilder sqlQuery = new StringBuilder("SELECT A FROM Admin A ")
                                .append("WHERE A.adminServer = :server ");

                        Query queryObj = sessionObj.createQuery(sqlQuery.toString());
                        queryObj.setParameter("server", adminServer);

                        list = FXCollections.observableArrayList(queryObj.list());
                        System.out.println(list);
                } catch (Exception sqlException) {
                        if (null != sessionObj.getTransaction()) {
                                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                                sessionObj.getTransaction().rollback();
                        }
                        sqlException.printStackTrace();
                } finally {
                        if (sessionObj != null) {
                                sessionObj.close();
                        }
                }
                return list;
        }

        public static List getAdminInfo(String adminName, String adminServer) {
                List<Admin> adminsList = new ArrayList();
                try {
                        // Getting Session Object From SessionFactory
                        sessionObj = buildSessionFactory().openSession();
                        // Getting Transaction Object From Session Object
                        sessionObj.beginTransaction();

                        StringBuilder sqlQuery = new StringBuilder("SELECT Admin A ")
                                .append("WHERE A.adminServer = :server ")
                                .append("AND A.adminName = :name ");
                        Query queryObj = sessionObj.createQuery(sqlQuery.toString());
                        queryObj.setParameter("name", adminName);
                        queryObj.setParameter("server", adminServer);
                        queryObj.executeUpdate();

                        adminsList = queryObj.getResultList();
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
                return adminsList;
        }

        // Method 3: This Method Is Used To Update A Record In The Database Table
        public static void updateRecord(String admin_name) {
                try {
                // Getting Session Object From SessionFactory
                sessionObj = buildSessionFactory().openSession();
                // Getting Transaction Object From Session Object
                sessionObj.beginTransaction();

                // Creating Transaction Entity
                Admin serObj = sessionObj.get(Admin.class, admin_name);
                serObj.setAdminName(admin_name);

                // Committing The Transactions To The Database
                sessionObj.getTransaction().commit();
                System.out.println("\nAdmin of name ?= " + admin_name + " Was Successfully Updated In The Database!\n");
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
        public static void deleteRecord(String adminName) {
                try {
                // Getting Session Object From SessionFactory
                sessionObj = buildSessionFactory().openSession();
                // Getting Transaction Object From Session Object
                sessionObj.beginTransaction();

                Admin servObj = findRecordByName(adminName);
                sessionObj.delete(servObj);

                // Committing The Transactions To The Database
                sessionObj.getTransaction().commit();
                System.out.println("\nAdmin With Name " + adminName + " Was Successfully Deleted From The Database!\n");
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
            public static Admin findRecordByName(String find_adminName) {
                    Admin findadminObj = null;
                    try {
                    // Getting Session Object From SessionFactory
                    sessionObj = buildSessionFactory().openSession();
                    // Getting Transaction Object From Session Object
                    sessionObj.beginTransaction();

                    findadminObj = sessionObj.load(Admin.class, find_adminName);
                    } catch(Exception sqlException) {
                    if(null != sessionObj.getTransaction()) {
                    System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                    sessionObj.getTransaction().rollback();
                    }
                    sqlException.printStackTrace();
                    }
                    return findadminObj;
                    }

        // Method 4(c): This Method Is Used To Delete A Particular Record By Name From The Database Table
        public static void deleteRecordByName(String adminName, String adminServer) {
                try {
                        // Getting Session Object From SessionFactory
                        sessionObj = buildSessionFactory().openSession();
                        // Getting Transaction Object From Session Object
                        sessionObj.beginTransaction();

                        Query queryObj = sessionObj.createQuery("delete from Admin where adminName=:name");
                        queryObj.setParameter("name", adminName);
                        queryObj.executeUpdate();

                        // Committing The Transactions To The Database
                        sessionObj.getTransaction().commit();
                        System.out.println("\nAdmin With Name " + adminName + " Was Successfully Deleted From The Database!\n");
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

        // Method 4(c): This Method Is Used To Delete A Particular Record By Name From The Database Table
        public static void updateAdminNameRecordByName (String adminName, String newAdminName, String adminServer) {
                try {
                        // Getting Session Object From SessionFactory
                        sessionObj = buildSessionFactory().openSession();
                        // Getting Transaction Object From Session Object
                        sessionObj.beginTransaction();

                        StringBuilder sqlQuery = new StringBuilder("UPDATE Admin A set A.adminName = :newAdminName ")
                                .append("WHERE A.adminName = :name ")
                                .append("AND A.adminServer = :server ");
                        Query queryObj = sessionObj.createQuery(sqlQuery.toString());
                        queryObj.setParameter("newAdminName", newAdminName);
                        queryObj.setParameter("name", adminName);
                        queryObj.setParameter("server", adminServer);
                        queryObj.executeUpdate();

                        // Committing The Transactions To The Database
                        sessionObj.getTransaction().commit();
                        System.out.println("\nAdmin With Name " + adminName + " Was Successfully Updated! New name of the admin is: " + newAdminName + "\n");
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

        public static void updateAdminLinkRecordByName (String adminName, String newAdminLink, String adminServer) {
                try {
                        // Getting Session Object From SessionFactory
                        sessionObj = buildSessionFactory().openSession();
                        // Getting Transaction Object From Session Object
                        sessionObj.beginTransaction();

                        StringBuilder sqlQuery = new StringBuilder("UPDATE Admin A set A.adminLink = :newAdminLink ")
                                .append("WHERE A.adminName = :name ")
                                .append("AND A.adminServer = :server ");
                        Query queryObj = sessionObj.createQuery(sqlQuery.toString());
                        queryObj.setParameter("newAdminLink", newAdminLink);
                        queryObj.setParameter("name", adminName);
                        queryObj.setParameter("server", adminServer);
                        queryObj.executeUpdate();

                        // Committing The Transactions To The Database
                        sessionObj.getTransaction().commit();
                        System.out.println("\nAdmin With Name " + adminName + " Was Successfully Updated! New link of the admin is: " + newAdminLink + "\n");
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

        public static void updateAdminColorRecordByName (String adminName, String newAdminColor, String adminServer) {
                try {
                        // Getting Session Object From SessionFactory
                        sessionObj = buildSessionFactory().openSession();
                        // Getting Transaction Object From Session Object
                        sessionObj.beginTransaction();

                        StringBuilder sqlQuery = new StringBuilder("UPDATE Admin A set A.adminColor = :newAdminColor ")
                                .append("WHERE A.adminName = :name ")
                                .append("AND A.adminServer = :server ");
                        Query queryObj = sessionObj.createQuery(sqlQuery.toString());
                        queryObj.setParameter("newAdminColor", newAdminColor);
                        queryObj.setParameter("name", adminName);
                        queryObj.setParameter("server", adminServer);
                        queryObj.executeUpdate();

                        // Committing The Transactions To The Database
                        sessionObj.getTransaction().commit();
                        System.out.println("\nAdmin With Name " + adminName + " Was Successfully Updated! New link of the admin is: " + newAdminColor + "\n");
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
            
                    Query queryObj = sessionObj.createQuery("DELETE FROM Admin");
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
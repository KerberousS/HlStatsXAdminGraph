package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

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

                String sqlQuery = "SELECT Admin"  +
                        "WHERE adminServer = :server";
                Query queryObj = sessionObj.createQuery(sqlQuery);
                queryObj.setParameter("server", adminServer);
                queryObj.executeUpdate();

                adminsList = sessionObj.createQuery("SELECT A.adminName FROM Admin A").getResultList();
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
                System.out.println("\nAdmin With Name?= " + adminName + " Was Successfully Deleted From The Database!\n");
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
                        System.out.println("\nAdmin With Name?= " + adminName + " Was Successfully Deleted From The Database!\n");
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

                        String sqlQuery = "UPDATE Admin set adminName = :newAdminName "  +
                                "WHERE adminName = :name" +
                                "WHERE adminServer = :server";
                        Query queryObj = sessionObj.createQuery(sqlQuery);
                        queryObj.setParameter("newAdminName", newAdminName);
                        queryObj.setParameter("name", adminName);
                        queryObj.setParameter("server", adminServer);
                        queryObj.executeUpdate();

                        // Committing The Transactions To The Database
                        sessionObj.getTransaction().commit();
                        System.out.println("\nAdmin With Name?= " + adminName + " Was Successfully Updated! New name of the admin is: " + newAdminName + "\n");
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

                        String sqlQuery = "UPDATE Admin set adminLink = :newAdminLink "  +
                                "WHERE adminName = :name" +
                                "WHERE adminServer = :server";
                        Query queryObj = sessionObj.createQuery(sqlQuery);
                        queryObj.setParameter("newAdminName", newAdminLink);
                        queryObj.setParameter("name", adminName);
                        queryObj.setParameter("server", adminServer);
                        queryObj.executeUpdate();

                        // Committing The Transactions To The Database
                        sessionObj.getTransaction().commit();
                        System.out.println("\nAdmin With Name?= " + adminName + " Was Successfully Updated! New link of the admin is: " + newAdminLink + "\n");
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
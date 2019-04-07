package hibernate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import java.util.*;

public class DBOperations {

        static Session sessionObj;
        static SessionFactory sessionFactory;

        private static SessionFactory buildSessionFactory() {
                try {
                        StandardServiceRegistry standardRegistry =
                                new StandardServiceRegistryBuilder().configure("/hibernate.cfg.xml").build();
                        Metadata metaData =
                                new MetadataSources(standardRegistry).getMetadataBuilder().build();
                        sessionFactory = metaData.getSessionFactoryBuilder().build();
                } catch (Throwable th) {
                        System.err.println("Iniitial SessionFactory creation failed" + th);
                        throw new ExceptionInInitializerError(th);
                }
                return sessionFactory;
        }

        /*
         *
         *
         * SERVER METHODS
         *
         *
         */

        public static void createServerRecord (String serverName, String serverLink) {
                long count = 0;
                Server serverObj = null;
                try {
                        // Getting Session Object From SessionFactory
                        sessionObj = buildSessionFactory().openSession();
                        // Getting Transaction Object From Session Object
                        sessionObj.beginTransaction();

                        serverObj = new Server();
                        serverObj.setServerName(serverName);
                        serverObj.setServerHlstatsLink(serverLink);
                        sessionObj.save(serverObj);

                        // Committing The Transactions To The Database
                        sessionObj.getTransaction().commit();
                        System.out.println("\nSuccessfully Created '" + count + "' Records In The Database!\n");
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
        }

        @SuppressWarnings("unchecked")
        public static List displayServerRecords() {
                List<Server> serversList = new ArrayList();
                try {
                        // Getting Session Object From SessionFactory
                        sessionObj = buildSessionFactory().openSession();
                        // Getting Transaction Object From Session Object
                        sessionObj.beginTransaction();

                        serversList = sessionObj.createQuery("FROM Server").getResultList();
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
                return serversList;
        }

        public static void updateServerRecord(Long serverID, String newServerName, String newServerLink) {
                try {
                        // Getting Session Object From SessionFactory
                        sessionObj = buildSessionFactory().openSession();
                        // Getting Transaction Object From Session Object
                        sessionObj.beginTransaction();

                        // Creating Transaction Entity
                        Server serObj = sessionObj.get(Server.class, serverID);
                        serObj.setServerName(newServerName);
                        serObj.setServerHlstatsLink(newServerLink);

                        // Committing The Transactions To The Database
                        sessionObj.getTransaction().commit();
                        System.out.println("\nServer of ID ?= " + serverID + " Was Successfully Updated In The Database!\n");
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
        }

        public static void deleteServerRecord(Long serverID) {
                try {
                        // Getting Session Object From SessionFactory
                        sessionObj = buildSessionFactory().openSession();
                        // Getting Transaction Object From Session Object
                        sessionObj.beginTransaction();

                        Server servObj = sessionObj.load(Server.class, serverID);
                        sessionObj.delete(servObj);

                        // Committing The Transactions To The Database
                        sessionObj.getTransaction().commit();
                        System.out.println("\nServer With Name?= " + servObj.getServerName() + " Was Successfully Deleted From The Database!\n");
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
        }

        public static Server findServerRecord(Long serverID) {
                Server findserverObj = null;
                try {
                        // Getting Session Object From SessionFactory
                        sessionObj = buildSessionFactory().openSession();
                        // Getting Transaction Object From Session Object
                        sessionObj.beginTransaction();

                        findserverObj = sessionObj.load(Server.class, serverID);
                } catch (Exception sqlException) {
                        if (null != sessionObj.getTransaction()) {
                                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                                sessionObj.getTransaction().rollback();
                        }
                        sqlException.printStackTrace();
                }
                return findserverObj;
        }

        public static void deleteAllServerRecords() {
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

        /*
        *
        *
        * ADMIN METHODS
        *
        *
         */

        public static void createAdminRecord(String adminName, String adminLink, String adminColor, String adminServer) throws PersistenceException {
                long count = 0;
                Admin adminObj = null;
//                try {
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
                        if(sessionObj != null) {
                                sessionObj.close();
                        }
                }

        public static List displayAdminRecords(String serverName) {
                List<Server> adminsList = new ArrayList();
                try {
                        // Getting Session Object From SessionFactory
                        sessionObj = buildSessionFactory().openSession();
                        // Getting Transaction Object From Session Object
                        sessionObj.beginTransaction();

                        Query query = sessionObj.createQuery("FROM Admin A where A.adminServer = :admin_server");
                        query.setParameter("admin_server", serverName);
                        adminsList = query.getResultList();
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
                return adminsList;
        }

        public static void updateAdminRecord(Long adminID, String newAdminName, String newAdminColor, String newAdminLink, String newAdminServer) throws PersistenceException {
//                try {
                // Getting Session Object From SessionFactory
                sessionObj = buildSessionFactory().openSession();
                // Getting Transaction Object From Session Object
                sessionObj.beginTransaction();

                // Creating Transaction Entity
                Admin admObj = sessionObj.get(Admin.class, adminID);
                admObj.setAdminName(newAdminName);
                admObj.setAdminColor(newAdminColor);
                admObj.setAdminLink(newAdminLink);
                admObj.setAdminServer(newAdminServer);

                // Committing The Transactions To The Database
                sessionObj.getTransaction().commit();
                System.out.println("\nAdmin of ID ?= " + adminID + " Was Successfully Updated In The Database!\n");
//                } catch(Exception sqlException) {
//                if(null != sessionObj.getTransaction()) {
//                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
//                sessionObj.getTransaction().rollback();
//                }
//                sqlException.printStackTrace();
//                } finally {
                if (sessionObj != null) {
                        sessionObj.close();
                }
        }

        public static void deleteAdminRecord(Long adminID) {
                try {
                // Getting Session Object From SessionFactory
                sessionObj = buildSessionFactory().openSession();
                // Getting Transaction Object From Session Object
                sessionObj.beginTransaction();

                Admin servObj = findAdminRecord(adminID);
                sessionObj.delete(servObj);

                // Committing The Transactions To The Database
                sessionObj.getTransaction().commit();
                System.out.println("\nAdmin of ID " + adminID + " Was Successfully Deleted From The Database!\n");
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

        public static Admin findAdminRecord(Long adminID) {
                Admin findserverObj = null;
                try {
                        // Getting Session Object From SessionFactory
                        sessionObj = buildSessionFactory().openSession();
                        // Getting Transaction Object From Session Object
                        sessionObj.beginTransaction();

                        findserverObj = sessionObj.load(Admin.class, adminID);
                } catch (Exception sqlException) {
                        if (null != sessionObj.getTransaction()) {
                                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                                sessionObj.getTransaction().rollback();
                        }
                        sqlException.printStackTrace();
                }
                return findserverObj;
        }

        public static String findAdminColorByName(String adminName) {
                String admColor = null;
                try {
                        // Getting Session Object From SessionFactory
                        sessionObj = buildSessionFactory().openSession();
                        // Getting Transaction Object From Session Object
                        sessionObj.beginTransaction();

                        String q = "SELECT a.adminColor FROM Admin a WHERE a.adminName = :aname";
                        Query query = sessionObj.createQuery(q);
                        query.setParameter("aname", adminName);
                        admColor = query.getSingleResult().toString();
                } catch (Exception sqlException) {
                        if (null != sessionObj.getTransaction()) {
                                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                                sessionObj.getTransaction().rollback();
                        }
                        sqlException.printStackTrace();
                }
                return admColor;
        }

        public static void deleteAllAdminRecords() {
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
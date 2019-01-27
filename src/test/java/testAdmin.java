//import hibernate.AdminData;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.junit.Test;
//
//import javax.persistence.TypedQuery;
//import java.util.List;
//
//public class testAdmin {
//
//    @Test
//    public void crud() {
//        SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
//        Session session = sessionFactory.openSession();
//
//        create(session);
//        read(session);
//
//        update(session);
//        read(session);
//
//        delete(session);
//        read(session);
//
//        session.close();
//    }
//
//    private void delete(Session session) {
//        System.out.println("Deleting admin remzo record...");
//        AdminData Remzo = (AdminData) session.get(AdminData.class, "Remzo");
//
//        session.beginTransaction();
//        session.delete(Remzo);
//        session.getTransaction().commit();
//    }
//
//    private void update(Session session) {
//        System.out.println("Updating Admin Timon Info");
//        AdminData Timon = (AdminData) session.get(AdminData.class, "Timon");
//        Timon.setAdminServer("Plac Zabaw");
//        Timon.setAdminName("Timon");
//        Timon.setAdminColor("#FFA500");
//        Timon.setAdminLink("http://hlstatsx.strefagier.com.pl/hlstats.php?mode=playerinfo&player=793");
//        Timon.setAdminTotalConnectionTime(10);
//
//        session.beginTransaction();
//        session.saveOrUpdate(Timon);
//        session.getTransaction().commit();
//    }
//
//    private void create(Session session) {
//        System.out.println("Creating Admin records...");
//        AdminData Remzo = new AdminData();
//        Remzo.setAdminServer("Plac Zabaw");
//        Remzo.setAdminName("Remzo");
//        Remzo.setAdminColor("#FFA500");
//        Remzo.setAdminLink("http://hlstatsx.strefagier.com.pl/hlstats.php?mode=playerinfo&player=793");
//        Remzo.setAdminTotalConnectionTime(10);
//
//        AdminData Timon = new AdminData();
//        Timon.setAdminServer("Plac Zabaw");
//        Timon.setAdminName("Timon");
//        Timon.setAdminColor("#FFA500");
//        Timon.setAdminLink("http://hlstatsx.strefagier.com.pl/hlstats.php?mode=playerinfo&player=793");
//        Timon.setAdminTotalConnectionTime(10);
//
//        session.beginTransaction();
//        session.save(Remzo);
//        session.save(Timon);
//        session.getTransaction().commit();
//    }
//
//    private void read(Session session) {
//        TypedQuery<AdminData> q = session.createQuery("select _Admin from Admin _Admin");
//
//        List<AdminData> results  = q.getResultList();
//
//        System.out.println("Reading Admin records...");
//        System.out.printf("%-30.30s  %-30.30s%n", "Server", "Name");
//        for (AdminData c : results) {
//            System.out.printf("%-30.30s  %-30.30s%n", c.getAdminServer(), c.getAdminName());
//        }
//    }
//}

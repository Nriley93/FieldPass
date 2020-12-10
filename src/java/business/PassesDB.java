
package business;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author n.riley
 */
public class PassesDB {
    
    public static List<Passes> getPasses() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory(); 
        Session session = null;
        List<Passes> passes = null;
        try {
            session = sessionFactory.openSession();
            String qs = "from Passes order by Group_Name";
            Query q = session.createQuery(qs);
            passes = q.list();
        } catch(HibernateException e) {
            passes = null;
        } finally {
            session.close();
        }
        return passes;
    }
    public static String updtPass(Passes pass) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = null;
        String msg="";
        try{
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.merge(pass);
            session.getTransaction().commit();
            session.flush();
            session.refresh(pass);
            msg = "<br>Pass updated!";
        } catch(HibernateException e) {
            if(session != null) {
                session.getTransaction().rollback();
            }
            msg = "Pass update error: " + e.getMessage();
        } finally {
            session.close();
        }
        return msg;
    }
    public static boolean addPass(Passes pass) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory(); 
        Session session = null;
        boolean dbstat = false;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(pass);
            session.getTransaction().commit();
            dbstat = true;
        } catch(HibernateException e) {
            if(session != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
        return dbstat;
    }
    public static boolean deletePass(Passes pass) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory(); 
        Session session = null;
        boolean dbstat = false;
        try{
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(pass);
            session.getTransaction().commit();
            dbstat = true;
        } catch(HibernateException e) {
            if(session != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
        return dbstat;
    } 
}
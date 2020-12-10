
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
public class EventDB {
    public static List<Event> getEvents() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory(); 
        Session session = null;
        List<Event> events = null;
        try {
            session = sessionFactory.openSession();
            String qs = "from Event order by EVT_ID";
            Query q = session.createQuery(qs);
            events = q.list();
        } catch(HibernateException e) {
            events = null;
        } finally {
            session.close();
        }
        return events;
    }
    public static String persistEvent(Event s) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory(); 
        Session session = null;
        String msg="";
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.merge(s);
            session.getTransaction().commit();
            session.flush();
            session.refresh(s);
            msg = "<br>Event updated!";
        } catch(HibernateException e) {
            if(session != null) {
                session.getTransaction().rollback();
            }
            msg = "Event update error: " + e.getMessage();
        } finally{
            session.close();
        }
        return msg;
    }
}

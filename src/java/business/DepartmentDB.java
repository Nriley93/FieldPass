
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
public class DepartmentDB {
    public static List<Department> getDepartments() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory(); 
        Session session = null;
        List<Department> departments = null;
        try {
            session = sessionFactory.openSession();
            String qs = "from Department order by DEPTNAME";
            Query q = session.createQuery(qs);
            departments = q.list();
        } catch(HibernateException e) {
            departments = null;
        } finally {
            session.close();
        }
        return departments;
    }
    public static String persistDept(Department s) {
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
            msg = "<br>Department updated!";
        } catch(HibernateException e) {
            if(session != null) {
                session.getTransaction().rollback();
            }
            msg = "Department update error: " + e.getMessage();
        } finally{
            session.close();
        }
        return msg;
    }
}

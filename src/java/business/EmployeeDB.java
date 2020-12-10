
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
public class EmployeeDB {
    public static Employee getEmp(int empid) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = null;
        Employee emp = null;
        try {
            session = sessionFactory.openSession();
            Query q = session.getNamedQuery("dbget_Employee");
            q.setInteger("empid", empid);
            emp = (Employee) q.uniqueResult();
            
        } catch(HibernateException e) {
            emp = null;
        } finally {
            session.close();
        }
        return emp;
    }
    public static List<Employee> getEmpList() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory(); 
        Session session = null;
        List<Employee> empList = null;
        try {
            session = sessionFactory.openSession();
            String qs = "from Employee order by EMP_ID";
            Query q = session.createQuery(qs);
            empList = q.list();
        } catch(HibernateException e) {
            empList = null;
        } finally {
            session.close();
        }
        return empList;
    }
}

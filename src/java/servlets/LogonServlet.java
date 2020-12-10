
package servlets;

import business.Department;
import business.DepartmentDB;
import business.Employee;
import business.EmployeeDB;
import business.Event;
import business.EventDB;
import business.Passes;
import business.PassesDB;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author n.riley
 */
public class LogonServlet extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String URL="/Logon.jsp";
        String msg="";
        String action="";
        int pwdattempt=0, empid=0;
        Employee emp = null;

        try {
//            Authenticate employee
            empid = Integer.parseInt(request.getParameter("empid").trim());
            emp = EmployeeDB.getEmp(empid);
            if(emp == null) {
                msg = "Employee " + empid + " not found in DB.<br>";
            } else {    
                pwdattempt = Integer.parseInt(
                    request.getParameter("password"));
                emp.setPwdAttempt(pwdattempt);
                if(emp.isAuthenticated()) {
                    msg = "Employee " + emp.getFullname() + " authenticated<br>";
                    request.getSession().setAttribute("employee", emp);
                } else {
                    msg = "Employee " + emp.getFullname() + " not authenticated<br>";
                }
            } 
        } catch(NumberFormatException e) {
            msg = "Logon servlet error: " + e.getMessage() + "<br>";
        }
//        Gather data from database based on employeee
        if(emp != null && emp.isAuthenticated()) {
            List<Department> departments;
            List<Event> events;
            List<Passes> passes;
            try {
                //List of events
                    events = EventDB.getEvents();
                    if(events != null && events.size() > 0) {
                        request.getSession().setAttribute("events", events);
                    } else {
                        msg += "No Events read from file<br>";
                    }
                //List of passes
                    passes = PassesDB.getPasses();
                    if(passes != null && passes.size() > 0) {
                        request.getSession().setAttribute("passes", passes);
                    } else {
                        msg += "No Passes read from file<br>";
                    }
                //List of departments
                    departments = DepartmentDB.getDepartments();
                    if(departments != null && departments.size() > 0) {
                        URL="/Home.jsp";
                        request.getSession().setAttribute("departments", departments);
                    } else {
                        msg += "No departments read from file<br>";
                    }
            } catch(Exception e) {
                msg = "Logon servlet error: " + e.getMessage() + "<br>";
            }
        }
//        remove unused session attributes
        request.getSession().removeAttribute("pass");
        request.getSession().removeAttribute("dept");
        request.setAttribute("msg", msg);
        RequestDispatcher disp = 
                getServletContext().getRequestDispatcher(URL);
        disp.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

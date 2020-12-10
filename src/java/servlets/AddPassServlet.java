
package servlets;

import business.Department;
import business.Employee;
import business.EmployeeDB;
import business.Event;
import business.Passes;
import business.PassesDB;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class AddPassServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String URL="/Home.jsp", msg="";
        String name, evtDT = "", groupNm, date;
        int groupNum, deptid, empid, evtID;
        Date pd = null;
        List<Employee> empList;
        List<Passes> passes;
        List<Event> evt;
        Passes pass = new Passes();
        Employee emp = null;
        String action = request.getParameter("actiontype");

        
        try {
//            in progress
            if(action.equalsIgnoreCase("event")) {
                
                     List<Event> events = (List<Event>) request.getSession().getAttribute("events");        
//                        int evtID = Integer.parseInt(request.getParameter("event"));
                        int evtID2 = Integer.parseInt(request.getParameter("event"));
                        for(Event e : events) {
                            if(evtID2 == e.getEvtID()) {
                                request.getSession().setAttribute("event", e);
                            }
                        }
                           
                }
            
            
//            Gather input fields
            groupNum = Integer.parseInt(request.getParameter("groupNum"));
            groupNm = request.getParameter("groupNm");
            deptid = Integer.parseInt(request.getParameter("department"));
            name = request.getParameter("name");
//            validate fields
            if(groupNm.isEmpty()){msg += "Group name is required.<br>";} 
            else if(groupNum <= 0) {msg += "Group number input is invalid.<br>";} 
            else if(groupNum > 8){msg += "Group exceeds max limit.<br>";}
            if(deptid <= 0 || String.valueOf(deptid).isEmpty()) {
                msg += "Choose a department.<br>";
            }
            if(name.isEmpty()) {msg += "Escort name is empty";}
//            validate event selection
            try {
                evtID =  Integer.parseInt(request.getParameter("event"));
                evt = (List<Event>) request.getSession().getAttribute("events");
                for(Event e : evt) {
                    if(e.getEvtID() == evtID) {
                        pd = e.getEvtDT();
//                        pass.setPassDt(pd);
                    }
                }
//                date = request.getParameter("event");
//                if(date.isEmpty() || date == null) {
//                 msg += " Empty";
//                } else {
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                pd = sdf.parse(date);
//                }
            } catch(Exception e) {
                msg += "Event Field is Empty<br>";
            }

            if(msg.isEmpty()) {
//           gather list of employees
                empList = EmployeeDB.getEmpList();
                boolean empfound = false;
                for(Employee e : empList) {
//                find employee based on first and last name
                    if(name.equalsIgnoreCase(e.getFname()+", "+e.getLname())) {
                        pass.setEmpID(e.getEmpID());
                        pass.setEscort(name);
                        empfound = true;
                    }
                }// end of for
                if(!empfound) {
                        msg += "Employee name not found<br>";
                }
//        Build New Pass
                pass.setGroupNum(groupNum);
                pass.setGroupNm(groupNm);
                pass.setDeptID(deptid);
                pass.setPassDt(pd);
                msg += pass.isValid();
                msg += pass.isDuplicate(pass);
//        Add Pass to DB
                if(msg.isEmpty()) {
                    if(PassesDB.addPass(pass)) {
                        passes = PassesDB.getPasses();
                        request.getSession().setAttribute("passes", passes);
                        msg += "Pass " + pass.getPassID() + " added!<br>";
                    } else {
                        msg += "Pass " + pass.getPassID() +
                                " not added to system.<br>";                 
                    }
                }
            }
        } catch(NumberFormatException e) {
            msg += "Add Pass Error: " + e.getMessage() + "<br>";
        }
        
        request.getSession().removeAttribute("pass");
        request.getSession().removeAttribute("dept");
        request.setAttribute("msg",msg);
        RequestDispatcher disp = 
                getServletContext().getRequestDispatcher(URL);
        disp.forward(request,response);
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

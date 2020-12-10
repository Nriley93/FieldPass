
package servlets;

import business.Department;
import business.DepartmentDB;
import business.Passes;
import business.PassesDB;
import java.io.IOException;
import java.io.PrintWriter;
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
public class UpdatePassServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String URL="/Home.jsp", name="", msg="";
        int groupNum=0, deptid=0;
        boolean success = false;
        List<Department> departments;
        List<Passes> passes;
        Passes p;     

        try {
//            retrieve input fields
            p = (Passes) request.getSession().getAttribute("pass");      
            groupNum = Integer.parseInt(request.getParameter("groupNum"));
//            deptid = Integer.parseInt(request.getParameter("department"));
            name = request.getParameter("name");
            
            if(groupNum > 0  && p != null) {
//                Update pass
                p.setEscort(name);
                p.setGroupNum(groupNum);
                p.setGroupNm(request.getParameter("groupNm"));
                p.setDeptID(Integer.parseInt(request.getParameter("department")));
                msg += p.isValid();
                msg += p.isDuplicate(p);
                if(msg.isEmpty()) {
                    PassesDB.updtPass(p);
                    msg += "Pass " + p.getPassID() + " Updated!";
                    success = true;
                }
            } else
//                Delete pass based on groupnum
                if(groupNum == 0 && p != null) {
                PassesDB.deletePass(p);
                    msg += "Pass Deleted!<br>";
                    success = true;
            }
            if(success) {
                passes = PassesDB.getPasses();
                request.getSession().setAttribute("passes", passes);
            }
        } catch(NumberFormatException e) {
            msg += "Update Servlet Error: " + e.getMessage();
        }
//            remove un used attributes
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

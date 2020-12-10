package servlets;

import business.Department;
import business.Passes;
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
public class ExistingPassServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String URL="/Home.jsp", msg="";
        List<Department> departments;
        List<Passes> passes;
        Passes pass = new Passes();
        
        try{
//            Retrieve reservation selection
            String passid = request.getParameter("passid");
            if(passid == null || passid.isEmpty()){
                msg += "Select a Reservation";
            } else {
                passes = (List<Passes>) request.getSession().getAttribute("passes");
                departments = (List<Department>) request.getSession().getAttribute("departments");
                for(Passes p : passes) {
//                    put reservation on session
                    if(p.getPassID() == Integer.parseInt(passid)) {
                        pass = p;
                        request.getSession().setAttribute("pass", pass);
                    }
                }
                for(Department d : departments) {
//                    put department on session
                    if(d.getDeptID() == pass.getDeptID()) {
                        request.getSession().setAttribute("dept", d);
                    }
                }
            }
        } catch(NumberFormatException e) {
            msg += "Existing pass Error: " + e.getMessage();
        }
        
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

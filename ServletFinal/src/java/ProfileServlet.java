/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProfileServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        // Checking if the session exist already
        HttpSession session=request.getSession(false);
        
        if(session == null)
        {
            response.sendRedirect("index.html");
            
        } else
        {       
            // retrieving profile details from the session
            String mail = session.getAttribute("username").toString().trim();
            String fname = session.getAttribute("firstname").toString().trim();
            String sname = session.getAttribute("surname").toString().trim();
            String pass = session.getAttribute("password").toString().trim();
            
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            
            try {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">");
                out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css\"");
                out.println("integrity=\"sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M\" crossorigin=\"anonymous\">");
                out.println("<title>ServletTitle</title>");
                out.println("</head>");
                out.println("<body>");
                
                out.println("<div class=\"container\">");  
                
                out.println("<h2>Welcome, " + fname + " " + sname + "! Details of your account: </h2>");
                out.println("<table class=\"table table-striped\">");
                 // Table Header
                 out.println("<thead>");
                  out.println("<tr>");
                    out.println("<th>Firstname</th>");
                    out.println("<th>Surname</th>");
                    out.println("<th>Email</th>");
                    out.println("<th>Password</th>");
                  out.println("</tr>");
                 out.println("</thead>");
                  // Display values
                  out.println("<tr>");
                    out.println("<td>" + fname + "</td>");
                    out.println("<td>" + sname + "</td>");
                    out.println("<td>" + mail + "</td>");
                    out.println("<td>" + pass + "</td>");
                  out.println("</tr>");

                 out.println("</tbody>");
                out.println("</table>");
                out.println("<a href=\"LogOut\" class=\"btn btn-primary\" role=\"button\">Log out</a>");
                
                out.println("</div>");
                
                out.println("<script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\" crossorigin=\"anonymous\"></script>");
                out.println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js\" integrity=\"sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4\" crossorigin=\"anonymous\"></script>");
                out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js\" integrity=\"sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1\" crossorigin=\"anonymous\"></script>");

                out.println("</body>");
                out.println("</html>");
                
            } catch(Exception e)
            {
                e.printStackTrace();
            }
                
        }
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

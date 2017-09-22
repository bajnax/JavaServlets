
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class DeleteAccountServlet extends HttpServlet {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL="jdbc:mysql://localhost:3306/test";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "password";


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        // Checking if the session exist already
        HttpSession session=request.getSession(false);

        if(session == null)
        {
            // user must login or register before deleting account
            response.sendRedirect(request.getContextPath() + "index.html");
            
        } else // User is Logged In
        {
            Connection conn = null;
            Statement stmt = null;
            String sql = "";

            // retrieving profile details from the session
            String mail = session.getAttribute("username").toString().trim();

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();

            try{
                  // Register JDBC driver
                  Class.forName(JDBC_DRIVER).newInstance();
                  // Open a connection
                  conn = DriverManager.getConnection(DB_URL, USER, PASS);
                  // Execute SQL query
                  stmt = conn.createStatement();

                  sql = "DELETE FROM users WHERE email = \'" + mail
                          + "\'";
                  stmt.executeUpdate(sql);

                  out.println("<!DOCTYPE html>");
                  out.println("<html>");
                  out.println("<head>");
                  out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">");
                  out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css\"");
                  out.println("integrity=\"sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M\" crossorigin=\"anonymous\">");
                  out.println("</head>");
                  out.println("<body>");

                  out.println("<div class=\"container\">");

                  // output visible to the user
                  out.println("<h1>You are successfully unregistered!</h1>");
                  out.println("<a href=\"index.html\" class=\"btn btn-primary\" role=\"button\">Main page</a>");


                  out.println("</div>");
                  out.println("<script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\" crossorigin=\"anonymous\"></script>");
                  out.println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js\" integrity=\"sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4\" crossorigin=\"anonymous\"></script>");
                  out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js\" integrity=\"sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1\" crossorigin=\"anonymous\"></script>");

                  out.println("</body>");
                  out.println("</html>");

            } catch(SQLException se) {
               //Handle errors for JDBC
               se.printStackTrace();
            } catch(Exception e) {
               //Handle errors for Class.forName
               e.printStackTrace();
            } finally {
               //finally block used to close resources

               // closing the session
               session=request.getSession();
               session.invalidate();

               try {
                  if(stmt!=null)
                     stmt.close();
               } catch(SQLException se2) {
                 se2.printStackTrace();
               } // nothing we can do
               try {
                  if(conn!=null)
                  conn.close();
               } catch(SQLException se) {
                  se.printStackTrace();
               } //end finally try
            } //end try


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

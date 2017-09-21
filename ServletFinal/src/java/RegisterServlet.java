
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RegisterServlet extends HttpServlet {

  // JDBC driver name and database URL
  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  static final String DB_URL="jdbc:mysql://localhost:3306/test";

  //  Database credentials
  static final String USER = "root";
  static final String PASS = "password";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected static void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

      // Retrieve the values of the query parameters
      String fname = request.getParameter("fname");
      String sname = request.getParameter("sname");
      String email = request.getParameter("email");
      String password = request.getParameter("password");

      Connection conn = null;
      Statement stmt = null;
      PrintWriter out = response.getWriter();

      try
      {
        String sql;
        ResultSet rs;

        response.setContentType("text/html;charset=UTF-8");
        /* TODO output your page here. You may use following sample code. */
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">");
        out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css\"");
        out.println("integrity=\"sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M\" crossorigin=\"anonymous\">");
        out.println("<title>Servlet RegisterServlet</title>");
        out.println("</head>");
        out.println("<body>");

        out.println("<div class=\"container\">");
   
        if( (email.length() == 0) || (password.length() == 0) )
        {
            // Since email is a primary key in the DB and password is required for Login Form
            out.println("<h2>Fill in the Register Form at least email and password!</h2>");
            out.println("<a href=\"index.html\" class=\"btn btn-primary\" role=\"button\">Get back</a>");
        }
        else
        {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER).newInstance();
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // Execute SQL query
            stmt = conn.createStatement();

            sql = "SELECT * FROM users WHERE email = \'" + email
                    + "\'";
            rs = stmt.executeQuery(sql);
            
            if(!rs.next())
            {              
                sql = "INSERT INTO users (email,password,fname,sname)" +
                "VALUES (" + "\'" + email + "\'," + " \'" + password + "\'," +
                " \'" + fname + "\'," + " \'" + sname + "\')";
                stmt.executeUpdate(sql);

                out.println("<h1>Welcome! You are successfully registered! </h1>");
                out.println("<h2>Details of your account: </h2>");
                out.println("<table class=\"table table-striped\">");
                out.println("<thead>");
                  out.println("<tr>");
                    out.println("<th>Firstname</th>");
                    out.println("<th>Surname</th>");
                    out.println("<th>Email</th>");
                    out.println("<th>Password</th>");
                  out.println("</tr>");
                out.println("</thead>");

                  out.println("<tr>");

                   //Display values
                   out.println("<td>" + fname + "</td>");
                   out.println("<td>" + sname + "</td>");
                   out.println("<td>" + email + "</td>");
                   out.println("<td>" + password + "</td>");

                  out.println("</tr>");

                out.println("</tbody>");
                out.println("</table>");
            }
            else   // if the user is already registered in the service        
            {
                out.println("<h1>The user with specified email already exists: </h1>");
                out.println("<a href=\"index.html\" class=\"btn btn-primary\" role=\"button\">Get back</a>");
            }
        }

        out.println("</div>");
        out.println("<script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\" crossorigin=\"anonymous\"></script>");
        out.println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js\" integrity=\"sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4\" crossorigin=\"anonymous\"></script>");
        out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js\" integrity=\"sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1\" crossorigin=\"anonymous\"></script>");

        out.println("</body>");
        out.println("</html>");


      }catch(SQLException se)
      {
        //Handle errors for JDBC
        se.printStackTrace();
      } catch(Exception e) {
        //Handle errors for Class.forName
        e.printStackTrace();
      } finally {
        //finally block used to close resources
        try {
                if(stmt!=null)
                        stmt.close();
        } catch(SQLException se2) {
        }    // nothing we can do
        try {
                if(conn!=null)
                        conn.close();
        } catch(SQLException se) {
                se.printStackTrace();
        }    //end finally try
      }         //end try
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

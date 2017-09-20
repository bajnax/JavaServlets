
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL="jdbc:mysql://localhost:3306/test";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "password";

    protected static void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve the value of the query parameter "email" (from text field)
        String email = request.getParameter("email");
        // Retrieve the value of the query parameter "password" (from password field)
        String password = request.getParameter("password");

        Connection conn = null;
        Statement stmt = null;

        PrintWriter out = response.getWriter();

        try{
            response.setContentType("text/html;charset=UTF-8");
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">");
            out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css\"");
            out.println("integrity=\"sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M\" crossorigin=\"anonymous\">");
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");

            out.println("<div class=\"container\">");

            if(password == null || (password.trim()).length() == 0 || email == null || (email.trim()).length() == 0)
            {
                out.println("<h1>Wrong username or password. LogIn Unsuccessful!</h1>");
                  // Get null if the parameter is missing from query string.
                  // Get empty string or string of white spaces if user did not fill in
                if (email == null || (email.trim()).length() == 0) {
                   out.println("<p>Name: MISSING</p>");
                } else {
                   out.println("<p>Name: " + email + "</p>");
                }

                if (password == null || (password.trim()).length() == 0) {
                   out.println("<p>Password: MISSING</p>");
                } else {
                   out.println("<p>Password: " + password + "</p>");
                }
                
                out.println("<a href=\"index.html\" class=\"btn btn-primary\" role=\"button\">Get back</a>");

                out.println("</div>");
                out.println("<script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\" crossorigin=\"anonymous\"></script>");
                out.println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js\" integrity=\"sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4\" crossorigin=\"anonymous\"></script>");
                out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js\" integrity=\"sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1\" crossorigin=\"anonymous\"></script>");

                out.println("</body>");
                out.println("</html>");
            }
            else
            {
                // Register JDBC driver
                Class.forName(JDBC_DRIVER).newInstance();

                // Open a connection
                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                // Execute SQL query
                stmt = conn.createStatement();
                String sql;
                
                // 'email' is a primary key. Therefore, only one record
                // will be retreived, if present
                sql = "SELECT * FROM users WHERE email = \'" + email + "\'";
                ResultSet rs = stmt.executeQuery(sql);
                
                String fname = "", sname = "", mail = "", pass = "";
                
                //check the size of ResultSet
                int size= 0;
                if (rs != null)
                {
                  rs.beforeFirst();
                  rs.last();
                  size = rs.getRow();
                }
                
                if(size != 0)
                {
                    rs.beforeFirst();
                    while(rs.next())
                    {
                        // Retrieve by column name
                        fname = rs.getString("fname");
                        sname = rs.getString("sname");
                        mail = rs.getString("email");
                        pass = rs.getString("password");

                        // matching email and password = LogIn
                        if(mail.trim().equals(email) && pass.trim().equals(password))
                        {
                            out.println("<h2>Details of your account: </h2>");
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
                        }
                        else
                        {
                            // mismatch of password and email
                            out.println("<h1>Wrong password! </h1>");
                            out.println("<a href=\"index.html\" class=\"btn btn-primary\" role=\"button\">Get back</a>");
                        }             
                    }
                }
                else
                {
                    // database does not contain a record with specified email address
                    out.println("<h1>Wrong email address!</h1>");
                    out.println("<a href=\"index.html\" class=\"btn btn-primary\" role=\"button\">Get back</a>");
                }
                
                out.println("</div>");

                out.println("<script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\" crossorigin=\"anonymous\"></script>");
                out.println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js\" integrity=\"sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4\" crossorigin=\"anonymous\"></script>");
                out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js\" integrity=\"sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1\" crossorigin=\"anonymous\"></script>");

                out.println("</body>");
                out.println("</html>");

                // Clean-up environment
                rs.close();
                stmt.close();
                conn.close();
            }

        }catch(SQLException se) {
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
         } // nothing we can do
         try {
            if(conn!=null)
            conn.close();
         } catch(SQLException se) {
            se.printStackTrace();
         } //end finally try
      } //end try
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

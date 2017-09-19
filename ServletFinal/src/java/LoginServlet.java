
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
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");

            if(password == null || (password.trim()).length() == 0 || email == null || (email.trim()).length() == 0)
            {
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
                 sql = "SELECT * FROM users WHERE email = \'" + email
                         + "\' AND password = \'" + password + "\'";
                 ResultSet rs = stmt.executeQuery(sql);

                 // Extract data from result set
                 while(rs.next())
		{
                    System.out.println("smth arrived");
                    //Retrieve by column name
                    String em = rs.getString("email");
                    String pass = rs.getString("password");

                    //Display values
                    out.println("email: " + em + "<br>");
                    out.println("password: " + pass + "<br>");
                 }
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

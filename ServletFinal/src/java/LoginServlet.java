
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

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

        // JDBC driver name and database URL
        static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        static final String DB_URL="jdbc:mysql://localhost/TEST";

        //  Database credentials
        static final String USER = "root";
        static final String PASS = "password";


        // Retrieve the value of the query parameter "email" (from text field)
        String email = request.getParameter("email");
        // Retrieve the value of the query parameter "password" (from password field)
        String password = request.getParameter("password");

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
                 Class.forName("com.mysql.jdbc.Driver");

                 // Open a connection
                 Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

                 // Execute SQL query
                 Statement stmt = conn.createStatement();
                 String sql;
                 sql = "SELECT id, first, last, age FROM Employees";
                 ResultSet rs = stmt.executeQuery(sql);

                 // Extract data from result set
                 while(rs.next()){
                    //Retrieve by column name
                    int id  = rs.getInt("id");
                    int age = rs.getInt("age");
                    String first = rs.getString("first");
                    String last = rs.getString("last");

                    //Display values
                    out.println("ID: " + id + "<br>");
                    out.println(", Age: " + age + "<br>");
                    out.println(", First: " + first + "<br>");
                    out.println(", Last: " + last + "<br>");
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

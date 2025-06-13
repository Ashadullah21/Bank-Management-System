import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class RegisterUser extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO users(name, email, password) VALUES (?, ?, ?)");
            ps.setString(1, req.getParameter("name"));
            ps.setString(2, req.getParameter("email"));
            ps.setString(3, req.getParameter("password"));
            ps.executeUpdate();
            res.sendRedirect("../frontend/login.html");
        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().println("Registration failed.");
        }
    }
}
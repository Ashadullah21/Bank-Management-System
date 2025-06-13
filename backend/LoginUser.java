import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class LoginUser extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email=? AND password=?");
            ps.setString(1, req.getParameter("email"));
            ps.setString(2, req.getParameter("password"));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                HttpSession session = req.getSession();
                session.setAttribute("userID", rs.getInt("id"));
                session.setAttribute("userName", rs.getString("name"));
                session.setAttribute("balance", rs.getDouble("balance"));
                res.sendRedirect("../frontend/dashboard.html");
            } else {
                res.getWriter().println("Invalid credentials.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().println("Login error.");
        }
    }
}
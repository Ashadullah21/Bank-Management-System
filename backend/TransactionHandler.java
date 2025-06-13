import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class TransactionHandler extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            res.getWriter().println("Not logged in.");
            return;
        }
        int userId = (int) session.getAttribute("userID");
        String type = req.getParameter("type");
        double amount = Double.parseDouble(req.getParameter("amount"));
        try {
            Connection con = DBConnection.getConnection();
            con.setAutoCommit(false);
            PreparedStatement balStmt = con.prepareStatement("SELECT balance FROM users WHERE id=?");
            balStmt.setInt(1, userId);
            ResultSet rs = balStmt.executeQuery();
            double currentBalance = 0;
            if (rs.next()) currentBalance = rs.getDouble("balance");
            if (type.equals("Withdraw") && currentBalance < amount) {
                res.getWriter().println("Insufficient funds.");
                return;
            }
            double updated = type.equals("Deposit") ? currentBalance + amount : currentBalance - amount;
            PreparedStatement updateBal = con.prepareStatement("UPDATE users SET balance=? WHERE id=?");
            updateBal.setDouble(1, updated);
            updateBal.setInt(2, userId);
            updateBal.executeUpdate();
            PreparedStatement trx = con.prepareStatement("INSERT INTO transactions(user_id, type, amount) VALUES (?, ?, ?)");
            trx.setInt(1, userId);
            trx.setString(2, type);
            trx.setDouble(3, amount);
            trx.executeUpdate();
            con.commit();
            res.sendRedirect("../frontend/dashboard.html");
        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().println("Transaction failed.");
        }
    }
}
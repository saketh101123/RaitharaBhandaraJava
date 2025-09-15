package com.rb.servlet;

import com.rb.dao.FarmerDAO;
import com.rb.dao.UserDAO;
import com.rb.model.Farmer;
import com.rb.model.User;
import com.rb.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="LoginServlet", urlPatterns={"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserDAO userDAO = new UserDAO();
        User u = userDAO.findByUsername(username);
        boolean ok = (u != null) && u.getPasswordHash().equals(PasswordUtil.sha256(password));

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            if (!ok) {
                out.println("<!doctype html><html><body>");
                out.println("<h3>Login failed</h3>");
                out.println("<a href='login.html'>Try again</a>");
                out.println("</body></html>");
                return;
            }
            // create session
            HttpSession session = req.getSession(true);
            session.setAttribute("username", u.getUsername());
            session.setAttribute("role", u.getRole());

            // Try to fetch farmerId by mapping username as email (simple demo)
            FarmerDAO farmerDAO = new FarmerDAO();
            Farmer f = farmerDAO.findByEmail(u.getUsername().contains("@") ? u.getUsername() : null);
            if (f != null) {
                session.setAttribute("farmerId", f.getFarmerId());
                session.setAttribute("farmerLocation", f.getLocation());
            }

            out.println("<!doctype html><html><body>");
            out.println("<h2>Welcome, " + u.getUsername() + "!</h2>");
            out.println("<ul>");
            out.println("<li><a href='addCrop.html'>Add Crop</a></li>");
            out.println("<li><a href='searchStorage.html'>Search Cold Storage</a></li>");
            out.println("<li><a href='dashboard'>Dashboard</a></li>");
            out.println("<li><a href='logout'>Logout</a></li>");
            out.println("</ul>");
            out.println("</body></html>");
        }
    }
}

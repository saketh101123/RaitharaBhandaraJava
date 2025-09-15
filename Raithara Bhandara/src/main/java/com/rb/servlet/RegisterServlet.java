package com.rb.servlet;

import com.rb.dao.FarmerDAO;
import com.rb.dao.UserDAO;
import com.rb.model.Farmer;
import com.rb.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="RegisterServlet", urlPatterns={"/register"})
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String location = req.getParameter("location");
        String username = req.getParameter("username"); // can be same as email
        String password = req.getParameter("password");

        if (name == null || email == null || username == null || password == null
                || name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing fields");
            return;
        }

        UserDAO userDAO = new UserDAO();
        FarmerDAO farmerDAO = new FarmerDAO();
        String hash = PasswordUtil.sha256(password);

        // create user
        int uid = userDAO.create(username, hash, "FARMER");

        // create farmer
        Farmer f = new Farmer();
        f.setName(name);
        f.setEmail(email);
        f.setPhone(phone);
        f.setLocation(location);
        int fid = farmerDAO.create(f);

        // Simple confirmation page
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!doctype html><html><head><title>Registered</title></head><body>");
            out.println("<h2>Registration successful!</h2>");
            out.println("<p>Welcome, " + name + ".</p>");
            out.println("<a href='login.html'>Go to Login</a>");
            out.println("</body></html>");
        }
    }
}

package com.rb.servlet;

import com.rb.dao.CropDAO;
import com.rb.model.Crop;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

@WebServlet(name="AddCropServlet", urlPatterns={"/add-crop"})
public class AddCropServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect("login.html");
            return;
        }
        Integer farmerId = (Integer) session.getAttribute("farmerId");
        if (farmerId == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Farmer profile not linked to user.");
            return;
        }

        String cropName = req.getParameter("crop_name");
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        Date harvestDate = Date.valueOf(req.getParameter("harvest_date"));
        Date expiryDate = Date.valueOf(req.getParameter("expiry_date"));

        Crop c = new Crop();
        c.setFarmerId(farmerId);
        c.setCropName(cropName);
        c.setQuantity(quantity);
        c.setHarvestDate(harvestDate);
        c.setExpiryDate(expiryDate);

        new CropDAO().create(c);

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!doctype html><html><body>");
            out.println("<h3>Crop added!</h3>");
            out.println("<a href='dashboard'>Go to Dashboard</a>");
            out.println("</body></html>");
        }
    }
}

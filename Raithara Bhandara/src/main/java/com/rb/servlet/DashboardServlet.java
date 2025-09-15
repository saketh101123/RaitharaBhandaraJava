package com.rb.servlet;

import com.rb.dao.BookingDAO;
import com.rb.dao.ColdStorageDAO;
import com.rb.dao.CropDAO;
import com.rb.model.Booking;
import com.rb.model.ColdStorage;
import com.rb.model.Crop;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name="DashboardServlet", urlPatterns={"/dashboard"})
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect("login.html");
            return;
        }
        Integer farmerId = (Integer) session.getAttribute("farmerId");
        CropDAO cropDAO = new CropDAO();
        BookingDAO bookingDAO = new BookingDAO();
        ColdStorageDAO csDAO = new ColdStorageDAO();

        List<Crop> crops = cropDAO.findByFarmer(farmerId);
        List<Booking> bookings = bookingDAO.findByFarmer(farmerId);

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!doctype html><html><body>");
            out.println("<h2>Farmer Dashboard</h2>");
            out.println("<p>User: " + session.getAttribute("username") + "</p>");
            out.println("<ul>");
            out.println("<li><a href='addCrop.html'>Add Crop</a></li>");
            out.println("<li><a href='search-storage'>Search/Book Storage</a></li>");
            out.println("<li><a href='logout'>Logout</a></li>");
            out.println("</ul><hr/>");

            out.println("<h3>Your Crops</h3>");
            if (crops.isEmpty()) {
                out.println("<p>No crops added yet.</p>");
            } else {
                out.println("<table border='1' cellpadding='6'>");
                out.println("<tr><th>Name</th><th>Qty</th><th>Harvest</th><th>Expiry</th></tr>");
                for (Crop c : crops) {
                    out.println("<tr>");
                    out.println("<td>"+c.getCropName()+"</td>");
                    out.println("<td>"+c.getQuantity()+"</td>");
                    out.println("<td>"+c.getHarvestDate()+"</td>");
                    out.println("<td>"+c.getExpiryDate()+"</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }

            out.println("<h3>Your Bookings</h3>");
            if (bookings.isEmpty()) {
                out.println("<p>No bookings yet.</p>");
            } else {
                out.println("<table border='1' cellpadding='6'>");
                out.println("<tr><th>ID</th><th>Crop</th><th>Storage</th><th>Status</th><th>When</th></tr>");
                for (Booking b : bookings) {
                    ColdStorage cs = csDAO.findById(b.getStorageId());
                    out.println("<tr>");
                    out.println("<td>"+b.getBookingId()+"</td>");
                    out.println("<td>"+b.getCropId()+"</td>");
                    out.println("<td>"+(cs!=null?cs.getName():"")+"</td>");
                    out.println("<td>"+b.getStatus()+"</td>");
                    out.println("<td>"+b.getBookingDate()+"</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }

            out.println("</body></html>");
        }
    }
}

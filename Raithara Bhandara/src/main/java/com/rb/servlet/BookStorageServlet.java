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

@WebServlet(name="BookStorageServlet", urlPatterns={"/book-storage"})
public class BookStorageServlet extends HttpServlet {

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

        int storageId = Integer.parseInt(req.getParameter("storage_id"));
        int cropId = Integer.parseInt(req.getParameter("crop_id"));

        CropDAO cropDAO = new CropDAO();
        ColdStorageDAO csDAO = new ColdStorageDAO();
        BookingDAO bookingDAO = new BookingDAO();

        Crop crop = cropDAO.findById(cropId);
        ColdStorage cs = csDAO.findById(storageId);

        // capacity check
        int used = csDAO.currentUsedCapacity(storageId);
        if (used + crop.getQuantity() > cs.getCapacity()) {
            resp.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!doctype html><html><body>");
                out.println("<h3>Cannot book. Storage over capacity!</h3>");
                out.println("<p>Used: "+used+" | Trying to add: "+crop.getQuantity()+" | Capacity: "+cs.getCapacity()+"</p>");
                out.println("<a href='search-storage'>Back</a>");
                out.println("</body></html>");
            }
            return;
        }

        Booking b = new Booking();
        b.setFarmerId(farmerId);
        b.setCropId(cropId);
        b.setStorageId(storageId);
        b.setStatus("CONFIRMED");
        bookingDAO.create(b);

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!doctype html><html><body>");
            out.println("<h3>Booking confirmed!</h3>");
            out.println("<a href='dashboard'>Go to Dashboard</a>");
            out.println("</body></html>");
        }
    }
}

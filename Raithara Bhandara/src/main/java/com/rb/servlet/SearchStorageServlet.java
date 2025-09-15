package com.rb.servlet;

import com.rb.dao.ColdStorageDAO;
import com.rb.dao.CropDAO;
import com.rb.model.ColdStorage;
import com.rb.model.Crop;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name="SearchStorageServlet", urlPatterns={"/search-storage"})
public class SearchStorageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect("login.html");
            return;
        }
        Integer farmerId = (Integer) session.getAttribute("farmerId");
        String location = req.getParameter("location");
        if (location == null || location.isEmpty()) {
            location = (String) session.getAttribute("farmerLocation");
        }

        ColdStorageDAO csDAO = new ColdStorageDAO();
        List<ColdStorage> storages = csDAO.findByLocation(location);

        CropDAO cropDAO = new CropDAO();
        List<Crop> crops = cropDAO.findByFarmer(farmerId);

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!doctype html><html><body>");
            out.println("<h2>Cold Storages in " + location + "</h2>");
            out.println("<form method='get' action='search-storage'>");
            out.println("Location: <input name='location'/> <button type='submit'>Search</button>");
            out.println("</form><hr/>");

            if (storages.isEmpty()) {
                out.println("<p>No storages found.</p>");
            } else {
                out.println("<table border='1' cellpadding='6'>");
                out.println("<tr><th>Name</th><th>Capacity</th><th>Contact</th><th>Book</th></tr>");
                for (ColdStorage cs : storages) {
                    out.println("<tr>");
                    out.println("<td>"+cs.getName()+"</td>");
                    out.println("<td>"+cs.getCapacity()+"</td>");
                    out.println("<td>"+(cs.getContactInfo()==null?"":cs.getContactInfo())+"</td>");
                    out.println("<td>");
                    out.println("<form method='post' action='book-storage'>");
                    out.println("<input type='hidden' name='storage_id' value='"+cs.getStorageId()+"'/>");
                    out.println("Crop: <select name='crop_id'>");
                    for (Crop c : crops) {
                        out.println("<option value='"+c.getCropId()+"'>"+c.getCropName()+" ("+c.getQuantity()+")</option>");
                    }
                    out.println("</select>");
                    out.println("<button type='submit'>Book</button>");
                    out.println("</form>");
                    out.println("</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }
            out.println("<p><a href='dashboard'>Back to Dashboard</a></p>");
            out.println("</body></html>");
        }
    }
}

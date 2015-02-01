/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alberto
 */
public class AddCommentServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String user = request.getParameter("username"), comment = request.getParameter("commentText"),
                id = request.getParameter("id");
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Key k = KeyFactory.createKey("Post", Long.parseLong(id));
        try {
            Entity e = ds.get(k);
            EmbeddedEntity c = new EmbeddedEntity();
            c.setProperty("autor", URLEncoder.encode(user, "UTF-8"));
            c.setProperty("texto", URLEncoder.encode(comment, "UTF-8"));
            c.setProperty("fecha", URLEncoder.encode(DateFormat.getInstance().format(new Date()), "UTF-8"));
            List<EmbeddedEntity> comments = (List<EmbeddedEntity>) e.getProperty("comments");
            if (comments == null) comments = new ArrayList<>();
            comments.add(c);
            e.setProperty("comments", comments);
			ds.put(e);
            PrintWriter pw = response.getWriter();
            pw.println("<div>");
            pw.println("<b>" + user + ":</b><br/>");
            pw.println("<small>" + URLDecoder.decode(c.getProperty("fecha").toString(), "UTF-8") + "</small><br/>");
            pw.println(comment);
            pw.println("</div>");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

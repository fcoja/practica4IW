/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author FRANCISCOJAVIER
 */
public class AddPostServlet extends HttpServlet {

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
        String type = request.getParameter("type");
        switch (type) {
            case "new":
                getServletContext().getRequestDispatcher("/addpost.jsp").forward(request, response);
                break;
            case "commit":
                String user = request.getParameter("user"),
                 title = request.getParameter("title"),
                 desc = request.getParameter("description"),
                 city = request.getParameter("city"),
                 thumbnail = request.getParameter("thumbnail");
                String[] images = request.getParameterValues("image");
                StringBuilder image = new StringBuilder(thumbnail != null
                        ? thumbnail.substring(0, thumbnail.length() - 4) + "_t.jpg" : "#");
                if (images != null) {
                    for (String i : images) {
                        image.append(";").append(i);
                    }
                }
                
                Entity post = new Entity("Post");
                post.setProperty("autor",URLEncoder.encode(user, "UTF-8"));
                post.setProperty("ciudad",URLEncoder.encode(city, "UTF-8"));
                post.setProperty("fecha",new Date());
                post.setProperty("imagen",image.toString());
                post.setProperty("texto",URLEncoder.encode(desc, "UTF-8"));
                post.setProperty("titulo",URLEncoder.encode(title, "UTF-8"));
                DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
                datastore.put(post);
                response.sendRedirect(getServletContext().getContextPath() + "/index");
                break;
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

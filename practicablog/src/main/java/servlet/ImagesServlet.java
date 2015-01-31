/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.SearchParameters;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alberto
 */
public class ImagesServlet extends HttpServlet {

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
        try {
            String city = request.getReader().readLine();
            Flickr api = new Flickr("d5da76b17d9aecf7269aad552ffffdc1", "483b74baa31f129c", new REST());
            SearchParameters parameters = new SearchParameters();
            parameters.setTags(new String[]{city});
            PhotoList<Photo> list = api.getPhotosInterface().search(parameters, 20, 0);
            PrintWriter pw = response.getWriter();
            if (list.isEmpty()) {
                pw.println("<h1>No existen fotos de la ciudad buscada.<h1/>");
            } else {
                pw.println("<h1>Elija una foto de portada:<h1/><br/>");
                for (Photo p : list) {
                    pw.println("<label><input type=\"radio\" name=\"thumbnail\" value=\""
                            + p.getMediumUrl() + "\" /><img src=\"" + p.getMediumUrl() + "\" width=\"400px\""
                            + " height=\"400px\"></label>");
                }
                pw.println("<br/><h1>Elija varias fotos para mostrar:<h1/><br/>");
                for (Photo p : list) {
                    pw.println("<label><input type=\"checkbox\" name=\"image\" value=\""
                            + p.getMediumUrl() + "\" /><img src=\"" + p.getMediumUrl() + "\" width=\"400px\""
                            + " height=\"400px\"></label>");
                }
            }
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

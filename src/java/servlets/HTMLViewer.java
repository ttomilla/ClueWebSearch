/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import java.io.*;
import java.util.Properties;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import searcher.*;

/**
 *
 * @author dganguly
 */
public class HTMLViewer extends HttpServlet {

    String propFileName;
    WT10GRetriever retriever;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            propFileName= config.getInitParameter("configFile");
            //retriever = new WT10GRetriever(propFileName);
            
            // Create an object for either WT10G or ClueWeb retriever
            Properties prop = new Properties();
            prop.load(new FileReader(propFileName));
            String retrClassName = prop.getProperty("retriever.type");
            if (retrClassName.equals("WT10G"))
                retriever = new WT10GRetriever(propFileName);
            else
                retriever = new ClueWebRetriever(propFileName);
        }
        catch (Exception ex) {
            ex.printStackTrace();            
        }
    }

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String queryStr = request.getParameter("docid");

            WT10GRetriever retriever = new WT10GRetriever(propFileName);
            String html = retriever.getHTMLFromDocId(request.getParameter("index"), queryStr);

            out.println(html);
            out.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
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

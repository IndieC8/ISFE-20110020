/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Negocios.Cifrado.Cifrado;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lupe
 */
public class Impresa extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
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
        Sql s = new Sql();
        try {
            /*
             * TODO output your page here. You may use following sample code.
             */
            if ("Buscar".equals(request.getParameter("Factura"))) {

                String idUsuario = request.getParameter("idUsuario");
                idUsuario = Cifrado.decodificarBase64(idUsuario);
                String fecha = request.getParameter("Fecha");  /*
                 * aaaa/mm/dd
                 */

                String consulta = "SELECT COUNT(*) as contador FROM factura WHERE idUsuario = " + Integer.parseInt(idUsuario) + " AND fechaElaboracion = " + fecha + "";
                ResultSet rs;
                rs = s.consulta(consulta);


                int Columnas = 0;
                if (rs.next()) {
                    Columnas = rs.getInt("contador");
                }

                if (Columnas > 0) {
                    out.println("<table align= \"center\" id=\"ResultadoBusquedaFactua\">");
                    out.println("<tr>");
                    out.println("<th  align=\"center\">&nbsp; &nbsp; &nbsp; Nombre de la Factura &nbsp; &nbsp;</th>");
                    out.println("<th  align=\"center\">&nbsp; &nbsp; &nbsp; Fecha &nbsp; &nbsp;</th>");
                    out.println("<th  align=\"center\">&nbsp; &nbsp;</th>");
                    out.println("</tr>");

                    consulta = "SELECT idFactura,fechaElaboracion, nombreXML FROM factura WHERE idUsuario = " + Integer.parseInt(idUsuario) + " AND fechaElaboracion = " + fecha + "";
                    rs = s.consulta(consulta);
                    while (rs.next()) {
                        out.println("<tr>");
                        out.println("<td Style=\"font-size: 10px;\" align=\"center\">" + rs.getString("nombreXML") + "</td>");
                        out.println("<td Style=\"font-size: 10px;\" align=\"center\">" + rs.getString("fechaElaboracion") + "</td>");
                        out.println("<td align=\"center\"><span><img src=\"../images/formularios/pdfICON.jpg\" title=\"Generar PDF\" alt=\"Generar PDF\" style=\"cursor:pointer\" onClick=\"GenerarPDF(" + rs.getInt("idFactura") + ")\"/></span></td>");
                        out.println("</tr>");
                    }
                } else {
                    out.println("0");
                }
            }
        } catch (InstantiationException ex) {
            Logger.getLogger(Impresa.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Impresa.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Impresa.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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

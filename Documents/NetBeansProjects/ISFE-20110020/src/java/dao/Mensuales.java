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
import java.util.Date;
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
public class Mensuales extends HttpServlet {

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
        Sql sql = new Sql();
        try {
            if("Cancelar".equals(request.getParameter("Factura"))){
                String idFolio = request.getParameter("idFolio");
                String consulta = "UPDATE folios SET usado = 2 WHERE idFolio = "+ Integer.parseInt(idFolio) +"";
                sql.ejecuta(consulta);
            }else if ("Buscar".equals(request.getParameter("Factura"))) {
                String id = request.getParameter("idUsuario");
                id = Cifrado.decodificarBase64(id);

                Date fecha = new Date();
                String mes = String.valueOf(fecha.getMonth() + 1);
                int Columnas = 0;
                                
                String consulta = "SELECT COUNT(*) as contador FROM factura f, folios fo WHERE f.idUsuario = " + Integer.parseInt(id) + " AND MONTH(f.fechaElaboracion) = "+ mes +" AND f.idFolio = fo.idFolio AND fo.usado = 1" ;
                ResultSet rs = sql.consulta(consulta);
                if(rs.next()){
                    Columnas = rs.getInt("contador");
                }
                
                if(Columnas > 0){
                    out.println("<table align= \"center\" id=\"FacturasCancelar\">");
                    out.println("<tr>");
                    out.println("<th  align=\"center\">&nbsp; &nbsp; &nbsp; Nombre de la Factura &nbsp; &nbsp;</th>");
                    out.println("<th  align=\"center\">&nbsp; &nbsp; &nbsp; Fecha &nbsp; &nbsp;</th>");
                    out.println("<th  align=\"center\">&nbsp; &nbsp;</th>");
                    out.println("</tr>");
                    
                    consulta = "SELECT f.nombreXML, f.idFolio, f.fechaElaboracion FROM factura f, folios fo WHERE f.idUsuario = " + Integer.parseInt(id) + " AND MONTH(f.fechaElaboracion) = "+ mes +" AND f.idFolio = fo.idFolio AND fo.usado = 1";
                    rs = sql.consulta(consulta);
                    while(rs.next()){
                        out.println("<tr>");
                        out.println("<td id=\"Nombre_"+rs.getInt("idFolio") +"\" Style=\"font-size: 10px;\" align=\"center\">" + rs.getString("nombreXML") + "</td>");
                        out.println("<td Style=\"font-size: 10px;\" align=\"center\">" + rs.getString("fechaElaboracion") + "</td>");
                        out.println("<td align=\"center\"><span><img src=\"../images/formularios/cancel.jpg\" title=\"Cancelar XML\" alt=\"Cancelar XML\" style=\"cursor:pointer\" onClick=\"Cancelar(" + rs.getInt("idFolio") + ")\"/></span></td>");
                        out.println("</tr>");
                    }
                    
                    out.println("</table>");
                    out.println("<br/><br/>");
                }else{
                    out.println("0");
                }
                
                


            }
        } catch (InstantiationException ex) {
            Logger.getLogger(Mensuales.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Mensuales.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            out.println(ex);
            //Logger.getLogger(Mensuales.class.getName()).log(Level.SEVERE, null, ex);
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

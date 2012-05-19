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
 * @author kawatoto
 */
public class Producto extends HttpServlet {

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
            throws ServletException, IOException, SQLException, InstantiationException, IllegalAccessException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Sql sql = new Sql();
            
            if("Actualizar".equals(request.getParameter("Producto"))){
                String nombreProducto = request.getParameter("nombreProducto");
                String descripcion = request.getParameter("descripcion");
                String unidad = request.getParameter("unidad");
                String Valor = request.getParameter("valorUnitario");
                String idProducto = request.getParameter("idProducto");
                String sentencia = "UPDATE producto SET nombreProducto='" + nombreProducto + "',descripcionProducto='" + descripcion + "',unidad='" + unidad + "',valorUnitario=" + Double.parseDouble(Valor) + "WHERE idProducto="+ Integer.parseInt(idProducto) +";";
                sql.ejecuta(sentencia);
                
            }else if ("Agregar".equals(request.getParameter("Producto"))) {
                String aux = request.getParameter("idUsuario");
                aux = Cifrado.decodificarBase64(aux);
                String nombreProducto = request.getParameter("nombreProducto");
                String descripcion = request.getParameter("descripcion");
                String unidad = request.getParameter("unidad");
                String Valor = request.getParameter("valorUnitario");
                String sentencia = "INSERT INTO producto VALUES(0,'" + nombreProducto + "','" + descripcion + "','" + unidad + "'," + Double.parseDouble(Valor) + "," + Integer.parseInt(aux) + ")";
                sql.ejecuta(sentencia);

            } else {
                String aux = request.getParameter("idUsuario");
                aux = Cifrado.decodificarBase64(aux);
                String nombreProducto = request.getParameter("nombreProducto");

                ResultSet busqueda = sql.consulta("select idProducto,nombreProducto,descripcionProducto,unidad,valorUnitario from producto where nombreProducto LIKE '" + nombreProducto + "%' AND idUsuario = " + Integer.parseInt(aux) + "");

                while (busqueda.next()) {
                    out.println("<li onclick=\"fill('" + busqueda.getString("nombreProducto") + "','" + busqueda.getString("descripcionProducto") + "','" + busqueda.getString("unidad") + "'," + busqueda.getDouble("valorUnitario") + "," + busqueda.getInt("idProducto") + ");\">" + busqueda.getString("nombreProducto") + "</li>");
                }
            }

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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
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

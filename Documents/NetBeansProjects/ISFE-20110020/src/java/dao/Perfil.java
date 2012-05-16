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
public class Perfil extends HttpServlet {

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

            if ("Actualizar".equals(request.getParameter("Perfil"))) {
                String aux = request.getParameter("idUsuario");
                aux = Cifrado.decodificarBase64(aux);
                
                                
                String mail = request.getParameter("mail");
                String telefono = request.getParameter("telefono");
                String pwd = request.getParameter("pwd");
                String calle = request.getParameter("calle");
                String exterior = request.getParameter("exterior");
                String interior = request.getParameter("interior");
                String localidad =  request.getParameter("idLocalidad");
                String referencia  = request.getParameter("referencia");
                
                
                
                
                String actualizar = "UPDATE usuario SET mail='"+mail+"' , telefono ='"+telefono+"' ,contrasena = '"+pwd+"' , calle = '"+calle+"' , "
                        + "noExterior = '"+exterior+"' , noInterior = '"+interior+"' , idLocalidad = "+localidad+" , referencia = '"+referencia+"' "
                        + "WHERE idUsuario =  "+ Integer.parseInt(aux) +" ";
                
             
                
                String resultado = s.ejecuta(actualizar);
                out.println(resultado);
                
            }else if("CSD".equals(request.getParameter("Archivo"))){
                String file_name = request.getParameter("archivo");
                out.println(file_name);
            } else {
                
                String aux = request.getParameter("idUsuario");
                aux = Cifrado.decodificarBase64(aux);

                String sql = "select tipoPersona,nombre,apellidoMaterno, apellidoPaterno,razonSocial,contrasena,mail,telefono,calle,noExterior,noInterior,referencia FROM usuario WHERE idUsuario = " + Integer.parseInt(aux) + ";";
                ResultSet rs;
                rs = s.consulta(sql);

                while (rs.next()) {

                    if (rs.getBoolean("tipoPersona") == false) {
                        out.println(rs.getString("nombre") + " " + rs.getString("apellidoPaterno") + " " + rs.getString("apellidoMaterno") + "/");

                    } else {
                        out.println(rs.getString("razonSocial") + "/");
                    }

                    out.println(rs.getString("contrasena") + "/");
                    out.println(rs.getString("mail") + "/");
                    out.println(rs.getString("telefono") + "/");
                    out.println(rs.getString("calle") + "/");
                    out.println(rs.getString("noExterior") + "/");
                    out.println(rs.getString("noInterior") + "/");
                    out.println(rs.getString("referencia") + "/");

                }

                sql = "select l.idLocalidad, l.nombreLocalidad, m.nombreMunicipio FROM localidad l, municipio m, usuario u WHERE u.idLocalidad = l.idLocalidad AND l.idMunicipio = m.idMunicipio AND u.idUsuario = " + Integer.parseInt(aux) + ";";
                rs = s.consulta(sql);
                while (rs.next()) {
                    out.println(rs.getString("nombreLocalidad") + "/");
                    out.println(rs.getInt("idLocalidad") + "/");
                    out.println(rs.getString("nombreMunicipio"));
                }
            }


        } catch (InstantiationException ex) {
            out.println(ex);
        } catch (IllegalAccessException ex) {
            out.println(ex);
        } catch (SQLException ex) {
            out.println(ex);
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

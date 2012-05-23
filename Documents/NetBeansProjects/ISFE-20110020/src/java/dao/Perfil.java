/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Negocios.Cifrado.Cifrado;
import Negocios.Cifrado.DES;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
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

                String pwd = request.getParameter("pwd");
                DES des = new DES(pwd);

                String nombre = request.getParameter("nombre");
                String mail = request.getParameter("mail");
                mail = des.Cifrador(mail);
                String telefono = request.getParameter("telefono");
                telefono = des.Cifrador(telefono);
                String calle = request.getParameter("calle");
                calle = des.Cifrador(calle);
                String exterior = request.getParameter("exterior");
                exterior = des.Cifrador(exterior);
                String interior = request.getParameter("interior");
                if (interior != "") {
                    interior = des.Cifrador(interior);
                }
                String localidad = request.getParameter("idLocalidad");
                String referencia = request.getParameter("referencia");
                
                String rfc = request.getParameter("rfc");
                String []auxRFC = rfc.split("-");
                rfc = auxRFC[0]+auxRFC[1]+auxRFC[2];
                rfc = des.Cifrador(rfc);
                
                referencia = des.Cifrador(referencia);
                pwd = Cifrado.codificarBase64(pwd);

                String actualizar;
                if ("Moral".equals(request.getParameter("tipo"))) {
                    nombre = des.Cifrador(nombre);
                    actualizar = "UPDATE usuario SET rfc = '"+ rfc +"' , razonSocial = '" + nombre + "', mail='" + mail + "' , telefono ='" + telefono + "' ,contrasena = '" + pwd + "' , calle = '" + calle + "' , "
                            + "noExterior = '" + exterior + "' , noInterior = '" + interior + "' , idLocalidad = " + localidad + " , referencia = '" + referencia + "' "
                            + "WHERE idUsuario =  " + Integer.parseInt(aux) + " ";

                } else {
                    String []auxNombre = nombre.split(" ");
                    actualizar = "UPDATE usuario SET rfc = '"+ rfc +"' , nombre = '" + des.Cifrador(auxNombre[0]) + "', apellidoMaterno = '"+ des.Cifrador(auxNombre[1]) +"', apellidoPaterno = '"+ des.Cifrador(auxNombre[2]) +"' , mail='" + mail + "' , telefono ='" + telefono + "' ,contrasena = '" + pwd + "' , calle = '" + calle + "' , "
                            + "noExterior = '" + exterior + "' , noInterior = '" + interior + "' , idLocalidad = " + localidad + " , referencia = '" + referencia + "' "
                            + "WHERE idUsuario =  " + Integer.parseInt(aux) + " ";
                }

                String resultado = s.ejecuta(actualizar);
                out.println(resultado);

            } else if ("CSD".equals(request.getParameter("Archivo"))) {
                String file_name = request.getParameter("archivo");
                out.println(file_name);
            } else {

                String aux = request.getParameter("idUsuario");
                aux = Cifrado.decodificarBase64(aux);


                String sql = "select tipoPersona,nombre,apellidoMaterno, apellidoPaterno,razonSocial,contrasena,mail,telefono,calle,noExterior,noInterior,referencia FROM usuario WHERE idUsuario = " + Integer.parseInt(aux) + ";";
                ResultSet rs;
                rs = s.consulta(sql);

                while (rs.next()) {
                    String pwd = rs.getString("contrasena");
                    pwd = Cifrado.decodificarBase64(pwd);
                    DES des = new DES(pwd);

                    if (rs.getBoolean("tipoPersona") == false) {
                        out.println("Fisica/");
                        out.println(des.Descifrado(rs.getString("nombre")) + " " + des.Descifrado(rs.getString("apellidoPaterno")) + " " + des.Descifrado(rs.getString("apellidoMaterno")) + "/");

                    } else {
                        out.println("Moral/");
                        out.println(des.Descifrado(rs.getString("razonSocial")) + "/");
                    }

                    out.println(pwd + "/");
                    out.println(des.Descifrado(rs.getString("mail")) + "/");
                    out.println(des.Descifrado(rs.getString("telefono")) + "/");
                    out.println(des.Descifrado(rs.getString("calle")) + "/");
                    out.println(des.Descifrado(rs.getString("noExterior")) + "/");
                    out.println(des.Descifrado(rs.getString("noInterior")) + "/");
                    out.println(des.Descifrado(rs.getString("referencia")) + "/");

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

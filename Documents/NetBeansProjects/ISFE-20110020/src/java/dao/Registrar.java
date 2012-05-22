package dao;

import Datos.Contribuyente;
import Datos.Usuario;
import Negocios.Cifrado.DES;
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
 * Servlet que registra a los solicitantes a la base de datos de ISFE
 *
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación
 * Electrónica acorde a la reforma de enero de 2011
 */
public class Registrar extends HttpServlet {

    /**
     * Método encargado de registrar al solicitante siempre y cuando este no
     * este registrado
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException si ocurren errores del Servlet
     * @throws IOException Si ocurren errores de entrada y/o salida de datos
     */
    Contribuyente c;
    Usuario u;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            if ("validar".equals(request.getParameter("usuario"))) {
                /*Buscamos si no hay otro rfc*/
                String RFC = request.getParameter("rfc");
                String sentencia = "SELECT idUsuario FROM usuario WHERE rfc = '" + RFC + "'";
                c = new Contribuyente();
                if (c.ValidarRFC(sentencia, "idUsuario") != 0) {
                    out.println("El RFC ya se dio de alta");
                }

            } else if ("Guardar".equals(request.getParameter("usuario"))) {


                String rfc = request.getParameter("rfcUsuario");
                String telefono = request.getParameter("telefono");
                String calle = request.getParameter("calle");
                String interior = request.getParameter("interior");
                String exterior = request.getParameter("exterior");
                int localidad = Integer.parseInt(request.getParameter("localidad"));
                String mail = request.getParameter("mail");
                String referencia = request.getParameter("referencia");
                String password = request.getParameter("password");
                Sql sql = new Sql();
                
                DES des = new DES(password);
                //out.println("Cifrando rfc: "+des.Cifrador(rfc) +"\n");

               /*if ("Moral".equals(request.getParameter("Tipo"))) {
                    String razon = request.getParameter("razon");
                    String consulta = "INSERT INTO usuario VALUES(0,true,null,null,null,'" + razon + "',null,'" + rfc + "','" + password + "',"
                            + "'" + mail + "','" + telefono + "','" + calle + "','" + exterior + "','" + interior + "','" + referencia + "'," + localidad + ")";
                    String resultado = sql.ejecuta(consulta);

                } else {
                    String nombre = request.getParameter("nombre");
                    String paterno = request.getParameter("paterno");
                    String materno = request.getParameter("materno");
                    String curp = request.getParameter("curp");

                    String consulta = "INSERT INTO usuario VALUES(0,false,'" + nombre + "','" + materno + "','" + paterno + "',null,'" + curp + "','" + rfc + "','" + password + "',"
                            + "'" + mail + "','" + telefono + "','" + calle + "','" + exterior + "','" + interior + "','" + referencia + "'," + localidad + ")";
                    sql.ejecuta(consulta);
                }*/

                /*String consulta = "SELECT idUsuario FROM usuario WHERE rfc='" + rfc + "'";
                ResultSet rs = sql.consulta(consulta);
                while(rs.next()) {
                    out.println(rs.getInt("idUsuario"));
                }*/

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

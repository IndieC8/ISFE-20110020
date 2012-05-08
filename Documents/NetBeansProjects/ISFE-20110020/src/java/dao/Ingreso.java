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
import javax.servlet.http.HttpSession;

/**
 * Servlet que se encarga de manejar los ingreso al ISFE por parte de los
 * usuarios y solicitantes
 *
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación
 * Electrónica acorde a la reforma de enero de 2011
 */
public class Ingreso extends HttpServlet {

    /**
     * Método encargado de dar acceso al usuario siempre y cuando este
     * registrado; en caso contrario redirecciona a la página de inicio
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException si ocurren errores del Servlet
     * @throws IOException Si ocurren errores de entrada y/o salida de datos
     */
    int idUsuario = 0;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        if ("consulta".equals(request.getParameter("Login"))) {
            try {
                String rfc = request.getParameter("RFCLogin");
                String pwd = request.getParameter("passwordLogin");

                Sql sql = new Sql();
                ResultSet rs = sql.consulta("SELECT idUsuario FROM usuario WHERE rfc='" + rfc + "' AND contrasena = '" + pwd + "'");
                while (rs.next()) {
                    idUsuario = rs.getInt("idUsuario");
                    out.println(rs.getInt("idUsuario"));
                }


            } catch (InstantiationException ex) {
                Logger.getLogger(Ingreso.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Ingreso.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Ingreso.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                out.close();
            }

        } else {
            String nombre = request.getParameter("RFCLogin").toUpperCase();
            String iD = Cifrado.codificarBase64( String.valueOf(idUsuario) );
            //creamos nuestra sesion
            HttpSession session = request.getSession(true);
            //Obtenemos los obejtos a guardar en session
            session.setAttribute("contribuyente", nombre);
            session.setAttribute("identificador", iD);
            //pagina a donde se enviara si se encuentra el usuario autenticado
            response.sendRedirect("index-user.jsp");
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

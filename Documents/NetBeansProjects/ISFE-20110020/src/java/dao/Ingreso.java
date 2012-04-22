/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
 *
 * @author lupe
 */
public class Ingreso extends HttpServlet {

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
    Sql sql = new Sql();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String rfc = request.getParameter("RFCLogin").toUpperCase();
            String aux[] = rfc.split("-");
            rfc = aux[0]+aux[1]+aux[2];
            String pwd = request.getParameter("passwordLogin");    
            
            //out.println("ENTRANDDOOOO");
            int idUsuario = 0;
            String nombre = "";
            
            ResultSet rs = sql.consulta("SELECT idUsuario,rfc FROM usuario WHERE rfc='"+rfc+"' AND contrasena = '"+pwd+"'");
            
            while(rs.next()){
                    idUsuario = rs.getInt("idUsuario");        
                    nombre = rs.getString("rfc");
            }
                
            String auxRFC[] =  nombre.split("");
            nombre = auxRFC[1]+auxRFC[2]+auxRFC[3]+auxRFC[4]+"-"+auxRFC[5]+auxRFC[6]+auxRFC[7]+auxRFC[8]+auxRFC[9]+auxRFC[10]+"-"+auxRFC[11]+auxRFC[12]+auxRFC[13];
            
            
            
            //out.println(idUsuario);
            if(idUsuario == 0){
                out.println("No estas dado de alta");
            }else{
            
                //creamos nuestra sesion
                HttpSession session = request.getSession(true);

                //Obtenemos los obejtos a guardar en session
                
                session.setAttribute("contribuyente", nombre);
                
                //pagina a donde se enviara si se encuentra el usuario autenticado
                response.sendRedirect("index-user.jsp");
                
                
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

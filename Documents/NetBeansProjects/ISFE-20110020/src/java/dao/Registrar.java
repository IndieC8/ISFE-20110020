/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Datos.Contribuyente;
import Datos.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Clase que hereda de un Servlet que registra a los solicitantes
 * @author lupe
 */
public class Registrar extends HttpServlet {

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
      Contribuyente c;
      Usuario u;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /*
             * Buscamos si no hay otro rfc
             */
            if ("validar".equals(request.getParameter("usuario"))) {
                String RFC = request.getParameter("rfc");
                String sentencia = "SELECT idUsuario FROM usuario WHERE rfc = '"+RFC+"'";
                c = new Contribuyente();
                if (c.ValidarRFC(sentencia,"idUsuario") != 0) {
                    out.println("El RFC ya se dio de alta");
                }

            }else if("Moral".equals(request.getParameter("usuario"))){
                String razon = request.getParameter("razon");
                String rfc = request.getParameter("rfcUsuario");
                String telefono = request.getParameter("telefono");
                String calle = request.getParameter("calle");
                String interior  = request.getParameter("interior");
                String exterior  = request.getParameter("exterior");
                String colonia = request.getParameter("colonia");
                String localidad = request.getParameter("localidad");
                String municipio = request.getParameter("municipio");
                String mail  = request.getParameter("mail");
                String referencia  = request.getParameter("referencia");
                String password  = request.getParameter("password");
                String estado = request.getParameter("estado");
                String codigoPostal = request.getParameter("codigoPostal");
               
                u = new Usuario();
                 
                u.inicializarDatos(true,rfc,null,null,null,razon,mail,calle,interior,exterior,colonia,localidad,municipio,referencia,estado,codigoPostal,password,null,telefono);
                
                out.println("<p>Razón Social: "+razon+"</p>");
                out.println("<p>RFC: "+rfc+"</p>");
                out.println("<p>Teléfono: "+telefono+"</p>");
                out.println("<p>Calle: "+calle+"</p>");
                out.println("<p>Número Interior: "+interior+"</p>");
                out.println("<p>Número Exterior: "+exterior+"</p>");
                out.println("<p>Colonia: "+colonia+"</p>");
                out.println("<p>Municipio: "+municipio+"</p>");
                out.println("<p>E-mail: "+mail+"</p>");
                out.println("<p>Referencia: "+referencia+"</p>");
                out.println("<p>Password: "+password+"</p>");
                
                
            }else if("Fisica".equals(request.getParameter("usuario"))){
                String nombre = request.getParameter("nombre");
                String paterno = request.getParameter("paterno");
                String materno = request.getParameter("materno");
                String rfc = request.getParameter("rfcUsuario");
                String curp = request.getParameter("curpUsuario");
                String tel = request.getParameter("telefono");
                String calle = request.getParameter("calle");
                String interior  = request.getParameter("interior");
                String exterior  = request.getParameter("exterior");
                String colonia = request.getParameter("colonia");
                String municipio = request.getParameter("municipio");
                String mail  = request.getParameter("mail");
                String referencia  = request.getParameter("referencia");
                String password  = request.getParameter("password");
                String estado = request.getParameter("estado");
                String codigoPostal = request.getParameter("codigoPostal");
                String localidad = request.getParameter("localidad");

                u = new Usuario();

                u.inicializarDatos(false,rfc,nombre,paterno,materno,null,mail,calle,interior,exterior,colonia,localidad,municipio,referencia,estado,codigoPostal,password,curp,tel);
                out.println("Objeto Creado fisica");
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

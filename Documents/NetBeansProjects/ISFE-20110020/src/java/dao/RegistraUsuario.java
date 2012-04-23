/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Integracion.ConexionSAT.CSD;
import Negocios.ObtenerFiel.Fiel;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Clase que hereda de un Servlet encargada de registrar a los solicitantes que
 * quieran hacer uso del servicio de ISFE
 * @author kawatoto
 */
public class RegistraUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        
        String tipoPersona=request.getParameter("tipoPersona");
        int tipoPersonaValor;
        if("personaMoral".equals(tipoPersona))
        {
            tipoPersonaValor=0;
        }
        else
        {
            tipoPersonaValor=1;
        }
        
        String rfc = request.getParameter("rfc"); 
        String nombre=request.getParameter("nombre"); 
        String apPaterno=request.getParameter("apPaterno");
        String apMaterno=request.getParameter("apMaterno");
        String curp=request.getParameter("curp");
        String password=request.getParameter("rePassword");
        String correo=request.getParameter("email");
        String telefono=request.getParameter("telefono");
        
        String codigoPostal=request.getParameter("codigoPostal");
        String estado=request.getParameter("estado");
        String municipio=request.getParameter("municipio");
        String colonia=request.getParameter("localidad");
        String calle=request.getParameter("calle");
        String noExterior=request.getParameter("numexterior");
        String noInterior=request.getParameter("numinterior"); 
        String referencia=request.getParameter("referencia");
        
        System.out.println("Codigo Postal de Form: "+codigoPostal);
        
        CSD csd=null;
        Fiel fiel=null;
        
       // Direccion direccion= new Direccion(calle,noInterior,noExterior,colonia,codigoPostal,municipio,estado,referencia);
       // Usuario usuario = new Usuario(tipoPersonaValor,rfc,nombre,apPaterno,apMaterno,correo,direccion,password,curp,telefono,csd,fiel);
 
//        System.out.println("Codigo Postal de Clase Usuario: "+usuario.getDireccion().getCodigoPostal());
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Sql sql = new Sql();

        try 
        {
            out.println("Haciendo COnsulta");
           /*     try {
                    sql.registrarUsuarioFisico(usuario);
                } catch (InstantiationException ex) {
                    Logger.getLogger(RegistraUsuario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(RegistraUsuario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(RegistraUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.out.println("Esto aun no lo he implantado :P"); */
        } 
        finally 
        {            
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

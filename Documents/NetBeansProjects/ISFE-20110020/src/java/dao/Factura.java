/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Datos.Contribuyente;
import Datos.Direccion;
import Datos.Usuario;
import Integracion.ConexionSAT.CSD;
import Negocios.Cifrado.Cifrado;
import Negocios.ObtenerFiel.Fiel;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class Factura extends HttpServlet {

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

        if ("Factura".equals(request.getParameter("Factura"))) {
            try {
                String aux = request.getParameter("idUsuaio");
                aux = Cifrado.decodificarBase64(aux);
                
                Sql s = new Sql();
                Usuario emisor = new Usuario();
                Contribuyente receptor = new Contribuyente();
                CSD csd = new CSD();
                Fiel fiel = new Fiel();
                Direccion d = new Direccion();
                
                /*Datos del Emisor*/
                String sql = "select u.tipoPersona,u.nombre,u.apellidoMaterno,u.apellidoPaterno,u.razonSocial,u.curp,u.rfc,u.mail, f.archivoFiel, c.noCertificado, c.archivoCSD, d.codigoPostal, d.nombreLocalidad,d.nombreMunicipio, d.nombreEstado from usuario u, csd c, fiel f, direccionUsuario d where idUsuario = " + aux + " and c.idCSD = u.idCSD and f.idFiel = u.idFiel and d.idUsuario = u.idUsuario;";
                ResultSet rs;
                rs = s.consulta(sql);
                while(rs.next()){
                    emisor.setTipoPersona(rs.getBoolean("tipoPersona"));
                    
                    if( rs.getBoolean("tipoPersona") == false ){
                        emisor.setNombre(rs.getString("nombre"));
                        emisor.setApMaterno(rs.getString("apellidoMaterno"));
                        emisor.setApPaterno(rs.getString("apellidoPaterno"));
                        emisor.setCurp(rs.getString("curp"));
                    }else{
                        emisor.setRazonSocial(rs.getString("razonSocial"));
                    }
                    
                    emisor.setRFC(rs.getString("rfc"));
                    emisor.setCorreo(rs.getString("mail"));
                    
                    csd.setArchivoCSD(rs.getBytes("archivoCSD"));
                    csd.setNoCertificado(rs.getString("noCertificado"));
                    emisor.setCSD(csd);
                    
                    fiel.setArchivoFiel(rs.getBytes("archivoFiel"));
                    emisor.setFiel(fiel);
                    
                    d.setCodigoPostal(rs.getString("codigoPostal"));
                    d.setLocalidad(rs.getString("nombreLocalidad"));
                    d.setMunicipio(rs.getString("nombreMunicipio"));
                    
                }
                
                /*Datos receptor*/
                


                

                Datos.Factura f = new Datos.Factura();
                Datos.Concepto conceptos = new Datos.Concepto();

                conceptos.setCantidad(Double.parseDouble(request.getParameter("cantidad")));
                conceptos.setNoIdentifiacion(request.getParameter("nombre"));
                conceptos.setValorUnitario(Double.parseDouble(request.getParameter("unitario")));
                conceptos.setImporte(Double.parseDouble(request.getParameter("total")));

                ArrayList<Datos.Concepto> productos = new ArrayList<Datos.Concepto>();
                productos.add(conceptos);

                f.setConceptos(productos);
                f.setFormaDePago(null);   //CAMPO A AGREGAR EN FORMULARIO
                f.setSubTotal(Double.parseDouble(request.getParameter("subTotal")));
                f.setIVA(Double.parseDouble(request.getParameter("iva")));
                f.setTotal(Double.parseDouble(request.getParameter("GranTotal")));
                f.setTipoDeComprobante("EGRESO");   //CAMPO A AGREGAR EN FORMULARIO
                f.setEmisor(null);
                f.setReceptor(null);
                f.setFolio(null);

            } catch (InstantiationException ex) {
                Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                out.println(ex);
                Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        try {
            String aux = request.getParameter("idUsuaio");
            aux = Cifrado.decodificarBase64(aux);
            Sql s = new Sql();
            String sql = "select idCliente, tipoPersona,nombreCliente,APaternoCliente,AMaternoCliente,razonCliente from cliente where idUsuario= " + aux + ";";
            ResultSet rs;
            rs = s.consulta(sql);

            while (rs.next()) {
                if (rs.getBoolean("tipoPersona") == false) {
                    out.println("<option value=\"" + rs.getInt("idCliente") + "\">" + rs.getString("nombreCliente") + " " + rs.getString("APaternoCliente") + " " + rs.getString("AMaternoCliente") + "</option>");
                } else {
                    out.println("<option value=\"" + rs.getInt("idCliente") + "\">" + rs.getString("razonCliente") + "</option>");
                }

            }


        } catch (InstantiationException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            out.println(ex);
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
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

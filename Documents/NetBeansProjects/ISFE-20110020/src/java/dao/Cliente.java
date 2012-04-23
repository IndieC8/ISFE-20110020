package dao;

import Datos.Contribuyente;
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
 * Servlet que se encarga de manejar y procesar la información para crear del cliente como
 * receptor de las facturas electrónicas
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación Electrónica acorde a la reforma de enero de 2011
 */
public class Cliente extends HttpServlet {
    Sql sql;
    /**
     * Método encargado de registrar al cliente de nuestro usuario a la base de datos en caso de no existir
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException si ocurren errores del Servlet
     * @throws IOException Si ocurren errores de entrada y/o salida de datos
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        /*
         * Crear al contribuyente
         */
        try {

            Contribuyente c = new Contribuyente();
            sql = new Sql();
            if ("baja".equals(request.getParameter("Cliente"))){
                 String RFC = request.getParameter("rfc");
                 String sentencia = "SELECT idCliente, tipoPersona, nombreCliente, APaternoCliente, AMaternoCliente, razonCliente FROM cliente WHERE rfc = '"+ RFC +"'";
                 String []resultado = c.ValidarRFC(sentencia);
                 out.println(resultado[0]+"/"+resultado[1]);
            }else if ("validar".equals(request.getParameter("Cliente"))) {
                String RFC = request.getParameter("rfc");
                String sentencia = "SELECT idCliente FROM cliente WHERE rfc = '"+RFC+"'";
                /*
                 * Buscamos si no hay otro rfc
                 */
                if (c.ValidarRFC(sentencia,"idCliente") != 0) {
                    out.println("El RFC ya se dio de alta");
                }
            } else if ("modificar".equals(request.getParameter("Cliente"))) {
                /*
                 * Creamos el formulario de modificación del cliente
                 */
                try {
                    ResultSet rs = sql.consulta("SELECT c.idCliente,c.tipoPersona, c.nombreCliente, c.APaternoCliente, c.AMaternoCliente, c.razonCliente, c.rfc, c.correoCliente, c.calleCliente, "
                            + "c.numInteriorCliente, c.numExteriorCliente, c.referenciaCliente, l.idLocalidad, l.codigoPostal, l.nombreLocalidad, m.nombreMunicipio, e.nombreEstado  FROM cliente c, "
                            + "localidad l, municipio m, estado e WHERE c.idCliente = " + request.getParameter("idCliente") + " AND c.idLocalidad = l.idLocalidad AND l.idEstado = e.idEstado "
                            + "AND l.idMunicipio = m.idMunicipio  ");
                    
                    while (rs.next()) {
                        if (rs.getBoolean("tipoPersona") == true) 
                        {
                            out.println("<center>");
                            out.println("<font color=\"red\">Los campos marcados con (*) son obligatorios</font><br><br><br>");
                            out.println("<table border=\"0\">");
                            out.println("<form id=\"frmResultadoModificarCLiente\" method=\"post\" >");
                            out.println("<tbody>");
                            out.println("<tr>");
                            out.println("<td rowspan=\"21\"><img src=\"../images/formularios/editar-perfil.png\" width=\"256\"/></td>");
                            out.println("<td><input type=\"hidden\" id=\"idClienteModificar\" value=\"" + rs.getInt("idCliente") + "\"");
                            out.println("<td>Razón Social: </td>");
                            out.println("<td><input type=\"text\" readonly=\"readonly\" value=\"" + rs.getString("razonCliente") + "\"</td>");
                            out.println("</tr>");
                            out.println("<tr>");
                            out.println("<td> R.F.C");
                            out.println("</td>");
                            out.println("<td> <input id=\"RFCClienteModificar\"  type=\"text\" value=\""+ rs.getString("rfc") +"\">");
                            out.println("</td>");
                            out.println("</tr>");
                            out.println("<tr>");
                            out.println("<td> E-mail*");
                            out.println("</td>");
                            out.println("<td> <input id=\"mailClienteModificar\"  type=\"text\" value=\""+ rs.getString("correoCliente") +"\">");
                            out.println("</td>");
                            out.println("</tr>");
                            out.println("<tr>");
                            out.println("<td> Calle");
                            out.println("</td>");
                            out.println("<td> <input id=\"CalleClienteModificar\"  type=\"text\" value=\""+ rs.getString("calleCliente") +"\">");
                            out.println("</td>");
                            out.println("</tr>");
                            out.println("<tr>");
                            out.println("<td> N° Exterior");
                            out.println("</td>");
                            out.println("<td> <input id=\"exteriorClienteModificar\"  type=\"text\" value=\""+ rs.getString("numExteriorCliente") +"\">");
                            out.println("</td>");
                            out.println("</tr>");
                            out.println("<tr>");
                            out.println("<td> N° Interior");
                            out.println("</td>");
                            out.println("<td> <input id=\"interiorClienteModificar\"  type=\"text\" value=\""+ rs.getString("numInteriorCliente") +"\">");
                            out.println("</td>");
                            out.println("</tr>");
                            out.println("<tr>");
                            out.println("<td> Código Postal");
                            out.println("</td>");
                            out.println("<td> <input id=\"codigoClienteModificar\" value=\""+ rs.getString("codigoPostal") +"\"  type=\"text\" >");
                            out.println("</td>");
                            out.println("</tr>");
                            out.println("<tr>");
                            out.println("<td> Estado");
                            out.println("</td>");
                            out.println("<td> <input id=\"mailClienteModificar\" value=\"" + rs.getString("nombreEstado")+ "\"  type=\"text\"  readonly=\"readonly\">");
                            out.println("</td>");
                            out.println("</tr>");
                            out.println("<tr>");
                            out.println("<td> Delegación/Municipio");
                            out.println("</td>");
                            out.println("<td> <select id=\"delegacionClienteModificar\"> <option>"+ rs.getString("nombreMunicipio") +"</option> </select>");
                            out.println("</td>");
                            out.println("</tr>");
                            out.println("<tr>");
                            out.println("<td> Colonia/Localidad");
                            out.println("</td>");
                            out.println("<td> <select id=\"localidadClienteModificar\"> <option value=\""+ rs.getInt("idLocalidad") +"\"> "+ rs.getString("nombreLocalidad") +" </option> </select>");
                            out.println("</td>");
                            out.println("</tr>");
                            out.println("<tr>");
                            out.println("<td> Referencia*");
                            out.println("</td>");
                            out.println("<td> <input id=\"referenciaClienteModificar\"  type=\"text\"  value=\""+ rs.getString("referenciaCliente") +"\" >");
                            out.println("</td>");
                            out.println("</tr>");
                            out.println("<tr>");
                            out.println("<td>");
                            out.println("<input type=\"button\" width=\"50\" value=\"Guardar Cambios\" id=\"GuardarModificarCliente\"  class=\"ui-button ui-widget ui-state-default ui-corner-all\" role=\"button\" aria-disabled=\"false\"/>");
                            out.println("</td>");
                            out.println("<td>");
                            out.println("<input type=\"button\" width=\"50\" onclick=\"regresarModificacion()\" value=\"Cancelar Cambios\" id=\"CancelarModificarCliente\" class=\"ui-button ui-widget ui-state-default ui-corner-all\" role=\"button\" aria-disabled=\"false\"/>");
                            out.println("</td>");
                            out.println("</tr>");
                            out.println("</tbody>");
                            out.println("</form>");
                            out.println("</table>");
                            out.println("<center>");
                        } 
                        else 
                        {
                        }
                    
                    }

                } catch (InstantiationException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if ("buscar".equals(request.getParameter("Cliente"))) {
                /*
                 * Creamos la tabla de resultado de la busqueda
                 */
                try {
                    ResultSet rs = sql.consulta("SELECT * FROM cliente WHERE rfc like '" + request.getParameter("rfc") + "' ");
                    out.println("<table align= \"center\" id=\"ResultadoBusquedaCliente\">");
                    out.println("<tr>");
                    out.println("<th  align=\"center\">&nbsp; &nbsp; &nbsp; Nombre o Razón Social &nbsp; &nbsp;</th>");
                    out.println("<th  align=\"center\">&nbsp; &nbsp; &nbsp; R.F.C. &nbsp; &nbsp;</th>");
                    out.println("<th  align=\"center\">&nbsp; &nbsp; &nbsp; E-mail &nbsp; &nbsp;</th>");
                    out.println("<th  align=\"center\">&nbsp; &nbsp;</th>");
                    out.println("</tr>");
                    while (rs.next()) {
                        out.println("<tr>");
                        if (rs.getBoolean("tipoPersona") == true) {
                            out.println("<td Style=\"font-size: 10px;\" align=\"center\">" + rs.getString("razonCliente") + "</td>");
                        } else {
                            out.println("<td Style=\"font-size: 10px;\" align=\"center\">" + rs.getString("nombreCliente") + " " + rs.getString("APaternoCliente") + " " + rs.getString("AMaternoCliente") + "</td>");
                        }

                        out.println("<td Style=\"font-size: 10px;\" align=\"center\">" + rs.getString("rfc") + "</td>");
                        out.println("<td Style=\"font-size: 10px;\" align=\"center\">" + rs.getString("correoCliente") + "</td>");
                        out.println("<td align=\"center\"><span><img src=\"../images/formularios/edit.png\" title=\"Editar\" alt=\"Editar\" style=\"cursor:pointer\" onClick=\"ActualizarCliente(" + rs.getInt("idCliente") + ")\"/></span></td>");
                        out.println("</tr>");


                    }
                    out.println("</table>");
                } catch (InstantiationException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                /*
                 * Alta del cliente
                 */
                if ("Moral".equals(request.getParameter("tipoPersona"))) {
                    c.inicializar(true, request.getParameter("RFCCliente"), null, null, null, request.getParameter("Razon"), request.getParameter("Mail"), request.getParameter("Calle"), request.getParameter("Interior"), request.getParameter("Exterior"), request.getParameter("Colonia"),request.getParameter("Localidad"),request.getParameter("Municipio"),request.getParameter("Referencia"),request.getParameter("Estado"),request.getParameter("codigoPostal"));
                } else {
                    c.inicializar(false, request.getParameter("RFCCliente"), request.getParameter("Nombre"), request.getParameter("Paterno"), request.getParameter("Materno"), null, request.getParameter("Mail"), request.getParameter("Calle"), request.getParameter("Interior"), request.getParameter("Exterior"), request.getParameter("Colonia"),request.getParameter("Localidad"),request.getParameter("Municipio"),request.getParameter("Referencia"),request.getParameter("Estado"),request.getParameter("codigoPostal"));
                }
                /*
                 * Validamos los datos del cliente
                 */
                String error = c.Validar();
                if (error != null) {
                    out.println(error);
                } else {
                    /*
                     * Insertamos los datos a la base
                     */
                    String mensaje = c.Insertar();
                    if (mensaje != null) {
                        out.println(mensaje);
                    } else {
                        out.println("Cliente guardado!");
                    }

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

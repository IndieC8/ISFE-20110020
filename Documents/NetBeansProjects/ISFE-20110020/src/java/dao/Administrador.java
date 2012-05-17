/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Integracion.ConexionSAT.SAT;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import subsistemaISFE.ReportesMensuales;

/**
 *
 * @author kawatoto
 */
public class Administrador extends HttpServlet {

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
            throws ServletException, IOException, SQLException, InstantiationException, IllegalAccessException, MessagingException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        if("folios".equals(request.getParameter("Action")))
        {
            try
            {
                SAT sat = new SAT();
                int numFolios=Integer.parseInt(request.getParameter("numeroFolios"));
                if(sat.manejoDeFolios(numFolios)==true)
                {
                    out.println("Fueron Recibidos "+numFolios+" Folios y han sido almacenados correctamente");
                }
                else
                {
                    out.println("Lo sentimos los folios no se han podido almacenar, intentelo mas tarde");
                }
                } finally {
                out.close();
            }
        }
        else if("reportes".equals(request.getParameter("Action")))
        {
            try
            {
                ReportesMensuales reportes = new ReportesMensuales();
                String mes = null;
                String anio = request.getParameter("anioReportado");
                switch(Integer.parseInt(request.getParameter("mesReportado")))
                {
                    case 1: mes="Enero"; break;
                    case 2: mes="Febrero"; break;
                    case 3: mes="Marzo"; break;
                    case 4: mes="Abril"; break;
                    case 5: mes="Mayo"; break;
                    case 6: mes="Junio"; break;
                    case 7: mes="Julio"; break;
                    case 8: mes="Agosto"; break;
                    case 9: mes="Septiembre"; break;
                    case 10: mes="Octubre"; break;
                    case 11: mes="Noviembre"; break;
                    case 12: mes="Diciembre"; break;
                    default: break;
                }
                if(reportes.generarReportes("ISFE-20110020", mes, anio)==true)
                {
                    out.println("Se ha enviado a su correo el reporte correspondiente al mes: "+mes+" del año "+anio);
                }
                else
                {
                    out.println("Lo sentimos no se ha podido generar el reporte mensual al SAT, intentelo mas tarde");
                }
                } finally {
                out.close();
            }
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
            try {
                processRequest(request, response);
            } catch (InstantiationException ex) {
                Logger.getLogger(Administrador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Administrador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException ex) {
                Logger.getLogger(Administrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Administrador.class.getName()).log(Level.SEVERE, null, ex);
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
            try {
                processRequest(request, response);
            } catch (InstantiationException ex) {
                Logger.getLogger(Administrador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Administrador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException ex) {
                Logger.getLogger(Administrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Administrador.class.getName()).log(Level.SEVERE, null, ex);
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

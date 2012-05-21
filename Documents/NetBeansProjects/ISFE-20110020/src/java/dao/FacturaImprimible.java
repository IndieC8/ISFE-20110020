/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Datos.PDF;
import java.io.*;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.fop.apps.FOPException;

/**
 *
 * @author Natalia Hernández
 */
public class FacturaImprimible extends HttpServlet {

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
        try {
            if("Generar".equals(request.getParameter(""))){
                try{
                    String idUsuario=request.getParameter("idUsuario");
                    String nombreXML=request.getParameter("xml");
                    Sql s=new Sql();
                    String sql="select facturaXML from factura where idUsuario="+idUsuario+" and nombreXML='"+nombreXML+"';";
                    ResultSet rs;
                    rs=s.consulta(sql);
                    Blob blobXML=null;
                    while(rs.next()){
                        blobXML=rs.getBlob("facturaXML");
                    }
                    File archivoXML=new File(nombreXML+".xml");
                    FileOutputStream fos=new FileOutputStream(archivoXML);
                    InputStream is=blobXML.getBinaryStream();
                    byte[] buffer=new byte[1024]; 
                    int lenght;
                    while((lenght=is.read(buffer))>0){
                        fos.write(buffer,0,lenght);
                    }
                    is.close();
                    fos.close();
                    
                    //GENERANDO EL ARCHIVO PDF
                    File archivoPDF=PDF.generarArchivoPDF(archivoXML, sql, "/ISFE-20110020/resources/xslt/");
                    PDF.visualizarPDF(archivoPDF, response, request);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FacturaImprimible.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FOPException ex) {
                    Logger.getLogger(FacturaImprimible.class.getName()).log(Level.SEVERE, null, ex);
                } catch (TransformerConfigurationException ex) {
                    Logger.getLogger(FacturaImprimible.class.getName()).log(Level.SEVERE, null, ex);
                } catch (TransformerException ex) {
                    Logger.getLogger(FacturaImprimible.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(FacturaImprimible.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(FacturaImprimible.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    System.err.println(ex);
                    Logger.getLogger(FacturaImprimible.class.getName()).log(Level.SEVERE, null, ex);
                }catch(IOException ex){
                    Logger.getLogger(FacturaImprimible.class.getName()).log(Level.SEVERE, null, ex);
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

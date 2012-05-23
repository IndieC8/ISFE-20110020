    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Datos.*;
import Integracion.ConexionSAT.CSD;
import Integracion.ConexionSAT.SAT;
import Negocios.Cifrado.Cifrado;
import Negocios.ObtenerFiel.Fiel;
import Negocios.ObtenerFolios.Folio;
import java.io.*;
import java.security.NoSuchProviderException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.fop.apps.FOPException;
import org.jdom.Document;
import subsistemaISFE.EnvioMail;

/**
 * Servlet encargada de la generación de la factura electrónica
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
        String idFolioPDF = null;
        String idUsuario = null;
        String pathAbsoluto=this.getServletContext().getRealPath("/");

        if ("Clientes".equals(request.getParameter("Factura"))) {

            try {
                String aux = request.getParameter("idUsuario");
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
                Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                out.close();
            }
        } else if ("Generar".equals(request.getParameter("Factura"))) {
            try {
                //out.println(pathAbsoluto);
                Random random=new Random();
                random.setSeed(new Date().getTime());
                String aux = request.getParameter("idUsuaio");
                aux = Cifrado.decodificarBase64(aux);
                Sql s = new Sql();
                XML xml = new XML();
                Datos.ISFE isfe = new Datos.ISFE();
                Fiel fielISFE = new Fiel();
                File isfeFIEL=new File(pathAbsoluto+"ISFE"+random.nextLong()+".key");
                CSD csdISFE = new CSD();
                File isfeCSD=new File(pathAbsoluto+"ISFE"+random.nextLong()+".cer");
                
                //FACTURA
                Datos.Factura factura=new Datos.Factura();
                Folio folio = new Folio();
                Direccion expedidoEn=new Direccion();
                
                //EMISOR
                Usuario emisor = new Usuario();
                Direccion direccionEmisor=new Direccion();
                File archivoFiel=new File(pathAbsoluto+"FIEL"+random.nextLong()+".key");
                Fiel fielEmisor=new Fiel();
                File archivoCsd=new File(pathAbsoluto+"CSD"+random.nextLong()+".cer");
                String noCertificado=null;
                CSD csdEmisor=new CSD();
                
                //RECEPTOR
                Contribuyente receptor = new Contribuyente();
                Direccion direccionReceptor=new Direccion();

                //DATOS DEL EMISOR (USUARIO)
                String sql = "select u.tipoPersona,u.nombre,u.apellidoMaterno,u.apellidoPaterno,u.razonSocial,u.curp,u.rfc,u.mail, f.archivoFiel, c.noCertificado, c.archivoCSD, d.codigoPostal, d.calle ,d.nombreLocalidad,d.nombreMunicipio, d.nombreEstado from usuario u, csd c, fiel f, direccionusuario d where u.idUsuario = " + aux + " and c.idUsuario = u.idUsuario and f.idUsuario = u.idUsuario and d.idUsuario = u.idUsuario;";
                ResultSet rsEmisor;
                rsEmisor = s.consulta(sql);
                
                while (rsEmisor.next()) {
                    //DATOS FISCALES DEL EMISOR
                    emisor.setTipoPersona(rsEmisor.getBoolean("tipoPersona"));
                    if (rsEmisor.getBoolean("tipoPersona") == false) {
                        emisor.setNombre(rsEmisor.getString("nombre")+" "+rsEmisor.getString("apellidoMaterno")+" "+rsEmisor.getString("apellidoPaterno"));
                        emisor.setApMaterno(rsEmisor.getString("apellidoMaterno"));
                        emisor.setApPaterno(rsEmisor.getString("apellidoPaterno"));
                        emisor.setCurp(rsEmisor.getString("curp"));
                    } else {
                        emisor.setRazonSocial(rsEmisor.getString("razonSocial"));
                    }
                    emisor.setRFC(rsEmisor.getString("rfc"));
                    emisor.setCorreo(rsEmisor.getString("mail"));
                    //OBTENIENDO .KEY DE LA FIEL
                    FileOutputStream fosFiel = new FileOutputStream(archivoFiel);
                    byte[] buffer = new byte[1];
                    InputStream is = rsEmisor.getBinaryStream("archivoFiel");
                    while (is.read(buffer) > 0) {
                        fosFiel.write(buffer);
                    }
                    fosFiel.close();
                    
                    //OBTENIENDO .CER DEL CSD
                    FileOutputStream fosCsd = new FileOutputStream(archivoCsd);
                    byte[] bufferCsd = new byte[1];
                    InputStream isCsd = rsEmisor.getBinaryStream("archivoCSD");
                    while (isCsd.read(bufferCsd) > 0) {
                        fosCsd.write(bufferCsd);
                    }
                    fosCsd.close();
                    noCertificado=rsEmisor.getString("noCertificado");
                    //DIRECCION DEL EMISOR
                    direccionEmisor.setCodigoPostal(rsEmisor.getString("codigoPostal"));
                    direccionEmisor.setLocalidad(rsEmisor.getString("nombreLocalidad"));
                    direccionEmisor.setMunicipio(rsEmisor.getString("nombreMunicipio"));
                    direccionEmisor.setEstado(rsEmisor.getString("nombreEstado"));
                    direccionEmisor.setCalle(rsEmisor.getString("calle"));
                    emisor.setDireccion(direccionEmisor);
                }
                //DATOS FIEL DEL EMISOR
                InputStream is=new FileInputStream(archivoFiel); 
                byte[] bFiel=new byte[(int)archivoFiel.length()]; 
                int offset=0; 
                int numRead=0;
                while(offset<bFiel.length && (numRead=is.read(bFiel, offset,bFiel.length-offset))>=0){ 
                    offset+=numRead;
                }
                fielEmisor.setArchivoFiel(bFiel);
                //fielEmisor.setPassword(request.getParameter("passwordFiel"));//PARA RECIBIR EL PASSWORD DESDE EL FORMULARIO
                fielEmisor.setPassword("a0123456789");
                emisor.setFiel(fielEmisor);
                //DATOS CSD DEL EMISOR
                InputStream isCsd=new FileInputStream(archivoCsd); 
                byte[] bCsd=new byte[(int)archivoCsd.length()]; 
                offset=0;
                numRead=0;
                while(offset<bCsd.length && (numRead=isCsd.read(bCsd, offset,bCsd.length-offset))>=0){ 
                    offset+=numRead;
                }
                csdEmisor.setArchivoCSD(bCsd);
                csdEmisor.setNoCertificado(noCertificado);
                emisor.setCSD(csdEmisor);

                if(archivoFiel.exists())
                {
                    archivoFiel.delete();
                }
                if(archivoCsd.exists())
                {
                    archivoCsd.delete();
                }
                
                //SAT sat=new SAT();
                //String firma=sat.ValidarCadenaOriginal("HOLA Usuario", bFiel, "a0123456789", true);
                
                //DATOS DEL RECEPTOR (CLIENTE)
                String sqlReceptor = "select c.tipoPersona,c.nombreCliente,c.APaternoCliente,c.AMaternoCliente,c.razonCliente,c.rfc, d.codigoPostal, d.calleCliente,d.nombreLocalidad,d.nombreMunicipio, d.nombreEstado from cliente c, direccioncliente d where c.idUsuario = " + aux + " and d.idCliente = c.idCliente;";
                ResultSet rsReceptor;
                rsReceptor = s.consulta(sqlReceptor);
                while (rsReceptor.next()) {
                    //DATOS FISCALES RECEPTOR
                    receptor.setTipoPersona(rsReceptor.getBoolean("tipoPersona"));
                    if (rsReceptor.getBoolean("tipoPersona") == false) {
                        receptor.setNombre(rsReceptor.getString("nombreCliente")+" "+rsReceptor.getString("APaternoCliente")+" "+rsReceptor.getString("AMaternoCliente"));
                        receptor.setApMaterno(rsReceptor.getString("APaternoCliente"));
                        receptor.setApPaterno(rsReceptor.getString("AMaternoCliente"));
                    } else {
                        receptor.setRazonSocial(rsReceptor.getString("razonCliente"));
                    }
                    receptor.setRFC(rsReceptor.getString("rfc"));
                    //DIRECCION DEL RECEPTOR
                    direccionReceptor.setCodigoPostal(rsReceptor.getString("codigoPostal"));
                    direccionReceptor.setLocalidad(rsReceptor.getString("nombreLocalidad"));
                    direccionReceptor.setMunicipio(rsReceptor.getString("nombreMunicipio"));
                    direccionReceptor.setEstado(rsReceptor.getString("nombreEstado"));
                    direccionReceptor.setCalle(rsReceptor.getString("calleCliente"));
                    receptor.setDireccion(direccionReceptor);
                }
                
                
                //DATOS DE LA FACTURA
                int numProductos=Integer.parseInt(request.getParameter("cant_campos"));//numero de productos que se recibiran
                ArrayList<Datos.Concepto> productos = new ArrayList<Datos.Concepto>();
                String Cantidad = request.getParameter("cantidad");
                String Nombre= request.getParameter("nombre");
                String Unitario=request.getParameter("unitario");
                String Total= request.getParameter("total");
                String Descripcion= request.getParameter("descripcion");
                String Unidad=request.getParameter("unidad");
                
                String[] arrayCantidad = Cantidad.split(",");
                String[] arrayNombre = Nombre.split(",");
                String[] arrayUnitario = Unitario.split(",");
                String[] arrayTotal = Total.split(",");
                String[] arrayDescripcion = Descripcion.split(",");
                String[] arrayUnidad= Unidad.split(",");
                
                for(int i=0;i<numProductos;i++){
                    Datos.Concepto concepto = new Datos.Concepto();     
                    concepto.setCantidad(Double.parseDouble(arrayCantidad[i]));
                    concepto.setnombreProducto(arrayNombre[i]);
                    concepto.setValorUnitario(Double.parseDouble(arrayUnitario[i]));
                    concepto.setImporte(Double.parseDouble(arrayTotal[i]));
                    concepto.setDescripcion(concepto.getnombreProducto() + ": " + arrayDescripcion[i]);
                    concepto.setUnidad(arrayUnidad[i]);
                    productos.add(concepto);
                }
                //out.println(numProductos);
                //out.println("Conceptos: "+arrayNombre[0]);

                //CONCEPTOS DE LA FACTURA
                /**Datos.Concepto conceptos = new Datos.Concepto();
                conceptos.setCantidad(Double.parseDouble(request.getParameter("cantidad")));
                conceptos.setnombreProducto(request.getParameter("nombre"));
                conceptos.setValorUnitario(Double.parseDouble(request.getParameter("unitario")));
                conceptos.setImporte(Double.parseDouble(request.getParameter("total")));
                conceptos.setDescripcion(conceptos.getnombreProducto() + ": " + request.getParameter("descripcion"));
                productos.add(conceptos);*/
                //DIRECCION EXPEDICION
                expedidoEn.setCalle("JUAN DE DIOS BATIZ");
                expedidoEn.setCodigoPostal("07738");
                expedidoEn.setEstado("DISTRIO FEDERAL");
                expedidoEn.setMunicipio("GUSTAVO A. MADERO");
                //FOLIO DE LA FACTURA
                String sqlFolio = "select idFolio, numFolio from folios where usado=0 limit 1;";
                ResultSet rsFolio;
                rsFolio = s.consulta(sqlFolio);
                String idFolio=null;
                String numFolio=null;
                while(rsFolio.next()){
                    idFolio = rsFolio.getString("idfolio");
                    numFolio = rsFolio.getString("numfolio");
                }
                folio.setNoFolio(Long.parseLong(numFolio));
                folio.setUUID(Long.parseLong(idFolio));
                

                //CARGANDO DATOS  A LA FACTURA
                factura.setExpedidoEn(expedidoEn);
                factura.setConceptos(productos);
                factura.setFormaDePago(request.getParameter("formaDePago"));   //CAMPO A AGREGAR EN FORMULARIO
                factura.setSubTotal(Double.parseDouble(request.getParameter("subTotal")));
                factura.setIVA(Double.parseDouble(request.getParameter("iva")));
                factura.setDescuento(Double.parseDouble(request.getParameter("descuento")));
                factura.setTotal(Double.parseDouble(request.getParameter("GranTotal")));
                factura.setTipoDeComprobante(request.getParameter("tipoComprobante"));
                factura.setEmisor(emisor);
                factura.setReceptor(receptor);
                factura.setFolio(folio);
                factura.setFecha(new Date());
                
          
                
                //DATOS DE ISFE COMO PAC
                String sqlISFE = "select u.idUsuario,f.archivoFiel,c.noCertificado,c.archivoCSD from fiel f,csd c,usuario u where u.idUsuario=1 and u.idUsuario=f.idUsuario and u.idUsuario=c.idUsuario;";
                ResultSet rsIsfe = s.consulta(sqlISFE);
                String noCertificadoISFE=null;
                while (rsIsfe.next()) {
                    FileOutputStream fos = new FileOutputStream(isfeFIEL);
                    byte[] buffer = new byte[1];
                    InputStream isIsfe = rsIsfe.getBinaryStream("archivoFiel");
                    while (isIsfe.read(buffer) > 0) {
                        fos.write(buffer);
                    }
                    fos.close();
                    //
                    FileOutputStream fosCSD = new FileOutputStream(isfeCSD);
                    byte[] bufferCSD = new byte[1];
                    InputStream isCSD = rsIsfe.getBinaryStream("archivoCSD");
                    while (isCSD.read(bufferCSD) > 0) {
                        fosCSD.write(bufferCSD);
                    }
                    fosCSD.close();
                    noCertificadoISFE=rsIsfe.getString("noCertificado");
                }
                InputStream isISFEfiel=new FileInputStream(isfeFIEL); byte[] bIsfeFiel=new byte[(int)isfeFIEL.length()]; offset=0; numRead=0;
                while(offset<bIsfeFiel.length && (numRead=isISFEfiel.read(bIsfeFiel, offset,
                    bIsfeFiel.length-offset))>=0){ offset+=numRead; }
                fielISFE.setArchivoFiel(bIsfeFiel);
                fielISFE.setPassword("a0123456789");
                //
                InputStream isISFEcsd=new FileInputStream(isfeCSD); byte[] bIsfeCsd=new byte[(int)isfeCSD.length()]; offset=0; numRead=0;
                while(offset<bIsfeCsd.length && (numRead=isISFEcsd.read(bIsfeCsd, offset,
                    bIsfeCsd.length-offset))>=0){ offset+=numRead; }
                csdISFE.setArchivoCSD(bIsfeCsd);
                csdISFE.setNoCertificado(noCertificadoISFE);
                isfe.setFiel(fielISFE);
                isfe.setCSD(csdISFE);
                if(isfeFIEL.exists())
                {
                    isfeFIEL.delete();
                }
                if(isfeCSD.exists())
                {
                    isfeCSD.delete();
                }
                //SAT sat2=new SAT();
                //String firma2=sat2.ValidarCadenaOriginal("HOLA ISFE", bIsfeFiel, "a0123456789", true);
                //System.out.println("Firma ISFE: "+firma2);
                //System.out.println("Firma Usuario: "+firma);
                //GENERACION DEL XML
                Document facturaXML = xml.generarXML(factura, isfe, pathAbsoluto);
                File fXML = XML.generarArchivoXML(facturaXML, emisor.getRFC() + folio.getNoFolio() + receptor.getRFC() + ".xml",pathAbsoluto);

                //ENVIO DEL XML AL CORREO DEL USUARIO
                //EnvioMail mail = new EnvioMail();
                //mail.EnvioMail(emisor.getCorreo(), "Entrega de Factura Electrónica ISFE " + new Date(), "ISFE, hace entrega de la factura electrónica en formato XML.\nHacemos de su conocimiento que en este momento el resguardo de la factura\nes responsabilidad de usted.\n\nGracias por utilizar ISFE.", fXML, emisor.getRFC() + folio.getNoFolio() + receptor.getRFC() + ".xml");

                
                //ALMACENAMIENTO DEL XML EN LA BASE DE DATOS DE ISFE
                String sqlFactura = "insert into factura values (?,?,?,?,?,?,?);";
                s.insertarXml(sqlFactura, fXML,factura.getFormaDePago() ,Integer.parseInt(aux), Integer.parseInt(idFolio), emisor.getRFC() + folio.getNoFolio() + receptor.getRFC());
                //fXML.delete();
                out.println("Factura generada exitosamente, y se ha enviado al correo:\n " + emisor.getCorreo());

                //ESTABLECIENDO EL ESTADO DEL FOLIO USADO
                String actualizaEstadoFolio = "update folios set usado=1 where usado=0 and idFolio = " + idFolio + " limit 1";
                s.ejecutaUpdate(actualizaEstadoFolio);
                
                //VARIABLES AUXILIARES PARA CONSULTAS SQL
                idFolioPDF = idFolio;
                idUsuario = aux;
                
                //fXML.delete();
                                
                //PRUEBA PDF
                //File pdf = PDF.generarArchivoPDF(fXML, pathAbsoluto+"/resources/xslt/", pathAbsoluto+factura.getEmisor().getRFC()+factura.getFolio().getNoFolio()+factura.getReceptor().getRFC() + ".pdf");
                //PDF.visualizarPDF(pdf, response, request);
                
            } catch (InstantiationException ex) {
                Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                out.println(ex);
                Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
            } //catch (NoSuchProviderException ex) {
                //Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
            //} 
            catch (Exception ex){
                Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if ("Impresa".equals(request.getParameter("Factura"))) {
            try {
                //OBTENCIÓN DEL XML DESDE LA BASE DE DATOS
                String sqlPDF = "select nombreXML, facturaXML from factura where idFolio=" + idFolioPDF + " and idUsuario=" + idUsuario + ");";
                Sql s = new Sql();
                ResultSet rsPDF = s.consulta(sqlPDF);
                File xml=null;
                while(rsPDF.next()){
                    xml = new File(rsPDF.getString("nombreXML") + ".xml");
                    FileOutputStream fos = new FileOutputStream(xml);
                    byte[] buffer = new byte[1];
                    InputStream isCsd = rsPDF.getBinaryStream("facturaXML");
                    while (isCsd.read(buffer) > 0) {
                        fos.write(buffer);
                    }
                    fos.close();
                }
                
                InputStream is=new FileInputStream(xml); 
                byte[] b=new byte[(int)xml.length()]; 
                int offset=0; 
                int numRead=0;
                while(offset<b.length && (numRead=is.read(b, offset, b.length-offset))>=0){ 
                    offset+=numRead; 
                }

                //GENERACIÓN DEL PDF (FACTURA IMPRESA) PARA VISUALIZARLO EN LA PAGINA WEB
                String nombrePDF=pathAbsoluto+rsPDF.getString("nombreXML") + ".pdf";
                File pdf = PDF.generarArchivoPDF(xml, pathAbsoluto+"/resources/xslt/", nombrePDF);
                PDF.visualizarPDF(pdf, response, request);
            } catch (InstantiationException ex) {
                Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                out.println(ex);
                Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FOPException ex) {
                Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerConfigurationException ex) {
                Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
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

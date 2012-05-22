/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Integracion.ConexionSAT.CSD;
import Negocios.Cifrado.Cifrado;
import Negocios.ObtenerFiel.Fiel;
import Negocios.ObtenerFolios.Folio;
import dao.Sql;
import java.io.*;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.fop.apps.FOPException;
import org.jdom.Document;

/**
 *
 * @author Natalia Hernández
 */
public class Main2 {
    public static void main(String[] args) throws IOException, FileNotFoundException, FOPException, TransformerConfigurationException, TransformerException, CertificateException, SecurityException, UnsupportedEncodingException, NoSuchProviderException, Exception{
        //CERTIFICADO
        byte[] bCSD=null;
        byte[] bFIEL=null;
        Sql s=new Sql();
        Usuario emisor=new Usuario();
        CSD csd=new CSD();
        Fiel fiel=new Fiel();
        Direccion d=new Direccion();
        String sql = "select u.tipoPersona,u.nombre,u.apellidoMaterno,u.apellidoPaterno,u.razonSocial,u.curp,u.rfc,u.mail, f.archivoFiel, c.noCertificado, c.archivoCSD, d.codigoPostal, d.calle ,d.nombreLocalidad,d.nombreMunicipio, d.nombreEstado from usuario u, csd c, fiel f, direccionusuario d where u.idUsuario = " + 5 + " and c.idCSD = u.idCSD and f.idFiel = u.idFiel and d.idUsuario = u.idUsuario;";
        ResultSet rs;
        rs = s.consulta(sql);
        while (rs.next()) {
            emisor.setTipoPersona(rs.getBoolean("tipoPersona"));
                    if (rs.getBoolean("tipoPersona") == false) {
                        emisor.setNombre(rs.getString("nombre"));
                        emisor.setApMaterno(rs.getString("apellidoMaterno"));
                        emisor.setApPaterno(rs.getString("apellidoPaterno"));
                        emisor.setCurp(rs.getString("curp"));
                    } else {
                        emisor.setRazonSocial(rs.getString("razonSocial"));
                    }
                    emisor.setRFC(rs.getString("rfc"));
                    emisor.setCorreo(rs.getString("mail"));
                    //CSD DEL EMISOR
                    
                    emisor.setCSD(csd);
                    //FIEL DEL EMISOR
                    File fCSD=new File("CSD-ISFE.cer");
                    InputStream isCSD=new FileInputStream(fCSD);
                    bCSD=new byte[(int)fCSD.length()];
                    int offset=0;
                    int numRead=0;
                    while(offset<bCSD.length && (numRead=isCSD.read(bCSD, offset, bCSD.length-offset))>=0){
                        offset+=numRead;
                    }
                    isCSD.close();
                    csd.setArchivoCSD(bCSD);
                    csd.setNoCertificado(Cifrado.obtenerNumeroCertificado(bCSD));
            //FIEL
            File fFIEL=new File("FIEL-ISFE.key");
            InputStream isFIEL=new FileInputStream(fFIEL);
            bFIEL=new byte[(int)fFIEL.length()];
            offset=0;
            numRead=0;
            while(offset<bFIEL.length && (numRead=isFIEL.read(bFIEL, offset, bFIEL.length-offset))>=0){
                offset+=numRead;
            }
            fiel.setArchivoFiel(bFIEL);
            fiel.setPassword("a0123456789");
                    emisor.setFiel(fiel);
                    //DIRECCION DEL EMISOR
                    d.setCodigoPostal(rs.getString("codigoPostal"));
                    d.setLocalidad(rs.getString("nombreLocalidad"));
                    d.setMunicipio(rs.getString("nombreMunicipio"));
                    d.setEstado(rs.getString("nombreEstado"));
                    d.setCalle(rs.getString("calle"));
                    emisor.setDireccion(d);
                }
                //PAC
                XML xml = new XML();
                Datos.ISFE isfe = new Datos.ISFE();
                String sqlISFE = "select u.idUsuario,f.archivoFiel,c.noCertificado,c.archivoCSD from fiel f,csd c,usuario u where u.idUsuario=1 and u.idFiel=f.idFiel and u.idCSD=c.idCSD;";
                ResultSet rsIsfe = s.consulta(sqlISFE);
                Fiel fielISFE = new Fiel();
                CSD csdISFE = new CSD();
                //while (rsIsfe.next()) {
                    //fielISFE.setArchivoFiel(rsIsfe.getBytes("f.archivoFiel"));
                    //csdISFE.setArchivoCSD(rsIsfe.getBytes("c.archivoCSD"));
                    //csdISFE.setNoCertificado(rsIsfe.getString("c.noCertificado"));
                //}
                fielISFE.setArchivoFiel(bFIEL);
                fielISFE.setPassword("a0123456789");
                isfe.setFiel(fielISFE);
                csdISFE.setArchivoCSD(bCSD);
                csdISFE.setNoCertificado(Cifrado.obtenerNumeroCertificado(bCSD));
                isfe.setCSD(csdISFE);
                //
                Contribuyente receptor=new Contribuyente();
                String sqlRec = "select c.tipoPersona,c.nombreCliente,c.APaternoCliente,c.AMaternoCliente,c.razonCliente,c.rfc, d.codigoPostal, d.calleCliente,d.nombreLocalidad,d.nombreMunicipio, d.nombreEstado from cliente c, direccioncliente d where c.idUsuario = " + 5 + " and d.idCliente = c.idCliente;";
                ResultSet rsRec;//SQLException after end of result set
                rsRec = s.consulta(sqlRec);
                while (rsRec.next()) {
                    receptor.setTipoPersona(rsRec.getBoolean("tipoPersona"));
                    if (rsRec.getBoolean("tipoPersona") == false) {
                        receptor.setNombre(rsRec.getString("nombreCliente"));
                        receptor.setApMaterno(rsRec.getString("APaternoCliente"));
                        receptor.setApPaterno(rsRec.getString("AMaternoCliente"));
                    } else {
                        receptor.setRazonSocial(rsRec.getString("razonCliente"));
                    }
                    receptor.setRFC(rsRec.getString("rfc"));
                    //DIRECCION DEL RECEPTOR
                    Direccion dReceptor=new Direccion();
                    dReceptor.setCodigoPostal(rsRec.getString("codigoPostal"));
                    dReceptor.setLocalidad(rsRec.getString("nombreLocalidad"));
                    dReceptor.setMunicipio(rsRec.getString("nombreMunicipio"));
                    dReceptor.setEstado(rsRec.getString("nombreEstado"));
                    dReceptor.setCalle(rsRec.getString("calleCliente"));
                    receptor.setDireccion(dReceptor);
                }
                
            //CONCEPTOS
            ArrayList<Concepto> conceptos=new ArrayList<Concepto>();
            Concepto c1=new Concepto();
            c1.setCantidad(1.0);
            c1.setDescripcion("VIBRAMICINA 100MG 10");
            c1.setValorUnitario(244.00);
            c1.setUnidad("CAPSULAS");
            c1.setImporte(244.00);
            Concepto c2=new Concepto();
            c2.setCantidad(1.0);
            c2.setDescripcion("CLORUTO 500M");
            c2.setValorUnitario(137.93);
            c2.setUnidad("BOTELLA");
            c2.setImporte(137.93);
            Concepto c3=new Concepto();
            c3.setCantidad(1.0);
            c3.setDescripcion("SEDEPRON 250MG 10");
            c3.setValorUnitario(84.50);
            c3.setUnidad("TABLETAS");
            c3.setImporte(84.50);
            conceptos.add(c1);
            conceptos.add(c2);
            conceptos.add(c3);
            //FACTURA
            Factura f=new Factura();
            f.setConceptos(conceptos);
            f.setEmisor(emisor);
            //
            Direccion expedidoEn = new Direccion();
                expedidoEn.setCalle("JUAN DE DIOS BATIZ");
                expedidoEn.setCodigoPostal("07738");
                expedidoEn.setEstado("DISTRIO FEDERAL");
                expedidoEn.setMunicipio("GUSTAVO A. MADERO");
                f.setExpedidoEn(expedidoEn);
            f.setFecha(new Date());
            //
            //FOLIO DE LA FACTURA
                String sqlFolio = "select idFolio, numFolio from folios where usado=0 limit 1;";
                ResultSet rsFolio;
                rsFolio = s.consulta(sqlFolio);
                rsFolio.next();
                String idFolio = rsFolio.getString("idFolio");
                String numFolio = rsFolio.getString("numFolio");
                //
                Folio folio = new Folio();
                folio.setNoFolio(Long.parseLong(numFolio));
                folio.setUUID(Long.parseLong(idFolio));
                f.setFolio(folio);
                //ESTABLECIENDO EL ESTADO DEL FOLIO USADO
                String actualizaEstadoFolio = "update folios set usado=1 where usado=0 and idFolio = " + idFolio + " limit 1";
                s.ejecuta(actualizaEstadoFolio);
            f.setFolio(folio);
            f.setFormaDePago("UNA SOLA EXIBICION");
            f.setReceptor(receptor);
            f.setTipoDeComprobante("INGRESO");
            f.generarTotal();
            Document dXML=xml.generarXML(f, isfe,"");
            File fXML=XML.generarArchivoXML(dXML, "f2.xml","");
            BufferedReader br=new BufferedReader(new FileReader(fXML));
            String l=br.readLine();
            while(l!=null){
               System.out.println(l);
                l=br.readLine();
            }
            File fPDF=PDF.generarArchivoPDF(fXML, "", "F2.pdf");
    }
}

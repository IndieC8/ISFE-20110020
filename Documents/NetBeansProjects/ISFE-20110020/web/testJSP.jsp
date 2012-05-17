<%-- 
    Document   : testJSP
    Created on : 16/05/2012, 12:02:51 PM
    Author     : Natalia Hernández
--%>

<%@page import="Datos.PDF"%>
<%@page import="Datos.PDF"%>
<%@page import="Datos.Usuario"%>
<%@page import="Datos.Direccion"%>
<%@page import="Datos.Concepto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Datos.ISFE"%>
<%@page import="Datos.Factura"%>
<%@page import="Integracion.ConexionSAT.CSD"%>
<%@page import="java.util.Date"%>
<%@page import="Negocios.ObtenerFiel.Fiel"%>
<%@page import="Negocios.ObtenerFolios.Folio"%>
<%@page import="org.jdom.Document"%>
<%@page import="Datos.XML"%>
<%@page import="Negocios.Cifrado.Cifrado"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test JSP</title>
    </head>
    <body>
        <%
        String s=this.getServletContext().getRealPath("/");
        //System.out.println(s);
        //
        String path="C:\\Users\\Natalia Hernández\\Documents\\NetBeansProjects\\ISFE-20110020\\Documents\\NetBeansProjects\\ISFE-20110020\\build\\web\\";
        File fCSD=new File(path+"resources\\test\\"+"CSD-ISFE.cer");
        InputStream isCSD=new FileInputStream(fCSD);
        byte[] bCSD=new byte[(int)fCSD.length()];
        int offset=0;
        int numRead=0;
        while(offset<bCSD.length && (numRead=isCSD.read(bCSD, offset, bCSD.length-offset))>=0){
                offset+=numRead;
        }
        String nCSD=Cifrado.obtenerNumeroCertificado(bCSD);
        isCSD.close();
            //FIEL
            File fFIEL=new File(path+"resources\\test\\"+"FIEL-ISFE.key");
            InputStream isFIEL=new FileInputStream(fFIEL);
            byte[] bFIEL=new byte[(int)fFIEL.length()];
            offset=0;
            numRead=0;
            while(offset<bFIEL.length && (numRead=isFIEL.read(bFIEL, offset, bFIEL.length-offset))>=0){
                offset+=numRead;
            }
            String password="a0123456789";
            Direccion d=new Direccion();
            d.setCalle("ANTONIO ROANOVA VARGAS");
            d.setCodigoPostal("07230");
            d.setColonia("ZONA ESCOLAR");
            d.setEstado("DISTRITO FEDERAL");
            d.setLocalidad("CIUDAD DE MEXICO");
            d.setMunicipio("GUSTAVO A MADERO");
            d.setNoExterior("51");
            d.setNoInterior("51");
            d.setReferencia("ENTRE FRANCISCO VILLA Y CENTRO ESCOLAR");
            //EMISOR-RECEPTOR
            Usuario u=new Usuario();
            u.setApMaterno("DELGADO");
            u.setApPaterno("HERNANDEZ");
            u.setCSD(new CSD(bCSD,nCSD));
            u.setCURP("PRUEBA");
            u.setCorreo("PRUEBA");
            u.setDirecction(d);
            u.setFiel(new Fiel(bFIEL,password));
            u.setNombre("RAUL");
            u.setRFC("PAM660606ER9");
            u.setReferencia("ENTRE FRANCISCO VILLA Y CENTRO ESCOLAR");
            //PAC
            ISFE isfe=new ISFE();
            isfe.setCSD(new CSD(bCSD,nCSD));
            isfe.setFiel(new Fiel(bFIEL,password));
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
            f.setCSD(new CSD(bCSD,nCSD));
            f.setConceptos(conceptos);
            f.setEmisor(u);
            f.setExpedidoEn(d);
            f.setFiel(new Fiel(bFIEL,password));
            f.setFolio(new Folio(111,111,false));
            f.setFormaDePago("UNA SOLA EXIBICION");
            f.setMetodoDePago("PRUEBA");
            f.setReceptor(u);
            f.setTipoDeComprobante("INGRESO");
            f.generarTotal();
            File xslt=new File("cadOriginalCFDI_3.xslt");
            if(xslt==null)
                System.out.println("hola");
            else
                System.out.println("adios");
            
            XML xml=new XML();
            System.out.println("Este es el path: "+path+"resources\\xml\\");
            Document dXML=xml.generarXML(f, isfe,path+"resources\\xml\\");
            File fXML=XML.generarArchivoXML(dXML, f.getEmisor().getRFC()+f.getFolio().getNoFolio()+f.getReceptor().getRFC()+".xml");
            //File fPDF=PDF.generarArchivoPDF(fXML, path+"resources\\xslt\\", f.getEmisor().getRFC()+f.getFolio().getNoFolio()+f.getReceptor().getRFC()+".pdf");
        %>
    </body>
</html>

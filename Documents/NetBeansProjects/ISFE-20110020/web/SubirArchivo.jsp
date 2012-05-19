<%-- 
    Document   : SubirArchivo
    Created on : 19/05/2012, 09:44:40 AM
    Author     : lupe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="javazoom.upload.*" %>
<%@page language="java" import="java.util.*" %>

<%
    String direccion = request.getSession().getServletContext().getRealPath("imagenesDB/");
%>

<jsp:useBean id="upBean" scope="page" class="javazoom.upload.UploadBean" >
    <jsp:setProperty name="upBean" property="folderstore" value="<%= direccion%>" />
    <jsp:setProperty name="upBean" property="whitelist" value="*.cer,*.key" />
    <jsp:setProperty name="upBean" property="overwritepolicy" value="nametimestamp"/>
</jsp:useBean>

<%
    if (MultipartFormDataRequest.isMultipartFormData(request)) {
        MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request);
        String todo = null;
        if (mrequest != null) {
            todo = mrequest.getParameter("todo");
        }
        if ((todo != null) && (todo.equalsIgnoreCase("upload"))) {
            Hashtable files = mrequest.getFiles();
            if ((files != null) && (!files.isEmpty())) {
                //java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("yyMMddHHmmss");
                String archivo = ((UploadFile) mrequest.getFiles().get("uploadfile")).getFileName();
                int posicionPunto = archivo.indexOf(".");
                String nombreImagen = archivo.substring(0, posicionPunto);
                String extension = archivo.substring(posicionPunto);
                //nombreImagen = nombreImagen + formato.format(new java.util.Date());
                nombreImagen = nombreImagen + extension;
                ((UploadFile) mrequest.getFiles().get("uploadfile")).setFileName(nombreImagen);
                UploadFile file = (UploadFile) files.get("uploadfile");
                if (file != null) {
                    out.println("El archivo: " + file.getFileName() + " se subio correctamente");
                }
                upBean.store(mrequest, "uploadfile");
            } else {
                out.println("Archivos no subidos");
            }
        } else {
            out.println("<BR> todo=" + todo);
        }
    }
%>
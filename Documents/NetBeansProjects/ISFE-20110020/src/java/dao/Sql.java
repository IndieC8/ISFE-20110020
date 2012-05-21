package dao;

import java.io.*;
import java.sql.*;


/**
 * Clase que se encarga del manejo de la base de datos de ISFE
 * (consultas,inserciones, eliminaciones, etc)
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación Electrónica acorde a la reforma de enero de 2011
 */
public class Sql {

    private Connection conn = null;
    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost/isfe";
    private String usuario = "isfe";
    private String password = "isfe";
    /**
     * Método que se encarga de conectarse a la base de datos de ISFE
     * @throws InstantiationException si hay errores para crear una instancia
     * @throws IllegalAccessException si hay errores de acceso
     */
    public void conectar() throws InstantiationException, IllegalAccessException {
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, usuario, password);
        } catch (SQLException e) {
        } catch (ClassNotFoundException ex) {
        }
    }
    /**
     * Método de encargado de cerrar la conexión con la base de datos de ISFE
     */
    public void cerrarConexion() {
        try {
            conn.close();
            System.out.println("Conexion Cerrada");
        } catch (SQLException e) {
        }
    }
    /**
     * Método encargado de realizar una consulta a la base de datos de ISFE
     * @param sql consulta a la base de datos
     * @return resultado de la consulta realizada
     * @throws InstantiationException si hay errores de instancia
     * @throws IllegalAccessException si hay errores de acceso
     * @throws SQLException Si hay errores de consulta de SQL
     */
    public ResultSet consulta(String sql) throws InstantiationException, IllegalAccessException, SQLException {
        this.conectar();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        return rs;
    }
    /**
     * Método que ejecuta Insert, Delete y Update a la base de datos de ISFE
     * @param sql actualización a la base de datos
     * @return mensaje de la actualización. Retorna null si todo bien, caso
     * contrario, el mensaje de error
     */
    public String ejecuta(String sql) {
        String mensaje = null;
        try {

            this.conectar();
            Statement st = conn.createStatement();
            st.execute(sql);

        } catch (IllegalAccessException e) {
            mensaje = e.getMessage();
        } catch (InstantiationException e) {
            mensaje = e.getMessage();
        } catch (SQLException e) {
            mensaje = e.getMessage();
        }
        return mensaje;
    }

    public String ejecutaUpdate(String sql) {
        String mensaje = null;
        try {

            this.conectar();
            Statement st = conn.createStatement();
            st.execute(sql);

        } catch (IllegalAccessException e) {
            mensaje = e.getMessage();
        } catch (InstantiationException e) {
            mensaje = e.getMessage();
        } catch (SQLException e) {
            mensaje = e.getMessage();
        }
        return mensaje;
    }
    /**
     * Método encargado de subir la Fiel a la base de datos
     * @param sql Instrucción para isnertar el archivo en la base de datos
     * @param fiel Archivo a subir
     * @throws InstantiationException Si hay errores de instancia
     * @throws IllegalAccessException Si hay errores de accesos ilegales
     * @throws SQLException Si hay errores con instrucciones SQL
     * @throws FileNotFoundExceptionSi hay errores con los archivos
     * @throws IOException Sihay errores de entrada y salida de datos
     */
    public void insertarFiel(String sql,File fiel) throws InstantiationException, IllegalAccessException, SQLException, FileNotFoundException, IOException{
        this.conectar();
        PreparedStatement pst=conn.prepareStatement(sql);
        pst.setString(1, null);
        //
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        ObjectOutputStream oos=new ObjectOutputStream(baos);
        oos.writeObject(fiel);
        pst.setBytes(2,baos.toByteArray());
        //
        pst.execute();
    }
    /**
     * Método encargado de subir el CSD a la base de datos
     * @param sql Instrucción para isnertar el archivo en la base de datos
     * @param noCSD Número del certificado a subir
     * @param csd Archivo a subir
     * @throws InstantiationException Si hay errores de instancia
     * @throws IllegalAccessException Si hay errores de accesos ilegales
     * @throws SQLException Si hay errores con instrucciones SQL
     * @throws FileNotFoundExceptionSi hay errores con los archivos
     * @throws IOException Sihay errores de entrada y salida de datos
     */
    public void insertarCsd(String sql,String noCSD,File csd) throws InstantiationException, IllegalAccessException, SQLException, FileNotFoundException, IOException{
        this.conectar();
        PreparedStatement pst=conn.prepareStatement(sql);
        pst.setString(1, null);
        pst.setString(2, noCSD);
        FileInputStream fis=new FileInputStream(csd);
        pst.setBinaryStream(3, fis, csd.length());
        pst.execute();
        fis.close();
    }
}

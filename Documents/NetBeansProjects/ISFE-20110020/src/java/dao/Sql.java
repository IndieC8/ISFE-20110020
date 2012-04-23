/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.*;


/**
 * Clase que se encarga del manejo de la base de datos de ISFE 
 * (consultas,inserciones, eliminaciones, etc) 
 * @author kawatoto
 */
public class Sql {

    private Connection conn = null;
    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost/isfe";
    private String usuario = "isfe";
    private String password = "isfe";
    /**
     * Método que se encarga de conectarse a la base de datos de ISFE
     * @throws InstantiationException
     * @throws IllegalAccessException 
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
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws SQLException 
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
}

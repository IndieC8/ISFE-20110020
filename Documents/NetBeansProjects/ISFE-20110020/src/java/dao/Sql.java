/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Datos.Usuario;
import java.sql.*;


/**
 *
 * @author kawatoto
 */
public class Sql {

    private Connection conn = null;
    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost/isfe";
    private String usuario = "isfe";
    private String password = "isfe";
    
    public void conectar() throws InstantiationException, IllegalAccessException {        
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, usuario, password);            
        } catch (SQLException e) {
        } catch (ClassNotFoundException ex) {
        }
    }
    
    public void cerrarConexion() {
        try {
            conn.close();
            System.out.println("Conexion Cerrada");
        } catch (SQLException e) {
        }
    }

    /*
     * Ejecucion de SELECT
     */
    public ResultSet consulta(String sql) throws InstantiationException, IllegalAccessException, SQLException {
        this.conectar();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        return rs;
    }

    /*
     * Ejecuta Insert, Delete y Update. Retorna null si todo bien, caso
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

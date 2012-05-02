package Negocios.ObtenerFolios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase que representa los folios que se utilizan para la generación de las
 * Facturas electrónicas dentro del ISFE, ademas se encarga de la solicitud de
 * los bloques de folios al servidor simulado del SAT
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación Electrónica acorde a la reforma de enero de 2011
 */
public class Folio {
    private long UUID;
    private long noFolio;
    private boolean usado;
    private Connection conn;
    private String driver="com.mysql.jdbc.Driver";
    private String url="jdbc:mysql://148.204.57.222/isfe";
    private String usuario="isfe";
    private String password="isfe";
    /**
     * Constructor vacío
     */
    public Folio(){
    }
    /**
     * Cosntructor que recibe el folio y el estado del mismo
     */
    public Folio(long UUID,long noFolio,boolean usado){
        this.setUUID(UUID);
        this.setNoFolio(noFolio);
        this.setUsado(usado);
    }
    /**
     * Método que se encarga de la conexión con la Base de Datos unicamente para
     * la solicitud de Folios
     */
    public void conectar()
    {
        try
	{
            Class.forName(driver).newInstance();
            conn=DriverManager.getConnection(url, usuario, password);
            if(conn!=null)
            {
		System.out.println("Conexion Exitosa a BD "+url);
            }
            else
            {
		System.out.println("Fallo la conexion");
            }
	}
	catch (Exception e)
	{
            System.out.println("Exception:" + e.getMessage());
	}
    }

    /**
     * Método encargado del almacenamiento de los folios solicitados en la Base
     * de Datos de ISFE
     * @param folio parametro que se encarga de insertar el numero de folio dado
     * a la base de datos
     * @return true en caso de que se hayan almacenado exitosamente los folios,
     * false en caso contrario
     * @throws SQLException excepción encargada de manejar los errores correspondientes
     * a las sentencias SQL
     */
    public boolean almacenarBloqueFolios(Integer folio) throws SQLException
    {
	String almacenarFolios="insert into folios (numFolio, usado) values ("+folio+",0)";

	this.conectar();
	Statement st = conn.createStatement();
	int rs = st.executeUpdate(almacenarFolios);

	if(rs>0)
            return true;
	else
            return false;
    }

    public long getUUID(){
        return this.UUID;
    }
    public void setUUID(long UUID){
        this.UUID=UUID;
    }
    /**
     * Obtiene el folio de la factura
     * @return folio de la factura
     */
    public long getNoFolio(){
        return this.noFolio;
    }
    /**
     * Ingresa el folio de la factura
     * @param noFolio
     */
    public void setNoFolio(long noFolio){
        this.noFolio=noFolio;
    }
    /**
     * Obtiene el estado del folio si esta usado o no
     * @return estado del folio
     */
    public boolean getUsado(){
        return this.usado;
    }
    /**
     * Ingresa el estado del folio de la factura
     * @param usado
     */
    public void setUsado(boolean usado){
        this.usado=usado;
    }
}

package servidorSAT.manejoFolios;

import java.sql.*;

/**
 * Clase que realiza la conexion con la Base de Dato del SAT y la solicitud de folios
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación Electrónica acorde a la reforma de enero de 2011
 *
 */
public class SolicitarFolios
{
	private Connection conn;
	private String driver="com.mysql.jdbc.Driver";
	private String url="jdbc:mysql://148.204.57.222/foliosSAT";
	private String usuario="isfe";
	private String password="isfe";

	/**
	 * Metodo que se encarga de la conexion con la Base de Datos
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
	 * Metodo que solicita un numero dado de folios y establece el estado de los mismos como ocupados
	 * @param numFolios parametro que establece el numero de folios que se solicitan al SAT
	 * @return regresa el resultado dela consulta pertinente de la solicitud de folios
	 * @throws SQLException excepcion que maneja los errores posibles con las sentencias SQL
	 */
	public ResultSet solicitarFolios(int numFolios) throws SQLException
	{
		String consultaFolios="select * from folios where usado=0 limit "+numFolios;
		String actualizarFolios="update folios set usado=1 where usado=0 limit "+numFolios;

		this.conectar();
		Statement st = conn.createStatement();
		Statement st2 = conn.createStatement();
		ResultSet rs = st.executeQuery(consultaFolios);
		st2.executeUpdate(actualizarFolios);

		return rs;
	}
}
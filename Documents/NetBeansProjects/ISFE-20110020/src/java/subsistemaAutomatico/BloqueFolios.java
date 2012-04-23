/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package subsistemaAutomatico;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author kawatoto
 */
public class BloqueFolios
{
    private Connection conn;
	private String driver="com.mysql.jdbc.Driver";
	private String url="jdbc:mysql://localhost/isfe";
	private String usuario="isfe";
	private String password="isfe";

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
}

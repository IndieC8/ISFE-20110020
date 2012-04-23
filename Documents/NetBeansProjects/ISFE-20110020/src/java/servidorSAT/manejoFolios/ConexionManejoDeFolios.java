package servidorSAT.manejoFolios;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase encargada de la conexion para la solicitud de folios entre cliente y servidor
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación Electrónica acorde a la reforma de enero de 2011
 */
public final class ConexionManejoDeFolios
{
	DataInputStream entrada;
	DataOutputStream salida;
	Socket socketCliente;
	Integer numFolios;

	/**
	 * Constructor que se encarga de la conexion correspondiente para la solicitud de folios
	 * @param socketCliente parametro que es el socket cliente a traves del cual se conectaran
	 */
	public ConexionManejoDeFolios(Socket socketCliente)
	{
		try
		{
			this.socketCliente=socketCliente;
			entrada = new DataInputStream(socketCliente.getInputStream());
			salida = new DataOutputStream(socketCliente.getOutputStream());
			this.numFolios=entrada.readInt();
			this.solicitar();
		}
		catch (Exception e)
		{
			System.out.println("Conexion Folios: "+e.getMessage());
		}
	}

	/**
	 * Metodo que solicita los folios al servidor simulado del SAT
	 */
	public void solicitar() throws IOException
	{
		SolicitarFolios folios = new SolicitarFolios();
		ResultSet rs;
		Integer arrayFolios[]=new Integer[numFolios];
		int i=0;
		try
		{
			rs = folios.solicitarFolios(numFolios);
			while(rs.next())
			{
				arrayFolios[i]=Integer.parseInt(rs.getString("numFolio"));
				salida.writeInt(arrayFolios[i]);
				i++;
			}
			i=0;
		}
		catch (SQLException e)
		{
		}
		for(i=0;i<numFolios;i++)
		{
			System.out.println("Folio: "+i+" vale : "+arrayFolios[i]);
		}
	}
}
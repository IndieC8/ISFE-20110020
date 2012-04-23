package servidorSAT.validarCadenaOriginal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

/**
 * Clase que se encarga de la conexion entre cliente y servidor para la validacion de la
 * cadena original
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación Electrónica acorde a la reforma de enero de 2011
 *
 */
public class ConexionCadenaOriginal
{
	DataInputStream entrada;
	DataOutputStream salida;
	Socket socketCliente;

	/**
	 * Constructor de la clase que realiza la conexion con el cliente
	 * @param socketCliente parametro que se refiere al socket con el que se realizara la conexion
	 */
	public ConexionCadenaOriginal(Socket socketCliente)
	{
		try
		{
			this.socketCliente=socketCliente;
			entrada = new DataInputStream(socketCliente.getInputStream());
			salida = new DataOutputStream(socketCliente.getOutputStream());
			this.validar();
		}
		catch (IOException e)
		{
			System.out.println("Conexion Cadena Original: "+e.getMessage());
		}
	}

	/**
	 * Metodo que valida la cadena original y envia un booleano indicando si la cadena es valida
	 * o no
	 */
	public void validar()
	{
		ValidaCadenas valida = new ValidaCadenas();
		try
		{
			Object cadenaOriginal=entrada.readUTF();
			if(valida.validaCadenaOriginal(cadenaOriginal)==true)
			{
				salida.writeBoolean(true);
				System.out.println("La cadena original ingresada ES valida");
			}
			else
			{
				salida.writeBoolean(false);
				System.out.println("La cadena original ingresada NO es valida");
			}

			salida.writeUTF(cadenaOriginal+"Esta es la cadena original recibida");
			socketCliente.close();
		}
		catch (EOFException e)
		{
			System.out.println("EOF: "+e.getMessage());
		}
		catch(IOException e)
		{
			System.out.println("IO: "+e.getMessage());
		}
	}
}
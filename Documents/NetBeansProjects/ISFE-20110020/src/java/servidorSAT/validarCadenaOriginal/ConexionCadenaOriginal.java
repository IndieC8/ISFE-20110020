package servidorSAT.validarCadenaOriginal;

import java.io.*;
import java.net.Socket;
import java.security.NoSuchProviderException;

/**
 * Clase que se encarga de la conexion entre cliente y servidor para la validacion de la
 * cadena original
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación Electrónica acorde a la reforma de enero de 2011
 *
 */
public class ConexionCadenaOriginal
{
	ObjectInputStream entrada;
	ObjectOutputStream salida;
	Socket socketCliente;

	/**
	 * Constructor de la clase que realiza la conexion con el cliente
	 * @param socketCliente parametro que se refiere al socket con el que se realizara la conexion
	 */
    @SuppressWarnings("OverridableMethodCallInConstructor")
	public ConexionCadenaOriginal(Socket socketCliente) throws SecurityException, NoSuchProviderException, Exception
	{
		try
		{
			this.socketCliente=socketCliente;
			entrada = new ObjectInputStream(socketCliente.getInputStream());
			salida = new ObjectOutputStream(socketCliente.getOutputStream());
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
	public void validar() throws SecurityException, NoSuchProviderException, Exception
	{
		ValidaCadenas valida = new ValidaCadenas();
		try
		{
			Object cadenaOriginal=entrada.readObject();
                        byte[] llave = (byte[]) entrada.readObject();
                        String password=(String) entrada.readObject();
                        Boolean tipo= (Boolean) entrada.readObject();
			if(!"NOGENERADO".equals(valida.validaCadenaOriginal(cadenaOriginal,llave,password,tipo)))
			{
				salida.writeObject(valida.validaCadenaOriginal(cadenaOriginal, llave, password, tipo));
				System.out.println("La cadena original ingresada ES valida");
			}
			else
			{
				salida.writeObject("NOGENERADO");
				System.out.println("La cadena original ingresada NO es valida");
			}

			salida.writeObject(cadenaOriginal+"Esta es la cadena original recibida");
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
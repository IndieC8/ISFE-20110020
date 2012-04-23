package servidorSAT.sat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import servidorSAT.validarCadenaOriginal.ConexionCadenaOriginal;


/**
 * Clase que lanza el hilo encargado del servidor de la validacion de las Cadenas Originales
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación Electrónica acorde a la reforma de enero de 2011
 *
 */
public class ServidorValidarCadenaOriginal extends Thread
{
    @Override
    public void run()
    {
        System.out.println("Servidor cadena original arrancando ...");
	try
	{
            System.out.println("Servidor abriendo Puerto...");
            int puerto=8090;
            System.out.println("Servidor abriendo socket...");
            ServerSocket socketCadenaOriginal=new ServerSocket(puerto);
            while(true)
            {
		Socket socketClienteCadenaOriginal=socketCadenaOriginal.accept();
		System.out.println("Servidor esperando...");
		@SuppressWarnings("unused")
		ConexionCadenaOriginal connCadenaOriginal= new ConexionCadenaOriginal(socketClienteCadenaOriginal);
            }
	}
	catch (IOException e)
	{
            System.out.println("Escuchando: "+e.getMessage());
	}
    }

}
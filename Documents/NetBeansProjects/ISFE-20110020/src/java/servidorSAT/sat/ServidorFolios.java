package servidorSAT.sat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import servidorSAT.manejoFolios.ConexionManejoDeFolios;

/**
 * Clase que lanza el hilo encargado del servidor simulado de la solicitud de folios del SAT
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación Electrónica acorde a la reforma de enero de 2011
 *
 */
public class ServidorFolios extends Thread
{
    @Override
    public void run()
    {
	System.out.println("Servidor manejo de folios arrancando ...");
	try
	{
            System.out.println("Servidor abriendo Puerto...");
            int puerto=8091;
            System.out.println("Servidor abriendo Socket ...");
            ServerSocket socketManejoFolios = new ServerSocket(puerto);
            while(true)
            {
                System.out.println("Servidor entrando a while...");
		Socket socketCliente=socketManejoFolios.accept();
                System.out.println("Servidor esperando:...");
		@SuppressWarnings("unused")
		ConexionManejoDeFolios conn = new ConexionManejoDeFolios(socketCliente);
		while(true)
		{
                    Socket socketClienteManejoFolios=socketManejoFolios.accept();
                    System.out.println("Servidor esperando...");
                    @SuppressWarnings("unused")
                    ConexionManejoDeFolios connManejoFolios= new ConexionManejoDeFolios(socketClienteManejoFolios);
		}
            }
	}
	catch(IOException e)
	{
            System.out.println("Escuchando: "+e.getMessage());
	}
    }
}
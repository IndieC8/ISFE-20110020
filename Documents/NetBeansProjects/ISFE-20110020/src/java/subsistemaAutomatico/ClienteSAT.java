/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package subsistemaAutomatico;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author kawatoto
 */
public class ClienteSAT
{
    public void ValidarCadenaOriginal(String cadenaOriginal)
    {
	try
	{
            int puertoServicio=8090;
            Socket socket = new Socket("localhost", puertoServicio);
            DataInputStream entrada = new DataInputStream(socket.getInputStream());
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            salida.writeUTF(cadenaOriginal);
            Object datos = entrada.readUTF();
            System.out.println("Recibido: "+datos);
            socket.close();
	}
	catch (UnknownHostException e) {System.out.println("Socket: "+e.getMessage());}
	catch (EOFException e) {System.out.println("EOF: "+e.getMessage());}
	catch (IOException e) {System.out.println("IO: "+e.getMessage());}
    }

    public void manejoDeFolios(int numFolios)
    {
	Integer arrayFolios[]=new Integer[numFolios];
    	BloqueFolios almacenaFolios = new BloqueFolios();
	int i=0;
	try
	{
            int puertoServicio=8091;
            Socket socket = new Socket("localhost", puertoServicio);
            DataInputStream entrada = new DataInputStream(socket.getInputStream());
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            //aqui ira el envio de informacion para la solicitud de Folios
            salida.writeInt(numFolios);
            while(i<=numFolios)
            {
		arrayFolios[i]=entrada.readInt();
		if(almacenaFolios.almacenarBloqueFolios(arrayFolios[i])==true)
		{
                    System.out.println("Folio almacenado correctamente");
		}
		else
		{
                    System.out.println("Los folios no se han podido almacenar");
		}
		i++;
            }
	}
	catch (Exception e)
	{
            System.out.println("Excepcion: "+e.getMessage());
	}
	}
}

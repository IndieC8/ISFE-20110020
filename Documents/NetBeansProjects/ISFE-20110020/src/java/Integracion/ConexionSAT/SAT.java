package Integracion.ConexionSAT;

import Negocios.ObtenerFolios.Folio;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Clase encargada de realizar las conexiones y solicitudes correspondientes
 * con el SAT
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación Electrónica acorde a la reforma de enero de 2011
 */
public class SAT{
    /**
     * Metodo encargado de realizar la conexion al servidor simulado del SAT
     * para la validacion de la cadena original generada
     * @param cadenaOriginal parametro que contendra la cadena original que
     * posteriormente sera validada
     * @return regresara un valor de true en caso de que la cadena original cumpla
     * con los requerimientos dados en el Anexo 20 de la miscelanea fiscal,
     * regresara false, en caso de que la cadena no cumpla con la especificacion
     * @throws UnknownHostException excepcion que maneja el caso de que el host
     * al que se realiza la peticion no se encuentre
     * @throws IOException maneja las excepciones de entrada-salida que se puedan
     * presentar al momento del envio de la informacion
     */
    public boolean ValidarCadenaOriginal(String cadenaOriginal) throws UnknownHostException, IOException
    {
        int puertoServicio=8090;
        Socket socket = new Socket("http://148.204.57.222", puertoServicio);
        DataInputStream entrada = new DataInputStream(socket.getInputStream());
        DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
        salida.writeUTF(cadenaOriginal);
        Object datos = entrada.readBoolean();
        if(datos.equals(datos) ==true)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    /**
     * Metodo encargado de realizar la conexio y la solicitud los folios al
     * sevidor simulado del SAT
     * @param numFolios este parametro establece el numero de folios que se habran
     * de solicitar con el SAT
     */
    public void manejoDeFolios(int numFolios)
    {
	Integer arrayFolios[]=new Integer[numFolios];
    	Folio almacenaFolios = new Folio();
	int i=0;
	try
	{
            int puertoServicio=8091;
            Socket socket = new Socket("http://148.204.57.222", puertoServicio);
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
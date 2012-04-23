package servidorSAT;

import servidorSAT.sat.ServidorFolios;
import servidorSAT.sat.ServidorValidarCadenaOriginal;

/**
 * Clase que se encarga de lanzar los hilos de los servidores de Validacion de Cadenas y
 * Solicitud de Folios
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación Electrónica acorde a la reforma de enero de 2011
 */
public class Main{
    public static void main(String args[]){
        ServidorValidarCadenaOriginal serverCadena = new ServidorValidarCadenaOriginal();
        ServidorFolios serverFolios = new ServidorFolios();
        serverCadena.start();
        serverFolios.start();
    }
}
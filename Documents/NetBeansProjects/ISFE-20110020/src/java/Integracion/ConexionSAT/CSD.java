package Integracion.ConexionSAT;

/**
 * Clase que representa al CSD (Certificado de Sello Digital) de los 
 * contribuyentes y del ISFE
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación Electrónica acorde a la reforma de enero de 2011
 */
public class CSD {
    private String noCertificado; //No. del Certificado del CSD
    private byte[] archivoCSD; //Archivo binario del CSD
    /**
     * Constructor vacío
     */
    public CSD(){
    }
    /**
     * Constructor sobrecargado que permite recibir parametros para crear el 
     * objetoc CSD.
     * @param archivoCSD binario del CSD
     * @param noCertificado del CSD previamente extraído
     */
    public CSD(byte[] archivoCSD,String noCertificado){
        this.setArchivoCSD(archivoCSD);
        this.setNoCertificado(noCertificado);
    }
    /**
     * Obtiene el No. del Certificado del CSD
     * @return NoCertificado del CSD
     */
    public String getNoCertificado(){
        return this.noCertificado;
    }
    /**
     * Ingresa el No. del Certificado del CSD previamente extraído
     * @param noCertificado del CSD
     */
    public void setNoCertificado(String noCertificado){
        this.noCertificado=noCertificado;
    }
    /**
     * Obtiene el archivo binario dek CSD para ser procesado dentro del ISFE
     * @return Archivo binario CSD
     */
    public byte[] getArchivoCSD(){
        return this.archivoCSD;
    }
    /**
     * Ingresa el archivo binario del CSD a utilizar
     * @param archivoCSD binario del CSD
     */
    public void setArchivoCSD(byte[] archivoCSD){
        this.archivoCSD=archivoCSD;
    }
}

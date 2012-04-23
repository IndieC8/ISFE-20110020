/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocios.ObtenerFiel;

/**
 * Clase que representa a la Firma Electrónica Avanzada FIEL
 * @author Raul Hernandez
 */
public class Fiel {
    private byte[] archivoFiel; //Archivo binario de la FIEL
    private String Password; //Contraseña de la FIEL
    /**
     * Constructor vacío
     */
    public Fiel(){
    }
    /**
     * Constructor sobrecargado que recibe parametros
     * @param archivoFiel archivo binario de la FIEL
     * @param Password contraseña de la FIEL
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Fiel(byte[] archivoFiel,String Password){
        this.setArchivoFiel(archivoFiel);
        this.setPassword(Password);
    }
    /**
     * Obtiene el archivo binario de la FIEL
     * @return archivo binario de la FIEL
     */
    public byte[] getArchivoFiel(){
        return this.archivoFiel;
    }
    /**
     * Ingresa el archivo binario de la FIEL 
     * @param archivoFiel binario de la FIEL
     */
    public void setArchivoFiel(byte[] archivoFiel){
        this.archivoFiel=archivoFiel;
    }
    /**
     * Obtiene el password o contraseña de la FIEL para descifrar la llave
     * privada
     * @return password o contraseña como una cadena de caracteres
     */
    public String getPassword(){
        return this.Password;
    }
    /**
     * Ingresa el password o contraseña de la FIEL para descifrar la llave
     * privada
     * @param Password de la llave privada de la FIEL
     */
    public void setPassword(String Password){
        this.Password=Password;
    }
}

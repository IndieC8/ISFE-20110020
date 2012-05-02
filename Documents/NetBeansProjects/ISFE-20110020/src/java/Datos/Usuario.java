package Datos;

import Integracion.ConexionSAT.CSD;
import Negocios.ObtenerFiel.Fiel;

/**
 * Clase que representa al usuario que esta registrado o que se va ha registrar
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación Electrónica acorde a la reforma de enero de 2011
 */
public class Usuario extends Contribuyente{
    private String password=null;
    private String curp=null;
    private String telefono;
    private CSD csd=null;
    private Fiel fiel=null;
    /**
     * Constructor vacío
     */
    public Usuario(){
    }
    /**
     * Constructor sobre cargado para recibir todos los parametros del usuario
     * directamente
     * @param RFC del usuario
     * @param Nombre del usuario
     * @param Correo del usuario
     * @param direccion del usuario
     * @param password del usuario
     * @param csd del usuario
     * @param fiel del usuario
     */
    public Usuario(boolean TipoPersona,String RFC, String Nombre, String ApPaterno, String ApMaterno,String Correo, Direccion direccion, String password, String curp, String telefono, CSD csd,Fiel fiel){
        this.setTipoPersona(TipoPersona);
        this.setNombre(Nombre);
        this.setApPaterno(ApPaterno);
        this.setApMaterno(ApMaterno);
        this.setRFC(RFC);
        this.setCorreo(Correo);
        this.setPassword(password);
        this.setCSD(csd);
        this.setFiel(fiel);
        this.setCurp(curp);
        this.setTelefono(telefono);
    }
    /**
     * Constructor sobre cargado para recibir los datos de una instancia del 
     * Contribuyente y los datos propios del Usuario
     * @param contribuyente instancia de la clase Contribuyente
     * @param password del usuario
     * @param csd del usuario
     * @param fiel del usuario
     */
    public Usuario(Contribuyente contribuyente, String password, CSD csd, Fiel fiel){
        this.setRFC(contribuyente.getRFC());
        this.setCorreo(contribuyente.getCorreo());
        this.setPassword(password);
        this.setCSD(csd);
        this.setFiel(fiel);
    }
    /**
     * Obtiene el password del usuario
     * @return password del usuario
     */
    public String getPassword(){
        return this.password;
    }
    /**
     * Ingresa el password del usuario
     * @param password del usuario
     */
    public void setPassword(String password){
        this.password=password;
    }
    
    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    /**
     * Obtiene el CSD del usuario
     * @return CSD del usuario
     */
    public CSD getCSD(){
        return this.csd;
    }
    /**
     * Ingresa el CSD del usuario
     * @param csd del usuario
     */
    public void setCSD(CSD csd){
        this.csd=csd;
    }
    /**
     * Obtiene la Fiel del usuario
     * @return Fiel del usuario
     */
    public Fiel getFiel(){
        return this.fiel;
    }
    /**
     * Ingresa la Fiel del usuario
     * @param fiel del usuario
     */
    public void setFiel(Fiel fiel){
        this.fiel=fiel;
    }
    /**
     * Método encargado de inicializar los datos del usuario.
     * @param TipoPersona del usuario.
     * @param RFC del usuario.
     * @param Nombre del usuario.
     * @param ApPaterno del usuario.
     * @param ApMaterno del usuario.
     * @param Razon del usuario.
     * @param Correo del usuario.
     * @param calle del usuario.
     * @param interior del usuario.
     * @param exterior del usuario.
     * @param colonia del usuario.
     * @param localidad del usuario.
     * @param municipio del usuario.
     * @param referencia del usuario.
     * @param estado del usuario.
     * @param codigoPostal del usuario.
     * @param password del usuario.
     * @param curp del usuario.
     * @param telefono del usuario.
     */
    public void inicializarDatos (boolean TipoPersona,String RFC, String Nombre, String ApPaterno, String ApMaterno,String Razon, String Correo,String calle, String interior, String exterior, String colonia, String localidad, String municipio,String referencia, String estado, String codigoPostal, String password, String curp, String telefono){
        super.inicializar(TipoPersona, RFC, Nombre, ApPaterno, ApMaterno, Razon, Correo, calle, interior, exterior, colonia, localidad, municipio, referencia, estado, codigoPostal);
        this.setCurp(curp);
        this.setPassword(password);
        this.setTelefono(telefono);
    }
}

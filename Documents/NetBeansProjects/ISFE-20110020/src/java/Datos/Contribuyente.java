package Datos;

import Negocios.Cifrado.Cifrado;
import dao.Sql;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase padre que representa los datos básicos que tienen las entidades 
 * clientes y usuarios como Contribuyentes registrados en el SAT.
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación Electrónica acorde a la reforma de enero de 2011
 */
public class Contribuyente {
    private boolean TipoPersona;
    private String RFC = "";
    private String CURP = "";
    private String Nombre = "";
    private String ApPaterno = "";
    private String ApMaterno = "";
    private String RazonSocial = "";
    private String Correo = "";
    private Direccion direccion = null;
    private String Referencia = "";
    private String idUsuario = "";
    Sql sql;
    /**
     * Constructor vacio
     */
    public Contribuyente(){
    }
    /**
     * Método que se encarga de inicializar los datos del contribuyente de 
     * acuerdo a los datos requeridos para la generación y emisión de facturas 
     * electrónicas en ISFE.
     * @param TipoPersona si es moral o física.
     * @param RFC del Contribuyente
     * @param Nombre del Contribuyente
     * @param Paterno del Contribuyente
     * @param Materno del Contribuyente
     * @param Razon del Contribuyente
     * @param Correo del Contribuyente
     * @param calle de la dirección del Contribuyente
     * @param interior de la dirección del Contribuyente
     * @param exterior de la dirección del Contribuyente
     * @param colonia de la dirección del Contribuyente
     * @param localidad de la dirección del Contribuyente
     * @param municipo de la dirección del Contribuyente
     * @param referencia de la dirección del Contribuyente
     * @param estado de la dirección del Contribuyente
     * @param codigoPostal de la dirección del Contribuyente
     */
    public void inicializar(boolean TipoPersona, String RFC, String Nombre, String Paterno, String Materno, String Razon, String Correo, String calle, String interior, String exterior, String colonia, String localidad, String municipio, String referencia, String estado, String codigoPostal,String usuario) {
        this.setTipoPersona(TipoPersona);
        this.setRFC(RFC);
        this.setNombre(Nombre);
        this.setApPaterno(Paterno);
        this.setApMaterno(Materno);
        this.setRazonSocial(Razon);
        this.setCorreo(Correo);
        direccion = new Direccion(calle, interior, exterior, colonia, localidad, municipio, referencia, estado, codigoPostal);
        this.setReferencia(referencia);
        this.idUsuario = Cifrado.decodificarBase64(usuario);
        sql = new Sql();
    }
    /**
     * Ingresa la refencia de la dirección del contribuyente
     * @param Referencia de la dirección
     */
    public void setReferencia(String Referencia) {
        this.Referencia = Referencia;
    }
    /**
     * Obtiene la referencia de la dirección del contribuyente
     * @return Refenecia de la dirección
     */
    public String getReferencia(){
        return this.Referencia;
    }
    /**
     * Obtiene el Apellido Materno del constribuyente
     * @return El apellido materno
     */
    public String getApMaterno() {
        return ApMaterno;
    }
    /**
     * Método que se encarga de insertar en la base de datos a un cliente de 
     * acuerdo a los datos establecidos del contribuyente,
     * @return el mensaje de error al ejecutar la consulta
     */
    public String Insertar(){
        sql = new Sql();
        String consulta= "INSERT INTO cliente VALUES(0,"+this.isTipoPersona()+",'"+this.getNombre()+"','"+this.getApPaterno()+"','"+this.getApMaterno()+"','"+this.getRazonSocial()+"','"+this.getRFC()+"',"
                + "'"+this.getCorreo()+"','"+direccion.getCalle()+"','"+direccion.getNoInterior()+"','"+direccion.getNoExterior()+"',"+direccion.getColonia()+",'"+direccion.getReferencia()+"',"+Integer.parseInt(this.idUsuario) +")";
        return sql.ejecuta(consulta);
    }
    /**
     * Ingresa el apellido materno del contribuyente
     * @param ApMaterno 
     */
    public void setApMaterno(String ApMaterno) {
        this.ApMaterno = ApMaterno;
    }
    /**
     * Obtiene el apellido paterno del contribuyente
     * @return Apellido paterno del contribuyente
     */
    public String getApPaterno() {
        return ApPaterno;
    }
    /**
     * Ingresa el apellido paterno del contribuyente
     * @param ApPaterno  del contribuyente
     */
    public void setApPaterno(String ApPaterno) {
        this.ApPaterno = ApPaterno;
    }
    /**
     * Obtiene el curp del contribuyente
     * @return curp del contribuyente
     */
    public String getCURP() {
        return CURP;
    }
    /**
     * Ingresa el curp del contribuyente
     * @param CURP del contribuyente
     */
    public void setCURP(String CURP) {
        this.CURP = CURP;
    }
    /**
     * Obtiene el correo electrónico del contribuyente
     * @return correo electrónico del contribuyente
     */
    public String getCorreo() {
        return Correo;
    }
    /**
     * Ingresa el correo electrónico del contribuyente
     * @param Correo del contribuyente
     */
    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }
    /**
     * Obtiene el nombre del contribuyente
     * @return nombre del contribuyente
     */
    public String getNombre() {
        return Nombre;
    }
    /**
     * Ingresa el nombre del contribuyente
     * @param Nombre del contribuyente
     */
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    /**
     * Obtiene el RFC del contribuyente
     * @return RFC del contribuyente
     */
    public String getRFC() {
        return RFC;
    }
    /**
     * Ingresa el RFC del contribuyente
     * @param RFC del contribuyente
     */
    public void setRFC(String RFC) {
        this.RFC = RFC;
    }
    /**
     * Obtiene la Razon Social del contribuyente
     * @return Razon Social del contribuyente
     */
    public String getRazonSocial() {
        return RazonSocial;
    }
    /**
     * Ingresa la Razon Social del contribuyente
     * @param RazonSocial del contribuyente
     */
    public void setRazonSocial(String RazonSocial) {
        this.RazonSocial = RazonSocial;
    }
    /**
     * válida el tipo de persona que es el contribuyente (Física o Moral)
     * @return Tipo de persona del contribuyente
     */
    public boolean isTipoPersona() {
        return TipoPersona;
    }
    /**
     * Ingresa el tipo de persona del contribuyente
     * @param TipoPersona del contribuyente
     */
    public void setTipoPersona(boolean TipoPersona) {
        this.TipoPersona = TipoPersona;
    }
    /**
     * Obtiene la Dirección del contribuyente
     * @return Dirección del contribuyente
     */
    public Direccion getDireccion(){
        return this.direccion;
    }
    /**
     * Ingresa la dirección del contribuyente
     * @param direccion del contribuyente
     */
    public void setDirecction(Direccion direccion){
        this.direccion=direccion;
    }
    /**
     * Método encargado de validar el RFC del contribuyente realizando una 
     * consulta a la base de datos verificando el IdCliente, el tipo de persona 
     * y su razón social.
     * @param consulta que válida el RFC del contribuyente
     * @return el Id del Cliente y su RFC de acuerdo a la razón social del mismo
     * en un arreglo de String
     */
    public String[] ValidarRFC(String consulta){
       sql = new Sql();
       String []resultado = new String[2];
       
       try {
            ResultSet rs = sql.consulta(consulta);
            while(rs.next()){
                resultado[0] = String.valueOf( rs.getInt("idCliente") );
                if( rs.getBoolean("tipoPersona") == false){
                    resultado[1] = rs.getString("nombreCliente") +" "+ rs.getString("APaternoCliente")+" "+ rs.getString("AMaternoCliente");
                }else{
                    resultado[1] = rs.getString("razonCliente");
                }
                
            }
        } catch (InstantiationException ex) {
            Logger.getLogger(Contribuyente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Contribuyente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Contribuyente.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return resultado;
    }
    /**
     * Válida el RFC del contribuyente realizando la consulta a la base de datos
     * @param consulta para validar el RFC  del contribuyente
     * @param elemento de la consulta para devolver el resultado de la 
     * validación
     * @return entero de la columna del nombre elemento de la consulta realizada
     * si existe; En caso contrario devuelve un cero
     */
    public int ValidarRFC(String consulta,String elemento){
        sql = new Sql();
        int resultado = 0; 
        try {
            ResultSet rs = sql.consulta(consulta);
            while(rs.next()){
                resultado =  rs.getInt(elemento);  
            }
        } catch (InstantiationException ex) {
            Logger.getLogger(Contribuyente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Contribuyente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Contribuyente.class.getName()).log(Level.SEVERE, null, ex);
        }
       return resultado;
    }
    /**
     * Método encargado de validar los datos del contribuyente de acuerdo al 
     * tipo de persona (true es persona moral, false es persona física)
     * @return mensaje para solicitar los datos pertinente del contribuyente en
     * base al tipo de persona
     */
    public String Validar() {

        if (TipoPersona == true) {
            /*
             * Persona Moral
             */
            Nombre = null;
            ApPaterno = null;
            ApMaterno = null;
            if ("".equals(RazonSocial)) {
                return "Ingresa Razón Social";
            }
        } else {
            /*
             * Persona Fisica
             */
            RazonSocial = null;
            if ("".equals(Nombre)) {
                return "Ingresa el Nombre";
            } else if ("".equals(ApPaterno)) {
                return "Ingresa el Apellido Paterno";
            } else if ("".equals(ApMaterno)) {
                return "Ingresa el Apellido Materno";
            }
        }

        /*Valores por defecto*/
        if ("".equals(RFC)) {
            return "Ingresa el RFC";
        } else if ("".equals(Correo)) {
            return "Ingresa el E-mail";
        } 

        return null;
    }
}

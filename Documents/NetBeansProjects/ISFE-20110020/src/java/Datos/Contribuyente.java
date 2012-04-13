package Datos;

import dao.Sql;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase padre que representa los datos básicos que tienen las entidades 
 * clientes y usuarios
 * @author Raul Hernandez
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
    Sql sql;
    /**
     * Constructor vacio
     */
    public Contribuyente(){
    }
    /**
     * Método que se encarga de inicializar los datos del contribuyente
     * @param TipoPersona
     * @param RFC
     * @param Nombre
     * @param Paterno
     * @param Materno
     * @param Razon
     * @param Correo
     * @param calle
     * @param interior
     * @param exterior
     * @param colonia
     * @param localidad
     * @param municipo
     * @param referencia
     * @param estado
     * @param codigoPostal 
     */
    public void inicializar(boolean TipoPersona, String RFC, String Nombre, String Paterno, String Materno, String Razon, String Correo, String calle, String interior, String exterior, String colonia, String localidad, String municipio, String referencia, String estado, String codigoPostal) {
        this.setTipoPersona(TipoPersona);
        this.setRFC(RFC);
        this.setNombre(Nombre);
        this.setApPaterno(Paterno);
        this.setApMaterno(Materno);
        this.setRazonSocial(Razon);
        this.setCorreo(Correo);
        direccion = new Direccion(calle, interior, exterior, colonia, localidad, municipio, referencia, estado, codigoPostal);
        this.setReferencia(referencia);
        sql = new Sql();
    }
    public void setReferencia(String Referencia) {
        this.Referencia = Referencia;
    }
    /**
     * Obtiene el Apellido Materno del constribuyente
     * @return El apellido materno
     */
    public String getApMaterno() {
        return ApMaterno;
    }
    public String Insertar(){
        sql = new Sql();
        String consulta= "INSERT INTO cliente VALUES(0,"+this.isTipoPersona()+",'"+this.getNombre()+"','"+this.getApPaterno()+"','"+this.getApMaterno()+"','"+this.getRazonSocial()+"','"+this.getRFC()+"',"
                + "'"+this.getCorreo()+"','"+direccion.getCalle()+"','"+direccion.getNoInterior()+"','"+direccion.getNoExterior()+"',"+direccion.getColonia()+",'"+direccion.getReferencia()+"',0)";
        return sql.ejecuta(consulta);
    }

    public void setApMaterno(String ApMaterno) {
        this.ApMaterno = ApMaterno;
    }

    public String getApPaterno() {
        return ApPaterno;
    }
        

    public void setApPaterno(String ApPaterno) {
        this.ApPaterno = ApPaterno;
    }

    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getRazonSocial() {
        return RazonSocial;
    }

    public void setRazonSocial(String RazonSocial) {
        this.RazonSocial = RazonSocial;
    }

    public boolean isTipoPersona() {
        return TipoPersona;
    }

    public void setTipoPersona(boolean TipoPersona) {
        this.TipoPersona = TipoPersona;
    }
    public Direccion getDireccion(){
        return this.direccion;
    }
    public void setDirecction(Direccion direccion){
        this.direccion=direccion;
    }
    public String[] ValidarRFC(String consulta){
       sql = new Sql();
       String []resultado = new String[2];
       
       try {
            ResultSet rs = sql.consulta(consulta);
            while(rs.next()){
                resultado[0] = String.valueOf( rs.getInt("idCliente") );
                if( rs.getBoolean("tipoPersona") == false){
                    resultado[1] = rs.getString("nombreCliente") + rs.getString("APaternoCliente") + rs.getString("AMaternoCliente");
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
        } else if ("".equals(Referencia)) {
            return "Ingresa la Referencia";
        }

        return null;
    }
}

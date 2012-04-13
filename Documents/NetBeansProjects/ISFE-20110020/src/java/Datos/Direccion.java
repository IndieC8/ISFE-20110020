package Datos;

/**
 * Clase que representa la dirección del cliente
 * @author Raul Hernandez
 */
public class Direccion {
    private String Calle=null;
    private String NoInterior=null;
    private String NoExterior=null;
    private String Colonia=null;
    private String Localidad=null;
    private String Municipio=null; 
    private String Referencia=null;
    private String Estado=null;
    private String CodigoPostal=null;
    /**
     * Constructor vacio
     */
    public Direccion(){
    }
    /**
     * Constructor sobrecargado para ingresar todos los datos de la direccion 
     * del Contribuyente ya sea persona física o moral
     * @param Calle
     * @param NoInterior
     * @param NoExterior
     * @param Colonia
     * @param Localidad
     * @param Municipio
     * @param Referencia
     * @param Estado
     * @param CodigoPostal 
     */
    public Direccion(String Calle, String NoInterior, String NoExterior, String Colonia, String Localidad, String Municipio,String Referencia,String Estado,String CodigoPostal){
        this.setCalle(Calle);
        this.setNoInterior(NoInterior);
        this.setNoExterior(NoExterior);
        this.setColonia(Colonia);
        this.setLocalidad(Localidad);
        this.setMunicipio(Municipio);
        this.setReferencia(Referencia);
        this.setEstado(Estado);
        this.setCodigoPostal(CodigoPostal);
    }
    /**
     * Obtiene la Calle de la dirección fiscal
     * @return calle fiscal
     */
    public String getCalle() {
        return Calle;
    }
    /**
     * Ingresa la calle de la dirección fiscal
     * @param Calle de la dirección fiscal
     */
    public void setCalle(String Calle) {
        this.Calle = Calle;
    }
    /**
     * Obtiene la Colonia de la dirección fiscal
     * @return colonia de la dirección fiscal
     */
    public String getColonia() {
        return Colonia;
    }
    /**
     * Ingresa la colonia de la dirección fiscal
     * @param Colonia de la dirección fiscal
     */
    public void setColonia(String Colonia) {
        this.Colonia = Colonia;
    }
    /**
     * Obtiene el municipio de la dirección fiscal
     * @return municipio de la dirección fiscal
     */
    public String getMunicipio() {
        return Municipio;
    }
    /**
     * Ingresa el municipio de la dirección fiscal
     * @param Municipio de la dirección fiscal
     */
    public void setMunicipio(String Municipio) {
        this.Municipio = Municipio;
    }
    /**
     * Obtiene el No. exterior de la dirección fiscal
     * @return No. exterior de la dirección fiscal
     */
    public String getNoExterior() {
        return NoExterior;
    }
    /**
     * Ingresa el No. exterior de la dirección fiscal
     * @param NoExterior de la dirección fiscal
     */
    public void setNoExterior(String NoExterior) {
        this.NoExterior = NoExterior;
    }
    /**
     * Obtiene el No. interior de la dirección fiscal
     * @return No. interior de la dirección fiscal
     */
    public String getNoInterior() {
        return NoInterior;
    }
    /**
     * Ingresa el No. Interior de la dirección fiscal
     * @param NoInterior de la dirección fiscal
     */
    public void setNoInterior(String NoInterior) {
        this.NoInterior = NoInterior;
    }
    /**
     * Obtiene la referencia de la dirección fiscal
     * @return referencia de la dirección fiscal
     */
    public String getReferencia() {
        return Referencia;
    }
    /**
     * Ingresa la referencia de la dirección fiscal
     * @param Referencia de l a dirección fiscal
     */
    public void setReferencia(String Referencia) {
        this.Referencia = Referencia;
    }
    /**
     * Obtiene el estado de la dirección fiscal
     * @return  estado de la dirección fiscal
     */
    public String getEstado() {
        return Estado;
    }
    /**
     * Ingresa el estado de la dirección fiscal
     * @param Estado de la dirección fiscal
     */
    public void setEstado(String Estado) {
        this.Estado = Estado;
    }
    /**
     * Obtiene la localidad de la dirección fiscal
     * @return la localidad de la dirección fiscal
     */
    public String getLocalidad() {
        return Localidad;
    }
    /**
     * Ingresa la localidad de la dirección fiscal
     * @param Localidad de la dirección fiscal
     */
    public void setLocalidad(String Localidad) {
        this.Localidad = Localidad;
    }
    /**
     * Obtiene el código postal de la dirección fiscal
     * @return el código postal de la dirección fiscal
     */
    public String getCodigoPostal() {
        return CodigoPostal;
    }
    /**
     * Ingresa el código postal de la dirección fiscal
     * @param CodigoPostal de la dirección fiscal
     */
    public void setCodigoPostal(String CodigoPostal) {
        this.CodigoPostal = CodigoPostal;
    }
}

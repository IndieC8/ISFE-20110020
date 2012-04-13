package Datos;

import Integracion.ConexionSAT.CSD;
import Negocios.ObtenerFiel.Fiel;
import Negocios.ObtenerFolios.Folio;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
/**
 * Clase que representa a la entidad factura que se genera en base a los datos 
 * ingresados por el Usuario
 * @author Raul Hernandez
 */
public class Factura {
    private Date Fecha=null;
    private double IVA=0f;
    private double SubTotal=0f;
    private double Total=0f;
    private String cadenaCSD=null;
    //private CadenaOriginal cadenaOriginal=null;
    private Folio folio=null;
    private ArrayList<Concepto> conceptos;
    private Contribuyente receptor;
    private Usuario emisor;
    private Direccion expedidoEn;
    private Fiel fiel=null;
    private CSD csd=null;
    private String formaDePago=null;
    private String metodoDePago=null;
    private String tipoDeComprobante=null;
    /**
     * Constructor vacío.
     */
    public Factura(){
    }
    /**
     * Obtiene la fecha de la factura
     * @return Fecha en que se generó la factura
     */
    public Date getFecha(){
        return this.Fecha;
    }
    /**
     * Ingresa la fecha de la factura
     * @param Fecha de la factura
     */
    public void setFecha(Date Fecha){
        this.Fecha=Fecha;
    }
    /**
     * Obtiene el IVA de la factura
     * @return IVA de la factura generada
     */
    public double getIVA(){
        return this.IVA;
    }
    /**
     * Ingresa el IVA de la factura
     * @param IVA de la factura
     */
    public void setIVA(double IVA){
        this.IVA=IVA;
    }
    /**
     * Obtiene el SubTotal de la factura
     * @return SubTotal de la factura
     */
    public double getSubTotal(){
        return this.SubTotal;
    }
    /**
     * Ingresa el SubTotal de la factura
     * @param SubTotal de la factura
     */
    public void setSubTotal(double SubTotal){
        this.SubTotal=SubTotal;
    }
    /**
     * Obtienene el Total de la factura 
     * @return Total de la factura
     */
    public double getTotal(){
        return this.Total;
    }
    /**
     * Ingresa el Total de la factura
     * @param Total de la factura
     */
    public void setTotal(double Total){
        this.Total=Total;
    }
    /**
     * Obtiene el CSD de la factura
     * @return CSD de la factura
     */
    public CSD getCSD(){
        return this.csd;
    }
    /**
     * Ingresa el CSD de la factura
     * @param csd de la factura
     */
    public void setCSD(CSD csd){
        this.csd=csd;
    }
    /**
     * Obtiene la Cadena Original de la factura
     * @return Cadena Original de la factura
     */
    /**public CadenaOriginal getCadenaOriginal(){
        return this.cadenaOriginal;
    }*/
    /**
     * Ingresa la Cadena Original de la factura
     * @param cadenaOriginal de la factura
     */
    /**public void setCadenaOriginal(CadenaOriginal cadenaOriginal){
        this.cadenaOriginal=cadenaOriginal;
    }*/
    /**
     * Obtiene el Folio de la factura
     * @return Folio de la factura
     */
    public Folio getFolio(){
        return this.folio;
    }
    /**
     * Ingresa el folio de la factura
     * @param folio de la factura
     */
    public void setFolio(Folio folio){
        this.folio=folio;
    }
    /**
     * Obtiene el emisor de la factura (Usuario)
     * @return emisor 
     */
    public Usuario getEmisor(){
        return this.emisor;
    }
    /**
     * Ingresa el emisor de la factura (Usuario)
     * @param emisor de la factura
     */
    public void setEmisor(Usuario emisor){
        this.emisor=emisor;
    }
    /**
     * Obtiene el receptor de la factura (Cliente)
     * @return receptor
     */
    public Contribuyente getReceptor(){
        return this.receptor;
    }
    /**
     * Ingresa el receptor de la factura (Cliente)
     * @param receptor de la factura
     */
    public void setReceptor(Contribuyente receptor){
        this.receptor=receptor;
    }
    /**
     * Obtiene los distintos conceptos que contiene la factura electrónica
     * @return conceptos
     */
    public ArrayList<Concepto> getConceptos(){
        return this.conceptos;
    }
    /**
     * Ingresa los distintos conceptos que contiene la factura electrónica
     * @param conceptos de la factura
     */
    public void setConceptos(ArrayList<Concepto> conceptos){
        this.conceptos=conceptos;
    }
    /**
     * Obtiene la dirección en donde fue expedido la factura electrónica
     * @return Direccion de expedición
     */
    public Direccion getExpedidoEn(){
        return this.expedidoEn;
    }
    /**
     * Ingresa la Direccion de expedición de la factura electrónica
     * @param expedidoEn de la factura
     */
    public void setExpedidoEn(Direccion expedidoEn){
        this.expedidoEn=expedidoEn;
    }
    /**
     * Obtiene la cadena del CSD generada para sellar la factura electrónica
     * @return CSD como cadena de caracteres de la factura
     */
    public String getCadenaCSD(){
        return this.cadenaCSD;
    }
    /**
     * Ingresa la cadena de caracteres del CSD generada para la factura 
     * electrónica
     * @param cadenaCSD de la factura
     */
    public void setCadenaCSD(String cadenaCSD){
        this.cadenaCSD=cadenaCSD;
    }
    /**
     * Obtiene la tasa de importe de la factura electrónica
     * @return la tasa del importe calculada mediante la formula 
     * Tasa de Importe = Total - SubTotal
     */
    public double getImporteTasa(){
        return Total - SubTotal;
    }
    /**
     * Obtiene la Fiel del Usuario o emisor de la factura electrónica
     * @return FIEL del usuario o emisor
     */
    public Fiel getFiel(){
        return this.fiel;
    }
    /**
     * Ingresa la FIEL del Usuario o E>misor de la factura eletrónica
     * @param fiel del usuario o emisor
     */
    public void setFiel(Fiel fiel){
        this.fiel=fiel;
    }
    public String getFormaDePago(){
        return this.formaDePago;
    }
    public void setFormaDePago(String formaDePago){
        this.formaDePago=formaDePago;
    }
    public String getMetodoDePago(){
        return this.metodoDePago;
    }
    public void setMetodoDePago(String metodoDePago){
        this.metodoDePago=metodoDePago;
    }
    public String getTipoDeComprobante(){
        return this.tipoDeComprobante;
    }
    public void setTipoDeComprobante(String tipoDeComprobante){
        this.tipoDeComprobante=tipoDeComprobante;
    }
    public void generarIVA(){
        this.IVA=this.SubTotal*0.15;
    }
    public void generarSubtotal(){
        Iterator i=this.conceptos.iterator();
        while(i.hasNext()){
            double c=0;
            double vU=0;
            Concepto concepto=(Concepto)i.next();
            c=concepto.getCantidad();
            vU=concepto.getImporte();
            this.SubTotal+=(c*vU);
        }
    }
    public void generarTotal(){
        this.Total=this.SubTotal+this.IVA;
    }
}

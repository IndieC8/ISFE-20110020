/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

/**
 * Clase que representa el concepto de pago que se generará y que estará
 * dentro de la factura a generar
 * @author Raul Hernandez
 */
public class Concepto {
    private String unidad=null;
    private double cantidad=0;
    private String descripcion=null;
    private double valorUnitario=0;
    private double importe=0;
    private String noIdentificacion=null;
    /**
     * Constructor vacío
     */
    public Concepto(){
    }
    public Concepto(double cantidad, String unidad, String descripcion, double valorUnitario, double importe){
        this.setCantidad(cantidad);
        this.setUnidad(unidad);
        this.setDescripcion(descripcion);
        this.setValorUnitario(valorUnitario);
        this.setImporte(importe);
    }
    public Concepto(double cantidad, String unidad,String noIdentificacion, String descripcion, double valorUnitario, double importe){
        this.setCantidad(cantidad);
        this.setUnidad(unidad);
        this.setNoIdentifiacion(noIdentificacion);
        this.setDescripcion(descripcion);
        this.setValorUnitario(valorUnitario);
        this.setImporte(importe);
    }
    /**
     * Obtiene la cantidad del concepto de la factura a generar
     * @return cantidad del concepto
     */
    public double getCantidad(){
        return this.cantidad;
    }
    /**
     * Ingresa la cantidad del concepto de la factura a generar
     * @param cantidad del concepto
     */
    public void setCantidad(double cantidad){
        this.cantidad=cantidad;
    }
    /**
     * Obtiene la cantidad del concepto de la factura a generar
     * @return Descripcion del concepto 
     */
    public String getDescripcion(){
        return this.descripcion;
    }
    /**
     * Ingresa la cantidad del concepto de la factura a generar
     * @param descripcion del concepto
     */
    public void setDescripcion(String descripcion){
        this.descripcion=descripcion;
    }
    /**
     * Obtiene la cantidad del concepto de la factura a generar
     * @return valor unitario del concepto
     */
    public double getValorUnitario(){
        return this.valorUnitario;
    }
    /**
     * Ingresa el precio unitario del concepto de la factura a generar
     * @param valorUnitario del concepto
     */
    public void setValorUnitario(double valorUnitario){
        this.valorUnitario=valorUnitario;
    }
    /**
     * Obtiene el importe del concepto de la factura a generar
     * @return importe del concepto
     */
    public double getImporte(){
        return this.importe;
    }
    /**
     * Ingresa el importe del concepto de la factura a generar
     * @param importe del concepto
     */
    public void setImporte(double importe){
        this.importe=importe;
    }
    public String getUnidad(){
        return this.unidad;
    }
    public void setUnidad(String unidad){
        this.unidad=unidad;
    }
    public String getNoIdentificacion(){
        return this.noIdentificacion;
    }
    public void setNoIdentifiacion(String noIdentificacion){
        this.noIdentificacion=noIdentificacion;
    }
    /**
     * Agrega un concepto nuevo a la factura
     * @param concepto de la factura
     */
    public void agregarConcepto(Concepto concepto){
    }
}

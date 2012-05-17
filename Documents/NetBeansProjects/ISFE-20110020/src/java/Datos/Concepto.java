package Datos;

/**
 * Clase que representa al concepto (articulo) de la factura electrónica de
 * acuerdo a la version 3 que establece el SAT
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación Electrónica acorde a la reforma de enero de 2011
 */
public class Concepto {
    private String unidad=null;
    private double cantidad=0;
    private String descripcion=null;
    private double valorUnitario=0;
    private double importe=0;
    private String nombreProducto=null;
    /**
     * Constructor vacío
     */
    public Concepto(){
    }
    /**
     * Constructor del concepto que recibe como parametros a los datos
     * obligatorios de acuerdo a la versión 3 de la factura eletrónica como lo
     * establece el SAT.
     * @param cantidad del concepto.
     * @param unidad especificando como se mide el concepto (kg., g., etc.)
     * @param descripcion del concepto.
     * @param valorUnitario un articulo del concepto.
     * @param importe se calcula multiplicando el Valor Unitario por la cantidad
     * del concepto.
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Concepto(double cantidad, String unidad, String descripcion, double valorUnitario, double importe){
        this.setCantidad(cantidad);
        this.setUnidad(unidad);
        this.setDescripcion(descripcion);
        this.setValorUnitario(valorUnitario);
        this.setImporte(importe);
    }
    /**
     * Constructor del concepto que recibe como parametros a los datos
     * obligatorios y opcionales de acuerdo a la version 3 de la factura
     * electrónica como lo estable el SAT
     * @param cantidad
     * @param unidad
     * @param nombreProducto
     * @param descripcion
     * @param valorUnitario
     * @param importe
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Concepto(double cantidad, String unidad,String nombreProducto, String descripcion, double valorUnitario, double importe){
        this.setCantidad(cantidad);
        this.setUnidad(unidad);
        this.setnombreProducto(nombreProducto);
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
    public String getnombreProducto(){
        return this.nombreProducto;
    }
    public void setnombreProducto(String nombreProducto){
        this.nombreProducto=nombreProducto;
    }
    /**
     * Agrega un concepto nuevo a la factura
     * @param concepto de la factura
     */
    public void agregarConcepto(Concepto concepto){
    }
}

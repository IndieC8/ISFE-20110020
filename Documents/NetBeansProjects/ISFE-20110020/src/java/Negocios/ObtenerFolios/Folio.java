/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocios.ObtenerFolios;

/**
 * Clase que representa los folios que se utilizan para la generación de las
 * Facturas electrónicas dentro del ISFE
 * @author Raul Hernandez
 */
public class Folio {
    private long UUID;
    private long noFolio;
    private boolean usado;
    /**
     * Constructor vacío
     */
    public Folio(){
    }
    /**
     * Cosntructor que recibe el folio y el estado del mismo
     */
    public Folio(long UUID,long noFolio,boolean usado){
        this.setUUID(UUID);
        this.setNoFolio(noFolio);
        this.setUsado(usado);
    }
    public long getUUID(){
        return this.UUID;
    }
    public void setUUID(long UUID){
        this.UUID=UUID;
    }
    /**
     * Obtiene el folio de la factura 
     * @return folio de la factura
     */
    public long getNoFolio(){
        return this.noFolio;
    }
    /**
     * Ingresa el folio de la factura
     * @param noFolio 
     */
    public void setNoFolio(long noFolio){
        this.noFolio=noFolio;
    }
    /**
     * Obtiene el estado del folio si esta usado o no
     * @return estado del folio
     */
    public boolean getUsado(){
        return this.usado;
    }
    /**
     * Ingresa el estado del folio de la factura
     * @param usado 
     */
    public void setUsado(boolean usado){
        this.usado=usado;
    }
}

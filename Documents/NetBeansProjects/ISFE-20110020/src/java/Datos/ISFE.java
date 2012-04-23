/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Integracion.ConexionSAT.CSD;
import Negocios.ObtenerFiel.Fiel;

/**
 * Clase que representa al ISFE como PAC (Proveedor Autorizado de certificación)
 * para poder sellar la factura electrónica mediante el uso de nuestra FIEL y 
 * CSD.
 * @author Raul Hernandez
 */
public class ISFE {
    private Fiel fiel=null; //Fiel del ISFE como PAC
    private CSD csd=null; //CSD del ISFE como PAC
    /**
     * Constructor vacío
     */
    public ISFE(){
    }
    /**
     * Constructor sobrecargado que recibe los parametros del ISFE como PAC
     * @param fiel del ISFE
     * @param csd del ISFE
     * @param UUID a usar para la factura electrónica
     */
    public ISFE(Fiel fiel,CSD csd,String UUID){
        this.setFiel(fiel);
        this.setCSD(csd);
    }
    /**
     * Obtiene la Firma Electrónica Avanzada FIEL del ISFE para certificar 
     * la factura como PAC
     * @return FIEL del ISFE
     */
    public Fiel getFiel(){
        return this.fiel;
    }
    /**
     * Ingresa la Firma Electrónica Avanzada FIEL del ISFE para certificar 
     * una factura electrónica
     * @param fiel del ISFE
     */
    public void setFiel(Fiel fiel){
        this.fiel=fiel;
    }
    /**
     * Obtiene el Certificado de Sello Digital o CSD del ISFE como PAC
     * @return CSD del ISFE
     */
    public CSD getCSD(){
        return this.csd;
    }
    /**
     * Ingresa el Certificado de Sello Digital o CSD del ISFE como PAC
     * @param csd del ISFE
     */
    public void setCSD(CSD csd){
        this.csd=csd;
    }
}

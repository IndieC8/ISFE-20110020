/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocios.Cifrado;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;

/**
 *
 * @author lupe
 */
public class DES {

    SecretKey key;
    Cipher cifrador;

    public DES(String clave) {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
            DESKeySpec kspec = new DESKeySpec(clave.getBytes());
            key = skf.generateSecret(kspec);

            cifrador = Cipher.getInstance("DES");

        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String Cifrador(String Cadena) {
        byte[] cifrado = null;
        try {
            cifrador.init(Cipher.ENCRYPT_MODE, key);
            byte[] utf8 = Cadena.getBytes("UTF8");
            cifrado = cifrador.doFinal(utf8);
            //System.out.println("Cifrado: "+cifrado);

        } catch (InvalidKeyException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Cifrado.codificarBase64(cifrado);

    }

    public String Descifrado(String cifra) {
        String salida = null;
        try {
            cifrador.init(Cipher.DECRYPT_MODE, key);
            byte[] dec = Cifrado.decodificarBase64Byte(cifra);
            //System.out.println("Decodificado: "+dec);
            byte[] utf8 = cifrador.doFinal(dec);
            salida = new String(utf8, "UTF8");


        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        }

        return salida;
    }
    
    public static void main(String args[]){
        String base = "POPO999999POP";
        DES des = new DES("lupe8912");
        System.out.println(des.Cifrador(base));
    }

    
}

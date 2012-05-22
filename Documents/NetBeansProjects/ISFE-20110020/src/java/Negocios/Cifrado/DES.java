/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocios.Cifrado;

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
            /*
             * PASO 1: Crear e inicializar clave
             */
            SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
            DESKeySpec kspec = new DESKeySpec(clave.getBytes());
             key = skf.generateSecret(kspec);
            
            /*
             * PASO 2: Crear cifrador
             */
            cifrador = Cipher.getInstance("DES/ECB/PKCS5Padding");
            /*
             * Algoritmo DES Modo : ECB (Electronic Code Book) Relleno :
             * PKCS5Padding
             */
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
        String textoCifrado = null;
        try {
            /*
             * PASO 3b: Inicializar cifrador en modo DESCIFRADO
             */
            cifrador.init(Cipher.ENCRYPT_MODE, key);
            byte[] cadenaBytes = Cadena.getBytes();
            byte[] bufferCifrado = cifrador.doFinal(cadenaBytes);
           textoCifrado = new String(bufferCifrado);
            
        } catch (InvalidKeyException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
            }
        return textoCifrado;
    }
    
    public String Descifrado(String textoCifrado){
        String textoClaro = null;
        try {
            /*
             * PASO 3a: Inicializar cifrador en modo CIFRADO
             */
            cifrador.init(Cipher.DECRYPT_MODE, key);
            byte[] bufferCifrado = cifrador.doFinal(textoCifrado.getBytes());
           textoCifrado = new String(bufferCifrado);
            
        } catch (InvalidKeyException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return textoClaro;
    }
}

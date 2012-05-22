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
            System.out.println(key);
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
        String textoCifrado = new String();
        try {
            cifrador.init(Cipher.ENCRYPT_MODE, key);
            byte[] cadena = Cadena.getBytes();
            byte[] bufferCifrado = cifrador.update(cadena);

            textoCifrado = textoCifrado + new String(bufferCifrado, "ISO-8859-1");

           



        } catch (InvalidKeyException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        }
        return textoCifrado;
    }

    public String Descifrado(String textoCifrado) {
        String textoClaro = new String();
        try {
            cifrador.init(Cipher.DECRYPT_MODE, key);
            byte[] cadena = textoCifrado.getBytes();
            byte[] bufferCifrado = cifrador.update(cadena);
            textoClaro = textoClaro + new String(bufferCifrado, "ISO-8859-1");
            


        } catch (InvalidKeyException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        }

        return textoClaro;
    }

    public static void main(String arg[]) {
        DES des = new DES("12345678");
        String rfc = des.Cifrador("QUOG891212MDF");
        System.out.println("RFC: QUOG891212MDF");
        System.out.println(rfc);
        String aux = des.Descifrado(rfc);
        System.out.println("Cadena descifrada: " + aux);
    }
}

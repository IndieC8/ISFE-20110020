/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Negocios.Cifrado.Cifrado;
import dao.Sql;
import java.io.*;
import java.security.NoSuchProviderException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Natalia Hernández
 */
public class MAIN3 {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchProviderException {
        try {
            //File fiel=new File("FIEL-ISFE.key");
            //Sql s=new Sql();
            //s.insertarFiel("insert into Fiel values(?,?)", fiel);
            /**
             * File fiel=null; ResultSet rs=s.consulta("select archivoFiel from
             * Fiel where idFiel=3;"); java.sql.Blob blobFiel=null;
             * while(rs.next()){ blobFiel=rs.getBlob("archivoFiel");
             * System.out.println("puntito"+blobFiel); //fiel=(File)blobFiel;
            }
             */
            //ObjectInputStream ois=new ObjectInputStream(blobFiel.getBinaryStream());
            //fiel=(File)ois.readObject();
            //System.out.println(fiel);
            //
            //BufferedReader br=new BufferedReader(new FileReader(fiel));
            //String l=br.readLine();
            //while(l!=null){
            //   System.out.println(l);
            //    l=br.readLine();
            //}
            /**
             * InputStream is=new FileInputStream(fiel); byte[] bFiel=new
             * byte[(int)fiel.length()]; int offset=0; int numRead=0;
             * while(offset<bFiel.length && (numRead=is.read(bFiel, offset,
             * bFiel.length-offset))>=0){ offset+=numRead; }
             * System.out.println(blobFiel.length()); String
             * c=Cifrado.firmar(Cifrado.getLlavePrivada(bFiel, "a0123456789"),
             * "hola mundo".getBytes());
            System.out.println(c);
             */
            //Blob fiel = null;
            Sql s = new Sql();
            File fiel = new File("fiel.key");
            ResultSet rs = s.consulta("select archivoFiel from fiel where idFiel=1;");
            while (rs.next())
            {

                FileOutputStream fos = new FileOutputStream(fiel);

                byte[] buffer = new byte[1];
                InputStream is = rs.getBinaryStream(1);
                while (is.read(buffer) > 0) {
                    fos.write(buffer);
                }
                fos.close();
            }
            InputStream is=new FileInputStream(fiel); byte[] bFiel=new byte[(int)fiel.length()]; int offset=0; int numRead=0;
            while(offset<bFiel.length && (numRead=is.read(bFiel, offset,
            bFiel.length-offset))>=0){ offset+=numRead; }

            String c=Cifrado.firmar(Cifrado.getLlavePrivada(bFiel, "a0123456789"),"hola mundo".getBytes());
            System.out.println(c);


        } catch (SecurityException ex) {
            Logger.getLogger(MAIN3.class.getName()).log(Level.SEVERE, null, ex);
        }// catch (NoSuchProviderException ex) {
        //Logger.getLogger(MAIN3.class.getName()).log(Level.SEVERE, null, ex);
        //} //catch (ClassNotFoundException ex) {
        //Logger.getLogger(MAIN3.class.getName()).log(Level.SEVERE, null, ex);
        //}
        catch (SQLException ex) {
            Logger.getLogger(MAIN3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MAIN3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MAIN3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

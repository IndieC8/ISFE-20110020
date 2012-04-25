package Negocios.GenerarFactura;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
/**
 * Clase que genera la barra dimensional cuando se genera el PDF de la factura
 * eletrónica
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación Electrónica acorde a la reforma de enero de 2011
 */
public class BarraBidimensional {
    private static final String FORMATO_IMAGEN="gif";//Formato de la imagen
    private static final String RUTA_IMAGEN="QR.gif";//Ruta
    private static final int ancho = 500; //Ancho de la imagen
    private static final int alto = 500;//Alto de la imagen
    public BarraBidimensional(){
        
    }
    /**
     * Genera la barra dimensional (QR) de la factura cuando se genera el PDF
     * @param cadena Es la cadena original generada de la factura
     */
    public static void generarBarraDimensional(String cadena){
        BitMatrix bm;
        Writer writer = new QRCodeWriter();
        BufferedImage imagen=null;
        try {

            bm = writer.encode(cadena, BarcodeFormat.QR_CODE, ancho, alto);
            // Crear un buffer para escribir la imagen
            imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

            // Iterar sobre la matriz para dibujar los pixeles
            for (int y = 0; y < ancho; y++) {
                for (int x = 0; x < alto; x++) {
                    int grayValue = (bm.get(x, y) ? 1 : 0) & 0xff;
                    imagen.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF));
                }
            }

            //Escribir la imagen al archivo
            imagen = invertirColores(imagen);
            FileOutputStream qrCode = new FileOutputStream(RUTA_IMAGEN);
            ImageIO.write(imagen, FORMATO_IMAGEN, qrCode);

            //Se cierra el flujo de datos
            qrCode.close();

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
    /**
     * Método que se encarga de generar una imagen con los colores invertidos
     * @param imagen Imagen con los colores invertidos por defecto
     * @return Imagen QR definitiva 
     */
    private static BufferedImage invertirColores(BufferedImage imagen){
        for (int x = 0; x < ancho; x++) {
            for (int y = 0; y < alto; y++) {
                int rgb = imagen.getRGB(x, y);
                if (rgb == -16777216) {
                    imagen.setRGB(x, y, -1);
                } else {
                    imagen.setRGB(x, y, -16777216);
                }
            }
        }
        return imagen;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package subsistemaAutomatico;

import dao.Sql;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import javax.mail.MessagingException;

/**
 *
 * @author kawatoto
 */
public class ReportesMensuales
{
    /**
     * Estos atributos son para conformar el nombre del reporte mensual
     */
    private char numeroEsquema;
    private String RFCEmisor;
    private String mesReportado;
    private String anioReportado;

    /**
     * Estos atributos son de los datos contenidos en el reporte mensual
     */
    private String RFCCliente[];
    private String serie[];
    private String anioAprobacion[];
    private String numeroAprobacion[];

    private String fechaYHoraEmision[];
    private String montoOperacion[];
    private Double montoIVA[];
    private String estadoComprobante[];//cancelado o vigente
    private String elementoReporte[];

    public boolean generarReportes(String RFCEmisor, String MesReportado, String anioReportado)
            throws InstantiationException, SQLException, IllegalAccessException, IOException, MessagingException
    {
        this.numeroEsquema=1;
        this.RFCEmisor=RFCEmisor;
        this.mesReportado=MesReportado;
        this.anioReportado=anioReportado;

        String nombreArchivo=this.numeroEsquema+this.RFCEmisor+this.mesReportado+this.anioReportado+".txt";

        Sql sql = new Sql();
        Calendar fechaActual = Calendar.getInstance();
        String consulta="SELECT usuario.idUsuario, rfc, serie, yearAprobacion, "
                + "noAprobacion, fechaElaboracion,importeTotal,estadoComprobante FROM usuario "
                + "JOIN factura WHERE fechaElaboracion<'"+fechaActual.get(Calendar.YEAR)+"-"+(fechaActual.get(Calendar.MONTH)+2)+"-01' "
                + "and fechaElaboracion>'"+fechaActual.get(Calendar.YEAR)+"-"+fechaActual.get(Calendar.MONTH)+"-01'";

        System.out.println(consulta);
        ResultSet rs = sql.consulta(consulta);

        rs.last();
        int i=rs.getRow();
        RFCCliente=new String[i];
        System.out.println(RFCCliente.length);
        serie=new String[i];
        anioAprobacion=new String[i];
        numeroAprobacion=new String[i];
        fechaYHoraEmision=new String[i];
        montoOperacion=new String[i];
        montoIVA=new Double[i];
        estadoComprobante=new String[i];
        elementoReporte=new String[i];
        rs.first();

        while(rs.next())
        {
            for(int j=0;j<i;j++)
            {
                RFCCliente[j]=rs.getString("rfc");
                System.out.println(RFCCliente[j]);
                serie[j]=rs.getString("serie");
                anioAprobacion[j]=rs.getString("yearAprobacion");
                numeroAprobacion[j]=rs.getString("noAprobacion");
                fechaYHoraEmision[j]=rs.getString("fechaElaboracion");
                montoOperacion[j]=rs.getString("importeTotal");
                montoIVA[j]=rs.getDouble("importeTotal")*(0.16);
                estadoComprobante[j]=rs.getString("estadoComprobante");
                elementoReporte[j]="||"+RFCCliente[j]+"|"+serie[j]+""
                        + "|"+anioAprobacion[j]+"|"+numeroAprobacion[j]+""
                        + "|"+fechaYHoraEmision[j]+"|"+montoOperacion[j]+""
                        + "|"+montoIVA[j]+"|"+estadoComprobante[j]+"||";
            }
        }

        File reporteMensual = new File(nombreArchivo);
        BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo));
        for(int j=0;j<i;j++)
        {
            System.out.println(elementoReporte[j]);
            bw.write(elementoReporte[j]+"\n");
        }
        bw.close();
        EnvioMail mail = new EnvioMail("ra_silence@hotmail.com","Reporte Mensual","Archivo del Reporte Mensual del SAT", reporteMensual ,nombreArchivo);
        reporteMensual.delete();
        return true;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author André
 */
public class GestorHistorialCompras {
    private static final int MAX = 200;
    private static String[] codigosCompra = new String[MAX];
    private static String[] fechas = new String[MAX];
    private static double[] totales = new double[MAX];
    private static int total = 0;

    public static void registrarCompra(double totalCompra) {
        String codigo = "C" + (total + 1);
        String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        codigosCompra[total] = codigo;
        fechas[total] = fecha;
        totales[total] = totalCompra;
        total++;
    }

    public static int getTotal() { return total; }
    public static String[] getCodigos() { return codigosCompra; }
    public static String[] getFechas() { return fechas; }
    public static double[] getTotales() { return totales; }
}

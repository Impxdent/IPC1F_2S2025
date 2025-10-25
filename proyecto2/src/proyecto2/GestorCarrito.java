/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2;

/**
 *
 * @author André
 */
public class GestorCarrito {
    private static final int MAX = 100;
    private static String[] codigos = new String[MAX];
    private static String[] nombres = new String[MAX];
    private static double[] precios = new double[MAX];
    private static int[] cantidades = new int[MAX];
    private static int total = 0;

    public static boolean agregar(String codigo, String nombre, double precio, int cantidad) {
        // Ver si ya está en el carrito
        for (int i = 0; i < total; i++) {
            if (codigos[i].equals(codigo)) {
                cantidades[i] += cantidad;
                return true;
            }
        }
        if (total >= MAX) return false;
        codigos[total] = codigo;
        nombres[total] = nombre;
        precios[total] = precio;
        cantidades[total] = cantidad;
        total++;
        return true;
    }

    public static boolean eliminar(String codigo) {
        int i = buscarIndice(codigo);
        if (i == -1) return false;
        for (int j = i; j < total - 1; j++) {
            codigos[j] = codigos[j+1];
            nombres[j] = nombres[j+1];
            precios[j] = precios[j+1];
            cantidades[j] = cantidades[j+1];
        }
        total--;
        return true;
    }

    public static boolean actualizarCantidad(String codigo, int nuevaCant) {
        int i = buscarIndice(codigo);
        if (i == -1) return false;
        cantidades[i] = nuevaCant;
        return true;
    }

    public static int buscarIndice(String codigo) {
        for (int i = 0; i < total; i++)
            if (codigos[i].equals(codigo)) return i;
        return -1;
    }

    public static double calcularTotal() {
        double totalC = 0;
        for (int i = 0; i < total; i++)
            totalC += precios[i] * cantidades[i];
        return totalC;
    }

    public static int getTotal() { return total; }
    public static String[] getCodigos() { return codigos; }
    public static String[] getNombres() { return nombres; }
    public static double[] getPrecios() { return precios; }
    public static int[] getCantidades() { return cantidades; }

    public static void vaciar() { total = 0; }
}

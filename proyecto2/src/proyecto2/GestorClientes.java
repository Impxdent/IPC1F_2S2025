/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2;

/**
 *
 * @author André
 */
public class GestorClientes {
    private static final int MAX = 300;
    private static String[] codigos = new String[MAX];
    private static String[] nombres = new String[MAX];
    private static String[] generos = new String[MAX]; // "M" o "F"
    private static String[] cumple = new String[MAX];
    private static String[] contrasenas = new String[MAX];
    private static int total = 0;

    // ---------- CRUD ----------
    public static boolean agregar(String codigo, String nombre, String genero, String fecha, String contrasena) {
        if (total >= MAX) return false;
        if (buscarIndicePorCodigo(codigo) != -1) return false;
        codigos[total] = codigo;
        nombres[total] = nombre;
        generos[total] = genero;
        cumple[total] = fecha;
        contrasenas[total] = contrasena;
        total++;
        return true;
    }

    public static boolean modificar(String codigo, String nombre, String genero, String fecha, String contrasena) {
        int i = buscarIndicePorCodigo(codigo);
        if (i == -1) return false;
        nombres[i] = nombre;
        generos[i] = genero;
        cumple[i] = fecha;
        contrasenas[i] = contrasena;
        return true;
    }

    public static boolean eliminar(String codigo) {
        int i = buscarIndicePorCodigo(codigo);
        if (i == -1) return false;
        for (int j = i; j < total - 1; j++) {
            codigos[j] = codigos[j + 1];
            nombres[j] = nombres[j + 1];
            generos[j] = generos[j + 1];
            cumple[j] = cumple[j + 1];
            contrasenas[j] = contrasenas[j + 1];
        }
        total--;
        return true;
    }

    public static int buscarIndicePorCodigo(String codigo) {
        for (int i = 0; i < total; i++) if (codigos[i].equals(codigo)) return i;
        return -1;
    }

    // ---------- GETTERS ----------
    public static int getTotal() { return total; }
    public static String[] getCodigos() { return codigos; }
    public static String[] getNombres() { return nombres; }
    public static String[] getGeneros() { return generos; }
    public static String[] getCumples() { return cumple; }
    public static String[] getContrasenas() { return contrasenas; }
}

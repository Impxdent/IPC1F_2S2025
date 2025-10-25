/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2;

/**
 *
 * @author André
 */
public class GestorVendedores {
    private static final int MAX = 200;
    private static String[] codigos = new String[MAX];
    private static String[] nombres = new String[MAX];
    private static String[] generos = new String[MAX];
    private static String[] contrasenas = new String[MAX];
    private static int[] ventasConfirmadas = new int[MAX];
    private static int total = 0;

    public static boolean agregar(String codigo, String nombre, String genero, String contrasena) {
        if (total >= MAX) return false;
        if (buscarIndicePorCodigo(codigo) != -1) return false; //validacion de id
        codigos[total] = codigo;
        nombres[total] = nombre;
        generos[total] = genero;
        contrasenas[total] = contrasena;
        ventasConfirmadas[total] = 0;
        total++;
        return true;
    }

    public static boolean modificar(String codigo, String nuevoNombre, String nuevaContrasena) {
        int i = buscarIndicePorCodigo(codigo);
        if (i == -1) return false;
        nombres[i] = nuevoNombre;
        contrasenas[i] = nuevaContrasena;
        return true;
    }

    public static boolean eliminar(String codigo) {
        int i = buscarIndicePorCodigo(codigo);
        if (i == -1) return false;
        for (int j=i; j<total-1; j++) {
            codigos[j]=codigos[j+1];
            nombres[j]=nombres[j+1];
            generos[j]=generos[j+1];
            contrasenas[j]=contrasenas[j+1];
            ventasConfirmadas[j]=ventasConfirmadas[j+1];
        }
        total--;
        return true;
    }

    public static int buscarIndicePorCodigo(String codigo) {
        for (int i=0; i<total; i++) if (codigos[i].equals(codigo)) return i;
        return -1;
    }

    //getters y setters
    public static int getTotal() { 
        return total; 
    }
    
    public static String[] getCodigos() { 
        return codigos; 
    }
    
    public static String[] getNombres() { 
        return nombres; 
    }
    
    public static String[] getGeneros() {
        return generos; 
    }
    
    public static int[] getVentasConfirmadas() { 
        return ventasConfirmadas; 
    }
    
    public static String[] getContrasenas() { 
        return contrasenas; 
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2;

/**
 *
 * @author André
 */
public class GestorProductos {
    
    //variables de almacenamiento
    private static final int MAX = 300;
    private static String[] codigos = new String[MAX];
    private static String[] nombres = new String[MAX];
    private static String[] categorias = new String[MAX];
    private static String[] atributoUnico = new String[MAX];
    private static double[] precios = new double[MAX];
    private static int total = 0;

    public static boolean agregar(String codigo, String nombre, String categoria, String atributo, double precio) {
        if (total >= MAX) return false;
        if (buscarIndicePorCodigo(codigo) != -1) return false;

        codigos[total] = codigo;
        nombres[total] = nombre;
        categorias[total] = categoria;
        atributoUnico[total] = atributo;
        precios[total] = precio;
        total++;
        return true;
    }

    public static boolean modificar(String codigo, String nombre, String categoria, String atributo, double precio) {
        int i = buscarIndicePorCodigo(codigo);
        if (i == -1) return false;

        nombres[i] = nombre;
        categorias[i] = categoria;
        atributoUnico[i] = atributo;
        precios[i] = precio;
        return true;
    }

    public static boolean eliminar(String codigo) {
        int i = buscarIndicePorCodigo(codigo);
        if (i == -1) return false;

        for (int j = i; j < total - 1; j++) {
            codigos[j] = codigos[j+1];
            nombres[j] = nombres[j+1];
            categorias[j] = categorias[j+1];
            atributoUnico[j] = atributoUnico[j+1];
            precios[j] = precios[j+1];
        }
        total--;
        return true;
    }

    public static int buscarIndicePorCodigo(String codigo) {
        for (int i = 0; i < total; i++)
            if (codigos[i].equalsIgnoreCase(codigo)) return i;
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
    
    public static String[] getCategorias() { 
        return categorias; 
    }
    
    public static String[] getAtributoUnico() { 
        return atributoUnico; 
    }
    
    public static double[] getPrecios() { 
        return precios; 
    }
}

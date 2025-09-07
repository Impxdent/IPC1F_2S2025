package proyecto1;
import java.util.Scanner;

public class Proyecto1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opc = 0;

        while (opc != 8) {
            System.out.println("Bienvenido al sistema de inventario de tienda de ropa");
            System.out.println("1. Agregar producto");
            System.out.println("2. Buscar producto");
            System.out.println("3. Eliminar producto");
            System.out.println("4. Registrar venta");
            System.out.println("5. Generar reportes");
            System.out.println("6. Ver datos de estudiante");
            System.out.println("7. Bitacora");
            System.out.println("8. Salir");
            System.out.println("Ingrese el numero correspondiente a su opcion:");
            opc = sc.nextInt();
        }
    }
}






package practica2;

import java.util.Scanner;

public class Practica2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int opc = 0;
        
        while(opc != 9){
            System.out.println("\nBienvenido a ArenaUSAC");
            System.out.println("1. Agregar personajes");
            System.out.println("2. Modificar personaje");
            System.out.println("3. Eliminar personaje");
            System.out.println("4. Ver personajes registrados");
            System.out.println("5. Simulación de batallas");
            System.out.println("6. Ver historial de batallas");
            System.out.println("7. Buscar personaje por nombre");
            System.out.println("8. Guardar y cargar estado del sistema");
            System.out.println("9. Ver datos del estudiante");
            System.out.print("Seleccione su opcion: ");
            opc = sc.nextInt();
        }
    }
    
}

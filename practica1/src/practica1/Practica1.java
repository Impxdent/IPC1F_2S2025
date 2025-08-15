package practica1;

import java.util.Date;
import java.util.Scanner;

public class Practica1 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String[] nombres = new String[100];
        String[] armas = new String[100];
        String[][] habilidades = new String[100][5];
        int[] niveles = new int[100];
        int totalPersonajes = 0;
        String[] peleas = new String[100];
        int totalPeleas = 0;
        String estudianteNombre = "Eldan Andre Escobar Asturias";
        String estudianteCarnet = "202303088";
        String estudianteCurso = "Introducción a la Programación y Computación 1 sección F";

        int opc = 0;

        while (opc != 9) {
            System.out.println("1. Agregar personaje");
            System.out.println("2. Modificar personaje");
            System.out.println("3. Eliminar personaje");
            System.out.println("4. Ver datos de un personaje");
            System.out.println("5. Ver listado de personajes");
            System.out.println("6. Realizar pelea entre personajes");
            System.out.println("7. Ver historial de peleas");
            System.out.println("8. Ver datos de estudiante");
            System.out.println("9. Salir");

            opc = sc.nextInt();

            if (opc == 1) {
                sc.nextLine();
                System.out.print("Nombre del personaje: ");
                String nombre = sc.nextLine();

                boolean existe = false;
                for (int i = 0; i < totalPersonajes; i++) {
                    if (nombres[i] != null && nombres[i].equals(nombre)) {
                        existe = true;
                    }
                }

                if (existe) {
                    System.out.println("Ese nombre ya existe.");
                } else {
                    nombres[totalPersonajes] = nombre;
                    System.out.print("Arma: ");
                    armas[totalPersonajes] = sc.nextLine();

                    System.out.println("Ingrese hasta 5 habilidades:");
                    for (int j = 0; j < 5; j++) {
                        System.out.print("Habilidad " + (j + 1) + ": ");
                        habilidades[totalPersonajes][j] = sc.nextLine();
                    }

                    System.out.print("Nivel de poder (1-100): ");
                    int nivel = sc.nextInt();

                    if (nivel >= 1 && nivel <= 100) {
                        niveles[totalPersonajes] = nivel;
                        totalPersonajes++;
                        System.out.println("Personaje agregado.");
                    } else {
                        System.out.println("Nivel inválido.");
                    }
                }
            }

            if (opc == 2) {
                sc.nextLine(); 
                System.out.print("Ingrese nombre del personaje a modificar: ");
                String nombre = sc.nextLine();
                int index = -1;
                for (int i = 0; i < totalPersonajes; i++) {
                    if (nombres[i] != null && nombres[i].equals(nombre)) {
                        index = i;
                    }
                }
                if (index == -1) {
                    System.out.println("Personaje no encontrado.");
                } else {
                    System.out.print("Nueva arma: ");
                    armas[index] = sc.nextLine();
                    System.out.println("Nuevas habilidades:");
                    for (int j = 0; j < 5; j++) {
                        System.out.print("Habilidad " + (j + 1) + ": ");
                        habilidades[index][j] = sc.nextLine();
                    }
                    System.out.print("Nuevo nivel de poder (1-100): ");
                    int nivel = sc.nextInt();
                    if (nivel >= 1 && nivel <= 100) {
                        niveles[index] = nivel;
                        System.out.println("Personaje modificado.");
                    } else {
                        System.out.println("Nivel inválido.");
                    }
                }
            }

            if (opc == 3) {
                sc.nextLine(); 
                System.out.print("Ingrese nombre del personaje a eliminar: ");
                String nombre = sc.nextLine();
                int index = -1;
                for (int i = 0; i < totalPersonajes; i++) {
                    if (nombres[i] != null && nombres[i].equals(nombre)) {
                        index = i;
                    }
                }
                if (index == -1) {
                    System.out.println("Personaje no encontrado.");
                } else {
                    for (int i = index; i < totalPersonajes - 1; i++) {
                        nombres[i] = nombres[i + 1];
                        armas[i] = armas[i + 1];
                        niveles[i] = niveles[i + 1];
                        habilidades[i] = habilidades[i + 1];
                    }
                    totalPersonajes--;
                    System.out.println("Personaje eliminado.");
                }
            }

            if (opc == 4) {
                sc.nextLine(); 
                System.out.print("Ingrese nombre del personaje: ");
                String nombre = sc.nextLine();
                int index = -1;
                for (int i = 0; i < totalPersonajes; i++) {
                    if (nombres[i] != null && nombres[i].equals(nombre)) {
                        index = i;
                    }
                }
                if (index == -1) {
                    System.out.println("Personaje no encontrado.");
                } else {
                    System.out.println("ID: " + index);
                    System.out.println("Nombre: " + nombres[index]);
                    System.out.println("Arma: " + armas[index]);
                    System.out.println("Nivel de poder: " + niveles[index]);
                    System.out.println("Habilidades:");
                    for (int j = 0; j < 5; j++) {
                        System.out.println("- " + habilidades[index][j]);
                    }
                }
            }

            if (opc == 5) {
                if (totalPersonajes == 0) {
                    System.out.println("No hay personajes registrados.");
                } else {
                    for (int i = 0; i < totalPersonajes; i++) {
                        System.out.println("ID: " + i + " | Nombre: " + nombres[i] + " | Nivel: " + niveles[i]);
                    }
                }
            }

            if (opc == 6) {
                System.out.print("ID del primer personaje: ");
                int id1 = sc.nextInt();
                System.out.print("ID del segundo personaje: ");
                int id2 = sc.nextInt();
                if (id1 >= 0 && id1 < totalPersonajes && id2 >= 0 && id2 < totalPersonajes) {
                    Date fecha = new Date();
                    peleas[totalPeleas] = nombres[id1] + " vs " + nombres[id2] + " - " + fecha.toString();
                    totalPeleas++;
                    System.out.println("Pelea registrada.");
                } else {
                    System.out.println("ID inválido.");
                }
            }

            if (opc == 7) {
                if (totalPeleas == 0) {
                    System.out.println("No hay peleas registradas.");
                } else {
                    for (int i = 0; i < totalPeleas; i++) {
                        System.out.println(peleas[i]);
                    }
                }
            }

            if (opc == 8) {
                System.out.println("Nombre del estudiante: " + estudianteNombre);
                System.out.println("Carnet del estudiante: " + estudianteCarnet);
                System.out.println("Curso del estudiante: " + estudianteCurso);
            }
        }

        sc.close();
    }
}

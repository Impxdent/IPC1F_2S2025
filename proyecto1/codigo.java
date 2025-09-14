package proyecto1;

import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Proyecto1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //variables con vectores
        int[] ids = new int[100];
        String[] nombres = new String[100];
        int[] cantidades = new int[100];
        double[] precios = new double[100];
        int totalProductos = 0;

        String[] bitacora = new String[200];
        int totalAcciones = 0;

        String[] ventas = new String[200];
        int totalVentas = 0;
        double totalAcumuladoVentas = 0.0; // acumulador de todas las ventas

        int opc = 0;

        while (opc != 8) { //menu de opciones
            System.out.println("\nBienvenido al sistema de inventario de tienda de ropa");
            System.out.println("1. Agregar producto");
            System.out.println("2. Buscar producto");
            System.out.println("3. Eliminar producto");
            System.out.println("4. Registrar venta");
            System.out.println("5. Generar reportes");
            System.out.println("6. Ver datos de estudiante");
            System.out.println("7. Bitacora");
            System.out.println("8. Salir");
            System.out.print("Seleccione su opcion: ");
            opc = sc.nextInt();
            sc.nextLine();

            if (opc == 1) { //agregar producto
                System.out.print("Ingrese ID del producto: ");
                int id = sc.nextInt();
                sc.nextLine();

                boolean repetido = false; //verificador de id
                for (int i = 0; i < totalProductos; i++) {
                    if (ids[i] == id) {
                        repetido = true;
                        break;
                    }
                }

                if (repetido) {
                    System.out.println("Error, ya existe un producto con ese ID");
                } else {
                    System.out.print("Ingrese producto: ");
                    String producto = sc.nextLine();
                    System.out.print("Ingrese cantidad: ");
                    int cantidad = sc.nextInt();
                    System.out.print("Ingrese precio: ");
                    double precio = sc.nextDouble();

                    if (precio < 0) {
                        System.out.println("Error, el precio tiene que ser positivo");
                    } else { //almacenar datos para reporte y bitacora
                        ids[totalProductos] = id;
                        nombres[totalProductos] = producto;
                        cantidades[totalProductos] = cantidad;
                        precios[totalProductos] = precio;
                        totalProductos++;

                        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                        bitacora[totalAcciones++] = fecha + " Se agrego el producto: " + producto;

                        System.out.println("Producto agregado exitosamente: " + producto);
                    }
                }

            } else if (opc == 2) { //buscar producto
                System.out.print("Ingrese ID del producto a buscar: ");
                int id = sc.nextInt();
                boolean encontrado = false;
                for (int i = 0; i < totalProductos; i++) {
                    if (ids[i] == id) { //buscar por medio de id
                        System.out.println("Producto encontrado: " + nombres[i]);
                        System.out.println("ID: " + ids[i] + " | Nombre: " + nombres[i] + " | Cantidad: " + cantidades[i] + " | Precio: Q" + precios[i]);
                        encontrado = true;
                    }
                }
                if (!encontrado) {
                    System.out.println("Error, no se encontro el producto");
                }

            } else if (opc == 3) { //eliminar producto
                System.out.print("Ingrese ID del producto a eliminar: ");
                int id = sc.nextInt();
                boolean eliminado = false;
                for (int i = 0; i < totalProductos; i++) {
                    if (ids[i] == id) { //buscar producto para eliminar por medio de id
                        System.out.println("Producto encontrado:");
                        System.out.println("ID: " + ids[i] + " | Nombre: " + nombres[i] + " | Cantidad: " + cantidades[i] + " | Precio: Q" + precios[i]);
                        System.out.print("Confirmar eliminacion (s/n): "); //confirmacion de eliminacion
                        sc.nextLine();
                        String confirmacion = sc.nextLine();

                        if (confirmacion.equalsIgnoreCase("s")) { //si el usuario marca la tecla, se eliminará
                            String nombre = nombres[i];
                            for (int j = i; j < totalProductos - 1; j++) {
                                ids[j] = ids[j + 1];
                                nombres[j] = nombres[j + 1];
                                cantidades[j] = cantidades[j + 1];
                                precios[j] = precios[j + 1];
                            }
                            totalProductos--;
                            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                            bitacora[totalAcciones++] = fecha + " Se elimino producto: " + nombre;
                            System.out.println("El producto se elimino correctamente");
                        } else {
                            System.out.println("El producto no se elimino");
                        }
                        eliminado = true;
                        break;
                    }
                }
                if (!eliminado) {
                    System.out.println("Error, producto no encontrado");
                }

            } else if (opc == 4) { //hacer venta
                System.out.print("Ingrese ID del producto a vender: ");
                int id = sc.nextInt();
                int indice = -1; //contar existencia de producto
                for (int i = 0; i < totalProductos; i++) {
                    if (ids[i] == id) {
                        indice = i;
                        break;
                    }
                }

                if (indice == -1) {
                    System.out.println("Error, ese producto no existe");
                } else {
                    System.out.println("Producto encontrado:");
                    System.out.println("ID: " + ids[indice] + " | Nombre: " + nombres[indice] + " | Cantidad disponible: " + cantidades[indice] + " | Precio: Q" + precios[indice]);

                    System.out.print("Ingrese cantidad a vender: ");
                    int cantVender = sc.nextInt();

                    if (cantVender <= 0) { //validacion de existencia de producto
                        System.out.println("Error, la cantidad a vender debe ser mayor que cero");
                    } else if (cantVender > cantidades[indice]) {
                        System.out.println("Error, no hay suficiente stock disponible, el stock actual es: " + cantidades[indice]);
                    } else {
                        System.out.print("Confirmar la venta (s/n): "); //confirmacion de venta
                        sc.nextLine();
                        String confirmacion = sc.nextLine();

                        if (confirmacion.equalsIgnoreCase("s")) {
                            cantidades[indice] -= cantVender;
                            double totalGastado = cantVender * precios[indice];
                            totalAcumuladoVentas += totalGastado; // acumular datos en los registros
                            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

                            bitacora[totalAcciones++] = fecha + "Se vendio " + cantVender + " de " + nombres[indice];
                            ventas[totalVentas++] = fecha + " | " + nombres[indice] + " | Cantidad: " + cantVender + " | Total: Q" + totalGastado;

                            System.out.println("Venta registrada: " + nombres[indice] + " | Total: Q" + totalGastado);
                            System.out.println("Stock restante: " + cantidades[indice]);
                        } else {
                            System.out.println("La venta no se concreto");
                        }
                    }
                }

            } else if (opc == 5) { //generacion de reportes
                try {
                    String desktopPath = System.getProperty("user.home") + "/Desktop/"; //ruta de almacenamiento de reportes
                    //nombre de archivos
                    String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss"));
                    String fileStock = desktopPath + fechaHora + "_Stock.pdf";
                    Document docStock = new Document();
                    PdfWriter.getInstance(docStock, new java.io.FileOutputStream(fileStock));
                    docStock.open();
                    docStock.add(new Paragraph("Reporte de Stock"));
                    docStock.add(new Paragraph("Fecha: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
                    docStock.add(new Paragraph("\n"));

                    PdfPTable tableStock = new PdfPTable(4); //datos incluidos en el pdf
                    tableStock.addCell("Codigo");
                    tableStock.addCell("Nombre");
                    tableStock.addCell("Precio");
                    tableStock.addCell("Cantidad");

                    for (int i = 0; i < totalProductos; i++) {
                        tableStock.addCell(String.valueOf(ids[i]));
                        tableStock.addCell(nombres[i]);
                        tableStock.addCell("Q" + String.format("%.2f", precios[i]));
                        tableStock.addCell(String.valueOf(cantidades[i])); //total de productos
                    }
                    docStock.add(tableStock);
                    docStock.close();

                    //reporte de ventas
                    String fileVenta = desktopPath + fechaHora + "_Venta.pdf";
                    Document docVenta = new Document();
                    PdfWriter.getInstance(docVenta, new java.io.FileOutputStream(fileVenta));
                    docVenta.open();
                    docVenta.add(new Paragraph("Reporte de Ventas"));
                    docVenta.add(new Paragraph("Fecha: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
                    docVenta.add(new Paragraph("\n"));

                    PdfPTable tableVenta = new PdfPTable(4);
                    tableVenta.addCell("Fecha");
                    tableVenta.addCell("Producto");
                    tableVenta.addCell("Cantidad");
                    tableVenta.addCell("Total");

                    for (int i = 0; i < totalVentas; i++) {
                        String[] partes = ventas[i].split("\\|");
                        tableVenta.addCell(partes[0].trim());
                        tableVenta.addCell(partes[1].trim());
                        tableVenta.addCell(partes[2].replace("Cantidad:", "").trim());
                        tableVenta.addCell(partes[3].replace("Total:", "").trim());
                    }
                    docVenta.add(tableVenta);
                    docVenta.add(new Paragraph("\nTotal general de ventas: Q" + String.format("%.2f", totalAcumuladoVentas)));
                    docVenta.close();

                    System.out.println("Los reportes se guardaron exitosamente en el escritorio");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (opc == 6) { //mostrar datos del estudiante
                System.out.println("Datos del estudiante");
                System.out.println("Nombre: Eldan Andre Escobar Asturias");
                System.out.println("Carnet: 202303088");
                System.out.println("Curso: Introduccion a la programacion y computacion 1");

            } else if (opc == 7) { //mostrar bitácora mediante datos almacenados en cada opción
                if (totalAcciones == 0) {
                    System.out.println("Error, la bitacora está vacía");
                } else {
                    System.out.println("Bitacora");
                    for (int i = 0; i < totalAcciones; i++) {
                        System.out.println(bitacora[i]);
                    }
                }

            } else if (opc == 8) { //fin del programa
                System.out.println("Gracias por usar el sistema");
            } else {
                System.out.println("Error, ingrese una opcion valida");
            }
        }
    }
}

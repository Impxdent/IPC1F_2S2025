package practica2;

import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;

public class Practica2 extends JFrame {

    //variables para almacenamiento de personaje con vectores e importación de la clase
    private Personaje[] personajes = new Personaje[100];
    private int totalPersonajes = 0;

    //variables con vectores para historial de batallas
    private String[] historialBatallas = new String[100];
    private int totalBatallas = 0;

    //variables para creación de botones en la interfaz grafica
    private JButton btnAgregar, btnModificar, btnEliminar, btnVerTodos, btnBuscar, btnBatalla, btnHistorial, btnGuardarDatos, btnEstudiante, btnSalir;

    //ruta de almacenamiento de la bitacora
    private static final String DESKTOP_PATH = System.getProperty("user.home") + File.separator + "Desktop";
    private static final String BITACORA_FILE = DESKTOP_PATH + File.separator + "bitacora.txt";

    public Practica2() {
        //diseño de ventana principal
        setTitle("Practica2");
        setSize(300, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        //creacion de botones y distancia entre ellos
        int y = 20;
        btnAgregar = crearBoton("Agregar personaje", y); y += 40;
        btnModificar = crearBoton("Modificar personaje", y); y += 40;
        btnEliminar = crearBoton("Eliminar personaje", y); y += 40;
        btnVerTodos = crearBoton("Ver todos los personajes", y); y += 40;
        btnBuscar = crearBoton("Buscar personaje por ID", y); y += 40;
        btnBatalla = crearBoton("Simular batalla", y); y += 40;
        btnHistorial = crearBoton("Historial de batallas", y); y += 40;
        btnGuardarDatos = crearBoton("Guardar/Cargar datos", y); y += 40;
        btnEstudiante = crearBoton("Datos del estudiante", y); y += 40;
        btnSalir = crearBoton("Salir", y);

        registrarBitacora("Sistema iniciado");
    }

    //clase para crear botones y registrar interacciones en la bitácora
    private JButton crearBoton(String texto, int y) {
        JButton boton = new JButton(texto);
        boton.setBounds(50, y, 180, 30);

        boton.addActionListener(e -> registrarBitacora("Se presiono el boton: " + texto));

        if (texto.equals("Agregar personaje")) {
            boton.addActionListener(e -> {
                new AgregarPersonaje(Practica2.this).setVisible(true); //abrir clase para agregar personaje
                registrarBitacora("Se abrio la ventana para agregar personaje");
                dispose();
            });
        } else if (texto.equals("Modificar personaje")) {
            boton.addActionListener(e -> {
                new ModificarPersonaje(Practica2.this).setVisible(true); //abrir ventana para agregar personaje
                registrarBitacora("Se abrio la ventana para modificar personaje");
                dispose();
            });
        } else if (texto.equals("Eliminar personaje")) {
            boton.addActionListener(e -> eliminarPersonaje());
        } else if (texto.equals("Ver todos los personajes")) {
            boton.addActionListener(e -> verTodosLosPersonajes());
        } else if (texto.equals("Buscar personaje por ID")) {
            boton.addActionListener(e -> buscarPersonajePorID());
        } else if (texto.equals("Simular batalla")) {
            boton.addActionListener(e -> {
                new SimulacionBatalla(Practica2.this).setVisible(true); //abrir ventana para simular batalla
                registrarBitacora("Se abrio la ventana para simular batalla"); 
            });
        } else if (texto.equals("Historial de batallas")) {
            boton.addActionListener(e -> mostrarHistorialBatallas());
        } else if (texto.equals("Guardar/Cargar datos")) {
            boton.addActionListener(e -> guardarDatos());
        } else if (texto.equals("Datos del estudiante")) {
            boton.addActionListener(e -> datosEstudiante());
        } else if (texto.equals("Salir")) {
            boton.addActionListener(e -> {
                registrarBitacora("Gracias por usar el sistema");
                System.exit(0);
            });
        }

        add(boton);
        return boton;
    }

    private void eliminarPersonaje() {
        if (totalPersonajes == 0) { 
            JOptionPane.showMessageDialog(this, "No hay personajes para eliminar"); //valodacion de existencia de personajes para eliminar
            return;
        }

        try { //buscar personaje para eliminar
            String input = JOptionPane.showInputDialog(this, "Ingrese el ID del personaje a eliminar (1-" + totalPersonajes + "):");
            if (input == null) return;

            int id = Integer.parseInt(input);

            if (id < 1 || id > totalPersonajes) { //validacion de id 
                JOptionPane.showMessageDialog(this, "Ingrese un ID valido");
                return;
            }

            Personaje personaje = personajes[id-1];
            String mensaje = "¿Está seguro que desea eliminar este personaje?\n\n" +
                            "ID: " + id + "\n" +
                            "Nombre: " + personaje.getNombre() + "\n" +
                            "Arma: " + personaje.getArma() + "\n" +
                            "HP: " + personaje.getHp();

            int confirmacion = JOptionPane.showConfirmDialog(this, mensaje, "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            //confirmacion de eliminacion de personaje
            if (confirmacion == JOptionPane.YES_OPTION) {
                registrarBitacora("Personaje eliminado: " + personaje.getNombre() + " (ID: " + id + ")");

                for (int i = id-1; i < totalPersonajes - 1; i++) {
                    personajes[i] = personajes[i + 1];
                }
                personajes[totalPersonajes - 1] = null;
                totalPersonajes--;

                JOptionPane.showMessageDialog(this, "Personaje eliminado exitosamente");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número valido");
        }
    }

    private void verTodosLosPersonajes() {
        if (totalPersonajes == 0) {
            JOptionPane.showMessageDialog(this, "No hay personajes guardados"); //valodacion de existencia de personajes para mostrar
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Lista de personajes:\n\n");

        //imprimir personajes existentes
        for (int i = 0; i < totalPersonajes; i++) {
            Personaje p = personajes[i];
            sb.append("ID: ").append(i + 1).append("\n")
              .append("Nombre: ").append(p.getNombre()).append("\n")
              .append("Arma: ").append(p.getArma()).append("\n")
              .append("HP: ").append(p.getHp()).append("\n")
              .append("Ataque: ").append(p.getAtaque()).append("\n")
              .append("Velocidad: ").append(p.getVelocidad()).append("\n")
              .append("Agilidad: ").append(p.getAgilidad()).append("\n")
              .append("Defensa: ").append(p.getDefensa()).append("\n")
              .append("-------------------------\n");
        }

        JTextArea textArea = new JTextArea(sb.toString(), 20, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(this, scrollPane, "Todos los Personajes", JOptionPane.INFORMATION_MESSAGE);

        registrarBitacora("Vista de todos los personajes");
    }

    private void buscarPersonajePorID() {
        if (totalPersonajes == 0) {
            JOptionPane.showMessageDialog(this, "No hay personajes guardados");//valodacion de existencia de personajes para buscar
            return;
        }

        try { //buscar personaje por id
            String input = JOptionPane.showInputDialog(this,
                "Ingrese el ID del personaje a buscar: ");
            if (input == null) return;

            int id = Integer.parseInt(input);

            if (id < 1 || id > totalPersonajes) { //validacion de datos
                JOptionPane.showMessageDialog(this, "ID invalido");
                return;
            }

            //imprimir datos
            Personaje p = personajes[id-1];
            StringBuilder info = new StringBuilder();
            info.append("Personaje:\n\n")
                .append("ID: ").append(id).append("\n")
                .append("Nombre: ").append(p.getNombre()).append("\n")
                .append("Arma: ").append(p.getArma()).append("\n")
                .append("HP: ").append(p.getHp()).append("\n")
                .append("Ataque: ").append(p.getAtaque()).append("\n")
                .append("Velocidad: ").append(p.getVelocidad()).append("\n")
                .append("Agilidad: ").append(p.getAgilidad()).append("\n")
                .append("Defensa: ").append(p.getDefensa()).append("\n\n");

            //mostrar historial de batallas del personaje
            info.append("Historial de peleas del personaje:\n");
            boolean tieneBatallas = false;
            for (int i = 0; i < totalBatallas; i++) {
                if (historialBatallas[i].contains(p.getNombre())) {
                    info.append(historialBatallas[i]).append("\n");
                    tieneBatallas = true;
                }
            }
            if (!tieneBatallas) {
                info.append("El personaje no tiene batallas registradas aun\n");
            }

            JOptionPane.showMessageDialog(this, info.toString(), "Personaje ID: " + id, JOptionPane.INFORMATION_MESSAGE);

            registrarBitacora("Busqueda de personaje: " + p.getNombre() + " (ID: " + id + ")");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un numero valido"); //validacion de datos
        }
    }

    //registro de batalla para mostrar en la bitacora
    public void registrarHistorialBatalla(String registro) {
        if (totalBatallas < historialBatallas.length) {
            String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            historialBatallas[totalBatallas] = "[" + timestamp + "] " + registro;
            totalBatallas++;
            registrarBitacora("Batalla registrada: " + registro);
        }
    }

    public void registrarBitacora(String accion) {
        String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        String linea = "[" + timestamp + "] " + accion;

        //mostrar en consola
        System.out.println(linea);

        //guardar en archivo bitácora en escritorio
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BITACORA_FILE, true))) {
            writer.write(linea);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al escribir en la bitacora: " + e.getMessage());
        }
    }

    //mostrar historial de batallas en un área de texto
    private void mostrarHistorialBatallas() {
        if (totalBatallas == 0) {
            JOptionPane.showMessageDialog(this, "No hay batallas registradas aun");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Historial de batallas:\n\n");
        for (int i = 0; i < totalBatallas; i++) {
            sb.append(historialBatallas[i]).append("\n\n");
        }

        JTextArea textArea = new JTextArea(sb.toString(), 20, 50);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(this, scrollPane, "Historial de Batallas", JOptionPane.INFORMATION_MESSAGE);

        registrarBitacora("Vista del historial de batallas");
    }

    private void guardarDatos() {
        try {
            //guardar archivo .txt de personajes en el escritorio
            FileWriter fw = new FileWriter(DESKTOP_PATH + File.separator + "personajes.txt");
            for (int i = 0; i < totalPersonajes; i++) {
                Personaje p = personajes[i];
                fw.write("ID: " + (i+1) + ", Nombre: " + p.getNombre() + ", Arma: " + p.getArma() +
                         ", HP: " + p.getHp() + ", Ataque: " + p.getAtaque() +
                         ", Velocidad: " + p.getVelocidad() + ", Agilidad: " + p.getAgilidad() +
                         ", Defensa: " + p.getDefensa() + "\n");
            }
            fw.close();

            //guardar archivo .txt de batallas en el escritorio
            FileWriter fw2 = new FileWriter(DESKTOP_PATH + File.separator + "batallas.txt");
            for (int i = 0; i < totalBatallas; i++) {
                fw2.write(historialBatallas[i] + "\n");
            }
            fw2.close();

            JOptionPane.showMessageDialog(this, "Datos guardados en el escritorio");
            registrarBitacora("Datos guardados en el escritorio");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar los datos: " + e.getMessage());
        }
    }

    //mostrar los datos del estudiante en un mensaje emergente
    private void datosEstudiante(){
        String nombre = "Eldan André Escobar Asturias";
        String carnet = "202303088";
        String curso = "IPC1-F";

        String mensaje= "Nombre: " + nombre + "\nCarnet: " + carnet + "\nCurso: " + curso;

        JOptionPane.showMessageDialog(this, mensaje, "Datos del estudiante", JOptionPane.INFORMATION_MESSAGE);
        registrarBitacora("Vista de los datos del estudiante");
    }

    public Personaje[] getPersonajes() { 
        return personajes; 
    }
    
    public int getTotalPersonajes() { 
        return totalPersonajes; 
    }
    
    public void setTotalPersonajes(int totalPersonajes) { 
        this.totalPersonajes = totalPersonajes; 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Practica2().setVisible(true));
    }
}

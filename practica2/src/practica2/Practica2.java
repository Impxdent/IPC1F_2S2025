package practica2;

import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author André
 */
public class Practica2 extends JFrame {

    private Personaje[] personajes = new Personaje[100];
    private int totalPersonajes = 0;
    
    // Arrays para historial y bitácora
    private String[] historialBatallas = new String[100];
    private int totalBatallas = 0;
    private String[] bitacoraSistema = new String[100];
    private int totalBitacoras = 0;

    private JButton btnAgregar, btnModificar, btnEliminar, btnVerTodos, btnBuscar, btnBatalla, btnHistorial, btnBitacora, btnEstudiante, btnSalir;
    
    public Practica2() {
        setTitle("Practica2");
        setSize(300, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        int y = 20;
        btnAgregar = crearBoton("Agregar personaje", y); y += 40;
        btnModificar = crearBoton("Modificar personaje", y); y += 40;
        btnEliminar = crearBoton("Eliminar personaje", y); y += 40;
        btnVerTodos = crearBoton("Ver todos los personajes", y); y += 40;
        btnBuscar = crearBoton("Buscar personaje por ID", y); y += 40;
        btnBatalla = crearBoton("Simular batalla", y); y += 40;
        btnHistorial = crearBoton("Historial de batallas", y); y += 40;
        btnBitacora = crearBoton("Estado del sistema", y); y += 40;
        btnEstudiante = crearBoton("Datos del estudiante", y); y += 40;
        btnSalir = crearBoton("Salir", y);
        
        // Registrar inicio del sistema
        registrarBitacora("Sistema iniciado");
    }

    private JButton crearBoton(String texto, int y) {
        JButton boton = new JButton(texto);
        boton.setBounds(50, y, 180, 30);
        
        if (texto.equals("Agregar personaje")) {
            boton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new AgregarPersonaje(Practica2.this).setVisible(true);
                    dispose();
                }
            });
        } else if (texto.equals("Modificar personaje")) {
            boton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new ModificarPersonaje(Practica2.this).setVisible(true);
                    dispose();
                }
            });
        } else if (texto.equals("Eliminar personaje")) {
            boton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    eliminarPersonaje();
                }
            });
        } else if (texto.equals("Ver todos los personajes")) {
            boton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    verTodosLosPersonajes();
                }
            });
        } else if (texto.equals("Buscar personaje por ID")) {
            boton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buscarPersonajePorID();
                }
            });
        } else if (texto.equals("Simular batalla")) {
            boton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new SimulacionBatalla(Practica2.this).setVisible(true);
                }
            });
        } else if (texto.equals("Historial de batallas")) {
            boton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mostrarHistorialBatallas();
                }
            });
        } else if (texto.equals("Estado del sistema")) {
            boton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mostrarBitacoraSistema();
                }
            });
        } else if (texto.equals("Datos del estudiante")) {
            boton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    datosEstudiante();
                }
            });
        } else if (texto.equals("Salir")) {
            boton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
        }
        
        add(boton);
        return boton;
    }
    
    private void eliminarPersonaje() {
        if (totalPersonajes == 0) {
            JOptionPane.showMessageDialog(this, "No hay personajes para eliminar.");
            return;
        }

        try {
            String input = JOptionPane.showInputDialog(this, "Ingrese el ID del personaje a eliminar (1-" + totalPersonajes + "):");
            if (input == null) return; // El usuario canceló
            
            int id = Integer.parseInt(input);
            
            if (id < 1 || id > totalPersonajes) {
                JOptionPane.showMessageDialog(this, "ID inválido. Debe ser entre 1 y " + totalPersonajes);
                return;
            }

            // Mostrar información del personaje a eliminar
            Personaje personaje = personajes[id-1];
            String mensaje = "¿Está seguro que desea eliminar este personaje?\n\n" +
                            "ID: " + id + "\n" +
                            "Nombre: " + personaje.getNombre() + "\n" +
                            "Arma: " + personaje.getArma() + "\n" +
                            "HP: " + personaje.getHp();
            
            int confirmacion = JOptionPane.showConfirmDialog(this, mensaje, 
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                // Registrar en bitácora antes de eliminar
                registrarBitacora("Personaje eliminado: " + personaje.getNombre() + " (ID: " + id + ")");
                
                // Eliminar desplazando los elementos
                for (int i = id-1; i < totalPersonajes - 1; i++) {
                    personajes[i] = personajes[i + 1];
                }
                personajes[totalPersonajes - 1] = null;
                totalPersonajes--;
                
                JOptionPane.showMessageDialog(this, "Personaje eliminado exitosamente.");
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido.");
        }
    }
    
    private void verTodosLosPersonajes() {
        if (totalPersonajes == 0) {
            JOptionPane.showMessageDialog(this, "No hay personajes guardados.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== TODOS LOS PERSONAJES ===\n\n");
        
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

        // Crear un JTextArea para mostrar mejor el contenido
        JTextArea textArea = new JTextArea(sb.toString(), 20, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        JOptionPane.showMessageDialog(this, scrollPane, "Todos los Personajes", 
                                    JOptionPane.INFORMATION_MESSAGE);
        
        // Registrar en bitácora
        registrarBitacora("Vista de todos los personajes");
    }
    
    private void buscarPersonajePorID() {
        if (totalPersonajes == 0) {
            JOptionPane.showMessageDialog(this, "No hay personajes guardados.");
            return;
        }

        try {
            String input = JOptionPane.showInputDialog(this, 
                "Ingrese el ID del personaje a buscar (1-" + totalPersonajes + "):");
            if (input == null) return;
            
            int id = Integer.parseInt(input);
            
            if (id < 1 || id > totalPersonajes) {
                JOptionPane.showMessageDialog(this, "ID inválido. Debe ser entre 1 y " + totalPersonajes);
                return;
            }

            Personaje p = personajes[id-1];
            String info = "=== PERSONAJE ENCONTRADO ===\n\n" +
                         "ID: " + id + "\n" +
                         "Nombre: " + p.getNombre() + "\n" +
                         "Arma: " + p.getArma() + "\n" +
                         "HP: " + p.getHp() + "\n" +
                         "Ataque: " + p.getAtaque() + "\n" +
                         "Velocidad: " + p.getVelocidad() + "\n" +
                         "Agilidad: " + p.getAgilidad() + "\n" +
                         "Defensa: " + p.getDefensa();
            
            JOptionPane.showMessageDialog(this, info, "Personaje ID: " + id, 
                                        JOptionPane.INFORMATION_MESSAGE);
            
            // Registrar en bitácora
            registrarBitacora("Búsqueda de personaje: " + p.getNombre() + " (ID: " + id + ")");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido.");
        }
    }
    
    public void registrarHistorialBatalla(String registro) {
        if (totalBatallas < historialBatallas.length) {
            String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            historialBatallas[totalBatallas] = "[" + timestamp + "] " + registro;
            totalBatallas++;
        } else {
            JOptionPane.showMessageDialog(this, "Historial de batallas lleno.");
        }
    }

    public void registrarBitacora(String accion) {
        if (totalBitacoras < bitacoraSistema.length) {
            String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            bitacoraSistema[totalBitacoras] = "[" + timestamp + "] " + accion;
            totalBitacoras++;
        } else {
            JOptionPane.showMessageDialog(this, "Bitácora del sistema llena.");
        }
    }

    private void mostrarHistorialBatallas() {
        if (totalBatallas == 0) {
            JOptionPane.showMessageDialog(this, "No hay batallas registradas.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== HISTORIAL DE BATALLAS ===\n\n");
        for (int i = 0; i < totalBatallas; i++) {
            sb.append(historialBatallas[i]).append("\n\n");
        }

        JTextArea textArea = new JTextArea(sb.toString(), 20, 50);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(this, scrollPane, "Historial de Batallas", 
                                    JOptionPane.INFORMATION_MESSAGE);
        
        registrarBitacora("Vista del historial de batallas");
    }

    private void mostrarBitacoraSistema() {
        if (totalBitacoras == 0) {
            JOptionPane.showMessageDialog(this, "No hay registros en la bitácora.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== BITÁCORA DEL SISTEMA ===\n\n");
        for (int i = 0; i < totalBitacoras; i++) {
            sb.append(bitacoraSistema[i]).append("\n");
        }

        JTextArea textArea = new JTextArea(sb.toString(), 20, 60);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(this, scrollPane, "Bitácora del Sistema", 
                                    JOptionPane.INFORMATION_MESSAGE);
        
        registrarBitacora("Vista de la bitácora del sistema");
    }
    
    private void datosEstudiante(){
        String nombre = "Eldan André Escobar Asturias";
        String carnet = "202303088";
        String curso = "IPC1-F";
        
        String mensaje= "Nombre: " + nombre + 
                        "\nCarnet: " + carnet +
                        "\nCurso: " + curso;
        
        javax.swing.JOptionPane.showMessageDialog(this, mensaje, "Datos del estudiante", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    public Personaje[] getPersonajes() { return personajes; }
    public int getTotalPersonajes() { return totalPersonajes; }
    public void setTotalPersonajes(int totalPersonajes) { this.totalPersonajes = totalPersonajes; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Practica2().setVisible(true);
        });
    }
}
    

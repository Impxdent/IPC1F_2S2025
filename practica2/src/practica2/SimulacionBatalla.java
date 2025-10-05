package practica2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimulacionBatalla extends JFrame {
    private Practica2 principal;
    private JComboBox<String> cmbPersonaje1, cmbPersonaje2;
    private JButton btnIniciarBatalla, btnMenu;
    private JTextArea txtBitacora;
    private boolean batallaEnCurso = false;

    public SimulacionBatalla(Practica2 principal) {
        this.principal = principal;
        setTitle("Simulacion de batalla");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        //combobox para selección de personajes
        JLabel lblPersonaje1 = new JLabel("Personaje 1:");
        lblPersonaje1.setBounds(20, 20, 100, 25);
        add(lblPersonaje1);

        cmbPersonaje1 = new JComboBox<>();
        cmbPersonaje1.setBounds(120, 20, 150, 25);
        add(cmbPersonaje1);

        JLabel lblPersonaje2 = new JLabel("Personaje 2:");
        lblPersonaje2.setBounds(20, 50, 100, 25);
        add(lblPersonaje2);

        cmbPersonaje2 = new JComboBox<>();
        cmbPersonaje2.setBounds(120, 50, 150, 25);
        add(cmbPersonaje2);

        // Botones
        btnIniciarBatalla = new JButton("Iniciar Batalla");
        btnIniciarBatalla.setBounds(300, 35, 120, 30);
        btnIniciarBatalla.addActionListener(e -> iniciarBatalla());
        add(btnIniciarBatalla);

        btnMenu = new JButton("Menú Principal");
        btnMenu.setBounds(430, 35, 120, 30);
        btnMenu.addActionListener(e -> {
            principal.setVisible(true);
            principal.registrarBitacora("Se regreso al menu principal");
            dispose();
        });
        add(btnMenu);

        //mostrar datos en un área de texto para la bitácora de batalla
        JLabel lblBitacora = new JLabel("Bitacora de Batalla:");
        lblBitacora.setBounds(20, 90, 150, 25);
        add(lblBitacora);

        txtBitacora = new JTextArea();
        txtBitacora.setEditable(false);
        JScrollPane scrollBitacora = new JScrollPane(txtBitacora);
        scrollBitacora.setBounds(20, 120, 550, 300);
        add(scrollBitacora);

        cargarPersonajes();
    }

    private void cargarPersonajes() {
        cmbPersonaje1.removeAllItems();
        cmbPersonaje2.removeAllItems();

        for (int i = 0; i < principal.getTotalPersonajes(); i++) {
            Personaje p = principal.getPersonajes()[i];
            String item = (i + 1) + ". " + p.getNombre() + " (HP: " + p.getHp() + ")";
            cmbPersonaje1.addItem(item);
            cmbPersonaje2.addItem(item);
        }
        principal.registrarBitacora("Lista de personajes cargados en la simulacion");
    }

    private void iniciarBatalla() {
        if (batallaEnCurso) {
            JOptionPane.showMessageDialog(this, "Error, ya hay una batalla en curso");
            principal.registrarBitacora("Intento de iniciar batalla fallido: ya hay una en curso");
            return;
        }

        int index1 = cmbPersonaje1.getSelectedIndex();
        int index2 = cmbPersonaje2.getSelectedIndex();

        if (index1 == -1 || index2 == -1) {
            JOptionPane.showMessageDialog(this, "Error, debe seleccionar dos personajes");
            principal.registrarBitacora("Intento de iniciar batalla fallido: no se seleccionaron ambos personajes");
            return;
        }

        if (index1 == index2) {
            JOptionPane.showMessageDialog(this, "Error, debe seleccionar personajes diferentes");
            principal.registrarBitacora("Intento de iniciar batalla fallido: mismos personajes seleccionados");
            return;
        }

        Personaje p1 = principal.getPersonajes()[index1];
        Personaje p2 = principal.getPersonajes()[index2];

        // Validar que estén vivos
        if (p1.getHp() <= 0 || p2.getHp() <= 0) {
            JOptionPane.showMessageDialog(this, "Error, ambos personajes deben estar vivos para combatir");
            principal.registrarBitacora("Intento de iniciar batalla fallido: uno de los personajes está muerto");
            return;
        }

        batallaEnCurso = true;
        btnIniciarBatalla.setEnabled(false);
        txtBitacora.setText("");

        // Registrar inicio de batalla
        String horaInicio = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        String registro = "Batalla iniciada: " + p1.getNombre() + " vs " + p2.getNombre() + " - " + horaInicio;
        txtBitacora.append(registro + "\n\n");
        principal.registrarHistorialBatalla(registro);
        principal.registrarBitacora("Inicio de batalla: " + p1.getNombre() + " vs " + p2.getNombre());

        // Crear copias de los personajes para la batalla
        Personaje copiaP1 = new Personaje(p1.getNombre(), p1.getArma(), p1.getHp(), p1.getAtaque(),
                                         p1.getVelocidad(), p1.getAgilidad(), p1.getDefensa());
        Personaje copiaP2 = new Personaje(p2.getNombre(), p2.getArma(), p2.getHp(), p2.getAtaque(),
                                         p2.getVelocidad(), p2.getAgilidad(), p2.getDefensa());

        // Crear hilos para la batalla
        Thread hilo1 = new Thread(new Combatiente(copiaP1, copiaP2, "Jugador 1", principal, this));
        Thread hilo2 = new Thread(new Combatiente(copiaP2, copiaP1, "Jugador 2", principal, this));

        hilo1.start();
        hilo2.start();
    }

    public void agregarLogBatalla(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            txtBitacora.append(mensaje + "\n");
            txtBitacora.setCaretPosition(txtBitacora.getDocument().getLength());
        });
    }

    public void batallaTerminada(Personaje ganador, Personaje perdedor) {
        batallaEnCurso = false;
        btnIniciarBatalla.setEnabled(true);

        String resultado = "Batalla finalizada - Ganador: " + ganador.getNombre() +
                          " (HP restante: " + ganador.getHp() + ")";
        agregarLogBatalla("\n" + resultado);

        principal.registrarHistorialBatalla(resultado);
        principal.registrarBitacora("Batalla finalizada - Ganador: " + ganador.getNombre());

        JOptionPane.showMessageDialog(this, resultado);
    }
}

class Combatiente implements Runnable {
    private Personaje atacante;
    private Personaje oponente;
    private String nombreHilo;
    private Practica2 principal;
    private SimulacionBatalla ventanaBatalla;

    public Combatiente(Personaje atacante, Personaje oponente, String nombreHilo,
                      Practica2 principal, SimulacionBatalla ventanaBatalla) {
        this.atacante = atacante;
        this.oponente = oponente;
        this.nombreHilo = nombreHilo;
        this.principal = principal;
        this.ventanaBatalla = ventanaBatalla;
    }

    @Override
    public void run() {
        try {
            while (oponente.getHp() > 0 && atacante.getHp() > 0) {
                int tiempoEspera = 1000 / atacante.getVelocidad();
                tiempoEspera = Math.min(Math.max(tiempoEspera, 200), 2000); // Entre 200ms y 2s
                Thread.sleep(tiempoEspera);

                if (oponente.getHp() <= 0) break;

                realizarAtaque();
            }

            if (atacante.getHp() > 0 && oponente.getHp() <= 0) {
                ventanaBatalla.batallaTerminada(atacante, oponente);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void realizarAtaque() {
        double probabilidadGolpe = 0.7 + (atacante.getAgilidad() * 0.01);
        boolean golpeExitoso = Math.random() < probabilidadGolpe;

        if (golpeExitoso) {
            int daño = atacante.getAtaque() - (oponente.getDefensa() / 3);
            daño = Math.max(daño, 1);

            oponente.setHp(oponente.getHp() - daño);

            String log =  atacante.getNombre() + " ataca a " + oponente.getNombre() +
                        " - Golpe! (" + daño + " de daño) - HP restante: " +
                        (oponente.getHp() > 0 ? oponente.getHp() : "0");
            ventanaBatalla.agregarLogBatalla(log);
            principal.registrarBitacora("Ataque exitoso: " + atacante.getNombre() + " -> " + oponente.getNombre() + " (" + daño + " daño)");
        } else {
            String log = atacante.getNombre() + " ataca a " + oponente.getNombre() + " - fallo";
            ventanaBatalla.agregarLogBatalla(log);
            principal.registrarBitacora("Ataque fallido: " + atacante.getNombre() + " -> " + oponente.getNombre());
        }
    }
}

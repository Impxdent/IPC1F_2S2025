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
        setTitle("Simulación de Batalla");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        
        // Registrar en bitácora
        principal.registrarBitacora("Acceso a simulación de batallas");

        // ComboBox para selección de personajes
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
        btnIniciarBatalla.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarBatalla();
            }
        });
        add(btnIniciarBatalla);

        btnMenu = new JButton("Menú Principal");
        btnMenu.setBounds(430, 35, 120, 30);
        btnMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                principal.setVisible(true);
                dispose();
            }
        });
        add(btnMenu);

        // Área de texto para la bitácora de batalla
        JLabel lblBitacora = new JLabel("Bitácora de Batalla:");
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
    }

    private void iniciarBatalla() {
        if (batallaEnCurso) {
            JOptionPane.showMessageDialog(this, "Ya hay una batalla en curso.");
            return;
        }

        int index1 = cmbPersonaje1.getSelectedIndex();
        int index2 = cmbPersonaje2.getSelectedIndex();

        if (index1 == -1 || index2 == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar dos personajes.");
            return;
        }

        if (index1 == index2) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar personajes diferentes.");
            return;
        }

        Personaje p1 = principal.getPersonajes()[index1];
        Personaje p2 = principal.getPersonajes()[index2];

        // Validar que estén vivos
        if (p1.getHp() <= 0 || p2.getHp() <= 0) {
            JOptionPane.showMessageDialog(this, "Ambos personajes deben estar vivos para combatir.");
            return;
        }

        batallaEnCurso = true;
        btnIniciarBatalla.setEnabled(false);
        txtBitacora.setText("");

        // Registrar inicio de batalla
        String horaInicio = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        String registro = "🏁 BATALLA INICIADA: " + p1.getNombre() + " vs " + p2.getNombre() + " - " + horaInicio;
        txtBitacora.append(registro + "\n\n");
        principal.registrarHistorialBatalla(registro);
        principal.registrarBitacora("Inicio de batalla: " + p1.getNombre() + " vs " + p2.getNombre());

        // Crear copias de los personajes para la batalla (para no modificar los originales)
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
            // Auto-scroll
            txtBitacora.setCaretPosition(txtBitacora.getDocument().getLength());
        });
    }

    public void batallaTerminada(Personaje ganador, Personaje perdedor) {
        batallaEnCurso = false;
        btnIniciarBatalla.setEnabled(true);
        
        String resultado = "🎯 BATALLA TERMINADA - Ganador: " + ganador.getNombre() + 
                          " (HP restante: " + ganador.getHp() + ")";
        agregarLogBatalla("\n" + resultado);
        
        principal.registrarHistorialBatalla(resultado);
        principal.registrarBitacora("Batalla terminada - Ganador: " + ganador.getNombre());
        
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
                // Calcular tiempo entre ataques basado en velocidad
                int tiempoEspera = 1000 / atacante.getVelocidad();
                if (tiempoEspera < 200) tiempoEspera = 200; // Mínimo 200ms
                if (tiempoEspera > 2000) tiempoEspera = 2000; // Máximo 2 segundos
                
                Thread.sleep(tiempoEspera);

                // Verificar si el oponente sigue vivo
                if (oponente.getHp() <= 0) break;

                // Realizar ataque
                realizarAtaque();
            }

            // Verificar si este hilo fue el que ganó
            if (atacante.getHp() > 0 && oponente.getHp() <= 0) {
                ventanaBatalla.batallaTerminada(atacante, oponente);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void realizarAtaque() {
        // Calcular probabilidad de golpe (basado en agilidad)
        double probabilidadGolpe = 0.7 + (atacante.getAgilidad() * 0.01);
        boolean golpeExitoso = Math.random() < probabilidadGolpe;

        if (golpeExitoso) {
            // Calcular daño (ataque - defensa del oponente)
            int daño = atacante.getAtaque() - (oponente.getDefensa() / 3);
            if (daño < 1) daño = 1; // Mínimo 1 de daño
            
            oponente.setHp(oponente.getHp() - daño);
            
            String log = "⚔️ " + atacante.getNombre() + " ataca a " + oponente.getNombre() + 
                        " - ¡Golpe! (" + daño + " de daño) - HP restante: " + 
                        (oponente.getHp() > 0 ? oponente.getHp() : "0");
            ventanaBatalla.agregarLogBatalla(log);
            principal.registrarBitacora("Ataque: " + atacante.getNombre() + " -> " + oponente.getNombre() + " (" + daño + " daño)");
        } else {
            String log = "🛡️ " + atacante.getNombre() + " ataca a " + oponente.getNombre() + " - Falló (esquiva)";
            ventanaBatalla.agregarLogBatalla(log);
            principal.registrarBitacora("Ataque fallido: " + atacante.getNombre() + " -> " + oponente.getNombre());
        }
    }
}
package practica2;

import javax.swing.*;
import java.awt.event.*;

public class AgregarPersonaje extends JFrame {
    private Practica2 principal;
    private JTextField txtNombre, txtArma, txtHp, txtAtaque, txtVelocidad, txtAgilidad, txtDefensa;
    private JButton btnGuardar, btnMenu;

    public AgregarPersonaje(Practica2 principal) {
        this.principal = principal;
        setTitle("Agregar Personaje");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        int y = 20;
        txtNombre = crearCampo("Nombre", y); y+=35;
        txtArma = crearCampo("Arma", y); y+=35;
        txtHp = crearCampo("Puntos de vida", y); y+=35;
        txtAtaque = crearCampo("Ataque", y); y+=35;
        txtVelocidad = crearCampo("Velocidad", y); y+=35;
        txtAgilidad = crearCampo("Agilidad", y); y+=35;
        txtDefensa = crearCampo("Defensa", y); y+=35;

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(50, y, 120, 30);
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { guardarPersonaje(); }
        });
        add(btnGuardar);

        btnMenu = new JButton("Menú Principal");
        btnMenu.setBounds(200, y, 120, 30);
        btnMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                principal.setVisible(true);
                dispose();
            }
        });
        add(btnMenu);
    }

    private JTextField crearCampo(String label, int y) {
        JLabel l = new JLabel(label);
        l.setBounds(20, y, 120, 25);
        add(l);
        JTextField t = new JTextField();
        t.setBounds(150, y, 200, 25);
        add(t);
        return t;
    }

    private void guardarPersonaje() {
        try {
            String nombre = txtNombre.getText().trim();
            String arma = txtArma.getText().trim();
            int hp = Integer.parseInt(txtHp.getText().trim());
            int ataque = Integer.parseInt(txtAtaque.getText().trim());
            int velocidad = Integer.parseInt(txtVelocidad.getText().trim());
            int agilidad = Integer.parseInt(txtAgilidad.getText().trim());
            int defensa = Integer.parseInt(txtDefensa.getText().trim());

            if(nombre.isEmpty() || arma.isEmpty()) {
                throw new Exception("Campos vacíos");
            }

            Personaje p = new Personaje(nombre, arma, hp, ataque, velocidad, agilidad, defensa);
            int id = principal.getTotalPersonajes();
            principal.getPersonajes()[id] = p;
            principal.setTotalPersonajes(id + 1);

            JOptionPane.showMessageDialog(this, "Personaje agregado con ID: " + (id+1));
            limpiarCampos();
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: Los campos numéricos deben contener números válidos");
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtArma.setText("");
        txtHp.setText("");
        txtAtaque.setText("");
        txtVelocidad.setText("");
        txtAgilidad.setText("");
        txtDefensa.setText("");
    }
}

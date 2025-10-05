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
        setSize(400, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        int y = 20;
        txtNombre = crearCampo("Nombre", y); y+=35;
        txtArma = crearCampo("Arma", y); y+=35;
        txtHp = crearCampo("Puntos de vida (100-500)", y); y+=35;
        txtAtaque = crearCampo("Ataque (10-100)", y); y+=35;
        txtVelocidad = crearCampo("Velocidad (1-10)", y); y+=35;
        txtAgilidad = crearCampo("Agilidad (1-10)", y); y+=35;
        txtDefensa = crearCampo("Defensa (1-50)", y); y+=35;

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(50, y, 120, 30);
        btnGuardar.addActionListener(e -> guardarPersonaje());
        add(btnGuardar);

        btnMenu = new JButton("Menú Principal");
        btnMenu.setBounds(200, y, 120, 30);
        btnMenu.addActionListener(e -> {
            principal.setVisible(true);
            principal.registrarBitacora("Se regreso al menu principal");
            dispose();
        });
        add(btnMenu);
    }

    private JTextField crearCampo(String label, int y) {
        JLabel l = new JLabel(label);
        l.setBounds(20, y, 200, 25);
        add(l);
        JTextField t = new JTextField();
        t.setBounds(180, y, 180, 25);
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

            //validaciones de datos
            if (nombre.isEmpty() || arma.isEmpty()) {
                throw new Exception("Llene los campos");
            }

            //validacion de existencia de nombre
            for (int i = 0; i < principal.getTotalPersonajes(); i++) {
                if (principal.getPersonajes()[i].getNombre().equalsIgnoreCase(nombre)) {
                    throw new Exception("Ya existe un personaje con el nombre '" + nombre + "'.");
                }
            }

            if (hp < 100 || hp > 500) throw new Exception("HP debe estar entre 100 y 500");
            if (ataque < 10 || ataque > 100) throw new Exception("Ataque debe estar entre 10 y 100");
            if (velocidad < 1 || velocidad > 10) throw new Exception("Velocidad debe estar entre 1 y 10");
            if (agilidad < 1 || agilidad > 10) throw new Exception("Agilidad debe estar entre 1 y 10");
            if (defensa < 1 || defensa > 50) throw new Exception("Defensa debe estar entre 1 y 50");

            //crear y guardar personaje
            Personaje p = new Personaje(nombre, arma, hp, ataque, velocidad, agilidad, defensa);
            int id = principal.getTotalPersonajes();
            principal.getPersonajes()[id] = p;
            principal.setTotalPersonajes(id + 1);

            JOptionPane.showMessageDialog(this, "Personaje agregado con ID: " + (id+1));
            principal.registrarBitacora("Se agrego personaje: " + nombre + " con ID: " + (id+1));
            limpiarCampos();

        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese numeros validos");
            principal.registrarBitacora("Error al agregar personaje: campos numericos invalidos");
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            principal.registrarBitacora("Error al agregar personaje: " + e.getMessage());
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
        principal.registrarBitacora("Se limpiaron los campos");
    }
}

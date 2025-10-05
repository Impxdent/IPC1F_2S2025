package practica2;

import javax.swing.*;
import java.awt.event.*;

public class ModificarPersonaje extends JFrame {
    private Practica2 principal;
    private JTextField txtId, txtArma, txtHp, txtAtaque, txtVelocidad, txtAgilidad, txtDefensa;
    private JButton btnBuscar, btnGuardar, btnMenu;
    private JLabel lblNombre;

    public ModificarPersonaje(Practica2 principal) {
        this.principal = principal;
        setTitle("Modificar Personaje");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel lId = new JLabel("ID");
        lId.setBounds(20, 20, 50, 25);
        add(lId);
        txtId = new JTextField();
        txtId.setBounds(60, 20, 50, 25);
        add(txtId);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(120, 20, 100, 25);
        btnBuscar.addActionListener(e -> buscarPersonaje());
        add(btnBuscar);

        lblNombre = new JLabel("Nombre: ");
        lblNombre.setBounds(20, 55, 350, 25);
        add(lblNombre);

        int y = 90;
        txtArma = crearCampo("Arma", y); y+=35;
        txtHp = crearCampo("Puntos de vida", y); y+=35;
        txtAtaque = crearCampo("Ataque", y); y+=35;
        txtVelocidad = crearCampo("Velocidad", y); y+=35;
        txtAgilidad = crearCampo("Agilidad", y); y+=35;
        txtDefensa = crearCampo("Defensa", y); y+=35;

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(50, y, 120, 30);
        btnGuardar.addActionListener(e -> guardarCambios());
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
        l.setBounds(20, y, 120, 25);
        add(l);
        JTextField t = new JTextField();
        t.setBounds(150, y, 200, 25);
        add(t);
        return t;
    }

    private void buscarPersonaje() {
        try {
            int id = Integer.parseInt(txtId.getText().trim()) - 1;
            if(id < 0 || id >= principal.getTotalPersonajes()) {
                JOptionPane.showMessageDialog(this, "No existe personaje con ese ID"); //validacion de existencia de id
                principal.registrarBitacora("Se busco un ID inexistente");
                return;
            }
            Personaje p = principal.getPersonajes()[id];
            lblNombre.setText("Nombre: " + p.getNombre());
            txtArma.setText(p.getArma());
            txtHp.setText(""+p.getHp());
            txtAtaque.setText(""+p.getAtaque());
            txtVelocidad.setText(""+p.getVelocidad());
            txtAgilidad.setText(""+p.getAgilidad());
            txtDefensa.setText(""+p.getDefensa());

            principal.registrarBitacora("Personaje buscado para modificar: " + p.getNombre() + " (ID: " + (id+1) + ")");
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID valido");
            principal.registrarBitacora("Se ingreso un ID invalido");
        }
    }

    private void guardarCambios() {
        try {
            int id = Integer.parseInt(txtId.getText().trim()) - 1;
            if(id < 0 || id >= principal.getTotalPersonajes()) {
                JOptionPane.showMessageDialog(this, "No existe personaje con ese ID");
                principal.registrarBitacora("Error por el ID al guardar datos (" + (id+1) + ")");
                return;
            }

            //actualizar los datos que se modificaron
            Personaje p = principal.getPersonajes()[id];
            p.setArma(txtArma.getText().trim());
            p.setHp(Integer.parseInt(txtHp.getText().trim()));
            p.setAtaque(Integer.parseInt(txtAtaque.getText().trim()));
            p.setVelocidad(Integer.parseInt(txtVelocidad.getText().trim()));
            p.setAgilidad(Integer.parseInt(txtAgilidad.getText().trim()));
            p.setDefensa(Integer.parseInt(txtDefensa.getText().trim()));

            JOptionPane.showMessageDialog(this, "Datos modificados correctamente");
            principal.registrarBitacora("Datos modificados del personaje: " + p.getNombre() + " (ID: " + (id+1) + ")");
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Revise que todos los campos sean correctos");
            principal.registrarBitacora("Error al guardar cambios en personaje: datos invalidos");
        }
    }
}

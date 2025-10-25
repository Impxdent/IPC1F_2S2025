/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2;


import javax.swing.*;
import java.awt.*;

/**
 *
 * @author André
 */
public class AgregarVendedor extends JFrame{
     private JTextField txtCodigo, txtNombre, txtContrasena;
    private JComboBox<String> cmbGenero;

    public AgregarVendedor() { //diseño de ventana
        setTitle("Agregar Vendedor");
        setSize(380, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6,2,8,8));

        add(new JLabel("Código:")); txtCodigo = new JTextField(); add(txtCodigo);
        add(new JLabel("Nombre:")); txtNombre = new JTextField(); add(txtNombre);
        add(new JLabel("Género:")); cmbGenero = new JComboBox<>(new String[]{"M","F"}); add(cmbGenero);
        add(new JLabel("Contraseña:")); txtContrasena = new JTextField(); add(txtContrasena);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnVolver = new JButton("Volver");
        add(btnGuardar); add(btnVolver);

        btnGuardar.addActionListener(e -> guardar());
        btnVolver.addActionListener(e -> { new admin().setVisible(true); dispose(); });
    }

    private void guardar() {
        //guardar datos
        String c = txtCodigo.getText().trim();
        String n = txtNombre.getText().trim();
        String g = (String) cmbGenero.getSelectedItem();
        String p = txtContrasena.getText().trim();

        if (c.isEmpty() || n.isEmpty() || p.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Llene todos los campos"); return;
        }
        if (GestorVendedores.agregar(c,n,g,p)) {
            JOptionPane.showMessageDialog(this, "Vendedor agregado exitosamente");
            new admin().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Código duplicado o límite alcanzado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

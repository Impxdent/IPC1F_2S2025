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
public class AgregarCliente extends JFrame{
    private JTextField txtCodigo, txtNombre, txtFecha, txtContrasena;
    private JComboBox<String> cmbGenero;

    public AgregarCliente() {
        setTitle("Agregar Cliente");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6,2,8,8));

        add(new JLabel("Código:")); txtCodigo = new JTextField(); add(txtCodigo);
        add(new JLabel("Nombre:")); txtNombre = new JTextField(); add(txtNombre);
        add(new JLabel("Género:")); cmbGenero = new JComboBox<>(new String[]{"M","F"}); add(cmbGenero);
        add(new JLabel("Cumpleaños (dd/mm/aaaa):")); txtFecha = new JTextField(); add(txtFecha);
        add(new JLabel("Contraseña:")); txtContrasena = new JTextField(); add(txtContrasena);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnVolver = new JButton("Volver");
        add(btnGuardar); add(btnVolver);

        btnGuardar.addActionListener(e -> guardar());
        btnVolver.addActionListener(e -> { new vendedor("Vendedor").setVisible(true); dispose(); });
    }

    private void guardar() {
        String c = txtCodigo.getText().trim();
        String n = txtNombre.getText().trim();
        String g = (String) cmbGenero.getSelectedItem();
        String f = txtFecha.getText().trim();
        String p = txtContrasena.getText().trim();

        if (c.isEmpty() || n.isEmpty() || f.isEmpty() || p.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos."); return;
        }

        if (GestorClientes.agregar(c, n, g, f, p)) {
            JOptionPane.showMessageDialog(this, "Cliente agregado correctamente.");
            new vendedor("Vendedor").setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error: código duplicado o límite alcanzado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

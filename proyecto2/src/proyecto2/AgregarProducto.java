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
public class AgregarProducto extends JFrame{
    private JTextField txtCodigo, txtNombre, txtAtributo, txtPrecio;
    private JComboBox<String> cmbCategoria;

    public AgregarProducto() {
        setTitle("Agregar Producto");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6,2,8,8));

        add(new JLabel("Código:")); txtCodigo = new JTextField(); add(txtCodigo);
        add(new JLabel("Nombre:")); txtNombre = new JTextField(); add(txtNombre);

        add(new JLabel("Categoría:"));
        cmbCategoria = new JComboBox<>(new String[]{"Alimentos", "Tecnología", "General"});
        add(cmbCategoria);

        add(new JLabel("Atributo (caducidad/garantía/material):"));
        txtAtributo = new JTextField();
        add(txtAtributo);

        add(new JLabel("Precio (Q):"));
        txtPrecio = new JTextField();
        add(txtPrecio);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnVolver = new JButton("Volver");
        add(btnGuardar); add(btnVolver);

        btnGuardar.addActionListener(e -> guardar());
        btnVolver.addActionListener(e -> { new admin().setVisible(true); dispose(); });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void guardar() {
        try {
            String c = txtCodigo.getText().trim();
            String n = txtNombre.getText().trim();
            String cat = (String) cmbCategoria.getSelectedItem();
            String att = txtAtributo.getText().trim();
            double p = Double.parseDouble(txtPrecio.getText().trim());

            if (GestorProductos.agregar(c, n, cat, att, p)) {
                JOptionPane.showMessageDialog(this, "Producto agregado.");
                new admin().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error: código duplicado o límite alcanzado.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: verifica los datos (precio debe ser número).");
        }
    }
}

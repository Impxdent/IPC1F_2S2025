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
public class ModificarVendedor extends JFrame{
    private JTextField txtBuscarCodigo, txtNombre, txtContrasena;

    public ModificarVendedor() {
        setTitle("Modificar Vendedor");
        setSize(380, 240);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5,2,8,8));

        add(new JLabel("Buscar por código:"));
        txtBuscarCodigo = new JTextField(); add(txtBuscarCodigo);

        JButton btnBuscar = new JButton("Buscar"); add(btnBuscar);
        JButton espacio = new JButton(); espacio.setVisible(false); add(espacio);

        add(new JLabel("Nombre:")); txtNombre = new JTextField(); add(txtNombre);
        add(new JLabel("Contraseña:")); txtContrasena = new JTextField(); add(txtContrasena);

        JButton btnGuardar = new JButton("Guardar cambios");
        JButton btnVolver = new JButton("Volver");
        add(btnGuardar); add(btnVolver);

        btnBuscar.addActionListener(e -> cargarDatos());
        btnGuardar.addActionListener(e -> guardarCambios());
        btnVolver.addActionListener(e -> { new admin().setVisible(true); dispose(); });
    }

    private void cargarDatos() {
        String codigo = txtBuscarCodigo.getText().trim();
        int i = GestorVendedores.buscarIndicePorCodigo(codigo);
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "No existe el vendedor.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        txtNombre.setText(GestorVendedores.getNombres()[i]);
        txtContrasena.setText(GestorVendedores.getContrasenas()[i]);
    }

    private void guardarCambios() {
        String codigo = txtBuscarCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String pass = txtContrasena.getText().trim();
        if (codigo.isEmpty() || nombre.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos."); return;
        }
        if (GestorVendedores.modificar(codigo, nombre, pass)) {
            JOptionPane.showMessageDialog(this, "Cambios guardados.");
            new admin().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo modificar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

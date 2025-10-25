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
public class ModificarCliente extends JFrame{
    private JTextField txtBuscar, txtNombre, txtFecha, txtContrasena;
    private JComboBox<String> cmbGenero;

    public ModificarCliente() {
        setTitle("Modificar Cliente");
        setSize(420, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6,2,8,8));

        add(new JLabel("Buscar por código:")); txtBuscar = new JTextField(); add(txtBuscar);
        JButton btnBuscar = new JButton("Buscar"); add(btnBuscar); add(new JLabel(""));

        add(new JLabel("Nombre:")); txtNombre = new JTextField(); add(txtNombre);
        add(new JLabel("Género:")); cmbGenero = new JComboBox<>(new String[]{"M","F"}); add(cmbGenero);
        add(new JLabel("Cumpleaños:")); txtFecha = new JTextField(); add(txtFecha);
        add(new JLabel("Contraseña:")); txtContrasena = new JTextField(); add(txtContrasena);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnVolver = new JButton("Volver");
        add(btnGuardar); add(btnVolver);

        btnBuscar.addActionListener(e -> buscar());
        btnGuardar.addActionListener(e -> guardar());
        btnVolver.addActionListener(e -> { new vendedor("Vendedor").setVisible(true); dispose(); });
    }

    private void buscar() { //buscar por id
        String codigo = txtBuscar.getText().trim();
        int i = GestorClientes.buscarIndicePorCodigo(codigo);
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "Error, cliente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        txtNombre.setText(GestorClientes.getNombres()[i]);
        cmbGenero.setSelectedItem(GestorClientes.getGeneros()[i]);
        txtFecha.setText(GestorClientes.getCumples()[i]);
        txtContrasena.setText(GestorClientes.getContrasenas()[i]);
    }

    private void guardar() { //guardar los datos de los textbox
        String c = txtBuscar.getText().trim();
        String n = txtNombre.getText().trim();
        String g = (String) cmbGenero.getSelectedItem();
        String f = txtFecha.getText().trim();
        String p = txtContrasena.getText().trim();

        if (c.isEmpty() || n.isEmpty() || f.isEmpty() || p.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error, llene todos los campos"); return;
        }

        if (GestorClientes.modificar(c, n, g, f, p)) {
            JOptionPane.showMessageDialog(this, "Cliente modificado exitosamente");
            new vendedor("Vendedor").setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al modificar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

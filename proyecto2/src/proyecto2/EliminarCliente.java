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
public class EliminarCliente extends JFrame{
    private JTextField txtCodigo;

    public EliminarCliente() { //diseño de ventana
        setTitle("Eliminar Cliente");
        setSize(360, 160);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3,2,8,8));

        add(new JLabel("Código del cliente:"));
        txtCodigo = new JTextField(); add(txtCodigo);

        JButton btnEliminar = new JButton("Eliminar");
        JButton btnVolver = new JButton("Volver");
        add(btnEliminar); add(btnVolver);

        btnEliminar.addActionListener(e -> eliminar());
        btnVolver.addActionListener(e -> { new vendedor("Vendedor").setVisible(true); dispose(); });
    }

    private void eliminar() { //validacion de id
        String c = txtCodigo.getText().trim();
        int i = GestorClientes.buscarIndicePorCodigo(c);
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "No existe ese cliente", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String resumen = "Código: " + c +
                "\nNombre: " + GestorClientes.getNombres()[i] +
                "\nGénero: " + GestorClientes.getGeneros()[i] +
                "\nCumpleaños: " + GestorClientes.getCumples()[i];

        int op = JOptionPane.showConfirmDialog(this, "¿Desea eliminar este cliente?\n\n" + resumen, "Confirmar", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            if (GestorClientes.eliminar(c)) {
                JOptionPane.showMessageDialog(this, "Cliente eliminado exitosamente");
                new vendedor("Vendedor").setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

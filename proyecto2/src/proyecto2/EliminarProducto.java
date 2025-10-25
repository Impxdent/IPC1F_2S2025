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
public class EliminarProducto extends JFrame{
    private JTextField txtCodigo;

    public EliminarProducto() {
        setTitle("Eliminar Producto");
        setSize(380, 160);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3,2,8,8));

        add(new JLabel("Código del producto:"));
        txtCodigo = new JTextField(); add(txtCodigo);

        JButton btnEliminar = new JButton("Eliminar");
        JButton btnVolver = new JButton("Volver");
        add(btnEliminar); add(btnVolver);

        btnEliminar.addActionListener(e -> eliminar());
        btnVolver.addActionListener(e -> { new admin().setVisible(true); dispose(); });
    }

    private void eliminar() {
        String c = txtCodigo.getText().trim();
        int i = GestorProductos.buscarIndicePorCodigo(c);
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "No existe el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String resumen = "Código: " + c +
                "\nNombre: " + GestorProductos.getNombres()[i] +
                "\nCategoría: " + GestorProductos.getCategorias()[i] +
                "\nAtributo: " + GestorProductos.getAtributoUnico()[i];

        int op = JOptionPane.showConfirmDialog(this, "¿Eliminar?\n\n" + resumen, "Confirmación", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            if (GestorProductos.eliminar(c)) {
                JOptionPane.showMessageDialog(this, "Producto eliminado.");
                new admin().setVisible(true); dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

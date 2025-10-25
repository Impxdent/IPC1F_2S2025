package proyecto2;

import javax.swing.*;
import java.awt.*;

public class ModificarProducto extends JFrame {
    private JTextField txtCodigoBuscar, txtNombre, txtAtributo, txtPrecio;
    private JComboBox<String> cmbCategoria;

    public ModificarProducto() { //encabezado de ventana
        setTitle("Modificar Producto");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 8, 8));

        //espacios para ingresar texto
        add(new JLabel("Buscar por código:"));
        txtCodigoBuscar = new JTextField();
        add(txtCodigoBuscar);
        JButton btnBuscar = new JButton("Buscar");
        add(btnBuscar);
        add(new JLabel(""));

        add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("Categoría:"));
        cmbCategoria = new JComboBox<>(new String[]{"Alimentos", "Tecnologia", "General"});
        add(cmbCategoria);

        add(new JLabel("Atributo:"));
        txtAtributo = new JTextField();
        add(txtAtributo);

        add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        add(txtPrecio);

        JButton btnGuardar = new JButton("Guardar cambios");
        JButton btnVolver = new JButton("Volver");
        add(btnGuardar);
        add(btnVolver);

        btnBuscar.addActionListener(e -> cargar());
        btnGuardar.addActionListener(e -> guardar());
        btnVolver.addActionListener(e -> { new admin().setVisible(true); dispose(); });
    }

    private void cargar() {
        String c = txtCodigoBuscar.getText().trim();
        int i = GestorProductos.buscarIndicePorCodigo(c);

        if (i == -1) {
            JOptionPane.showMessageDialog(this, "No existe ese producto", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        txtNombre.setText(GestorProductos.getNombres()[i]);
        cmbCategoria.setSelectedItem(GestorProductos.getCategorias()[i]);
        txtAtributo.setText(GestorProductos.getAtributoUnico()[i]);
        txtPrecio.setText(String.valueOf(GestorProductos.getPrecios()[i]));
    }

    private void guardar() { //guardar datos
        try {
            String c = txtCodigoBuscar.getText().trim();
            String n = txtNombre.getText().trim();
            String cat = (String) cmbCategoria.getSelectedItem();
            String atr = txtAtributo.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());

            if (c.isEmpty() || n.isEmpty() || atr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos");
                return;
            }

            if (GestorProductos.modificar(c, n, cat, atr, precio)) {
                JOptionPane.showMessageDialog(this, "Producto modificado correctamente");
                new admin().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error, no se pudo modificar", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error, precio invalido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

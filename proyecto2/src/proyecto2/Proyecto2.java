/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author André
 */
public class Proyecto2 extends JFrame{
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIngresar, btnSalir;

    public Proyecto2() {
        //encabezado de ventana
        setTitle("Login del Sistema");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //diseño de ventana
        JLabel lblTitulo = new JLabel("Inicio de Sesión", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        JLabel lblUsuario = new JLabel("Usuario:");
        gbc.gridx = 0;
        add(lblUsuario, gbc);

        txtUsuario = new JTextField();
        gbc.gridx = 1;
        add(txtUsuario, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblContrasena = new JLabel("Contraseña:");
        add(lblContrasena, gbc);

        txtContrasena = new JPasswordField();
        gbc.gridx = 1;
        add(txtContrasena, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        btnIngresar = new JButton("Ingresar");
        add(btnIngresar, gbc);

        gbc.gridx = 1;
        btnSalir = new JButton("Salir");
        add(btnSalir, gbc);

        btnIngresar.addActionListener(e -> validarCredenciales());

        btnSalir.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void validarCredenciales() {
        String u = txtUsuario.getText().trim();
        String p = new String(txtContrasena.getPassword());

        //inicio de sesión para administrador
        if (u.equals("admin") && p.equals("IPC1F")) {
            JOptionPane.showMessageDialog(this, "Bienvenido administrador");
            new admin().setVisible(true);
            dispose();
            return;
        }

        //inicio de sesión para vendedor con datos registrados por administrador
        int idx = GestorVendedores.buscarIndicePorCodigo(u);
        if (idx == -1) {
            for (int i = 0; i < GestorVendedores.getTotal(); i++) {
                if (GestorVendedores.getNombres()[i].equalsIgnoreCase(u)) {
                    idx = i;
                    break;
                }
            }
        }

        if (idx != -1 && GestorVendedores.getContrasenas()[idx].equals(p)) {
            JOptionPane.showMessageDialog(this, "Bienvenido vendedor: " + GestorVendedores.getNombres()[idx]);
            new vendedor(GestorVendedores.getNombres()[idx]).setVisible(true);
            dispose();
            return;
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            txtContrasena.setText("");
        }
        
        //iniciar sesión cliente
        int idCliente = GestorClientes.buscarIndicePorCodigo(u);
        if (idCliente == -1) {
            for (int i = 0; i < GestorClientes.getTotal(); i++) {
                if (GestorClientes.getNombres()[i].equalsIgnoreCase(u)) {
                    idCliente = i;
                    break;
                }
            }
        }
        if (idCliente != -1 && GestorClientes.getContrasenas()[idCliente].equals(p)) {
            JOptionPane.showMessageDialog(this, "Bienvenido cliente: " + GestorClientes.getNombres()[idCliente]);
            new cliente(GestorClientes.getNombres()[idCliente]).setVisible(true);
            dispose();
            return;
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Proyecto2());
    }

    /**
     * @param args the command line arguments
     */

}

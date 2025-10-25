/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
/**
 *
 * @author André
 */
public class vendedor extends JFrame {
    private JTable tablaProductos, tablaClientes, tablaPedidos;
    private DefaultTableModel modeloProductos, modeloClientes, modeloPedidos;
    private String nombreVendedor;

    public vendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;

        setTitle("Panel del Vendedor - " + nombreVendedor);
        setSize(950, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ----- ENCABEZADO -----
        JPanel top = new JPanel(new BorderLayout());
        JLabel lbl = new JLabel("Bienvenido, " + nombreVendedor, SwingConstants.LEFT);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        JButton btnCerrarSesion = new JButton("Cerrar sesión");
        btnCerrarSesion.addActionListener(e -> { new Proyecto2().setVisible(true); dispose(); });
        top.add(lbl, BorderLayout.WEST);
        top.add(btnCerrarSesion, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        // ----- TABS -----
        JTabbedPane tabs = new JTabbedPane();

        // ====== PESTAÑA PRODUCTOS ======
        JPanel panelProd = new JPanel(new BorderLayout(8,8));

        modeloProductos = new DefaultTableModel(new String[]{"Código","Nombre","Categoría","Stock","Acciones"},0){
            public boolean isCellEditable(int r,int c){ return false; }
        };
        tablaProductos = new JTable(modeloProductos);
        panelProd.add(new JScrollPane(tablaProductos), BorderLayout.CENTER);

        // Panel lateral de botones
        JPanel panelBotonesProd = new JPanel(new GridLayout(2,1,10,10));
        JButton btnAgregarStock = new JButton("Agregar Stock");
        JButton btnVerHistorial = new JButton("Ver historial ingresos");
        panelBotonesProd.add(btnAgregarStock);
        panelBotonesProd.add(btnVerHistorial);
        panelProd.add(panelBotonesProd, BorderLayout.EAST);

        btnAgregarStock.addActionListener(e -> agregarStock());
        btnVerHistorial.addActionListener(e -> JOptionPane.showMessageDialog(this, "Historial de ingresos (simulado)."));

        tabs.add("Productos", panelProd);

        // ====== PESTAÑA CLIENTES ======
        JPanel panelCli = new JPanel(new BorderLayout(8,8));
        modeloClientes = new DefaultTableModel(new String[]{"Código","Nombre","Género","Cumpleaños"},0){
            public boolean isCellEditable(int r,int c){ return false; }
        };
        tablaClientes = new JTable(modeloClientes);
        panelCli.add(new JScrollPane(tablaClientes), BorderLayout.CENTER);

        JPanel panelBotonesCli = new JPanel(new GridLayout(3,1,10,10));
        JButton btnAgregarC = new JButton("Agregar");
        JButton btnModificarC = new JButton("Modificar");
        JButton btnEliminarC = new JButton("Eliminar");
        panelBotonesCli.add(btnAgregarC);
        panelBotonesCli.add(btnModificarC);
        panelBotonesCli.add(btnEliminarC);
        panelCli.add(panelBotonesCli, BorderLayout.EAST);

        btnAgregarC.addActionListener(e -> { new AgregarCliente().setVisible(true); dispose(); });
        btnModificarC.addActionListener(e -> { new ModificarCliente().setVisible(true); dispose(); });
        btnEliminarC.addActionListener(e -> { new EliminarCliente().setVisible(true); dispose(); });

        tabs.add("Clientes", panelCli);

        // ====== PESTAÑA PEDIDOS ======
        JPanel panelPed = new JPanel(new BorderLayout(8,8));
        modeloPedidos = new DefaultTableModel(new String[]{"Código Pedido","Fecha","Cod Cliente","Nombre Cliente","Total","Opciones"},0){
            public boolean isCellEditable(int r,int c){ return false; }
        };
        tablaPedidos = new JTable(modeloPedidos);
        panelPed.add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);

        JPanel panelBotonesPed = new JPanel(new GridLayout(1,1,10,10));
        JButton btnConfirmar = new JButton("Confirmar Pedido");
        panelBotonesPed.add(btnConfirmar);
        panelPed.add(panelBotonesPed, BorderLayout.EAST);

        btnConfirmar.addActionListener(e -> confirmarPedido());

        tabs.add("Pedidos", panelPed);

        add(tabs, BorderLayout.CENTER);

        // Carga inicial
        cargarTablas();
    }

    private void cargarTablas() {
        // Productos
        modeloProductos.setRowCount(0);
        String[] cod = GestorProductos.getCodigos();
        String[] nom = GestorProductos.getNombres();
        String[] cat = GestorProductos.getCategorias();
        for (int i = 0; i < GestorProductos.getTotal(); i++) {
            modeloProductos.addRow(new Object[]{cod[i], nom[i], cat[i], 0, "Ver historial"});
        }

        // Clientes
        modeloClientes.setRowCount(0);
        String[] c = GestorClientes.getCodigos();
        String[] n = GestorClientes.getNombres();
        String[] g = GestorClientes.getGeneros();
        String[] f = GestorClientes.getCumples();
        for (int i = 0; i < GestorClientes.getTotal(); i++) {
            modeloClientes.addRow(new Object[]{c[i], n[i], g[i], f[i]});
        }

        // Pedidos (vacíos de momento)
        modeloPedidos.setRowCount(0);
    }

    private void agregarStock() {
        String codigo = JOptionPane.showInputDialog(this, "Ingrese código del producto:");
        if (codigo == null) return;
        int fila = -1;
        for (int i = 0; i < modeloProductos.getRowCount(); i++) {
            if (modeloProductos.getValueAt(i, 0).equals(codigo)) {
                fila = i;
                break;
            }
        }
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "No existe ese producto.");
            return;
        }
        String cantidadStr = JOptionPane.showInputDialog(this, "Ingrese cantidad a agregar:");
        try {
            int cant = Integer.parseInt(cantidadStr);
            int actual = (int) modeloProductos.getValueAt(fila, 3);
            modeloProductos.setValueAt(actual + cant, fila, 3);
            JOptionPane.showMessageDialog(this, "Stock actualizado: " + (actual + cant));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida.");
        }
    }

    private void confirmarPedido() {
        JOptionPane.showMessageDialog(this, "Pedido confirmado (simulado).");
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b) cargarTablas();
    }
}

package proyecto2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class cliente extends JFrame{
    private final String nombreCliente;

    private JTable tablaProductos, tablaCarrito, tablaHistorial;
    private DefaultTableModel modeloProductos, modeloCarrito, modeloHistorial;
    private JLabel lblTotal;

    public cliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;

        setTitle("Panel del Cliente - " + nombreCliente);
        setSize(950, 560);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ----- ENCABEZADO -----
        JPanel header = new JPanel(new BorderLayout());
        JLabel lbl = new JLabel("Bienvenido, " + nombreCliente, SwingConstants.LEFT);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JButton btnCerrarSesion = new JButton("Cerrar sesión");
        btnCerrarSesion.addActionListener(e -> { new Proyecto2().setVisible(true); dispose(); });

        header.add(lbl, BorderLayout.WEST);
        header.add(btnCerrarSesion, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // ----- TABS -----
        JTabbedPane tabs = new JTabbedPane();

        // ====== PRODUCTOS ======
        JPanel panelProd = new JPanel(new BorderLayout(8,8));
        modeloProductos = new DefaultTableModel(new String[]{"Código","Nombre","Categoría","Stock","Acciones"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return c == 4; }
        };
        tablaProductos = new JTable(modeloProductos);
        panelProd.add(new JScrollPane(tablaProductos), BorderLayout.CENTER);
        tabs.add("Productos", panelProd);

        // ====== CARRITO ======
        JPanel panelCarrito = new JPanel(new BorderLayout(8,8));
        modeloCarrito = new DefaultTableModel(new String[]{"Código","Producto","Cantidad","Precio","Total","Opciones"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return c == 5; }
        };
        tablaCarrito = new JTable(modeloCarrito);
        panelCarrito.add(new JScrollPane(tablaCarrito), BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new BorderLayout());
        lblTotal = new JLabel("Total: Q0.00");
        JButton btnComprar = new JButton("Realizar compra");
        panelInferior.add(lblTotal, BorderLayout.WEST);
        panelInferior.add(btnComprar, BorderLayout.EAST);
        panelCarrito.add(panelInferior, BorderLayout.SOUTH);

        btnComprar.addActionListener(e -> realizarCompra());
        tabs.add("Carrito", panelCarrito);

        // ====== HISTORIAL ======
        JPanel panelHist = new JPanel(new BorderLayout(8,8));
        modeloHistorial = new DefaultTableModel(new String[]{"Código Compra","Fecha","Total"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaHistorial = new JTable(modeloHistorial);
        panelHist.add(new JScrollPane(tablaHistorial), BorderLayout.CENTER);
        tabs.add("Historial de compras", panelHist);

        add(tabs, BorderLayout.CENTER);

        cargarProductos();
        prepararColumnasConBotones();
        actualizarCarrito();
        cargarHistorial();
    }

    // ================= CARGAS =================
    private void cargarProductos() {
        modeloProductos.setRowCount(0);
        String[] c = GestorProductos.getCodigos();
        String[] n = GestorProductos.getNombres();
        String[] cat = GestorProductos.getCategorias();
        double[] pre = GestorProductos.getPrecios();

        for (int i = 0; i < GestorProductos.getTotal(); i++) {
            modeloProductos.addRow(new Object[]{ c[i], n[i], cat[i], /*stock*/ 10, "Agregar al carrito" });
        }
    }

    private void actualizarCarrito() {
        modeloCarrito.setRowCount(0);
        String[] cod = GestorCarrito.getCodigos();
        String[] nom = GestorCarrito.getNombres();
        double[] pre = GestorCarrito.getPrecios();
        int[] cant = GestorCarrito.getCantidades();

        for (int i = 0; i < GestorCarrito.getTotal(); i++) {
            double total = pre[i] * cant[i];
            modeloCarrito.addRow(new Object[]{ cod[i], nom[i], cant[i], pre[i], total, "Opciones" });
        }

        lblTotal.setText(String.format("Total: Q%.2f", GestorCarrito.calcularTotal()));
    }

    private void cargarHistorial() {
        modeloHistorial.setRowCount(0);
        String[] c = GestorHistorialCompras.getCodigos();
        String[] f = GestorHistorialCompras.getFechas();
        double[] t = GestorHistorialCompras.getTotales();
        for (int i = 0; i < GestorHistorialCompras.getTotal(); i++) {
            modeloHistorial.addRow(new Object[]{ c[i], f[i], t[i] });
        }
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b) { cargarProductos(); actualizarCarrito(); cargarHistorial(); }
    }

    private void realizarCompra() {
        if (GestorCarrito.getTotal() == 0) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío.");
            return;
        }
        double totalCompra = GestorCarrito.calcularTotal();
        GestorHistorialCompras.registrarCompra(totalCompra);
        GestorCarrito.vaciar();
        JOptionPane.showMessageDialog(this, "Compra realizada.\nTotal: Q" + String.format("%.2f", totalCompra));
        actualizarCarrito();
        cargarHistorial();
    }

    private void prepararColumnasConBotones() {
        tablaProductos.getColumnModel().getColumn(4).setCellRenderer(new BtnAgregarRenderer());
        tablaProductos.getColumnModel().getColumn(4).setCellEditor(new BtnAgregarEditor(new JCheckBox(), this));

        tablaCarrito.getColumnModel().getColumn(5).setCellRenderer(new OpcionesRenderer());
        tablaCarrito.getColumnModel().getColumn(5).setCellEditor(new OpcionesEditor(new JCheckBox(), this));
    }

    // ===== BOTÓN "AGREGAR AL CARRITO" CON PRECIO REAL =====
    private static class BtnAgregarRenderer extends JButton implements TableCellRenderer {
        public BtnAgregarRenderer() { setOpaque(true); }
        @Override public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
            setText("Agregar al carrito");
            return this;
        }
    }

    private static class BtnAgregarEditor extends DefaultCellEditor {
        private final JButton button;
        private int fila = -1;
        private final cliente parent;

        public BtnAgregarEditor(JCheckBox cb, cliente parent) {
            super(cb);
            this.parent = parent;
            button = new JButton("Agregar al carrito");
            button.addActionListener(e -> onClick());
        }

        @Override public Component getTableCellEditorComponent(JTable t, Object v, boolean sel, int row, int col) {
            fila = row;
            return button;
        }

        @Override public Object getCellEditorValue() { return "Agregar al carrito"; }

        private void onClick() {
            String codigo = (String) parent.modeloProductos.getValueAt(fila, 0);
            String nombre = (String) parent.modeloProductos.getValueAt(fila, 1);

            // ✅ PRECIO REAL DESDE GESTORPRODUCTOS
            int index = GestorProductos.buscarIndicePorCodigo(codigo);
            double precio = GestorProductos.getPrecios()[index];

            String cantStr = JOptionPane.showInputDialog(parent, "Cantidad:", "1");
            if (cantStr == null) return;

            try {
                int cant = Integer.parseInt(cantStr);
                if (cant <= 0) throw new Exception();
                GestorCarrito.agregar(codigo, nombre, precio, cant);
                parent.actualizarCarrito();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, "Cantidad inválida.");
            }
        }
    }

    // ===== COLUMN "OPCIONES" EN CARRITO =====
    private static class OpcionesRenderer extends JPanel implements TableCellRenderer {
        public OpcionesRenderer() {
            setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));
            add(new JButton("Actualizar cantidad"));
            add(new JButton("Eliminar"));
        }
        @Override public Component getTableCellRendererComponent(JTable t,Object v,boolean s,boolean f,int r,int c){ return this; }
    }

    private static class OpcionesEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        private final JButton btnActualizar = new JButton("Actualizar cantidad");
        private final JButton btnEliminar = new JButton("Eliminar");
        private final cliente parent;
        private int fila;

        public OpcionesEditor(JCheckBox cb, cliente parent) {
            this.parent = parent;
            panel.add(btnActualizar);
            panel.add(btnEliminar);

            btnActualizar.addActionListener(e -> actualizar());
            btnEliminar.addActionListener(e -> eliminar());
        }

        @Override public Component getTableCellEditorComponent(JTable t,Object v,boolean s,int row,int col) {
            fila = row; return panel;
        }

        @Override public Object getCellEditorValue() { return "Opciones"; }

        private void actualizar() {
            String codigo = (String) parent.modeloCarrito.getValueAt(fila, 0);
            String actual = String.valueOf(parent.modeloCarrito.getValueAt(fila, 2));
            String nueva = JOptionPane.showInputDialog(parent, "Cantidad nueva:", actual);
            if (nueva == null) return;
            try {
                GestorCarrito.actualizarCantidad(codigo, Integer.parseInt(nueva));
                parent.actualizarCarrito();
            } catch (Exception ex) { JOptionPane.showMessageDialog(parent, "Cantidad inválida."); }
        }

        private void eliminar() {
            String codigo = (String) parent.modeloCarrito.getValueAt(fila, 0);
            if (JOptionPane.showConfirmDialog(parent, "¿Eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION)==0) {
                GestorCarrito.eliminar(codigo);
                parent.actualizarCarrito();
            }
        }
    }
}

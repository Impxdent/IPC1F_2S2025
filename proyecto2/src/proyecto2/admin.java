package proyecto2;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class admin extends JFrame {
    private JTable tablaVendedores, tablaProductos;
    private DefaultTableModel modeloVendedores, modeloProductos;

    public admin() {
        setTitle("Panel de Administrador");
        setSize(950, 560);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Bienvenido Administrador", SwingConstants.LEFT);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        JButton btnCerrarSesion = new JButton("Cerrar sesión");
        btnCerrarSesion.addActionListener(e -> { new Proyecto2().setVisible(true); dispose(); });
        header.add(titulo, BorderLayout.WEST);
        header.add(btnCerrarSesion, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // Tabs
        JTabbedPane tabs = new JTabbedPane();

        // ---- Vendedores ----
        JPanel panelVend = new JPanel(new BorderLayout(8,8));
        modeloVendedores = new DefaultTableModel(new String[]{"Código","Nombre","Género","Ventas Confirmadas"}, 0) {
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        tablaVendedores = new JTable(modeloVendedores);
        panelVend.add(new JScrollPane(tablaVendedores), BorderLayout.CENTER);

        JPanel accionesVend = new JPanel(new GridLayout(3,1,10,10));
        JButton btnAddV = new JButton("Agregar");
        JButton btnModV = new JButton("Modificar");
        JButton btnDelV = new JButton("Eliminar");
        accionesVend.add(btnAddV); accionesVend.add(btnModV); accionesVend.add(btnDelV);
        panelVend.add(accionesVend, BorderLayout.EAST);

        btnAddV.addActionListener(e -> { new AgregarVendedor().setVisible(true); dispose(); });
        btnModV.addActionListener(e -> { new ModificarVendedor().setVisible(true); dispose(); });
        btnDelV.addActionListener(e -> { new EliminarVendedor().setVisible(true); dispose(); });

        tabs.add("Vendedores", panelVend);

        // ---- Productos ----
        JPanel panelProd = new JPanel(new BorderLayout(8,8));
        modeloProductos = new DefaultTableModel(
            new String[]{"Código", "Nombre", "Categoría", "Atributo", "Precio"}, 0
        );
        tablaProductos = new JTable(modeloProductos);

        // Renderer/editor de botón en "Acciones"
        tablaProductos.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        tablaProductos.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox()));

        panelProd.add(new JScrollPane(tablaProductos), BorderLayout.CENTER);
        JPanel accionesProd = new JPanel(new GridLayout(3,1,10,10));
        JButton btnAddP = new JButton("Agregar");
        JButton btnModP = new JButton("Modificar");
        JButton btnDelP = new JButton("Eliminar");
        accionesProd.add(btnAddP); accionesProd.add(btnModP); accionesProd.add(btnDelP);
        panelProd.add(accionesProd, BorderLayout.EAST);

        btnAddP.addActionListener(e -> { new AgregarProducto().setVisible(true); dispose(); });
        btnModP.addActionListener(e -> { new ModificarProducto().setVisible(true); dispose(); });
        btnDelP.addActionListener(e -> { new EliminarProducto().setVisible(true); dispose(); });

        tabs.add("Productos", panelProd);

        // ---- Reportes ----
        JPanel panelRep = new JPanel(new BorderLayout(10,10));
        JLabel lbl = new JLabel("Dé clic en el botón para generar los reportes (PDF en Escritorio)", SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JButton btnReportes = new JButton("Generar PDFs de Vendedores y Productos");
        btnReportes.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panelRep.add(lbl, BorderLayout.NORTH);
        panelRep.add(btnReportes, BorderLayout.CENTER);
        btnReportes.addActionListener(e -> generarReportes());
        tabs.add("Reportes", panelRep);

        add(tabs, BorderLayout.CENTER);

        // Cargar datos iniciales
        cargarTablas();
    }

    private void cargarTablas() {
        // ----------- VENDEDORES -----------
        modeloVendedores.setRowCount(0);
        String[] c = GestorVendedores.getCodigos();
        String[] n = GestorVendedores.getNombres();
        String[] g = GestorVendedores.getGeneros();
        int[] v = GestorVendedores.getVentasConfirmadas();

        for (int i = 0; i < GestorVendedores.getTotal(); i++) {
            modeloVendedores.addRow(new Object[]{ c[i], n[i], g[i], v[i] });
        }

        // ----------- PRODUCTOS -----------
        modeloProductos.setRowCount(0);
        String[] pc = GestorProductos.getCodigos();
        String[] pn = GestorProductos.getNombres();
        String[] cat = GestorProductos.getCategorias();
        String[] att = GestorProductos.getAtributoUnico();
        double[] pre = GestorProductos.getPrecios();

        for (int i = 0; i < GestorProductos.getTotal(); i++) {
            modeloProductos.addRow(new Object[]{ pc[i], pn[i], cat[i], att[i], pre[i] });
        }
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b) cargarTablas();
    }

    private void generarReportes() {
        try {
            File v = GuardarPDF.generarPDFVendedoresEnEscritorio();
            File p = GuardarPDF.generarPDFProductosEnEscritorio();
            JOptionPane.showMessageDialog(this,
                    "PDFs generados:\n" + v.getAbsolutePath() + "\n" + p.getAbsolutePath(),
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al generar PDFs: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ---- Render/editor botón en tabla de productos ----
    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() { setOpaque(true); }
        @Override public Component getTableCellRendererComponent(JTable t, Object v, boolean isSel, boolean hasFocus, int row, int col) {
            setText("Ver");
            return this;
        }
    }

    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private int row;
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("Ver");
            button.addActionListener(e -> mostrarAccion(row));
        }
        @Override public Component getTableCellEditorComponent(JTable t, Object value, boolean isSelected, int row, int column) {
            this.row = row; button.setText("Ver"); return button;
        }
        @Override public Object getCellEditorValue() { return "Ver"; }
    }

    private void mostrarAccion(int fila) {
        String codigo = (String) modeloProductos.getValueAt(fila, 0);
        int idx = GestorProductos.buscarIndicePorCodigo(codigo);
        if (idx == -1) return;
        String categoria = GestorProductos.getCategorias()[idx];
        String atr = GestorProductos.getAtributoUnico()[idx];
        String msg;
        switch (categoria) {
            case "Alimentos":  msg = "Fecha de caducidad: " + atr; break;
            case "Tecnologia": msg = "Garantía: " + atr; break;
            default:           msg = "Material: " + atr; break;
        }
        JOptionPane.showMessageDialog(this, "Producto " + codigo + "\n" + msg);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.sistemainventario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import com.mycompany.sistemainventario.PuntoVentaDA0.Producto;
import com.mycompany.sistemainventario.PuntoVentaDA0.ItemVenta;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author franc
 */
public class FrmPuntoVenta extends javax.swing.JFrame {

    private final PuntoVentaDA0 dao = new PuntoVentaDA0();

    private final List<ItemVenta> carrito = new ArrayList<>();

    private Producto productoSeleccionado;

    private DefaultTableModel modeloCarrito;

    private int idUsuarioActual = 0;
    private final DefaultListModel<Producto> modeloSugerencias =
        new DefaultListModel<>();

    private final JList<Producto> listaSugerencias =
        new JList<>(modeloSugerencias);

    private final JPopupMenu popupProductos = new JPopupMenu();

    private Timer temporizadorBusqueda;

    private int versionBusqueda = 0;

    private boolean actualizandoBuscador = false;
    
    /**
     * Creates new form FrmPuntoVenta
     */
    public FrmPuntoVenta() {
        initComponents();
        configurarPuntoVenta();
        this.setExtendedState(FrmPuntoVenta.MAXIMIZED_BOTH);
}

    public FrmPuntoVenta(int idUsuario) {
    this();
    this.idUsuarioActual = idUsuario;
}
    
    private void configurarPuntoVenta() {

    modeloCarrito = crearModelo(
            "ID",
            "SKU",
            "Modelo",
            "Talla",
            "Cantidad",
            "Precio unitario",
            "Subtotal"
    );

    tblProductos.setModel(modeloCarrito);

    txtBuscarP.setText("");


    txtBuscarP.setToolTipText(
            "Escribe SKU, modelo o ID y selecciona una coincidencia.");

    txtPrecioU.setEditable(false);
    txtStock.setEditable(false);

    txtPrecioU.setText("");
    txtStock.setText("");

    spnCantidad.setModel(
            new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));

    lblTotal.setText("$0.00");

    btnAñadir.setEnabled(false);

    configurarBusquedaAutomatica();

    btnAñadir.addActionListener(e -> agregarArticulo());
    btnCancelar.addActionListener(e -> cancelarVenta());
    btnRegresar.addActionListener(e -> regresar());

    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

    addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
            regresar();
        }
    });

    setLocationRelativeTo(null);

}

private DefaultTableModel crearModelo(String... columnas) {

    return new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };
}

private String dinero(BigDecimal cantidad) {

    return cantidad
            .setScale(2, RoundingMode.HALF_UP)
            .toPlainString();
}

private BigDecimal calcularTotal() {

    BigDecimal total = BigDecimal.ZERO;

    for (ItemVenta item : carrito) {
        total = total.add(item.subtotal());
    }

    return total;
}


private void limpiarSeleccionProducto() {

    productoSeleccionado = null;

    txtPrecioU.setText("");
    txtStock.setText("");

    spnCantidad.setValue(1);
    btnAñadir.setEnabled(false);
}

private void configurarBusquedaAutomatica() {

    listaSugerencias.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION);

    listaSugerencias.setVisibleRowCount(6);

    listaSugerencias.setFocusable(false);
    popupProductos.setFocusable(false);

    JScrollPane scroll = new JScrollPane(listaSugerencias);
    scroll.setFocusable(false);

    popupProductos.setLayout(new java.awt.BorderLayout());
    popupProductos.add(scroll, java.awt.BorderLayout.CENTER);

    temporizadorBusqueda = new Timer(
            300, e -> consultarSugerencias());

    temporizadorBusqueda.setRepeats(false);

    txtBuscarP.getDocument().addDocumentListener(
            new DocumentListener() {

        @Override
        public void insertUpdate(DocumentEvent e) {
            cambioEnBuscador();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            cambioEnBuscador();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            cambioEnBuscador();
        }
    });

    listaSugerencias.addMouseListener(
            new java.awt.event.MouseAdapter() {

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {

            int indice = listaSugerencias.locationToIndex(e.getPoint());

            if (indice < 0) {
                return;
            }

            java.awt.Rectangle limites =
                    listaSugerencias.getCellBounds(indice, indice);

            if (limites != null && limites.contains(e.getPoint())) {
                seleccionarSugerencia(
                        modeloSugerencias.getElementAt(indice));
            }
        }
    });

    txtBuscarP.addActionListener(e -> {
        if (popupProductos.isVisible()
                && !modeloSugerencias.isEmpty()) {

            int indice = listaSugerencias.getSelectedIndex();

            if (indice < 0) {
                indice = 0;
            }

            seleccionarSugerencia(
                    modeloSugerencias.getElementAt(indice));
        }
    });

    txtBuscarP.addKeyListener(new java.awt.event.KeyAdapter() {

        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {

            if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                popupProductos.setVisible(false);
                return;
            }

            if (!popupProductos.isVisible()
                    || modeloSugerencias.isEmpty()) {
                return;
            }

            int indice = listaSugerencias.getSelectedIndex();

            if (e.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN) {
                indice = Math.min(
                        indice + 1, modeloSugerencias.size() - 1);

            } else if (e.getKeyCode()
                    == java.awt.event.KeyEvent.VK_UP) {

                indice = Math.max(indice - 1, 0);

            } else {
                return;
            }

            listaSugerencias.setSelectedIndex(indice);
            listaSugerencias.ensureIndexIsVisible(indice);
            e.consume();
        }
    });
}

private void cambioEnBuscador() {

    if (actualizandoBuscador) {
        return;
    }

    versionBusqueda++;

    temporizadorBusqueda.stop();

    popupProductos.setVisible(false);
    modeloSugerencias.clear();

    limpiarSeleccionProducto();

    String texto = txtBuscarP.getText().trim();

    if (!texto.isEmpty()) {
        temporizadorBusqueda.restart();
    }
}

private void consultarSugerencias() {

    final String texto = txtBuscarP.getText().trim();
    final int version = versionBusqueda;

    if (texto.isEmpty()) {
        return;
    }

    new SwingWorker<List<Producto>, Void>() {

        @Override
        protected List<Producto> doInBackground() throws Exception {
            return dao.buscarProductos(texto);
        }

        @Override
        protected void done() {

            if (version != versionBusqueda
                    || !isDisplayable()
                    || !txtBuscarP.isFocusOwner()) {
                return;
            }

            try {
                List<Producto> productos = get();

                modeloSugerencias.clear();

                for (Producto producto : productos) {
                    modeloSugerencias.addElement(producto);
                }

                if (modeloSugerencias.isEmpty()) {
                    popupProductos.setVisible(false);

                    txtBuscarP.setToolTipText(
                            "No se encontraron coincidencias.");
                    return;
                }

                txtBuscarP.setToolTipText(
                        "Selecciona un producto de la lista.");

                listaSugerencias.setSelectedIndex(0);

                int ancho = Math.max(txtBuscarP.getWidth(), 550);

                popupProductos.setPopupSize(ancho, 180);

                popupProductos.show(
                        txtBuscarP,
                        0,
                        txtBuscarP.getHeight()
                );

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();

            } catch (java.util.concurrent.ExecutionException ex) {
                popupProductos.setVisible(false);

                Throwable causa = ex.getCause();

                JOptionPane.showMessageDialog(
                        FrmPuntoVenta.this,
                        "No se pudieron buscar los productos:\n"
                        + (causa == null
                                ? ex.getMessage()
                                : causa.getMessage()),
                        "Búsqueda",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }.execute();
}

private void seleccionarSugerencia(Producto producto) {

    if (producto == null) {
        return;
    }

    versionBusqueda++;
    temporizadorBusqueda.stop();

    popupProductos.setVisible(false);

    actualizandoBuscador = true;

    try {
        txtBuscarP.setText(producto.sku);
    } finally {
        actualizandoBuscador = false;
    }

    modeloSugerencias.clear();

    productoSeleccionado = producto;

    txtPrecioU.setText(dinero(producto.precio));
    txtStock.setText(String.valueOf(producto.stock));

    spnCantidad.setValue(1);

    btnAñadir.setEnabled(producto.stock > 0);

    if (producto.stock <= 0) {
        JOptionPane.showMessageDialog(
                this,
                "El producto no tiene existencias."
        );
    }
}

private void agregarArticulo() {

    if (productoSeleccionado == null) {
        JOptionPane.showMessageDialog(
                this,
                "Primero busca y selecciona un producto."
        );
        return;
    }

    try {
        spnCantidad.commitEdit();

    } catch (java.text.ParseException ex) {
        JOptionPane.showMessageDialog(
                this,
                "Escribe una cantidad entera válida."
        );
        return;
    }

    int cantidad = ((Number) spnCantidad.getValue()).intValue();

    if (cantidad <= 0) {
        JOptionPane.showMessageDialog(
                this,
                "La cantidad debe ser mayor que cero."
        );
        return;
    }

    ItemVenta existente = null;

    for (ItemVenta item : carrito) {
        if (item.producto.id == productoSeleccionado.id) {
            existente = item;
            break;
        }
    }

    long cantidadTotal = cantidad;

    if (existente != null) {
        cantidadTotal += existente.cantidad;

        if (existente.producto.precio.compareTo(
                productoSeleccionado.precio) != 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "El precio cambió desde que añadiste el producto.\n"
                    + "Cancela la venta y vuelve a añadir los artículos."
            );
            return;
        }
    }

    if (cantidadTotal > productoSeleccionado.stock) {
        JOptionPane.showMessageDialog(
                this,
                "Existencias disponibles: " + productoSeleccionado.stock
                + "\nCantidad total solicitada: " + cantidadTotal
        );
        return;
    }

    if (existente == null) {
        carrito.add(
                new ItemVenta(productoSeleccionado, cantidad)
        );

    } else {
        existente.cantidad = (int) cantidadTotal;
    }

    actualizarCarrito();
    limpiarSeleccionProducto();

    txtBuscarP.setText("");
    txtBuscarP.requestFocusInWindow();
}

private void actualizarCarrito() {

    modeloCarrito.setRowCount(0);

    for (ItemVenta item : carrito) {
        modeloCarrito.addRow(new Object[]{
            item.producto.id,
            item.producto.sku,
            item.producto.modelo,
            item.producto.talla,
            item.cantidad,
            dinero(item.producto.precio),
            dinero(item.subtotal())
        });
    }

    lblTotal.setText("$" + dinero(calcularTotal()));
}

private void limpiarVentaActual() {

    carrito.clear();

    actualizarCarrito();
    limpiarSeleccionProducto();

    txtBuscarP.setText("");
}

private void cancelarVenta() {

    if (!carrito.isEmpty()) {
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Deseas descartar la venta que estás preparando?",
                "Cancelar venta actual",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }
    }

    limpiarVentaActual();
}

private void confirmarVenta() {

    if (!Sesion.puedeVentas()
            || Sesion.getIdUsuario() != idUsuarioActual) {

        JOptionPane.showMessageDialog(
                this,
                "No tienes una sesión autorizada para vender.");
        return;
    }

    if (carrito.isEmpty()) {
        JOptionPane.showMessageDialog(
                this,
                "Añade al menos un artículo.");
        return;
    }

    BigDecimal total = calcularTotal();

    btnConfirmar.setEnabled(false);

    try {
        DlgCobrar dialogo = new DlgCobrar(
                this,
                total,
                recibido -> dao.registrarVenta(
                        idUsuarioActual,
                        carrito,
                        recibido
                )
        );

        dialogo.setVisible(true);

        // Cancelar o cerrar el diálogo conserva el carrito.
        if (!dialogo.fueCobrado()) {
            return;
        }

        int folio = dialogo.getIdVentaRegistrada();

        BigDecimal recibido = dialogo.getDineroRecibido();
        BigDecimal cambio = dialogo.getCambioEntregado();

        limpiarVentaActual();

        JOptionPane.showMessageDialog(
                this,
                "Venta registrada correctamente."
                + "\nFolio: " + folio
                + "\nTotal: $" + dinero(total)
                + "\nRecibido: $" + dinero(recibido)
                + "\nCambio a entregar: $" + dinero(cambio),
                "Venta completada",
                JOptionPane.INFORMATION_MESSAGE
        );

    } finally {
        btnConfirmar.setEnabled(true);
    }
}

private void regresar() {

    if (!carrito.isEmpty()) {
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "Hay una venta sin guardar.\n"
                + "¿Deseas salir y descartar sus artículos?",
                "Salir del punto de venta",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }
    }

    dispose();
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtBuscarP = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        spnCantidad = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        txtPrecioU = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtStock = new javax.swing.JTextField();
        btnAñadir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel1.setText("Punto de Venta");

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Seleccionar Producto ");

        txtBuscarP.setText("Buscar...");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Cantidad");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Precio Unitario");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Stock disponibles");

        btnAñadir.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\add.png")); // NOI18N
        btnAñadir.setText("Añadir articulo");

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblProductos);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Total ($)");

        lblTotal.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotal.setText("$0.00");

        btnCancelar.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\shopping.png")); // NOI18N
        btnCancelar.setText("Cancelar venta");

        btnConfirmar.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\shopping-bag.png")); // NOI18N
        btnConfirmar.setText("Confirmar Venta");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnRegresar.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\regresar.png")); // NOI18N
        btnRegresar.setText("Regresar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtBuscarP, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPrecioU, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAñadir)))
                        .addGap(486, 486, 486))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(927, 927, 927)
                        .addComponent(btnRegresar)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(286, 286, 286))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnConfirmar)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addGap(335, 335, 335))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(307, 307, 307)))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(46, 46, 46)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBuscarP, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAñadir))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegresar)
                    .addComponent(btnCancelar)
                    .addComponent(btnConfirmar))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(1503, 1503, 1503))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1446, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1452, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        // TODO add your handling code here:
        confirmarVenta();
    }//GEN-LAST:event_btnConfirmarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmPuntoVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPuntoVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPuntoVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPuntoVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPuntoVenta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAñadir;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JSpinner spnCantidad;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtBuscarP;
    private javax.swing.JTextField txtPrecioU;
    private javax.swing.JTextField txtStock;
    // End of variables declaration//GEN-END:variables
}

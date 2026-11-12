package com.mycompany.sistemainventario;

import java.awt.Color;
import javax.swing.DefaultListModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
/**
 * 
 *
 * @author Alexa
 */
public class FrmDashboard extends javax.swing.JFrame {
    
    private int idUsuario;
    private String nombreUsuario;
    private String loginUsuario;
    private String rolUsuario;
public FrmDashboard(int idUsuario, String nombre, String login, String rol) {

    initComponents();

    this.idUsuario = idUsuario;
    this.nombreUsuario = nombre;
    this.loginUsuario = login;
    this.rolUsuario = rol;

    setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);

    aplicarPermisos();
    actualizarDashboard();
}
    
    private void aplicarPermisos() {
        boolean inventario = Sesion.puedeInventario();
        boolean ventas = Sesion.puedeVentas();

        btnProductos.setEnabled(inventario);
        btnCategorias.setEnabled(inventario);
        btnProveedores.setEnabled(inventario);

        btnEntradaMer.setEnabled(inventario);
        btnSalidaMer.setEnabled(inventario);
        btnPagoProveedor.setEnabled(inventario);

        btnPuntoVenta.setEnabled(ventas);
        btnPuntoVentaH.setEnabled(ventas);

        btnEmpleados.setEnabled(Sesion.puedeEmpleados());

        btnEmpleados.setToolTipText(
            Sesion.puedeEmpleados()
                    ? null
                    : "Solo el administrador puede gestionar empleados."
    );

        btnReportes.setEnabled(inventario || ventas);

        btnPerfil.setEnabled(Sesion.activa());
        btnCerrar.setEnabled(true);

        boolean mostrarResumen = Sesion.activa();

        jPanel3.setVisible(mostrarResumen);
        jPanel4.setVisible(mostrarResumen);
        jPanel5.setVisible(mostrarResumen);
        jPanel7.setVisible(mostrarResumen);
        jPanel8.setVisible(mostrarResumen);

        
        btnPuntoVenta.setToolTipText(
        Sesion.puedeVentas()
                ? null
                : "Solo el administrador y el cajero pueden acceder "
                  + "al punto de venta."
        );

        btnPuntoVentaH.setToolTipText(
        Sesion.puedeVentas()
                ? null
                : "Solo el administrador y el cajero pueden consultar "
                  + "el historial de ventas."
        );
    revalidate();
    repaint();
}
    
    
public void cargarActividad() {

DefaultListModel<String> modelo = new DefaultListModel<>();

    String sql = "SELECT Descripcion, FechaHora " + "FROM actividad_reciente " + "ORDER BY FechaHora DESC LIMIT 20";

    Conexion cc = new Conexion();
    try (Connection cn = cc.getConnection();
         PreparedStatement ps = cn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            
            String descripcion = rs.getString("Descripcion");
            String fecha = rs.getString("FechaHora");

            modelo.addElement(descripcion + "   |   " + fecha);
        }
        ListActividad.setModel(modelo);
    } catch (SQLException e) {
        System.out.println("Error al cargar actividad: " + e.getMessage());
    }
}

public void totalStockBajo(){
try {
        Conexion cc = new Conexion();
        Connection cn = cc.getConnection(); 

        String sql = "SELECT COUNT(*) AS total FROM producto WHERE Existencias <=Stock_Minimo AND Activo = 1";
        PreparedStatement ps = cn.prepareStatement(sql); 
        ResultSet rs = ps.executeQuery(); 

        if (rs.next()) {
            int total = rs.getInt("total"); 

            lblStock.setText(String.valueOf(total));

            if (total > 0) {
                lblStock.setForeground(java.awt.Color.RED);
            } else {
                lblStock.setForeground(java.awt.Color.GREEN);
            }
        }

    } catch (Exception e) {
        System.out.println("Error: " + e);
    }
}

public void cargarMovimientos(){
   DefaultTableModel modelo = new DefaultTableModel();
   
   modelo.addColumn("Tipo");
   modelo.addColumn("Fecha");
   modelo.addColumn("SKU");
   modelo.addColumn("Modelo");
   modelo.addColumn("Cantidad");
   modelo.addColumn("Usuario");
   modelo.addColumn("Motivo");
   
   String sql = "SELECT Tipo_Movimiento, Fecha, SKU, Modelo, Cantidad, Usuario, Motivo FROM historialmovimientos ORDER BY Fecha DESC";
   
        Conexion cc = new Conexion();
        
        try (Connection cn = cc.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
            
            Object []fila = new Object[7];
            
            fila[0] = rs.getString("Tipo_Movimiento");
            fila[1] = rs.getString("Fecha");
            fila[2] = rs.getString("SKU");
            fila[3] = rs.getString("Modelo");
            fila[4] = rs.getInt("Cantidad");
            fila[5] = rs.getString("Usuario");
            fila[6] = rs.getString("Motivo");
            
            modelo.addRow(fila);
        }
            
            TblMovimientos.setModel(modelo);
   } catch (SQLException e){
       System.out.println("Error al cargar movimientos" + e.getMessage());
   }
}
public void totalProducto(){
    try{
        Conexion cc = new Conexion();
        Connection cn = cc.getConnection();
        String sql = "SELECT COUNT(*) AS total FROM producto WHERE Activo = 1";
        PreparedStatement ps = cn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            int total = rs.getInt("total");
            lblProductos.setText(String.valueOf(total));
        }
        rs.close();
        ps.close();
        cn.close();
        }catch(Exception e){
                System.out.println("Error al contar productos: " + e);                
    }
}
public void actualizarDashboard() {
    if (!Sesion.activa()) {
        return;
    }

    cargarActividad();
    cargarMovimientos();
    totalStockBajo();
    mostrarEntradasTotales();
    mostrarSalidasTotales();
    totalProducto();
}

private boolean comprobarAcceso(boolean permitido) {

    if (!permitido) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Tu usuario no tiene permiso para acceder a esta opción.",
                "Acceso restringido",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        return false;
    }

    return true;
}
    /**
     * Creates new form FrmDashboard
     */
    public FrmDashboard() {
        initComponents();
        aplicarPermisos();
        actualizarDashboard();
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblProductos = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lblEntrada = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblSalidas = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblStock = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ListActividad = new javax.swing.JList<>();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TblMovimientos = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnPagoProveedor = new javax.swing.JButton();
        btnSalidaMer = new javax.swing.JButton();
        btnProveedores = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnCerrar = new javax.swing.JButton();
        btnEntradaMer = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        btnCategorias = new javax.swing.JButton();
        btnEmpleados = new javax.swing.JButton();
        btnPerfil = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        btnPuntoVenta = new javax.swing.JButton();
        btnPuntoVentaH = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(234, 234, 234));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Dashboard");

        jLabel3.setText("Resumen general del sistema");

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setText("Total de Productos");

        lblProductos.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addGap(0, 51, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setText("Entradas ");

        lblEntrada.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addGap(0, 102, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setText("Salidas ");

        lblSalidas.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSalidas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addGap(0, 109, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSalidas, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setText("Stock Bajo");

        lblStock.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStock, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStock, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addGap(28, 28, 28))
        );

        jPanel8.setBackground(new java.awt.Color(234, 234, 234));

        jLabel16.setText("Actividad Reciente");

        jScrollPane1.setBorder(null);

        ListActividad.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane1.setViewportView(ListActividad);

        jLabel21.setText("Historial de Movimientos");

        TblMovimientos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Tipo", "Fecha", "SKU", "Modelo", "Usuario", "Motivo"
            }
        ));
        jScrollPane3.setViewportView(TblMovimientos);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE))
                .addGap(52, 52, 52)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 667, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(136, 136, 136)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(164, 164, 164)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel17)
                        .addGap(43, 43, 43))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(7, 7, 7)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(15, 23, 42));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(250, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(250, 453));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Sistema de Inventario");

        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\dashboard.png")); // NOI18N
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setFocusPainted(false);
        jButton1.setLabel("Dashboard");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnProductos.setForeground(new java.awt.Color(255, 255, 255));
        btnProductos.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\productos.png")); // NOI18N
        btnProductos.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnProductos.setBorderPainted(false);
        btnProductos.setContentAreaFilled(false);
        btnProductos.setFocusPainted(false);
        btnProductos.setLabel("Productos");
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });

        btnPagoProveedor.setForeground(new java.awt.Color(255, 255, 255));
        btnPagoProveedor.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\pagoproveedor.png")); // NOI18N
        btnPagoProveedor.setText("Pagos a Proveedor");
        btnPagoProveedor.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPagoProveedor.setBorderPainted(false);
        btnPagoProveedor.setContentAreaFilled(false);
        btnPagoProveedor.setFocusPainted(false);
        btnPagoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagoProveedorActionPerformed(evt);
            }
        });

        btnSalidaMer.setForeground(new java.awt.Color(255, 255, 255));
        btnSalidaMer.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\salida.png")); // NOI18N
        btnSalidaMer.setText("Salidas de Mercancia");
        btnSalidaMer.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSalidaMer.setBorderPainted(false);
        btnSalidaMer.setContentAreaFilled(false);
        btnSalidaMer.setFocusPainted(false);
        btnSalidaMer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalidaMerActionPerformed(evt);
            }
        });

        btnProveedores.setForeground(new java.awt.Color(255, 255, 255));
        btnProveedores.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\proveedor.png")); // NOI18N
        btnProveedores.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnProveedores.setBorderPainted(false);
        btnProveedores.setContentAreaFilled(false);
        btnProveedores.setFocusPainted(false);
        btnProveedores.setLabel("Proveedores ");
        btnProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedoresActionPerformed(evt);
            }
        });

        btnCerrar.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrar.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\cerrar sesion.png")); // NOI18N
        btnCerrar.setText("Cerrar Sesión");
        btnCerrar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCerrar.setBorderPainted(false);
        btnCerrar.setContentAreaFilled(false);
        btnCerrar.setFocusPainted(false);
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnEntradaMer.setForeground(new java.awt.Color(255, 255, 255));
        btnEntradaMer.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\entrada.png")); // NOI18N
        btnEntradaMer.setText("Entradas de Mercancia");
        btnEntradaMer.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEntradaMer.setBorderPainted(false);
        btnEntradaMer.setContentAreaFilled(false);
        btnEntradaMer.setFocusPainted(false);
        btnEntradaMer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntradaMerActionPerformed(evt);
            }
        });

        btnReportes.setForeground(new java.awt.Color(255, 255, 255));
        btnReportes.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\reporte.png")); // NOI18N
        btnReportes.setText("Reportes");
        btnReportes.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnReportes.setBorderPainted(false);
        btnReportes.setContentAreaFilled(false);
        btnReportes.setFocusPainted(false);
        btnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportesActionPerformed(evt);
            }
        });

        btnCategorias.setForeground(new java.awt.Color(255, 255, 255));
        btnCategorias.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\categoria.png")); // NOI18N
        btnCategorias.setText("Categorias ");
        btnCategorias.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCategorias.setBorderPainted(false);
        btnCategorias.setContentAreaFilled(false);
        btnCategorias.setFocusPainted(false);
        btnCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoriasActionPerformed(evt);
            }
        });

        btnEmpleados.setForeground(new java.awt.Color(255, 255, 255));
        btnEmpleados.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\empleados.png")); // NOI18N
        btnEmpleados.setText("Empleados");
        btnEmpleados.setAutoscrolls(true);
        btnEmpleados.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEmpleados.setBorderPainted(false);
        btnEmpleados.setContentAreaFilled(false);
        btnEmpleados.setFocusPainted(false);
        btnEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpleadosActionPerformed(evt);
            }
        });

        btnPerfil.setForeground(new java.awt.Color(255, 255, 255));
        btnPerfil.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\perfil.png")); // NOI18N
        btnPerfil.setText("Mi perfil");
        btnPerfil.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPerfil.setBorderPainted(false);
        btnPerfil.setContentAreaFilled(false);
        btnPerfil.setFocusPainted(false);
        btnPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerfilActionPerformed(evt);
            }
        });

        btnPuntoVenta.setForeground(new java.awt.Color(255, 255, 255));
        btnPuntoVenta.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\point-of-sale.png")); // NOI18N
        btnPuntoVenta.setText("Punto de venta");
        btnPuntoVenta.setToolTipText("");
        btnPuntoVenta.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPuntoVenta.setBorderPainted(false);
        btnPuntoVenta.setContentAreaFilled(false);
        btnPuntoVenta.setFocusPainted(false);
        btnPuntoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPuntoVentaActionPerformed(evt);
            }
        });

        btnPuntoVentaH.setForeground(new java.awt.Color(255, 255, 255));
        btnPuntoVentaH.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\seo-report.png")); // NOI18N
        btnPuntoVentaH.setText("Historial de Venta");
        btnPuntoVentaH.setToolTipText("");
        btnPuntoVentaH.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPuntoVentaH.setBorderPainted(false);
        btnPuntoVentaH.setContentAreaFilled(false);
        btnPuntoVentaH.setFocusPainted(false);
        btnPuntoVentaH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPuntoVentaHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel1))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnProductos)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator5)
                            .addComponent(jSeparator4)
                            .addComponent(jSeparator3)
                            .addComponent(jSeparator1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnCerrar)
                                    .addComponent(jButton1)
                                    .addComponent(btnEntradaMer)
                                    .addComponent(btnPerfil)
                                    .addComponent(btnSalidaMer)
                                    .addComponent(btnPagoProveedor)
                                    .addComponent(btnCategorias)
                                    .addComponent(btnProveedores)
                                    .addComponent(btnEmpleados)
                                    .addComponent(btnReportes)
                                    .addComponent(btnPuntoVenta)
                                    .addComponent(btnPuntoVentaH))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(9, 9, 9)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEntradaMer)
                .addGap(18, 18, 18)
                .addComponent(btnSalidaMer)
                .addGap(18, 18, 18)
                .addComponent(btnPagoProveedor)
                .addGap(12, 12, 12)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnProductos)
                .addGap(18, 18, 18)
                .addComponent(btnCategorias)
                .addGap(18, 18, 18)
                .addComponent(btnProveedores)
                .addGap(18, 18, 18)
                .addComponent(btnEmpleados)
                .addGap(18, 18, 18)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnReportes)
                .addGap(18, 18, 18)
                .addComponent(btnPuntoVenta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPuntoVentaH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPerfil)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCerrar)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1231, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        // TODO add your handling code here:
        if (!comprobarAcceso(Sesion.puedeInventario())) {
        return;
    }
        FrmProductos productos = new FrmProductos();
        productos.addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosed(java.awt.event.WindowEvent e) {
        actualizarDashboard();
    }
});
        productos.setVisible(true);
    }//GEN-LAST:event_btnProductosActionPerformed

    private void btnPagoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagoProveedorActionPerformed
        // TODO add your handling code here:
        if (!comprobarAcceso(Sesion.puedeInventario())) {
        return;
    }
        PagoProveedores pagosprov = new PagoProveedores(idUsuario, rolUsuario);
        pagosprov.addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosed(java.awt.event.WindowEvent e) {
        actualizarDashboard();
         }
      });
        pagosprov.setVisible(true);
    }//GEN-LAST:event_btnPagoProveedorActionPerformed

    private void btnSalidaMerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalidaMerActionPerformed
        // TODO add your handling code here:
        if (!comprobarAcceso(Sesion.puedeInventario())) {
        return;
    }
        SalidaMercancia salida = new SalidaMercancia(idUsuario, nombreUsuario);
        salida.addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosed(java.awt.event.WindowEvent e) {
        actualizarDashboard();
    }
});
        salida.setVisible(true);
    }//GEN-LAST:event_btnSalidaMerActionPerformed

    private void btnProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedoresActionPerformed
        // TODO add your handling code here:
        if (!comprobarAcceso(Sesion.puedeInventario())) {
        return;
    }
        FrmProveedores proveedores = new FrmProveedores();
        proveedores.addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosed(java.awt.event.WindowEvent e) {
        actualizarDashboard();
    }
});
        proveedores.setVisible(true);
    }//GEN-LAST:event_btnProveedoresActionPerformed

    private void btnEntradaMerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntradaMerActionPerformed
        // TODO add your handling code here:
      if (!comprobarAcceso(Sesion.puedeInventario())) {
        return;
    }
      EntradaMercancia entrada = new EntradaMercancia(idUsuario, nombreUsuario);
      entrada.addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosed(java.awt.event.WindowEvent e) {
        actualizarDashboard();
    }
});
        entrada.setVisible(true);          
    }//GEN-LAST:event_btnEntradaMerActionPerformed

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        // TODO add your handling code here:
        FrmReportes reportes = new FrmReportes();
        reportes.addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosed(java.awt.event.WindowEvent e) {
        actualizarDashboard();
    }
});
        reportes.setVisible(true);
    }//GEN-LAST:event_btnReportesActionPerformed

    private void btnCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoriasActionPerformed
        // TODO add your handling code here:
        if (!comprobarAcceso(Sesion.puedeInventario())) {
        return;
    }
        FrmCategorias cate = new FrmCategorias();
        cate.addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosed(java.awt.event.WindowEvent e) {
        actualizarDashboard();
    }
});
        cate.setVisible(true);
    }//GEN-LAST:event_btnCategoriasActionPerformed

    private void btnEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpleadosActionPerformed
        // TODO add your handling code here:
        if (!comprobarAcceso(Sesion.puedeEmpleados())) {
        return;
    }
        FrmEmpleados empleados = new FrmEmpleados();
        empleados.addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosed(java.awt.event.WindowEvent e) {
        actualizarDashboard();
    }
});
        empleados.setVisible(true);
    }//GEN-LAST:event_btnEmpleadosActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        for (java.awt.Window ventana : java.awt.Window.getWindows()) {
        if (ventana != this) {
            ventana.dispose();
        }
    }

        Sesion.cerrar();

        new FrmLogin().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerfilActionPerformed
        // TODO add your handling code here:
        FrmPerfil perfil = new FrmPerfil (idUsuario);
        perfil.addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosed(java.awt.event.WindowEvent e) {
        actualizarDashboard();
    }
});
        perfil.setVisible(true);
    }//GEN-LAST:event_btnPerfilActionPerformed

    private void btnPuntoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPuntoVentaActionPerformed
        // TODO add your handling code here:
        if (!comprobarAcceso(Sesion.puedeVentas())) {
        return;
    }
        FrmPuntoVenta pv = new FrmPuntoVenta(idUsuario);
        pv.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosed(java.awt.event.WindowEvent e) {
            actualizarDashboard();
        }
    });

        pv.setLocationRelativeTo(this);
        pv.setVisible(true);
    }//GEN-LAST:event_btnPuntoVentaActionPerformed

    private void btnPuntoVentaHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPuntoVentaHActionPerformed
        // TODO add your handling code here:
        if (!comprobarAcceso(Sesion.puedeVentas())) {
        return;
    }
        FrmHistorialVentas hv = new FrmHistorialVentas(idUsuario);
        hv.addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosed(java.awt.event.WindowEvent e) {
            setVisible(true);
        }
    });
        hv.setLocationRelativeTo(null);
        hv.setVisible(true);
    }//GEN-LAST:event_btnPuntoVentaHActionPerformed

   public void mostrarEntradasTotales() {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    String sql = "SELECT SUM(Cantidad) AS total_ingresado FROM entradamercancia_producto";
    
    try {
        Conexion cn = new Conexion();
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        
        if (rs.next()) {
            int total = rs.getInt("total_ingresado");
            lblEntrada.setText(String.valueOf(total));
        } else {
            lblEntrada.setText("0");
        }
    } catch (Exception e) {
        System.err.println("Error en Dashboard (Entradas): " + e.getMessage());
    } finally {
        try { if(rs != null) rs.close(); if(ps != null) ps.close(); } catch(Exception e) {}
    }
}
        public void mostrarSalidasTotales() {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    String sql = "SELECT SUM(Cantidad) AS total_salidas FROM salidamercancia_producto";
    
    try {
        Conexion cn = new Conexion();
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        
        if (rs.next()) {
            int total = rs.getInt("total_salidas");
            lblSalidas.setText(String.valueOf(total));
        } else {
            lblSalidas.setText("0");
        }
    } catch (Exception e) {
        System.err.println("Error en Dashboard (Salidas): " + e.getMessage());
    } finally {
        try { if(rs != null) rs.close(); if(ps != null) ps.close(); } catch(Exception e) {}
    }
}
    
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
            java.util.logging.Logger.getLogger(FrmDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> ListActividad;
    private javax.swing.JTable TblMovimientos;
    private javax.swing.JButton btnCategorias;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEmpleados;
    private javax.swing.JButton btnEntradaMer;
    private javax.swing.JButton btnPagoProveedor;
    private javax.swing.JButton btnPerfil;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnProveedores;
    private javax.swing.JButton btnPuntoVenta;
    private javax.swing.JButton btnPuntoVentaH;
    private javax.swing.JButton btnReportes;
    private javax.swing.JButton btnSalidaMer;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lblEntrada;
    private javax.swing.JLabel lblProductos;
    private javax.swing.JLabel lblSalidas;
    private javax.swing.JLabel lblStock;
    // End of variables declaration//GEN-END:variables
}

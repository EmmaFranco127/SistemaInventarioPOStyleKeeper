package com.mycompany.sistemainventario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Alexa
 */
public class FrmProductos extends javax.swing.JFrame {

    
   public void mostrarDatos() {

         String sql = "SELECT p.Id_Producto, p.SKU, p.Modelo, p.Talla, " +
                 "p.Precio_Referencia, p.Existencias, p.Stock_Minimo, " +
                 "c.Nombre AS Categoria, pr.Nombre AS Proveedor " +
                 "FROM producto p " +
                 "INNER JOIN categoria c ON p.Id_Categoria = c.Id_Categoria " +
                 "INNER JOIN proveedor pr ON p.Id_Proveedor = pr.Id_Proveedor " +
                 "WHERE p.Activo = 1";
        
        Conexion cn = new Conexion();
        try (Connection con = cn.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {

        DefaultTableModel modelo = (DefaultTableModel) tblProductos.getModel();
        modelo.setRowCount(0); 

        while (rs.next()) {
            Object[] fila = new Object[9];
            fila[0] = rs.getInt("Id_Producto");
            fila[1] = rs.getString("SKU");
            fila[2] = rs.getString("Modelo");
            fila[3] = rs.getString("Talla");
            fila[4] = rs.getDouble("Precio_Referencia");
            fila[5] = rs.getInt("Existencias");
            fila[6] = rs.getInt("Stock_Minimo");
            fila[7] = rs.getString("Categoria");
            fila[8] = rs.getString("Proveedor");

            modelo.addRow(fila);
                tblProductos.getColumnModel().getColumn(0).setMinWidth(0);
                tblProductos.getColumnModel().getColumn(0).setMaxWidth(0);
                tblProductos.getColumnModel().getColumn(0).setPreferredWidth(0);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
    }
}
    public void buscarDatos(String valor) {

    String[] titulos = {"ID", "SKU", "Modelo", "Talla", "Precio", "Existencias", "Stock Minimo", "Categorí­a", "Proveedor"};
    String[] registros = new String[9];

    DefaultTableModel modelo = new DefaultTableModel(null, titulos);

    String sql = "SELECT p.Id_Producto, p.SKU, p.Modelo, p.Talla, " +
                 "p.Precio_Referencia, p.Existencias, p.Stock_Minimo, " +
                 "c.Nombre AS Categoria, pr.Nombre AS Proveedor " +
                 "FROM producto p " +
                 "INNER JOIN categoria c ON p.Id_Categoria = c.Id_Categoria " +
                 "INNER JOIN proveedor pr ON p.Id_Proveedor = pr.Id_Proveedor " +
                 "WHERE (p.SKU LIKE ? OR p.Modelo LIKE ? OR c.Nombre LIKE ? OR pr.Nombre LIKE ?) " +
                 "AND p.Activo = 1";

    Conexion cc = new Conexion();

    try (Connection cn = cc.getConnection();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        ps.setString(1, "%" + valor + "%");
        ps.setString(2, "%" + valor + "%");
        ps.setString(3, "%" + valor + "%");
        ps.setString(4, "%" + valor + "%");

        try (ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                registros[0] = rs.getString("Id_Producto");
                registros[1] = rs.getString("SKU");
                registros[2] = rs.getString("Modelo");
                registros[3] = rs.getString("Talla");
                registros[4] = rs.getString("Precio_Referencia");
                registros[5] = rs.getString("Existencias");
                registros[6] = rs.getString("Stock_Minimo");
                registros[7] = rs.getString("Categoria");
                registros[8] = rs.getString("Proveedor");

                modelo.addRow(registros);               
            }

            tblProductos.setModel(modelo);
                    tblProductos.getColumnModel().getColumn(0).setMinWidth(0);
                    tblProductos.getColumnModel().getColumn(0).setMaxWidth(0);
                    tblProductos.getColumnModel().getColumn(0).setPreferredWidth(0); 
        }

    } catch (SQLException e) {
        System.out.println("Error al filtrar: " + e.getMessage());
    }
}
    
    public void registrarActividad(String modulo, String accion, String descripcion) {

    String sql = "INSERT INTO actividad_reciente(Modulo, Accion, Descripcion, FechaHora) "
               + "VALUES (?, ?, ?, NOW())";

    Conexion cc = new Conexion();

    try (Connection cn = cc.getConnection();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        ps.setString(1, modulo);
        ps.setString(2, accion);
        ps.setString(3, descripcion);

        ps.executeUpdate();

    } catch (SQLException e) {
        System.out.println("Error al registrar actividad: " + e.getMessage());
    }
}
    /**
     * Creates new form FrmPrincipal
     */
    public FrmProductos() {
        initComponents();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        mostrarDatos();
        tblProductos.requestFocusInWindow();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel1.setText("Productos");

        jLabel2.setText("Catálogo de Productos");

        btnNuevo.setText("+ Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "SKU", "Modelo", "Talla", "Precio", "Existencias ", "Stock minimo ", "Categoria ", "Proveedor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblProductos);
        if (tblProductos.getColumnModel().getColumnCount() > 0) {
            tblProductos.getColumnModel().getColumn(0).setResizable(false);
            tblProductos.getColumnModel().getColumn(1).setResizable(false);
            tblProductos.getColumnModel().getColumn(2).setResizable(false);
            tblProductos.getColumnModel().getColumn(3).setResizable(false);
            tblProductos.getColumnModel().getColumn(4).setResizable(false);
            tblProductos.getColumnModel().getColumn(5).setResizable(false);
            tblProductos.getColumnModel().getColumn(6).setResizable(false);
            tblProductos.getColumnModel().getColumn(7).setResizable(false);
            tblProductos.getColumnModel().getColumn(8).setResizable(false);
        }

        btnRegresar.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\regresar.png")); // NOI18N
        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\editar.png")); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\eliminar.png")); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtBuscar.setText("Buscar...");
        txtBuscar.setBorder(null);
        txtBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBuscarFocusLost(evt);
            }
        });
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(164, 164, 164)
                                .addComponent(btnNuevo))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRegresar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditar)
                        .addGap(12, 12, 12)
                        .addComponent(btnEliminar)
                        .addGap(26, 26, 26))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegresar)
                    .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminar))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
 int fila = tblProductos.getSelectedRow();

    if (fila == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Debe seleccionar una fila de la tabla.");
        return;
    }

    int id = Integer.parseInt(tblProductos.getValueAt(fila, 0).toString());
    String nombre = tblProductos.getValueAt(fila, 2).toString();

    int confirmar = javax.swing.JOptionPane.showConfirmDialog(this, 
        "Estas¡ seguro de eliminar el producto " + nombre + "?", "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);

    if (confirmar == javax.swing.JOptionPane.YES_OPTION) {
        String sql = "UPDATE producto SET Activo = 0 WHERE Id_Producto = ?";
        Conexion cc = new Conexion();
        
        try (Connection cn = cc.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ps.executeUpdate();
            
            
            DefaultTableModel modeloTabla = (DefaultTableModel) tblProductos.getModel();
            modeloTabla.removeRow(fila); 
            
            registrarActividad("Productos", "Eliminar", "Se elimino el producto: " + nombre);
            
            javax.swing.JOptionPane.showMessageDialog(this, "Producto eliminado .");
            
            
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
    RegistrarProducto vRegistrar = new RegistrarProducto();
    vRegistrar.setVisible(true);
    vRegistrar.setLocationRelativeTo(null);
    
    vRegistrar.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosed(java.awt.event.WindowEvent e) {
            mostrarDatos();
        }
    });
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
 int fila = tblProductos.getSelectedRow();

    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Selecciona un producto para editar");
        return;
    }

    int id = Integer.parseInt(tblProductos.getValueAt(fila, 0).toString());
    String sku = tblProductos.getValueAt(fila, 1).toString();
    String modelo = tblProductos.getValueAt(fila, 2).toString();
    String talla = tblProductos.getValueAt(fila, 3).toString();
    double precio = Double.parseDouble(tblProductos.getValueAt(fila, 4).toString());
    int existencias = Integer.parseInt(tblProductos.getValueAt(fila, 5).toString());
    int stockMinimo = Integer.parseInt(tblProductos.getValueAt(fila, 6).toString());
    String categoria = tblProductos.getValueAt(fila, 7).toString();
    String proveedor = tblProductos.getValueAt(fila, 8).toString();

    RegistrarProducto form = new RegistrarProducto();
    form.Datos(id, sku, modelo, talla, precio, existencias, stockMinimo, categoria, proveedor);

    form.setVisible(true);
    form.setLocationRelativeTo(null);

    form.addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosing(java.awt.event.WindowEvent e) {
        mostrarDatos();
    }

    @Override
    public void windowClosed(java.awt.event.WindowEvent e) {
        mostrarDatos();
       }
     });
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        this.dispose( );
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        // TODO add your handling code here:
        if (txtBuscar.getText().equals("Buscar...")){
            txtBuscar.setText("");
        }
    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        // TODO add your handling code here:
        if (txtBuscar.getText().isEmpty()){
            txtBuscar.setText("Buscar...");
        }
    }//GEN-LAST:event_txtBuscarFocusLost

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:}
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // TODO add your handling code here:
        buscarDatos(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarKeyReleased

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
            java.util.logging.Logger.getLogger(FrmProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmProductos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}

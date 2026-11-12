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
public class PagoProveedores extends javax.swing.JFrame {
    private int idUsuario;
    private String rolUsuario;
    
    public void mostrarPagos(){
            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            
            try{
                Conexion cn = new Conexion();
                con = cn.getConnection();
                
        String sql = "SELECT p.Id_PagoProveedor, pr.Nombre, p.Fecha_Pago, " +
             "p.Monto, p.Concepto, p.Activo, u.Nombre AS Usuario " +
             "FROM pagoproveedor p " +
             "INNER JOIN proveedor pr " +
             "ON p.Id_Proveedor = pr.Id_Proveedor " +
             "INNER JOIN usuario u " +
             "ON p.Id_Usuario = u.Id_Usuario WHERE p.Activo = 1";         
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
            
                DefaultTableModel modelo = (DefaultTableModel) tblPagoP.getModel();
                modelo.setRowCount(0);
                
                while (rs.next()){
                    Object[] fila = new Object[6];
                fila[0] = rs.getInt("Id_PagoProveedor");
                fila[1] = rs.getString("Nombre");
                fila[2] = rs.getTimestamp("Fecha_Pago");              
                fila[3] = rs.getDouble("Monto");
                fila[4] = rs.getString("Concepto");
                fila[5] = rs.getString("Usuario");

                    modelo.addRow(fila);
                }
            
                tblPagoP.getColumnModel().getColumn(0).setMinWidth(0);
                tblPagoP.getColumnModel().getColumn(0).setMaxWidth(0);
                tblPagoP.getColumnModel().getColumn(0).setPreferredWidth(0);

                
            }catch (Exception e){
                JOptionPane.showMessageDialog(this, "Error al cargar los pagos: " + e.getMessage());
                
            }
            }
            public void calcularTotal(){
            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try{

                Conexion cn = new Conexion();
                con = cn.getConnection();

                String sql = "SELECT SUM(Monto) AS total FROM pagoproveedor WHERE Activo = 1";

                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();

                if(rs.next()){

                    double total = rs.getDouble("total");

            lblTotal.setText("$ " + total);

                }

            }catch(Exception e){

                JOptionPane.showMessageDialog(this,
                        "Error al calcular total: " + e.getMessage());

        }  
        }
public void buscarPagos(String buscar){

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try{

        Conexion cn = new Conexion();
        con = cn.getConnection();

        String sql = "SELECT p.Id_PagoProveedor, pr.Nombre, p.Fecha_Pago, " +
                     "p.Monto, p.Concepto, p.Activo, u.Nombre AS Usuario " +
                     "FROM pagoproveedor p " +
                     "INNER JOIN proveedor pr " +
                     "ON p.Id_Proveedor = pr.Id_Proveedor " +
                     "INNER JOIN usuario u " +
                     "ON p.Id_Usuario = u.Id_Usuario " +
                     "WHERE p.Activo = 1 AND (pr.Nombre LIKE ? " +
                     "OR p.Fecha_Pago LIKE ? " +
                     "OR CAST(p.Monto AS CHAR) LIKE ? " +
                     "OR p.Concepto LIKE ? " +
                     "OR u.Nombre LIKE ?)";

        ps = con.prepareStatement(sql);

        ps.setString(1, "%" + buscar + "%");
        ps.setString(2, "%" + buscar + "%");
        ps.setString(3, "%" + buscar + "%");
        ps.setString(4, "%" + buscar + "%");
        ps.setString(5, "%" + buscar + "%");

        rs = ps.executeQuery();

        DefaultTableModel modelo = (DefaultTableModel) tblPagoP.getModel();

        modelo.setRowCount(0);

        while(rs.next()){

            Object[] fila = new Object[6];

            fila[0] = rs.getInt("Id_PagoProveedor");
            fila[1] = rs.getString("Nombre");
            fila[2] = rs.getString("Fecha_Pago");
            fila[3] = rs.getDouble("Monto");
            fila[4] = rs.getString("Concepto");
            fila[5] = rs.getString("Usuario");
            modelo.addRow(fila);
        }
        tblPagoP.setModel(modelo);
        tblPagoP.getColumnModel().getColumn(0).setMinWidth(0);
        tblPagoP.getColumnModel().getColumn(0).setMaxWidth(0);           

        
    }catch(Exception e){

        JOptionPane.showMessageDialog(this,
                "Error al buscar: " + e.getMessage());

    }
}
           
public PagoProveedores(int idUsuario, String rolUsuario){
    initComponents();
    
    this.idUsuario = idUsuario;
    this.rolUsuario = rolUsuario;
    
    this.setLocationRelativeTo(null);
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mostrarPagos();
        calcularTotal();

}
    /**
     * Creates new form PagoProveedores
     */
    public PagoProveedores() {
        initComponents();
       
            this.setLocationRelativeTo(null);
            mostrarPagos();
            calcularTotal();
        tblPagoP.requestFocusInWindow();
        }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPagoP = new javax.swing.JTable();
        btnEliminar = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel1.setText("Pagos a Proveedor");

        btnNuevo.setText("+Nuevo Pago");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\editar.png")); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnRegresar.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\regresar.png")); // NOI18N
        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        jLabel2.setText("Total:");

        lblTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblPagoP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Proveedor", "Fecha", "Monto", "Concepto", "Usuario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblPagoP);
        if (tblPagoP.getColumnModel().getColumnCount() > 0) {
            tblPagoP.getColumnModel().getColumn(0).setResizable(false);
            tblPagoP.getColumnModel().getColumn(1).setResizable(false);
            tblPagoP.getColumnModel().getColumn(2).setResizable(false);
            tblPagoP.getColumnModel().getColumn(3).setResizable(false);
            tblPagoP.getColumnModel().getColumn(4).setResizable(false);
            tblPagoP.getColumnModel().getColumn(5).setResizable(false);
        }

        btnEliminar.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\eliminar.png")); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNuevo))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(btnRegresar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(btnRegresar)
                        .addComponent(btnEditar)
                        .addComponent(btnEliminar))
                    .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        if (!rolUsuario.equals("ADMINISTRADOR")){
            JOptionPane.showMessageDialog(this, "Solo los administradores pueden registrar pagos");
            return;
        }
        NuevoPago nPago = new NuevoPago(this, idUsuario);
        nPago.setVisible(true);
        nPago.setLocationRelativeTo(null);

        nPago.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e){
                mostrarPagos();
                calcularTotal();

            }

        });

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        int fila = tblPagoP.getSelectedRow();

        if(fila == -1){
            JOptionPane.showMessageDialog(this, "Seleccione un pago para editar");
            return;
        }

        int idPago = Integer.parseInt(tblPagoP.getValueAt(fila, 0).toString());
        String proveedor = tblPagoP.getValueAt(fila, 1).toString();
        java.util.Date fecha = new java.util.Date(
    ((java.sql.Timestamp) tblPagoP.getValueAt(fila, 2)).getTime());
        double monto = Double.parseDouble(tblPagoP.getValueAt(fila, 3).toString());
        String concepto = tblPagoP.getValueAt(fila, 4).toString();
        String rol = tblPagoP.getValueAt(fila, 5).toString();

        NuevoPago nuevo = new NuevoPago(this, idUsuario);
        nuevo.Datos(idPago, monto, fecha, concepto, proveedor, idUsuario);
        nuevo.setVisible(true);
        this.setVisible(false);
        nuevo.setLocationRelativeTo(null);

        nuevo.addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosed(java.awt.event.WindowEvent e){
                mostrarPagos();
                calcularTotal();
            }
        });
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        int fila = tblPagoP.getSelectedRow();

    if (fila == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Selecciona una fila de la tabla.");
        return;
    }

    int id = Integer.parseInt(tblPagoP.getValueAt(fila, 0).toString());
    String nombre = tblPagoP.getValueAt(fila, 1).toString();

    int confirmar = javax.swing.JOptionPane.showConfirmDialog(this, 
        "¿Está seguro de eliminar el pago al proveedor " + nombre + "?", "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);

    if (confirmar == javax.swing.JOptionPane.YES_OPTION) {
        String sql = "UPDATE pagoproveedor SET Activo = 0 WHERE Id_PagoProveedor = ?";
        Conexion cc = new Conexion();
        try (Connection cn = cc.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
           int filas =  ps.executeUpdate();
           if(filas > 0) {
               JOptionPane.showMessageDialog(this, "Pago eliminado correctamente");
               mostrarPagos();
               calcularTotal();
           }
            
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
        }
      }  
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // TODO add your handling code here:
        if(txtBuscar.getText().trim().isEmpty()){
            mostrarPagos();
        }else{
            buscarPagos(txtBuscar.getText());
        }
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
        buscarPagos(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarActionPerformed

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
            java.util.logging.Logger.getLogger(PagoProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PagoProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PagoProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PagoProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PagoProveedores().setVisible(true);
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblPagoP;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}

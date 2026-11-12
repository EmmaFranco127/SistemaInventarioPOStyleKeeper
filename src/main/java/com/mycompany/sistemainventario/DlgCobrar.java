/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.sistemainventario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Belen Chacon
 */
public class DlgCobrar extends javax.swing.JDialog {

  @FunctionalInterface
    public interface RegistroCobro {
        int registrar(BigDecimal recibido) throws SQLException;
    }

    private BigDecimal total = BigDecimal.ZERO;

    private RegistroCobro registroCobro;

    private int idVentaRegistrada = 0;

    private BigDecimal dineroRecibido;
    private BigDecimal cambioEntregado;

    private boolean guardando = false;
    /**
     * Creates new form DlgCobrar
     */
    
    private void configurarCobro() {

    txtPago.setText("");

    lblPago.setText("$0.00");
    lblCambio.setText("$0.00");

    btnCobrar.setEnabled(false);

    txtPago.setToolTipText(
            "Escribe el efectivo sin símbolo $ ni separadores de miles.");

    txtPago.getDocument().addDocumentListener(
            new DocumentListener() {

        @Override
        public void insertUpdate(DocumentEvent e) {
            actualizarCambio();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            actualizarCambio();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            actualizarCambio();
        }
    });

    btnCobrar.addActionListener(e -> cobrar());

    btnCancelar.addActionListener(e -> {
        if (!guardando) {
            dispose();
        }
    });

    getRootPane().setDefaultButton(btnCobrar);
    txtPago.addActionListener(e -> btnCobrar.doClick());

    setDefaultCloseOperation(
            javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

    addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
            if (!guardando) {
                dispose();
            }
        }
    });
}

private String dinero(BigDecimal valor) {
    return valor.setScale(2, RoundingMode.HALF_UP)
            .toPlainString();
}

private BigDecimal leerPago() {

    String texto = txtPago.getText().trim();

    if (!texto.matches("[0-9]+([.,][0-9]{1,2})?")) {
        throw new IllegalArgumentException(
                "Escribe un importe válido, por ejemplo: 500.00.");
    }

    BigDecimal recibido =
            new BigDecimal(texto.replace(',', '.'))
                    .setScale(2, RoundingMode.UNNECESSARY);

    
    if (recibido.compareTo(
            new BigDecimal("9999999999.99")) > 0) {

        throw new IllegalArgumentException(
                "El importe supera el límite permitido.");
    }

    return recibido;
}

private void actualizarCambio() {

    btnCobrar.setEnabled(false);

    if (txtPago.getText().trim().isEmpty()) {
        lblCambio.setText("$0.00");
        return;
    }

    try {
        BigDecimal recibido = leerPago();

        if (recibido.compareTo(total) < 0) {
            lblCambio.setText(
                    "Faltan $" + dinero(total.subtract(recibido)));
            return;
        }

        lblCambio.setText(
                "$" + dinero(recibido.subtract(total)));

        btnCobrar.setEnabled(
                registroCobro != null && !guardando);

    } catch (IllegalArgumentException ex) {
        lblCambio.setText("Importe no válido");
    }
}

private void cobrar() {

    if (guardando || registroCobro == null) {
        return;
    }

    BigDecimal recibido;

    try {
        recibido = leerPago();

        if (recibido.compareTo(total) < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "El efectivo recibido no cubre el total.");
            return;
        }

    } catch (IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage());
        return;
    }

    guardando = true;

    btnCobrar.setEnabled(false);
    btnCancelar.setEnabled(false);
    txtPago.setEditable(false);

    try {
        int folio = registroCobro.registrar(recibido);

        if (folio <= 0) {
            throw new SQLException(
                    "No se obtuvo un folio válido.");
        }

        
        idVentaRegistrada = folio;

        dineroRecibido = recibido;
        cambioEntregado = recibido.subtract(total);

        dispose();

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(
                this,
                "No se pudo completar el registro de la venta:\n"
                + ex.getMessage()
                + "\n\nNo entregues el cambio hasta confirmar "
                + "que el registro terminó correctamente.",
                "Error al registrar",
                JOptionPane.ERROR_MESSAGE
        );

    } finally {
        guardando = false;

        btnCancelar.setEnabled(true);
        txtPago.setEditable(true);

        actualizarCambio();
    }
}

public boolean fueCobrado() {
    return idVentaRegistrada > 0;
}

public int getIdVentaRegistrada() {
    return idVentaRegistrada;
}

public BigDecimal getDineroRecibido() {
    return dineroRecibido;
}

public BigDecimal getCambioEntregado() {
    return cambioEntregado;
}
    
    public DlgCobrar(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
        initComponents();
        configurarCobro();
    }

    public DlgCobrar(java.awt.Frame parent,
                 BigDecimal total,
                 RegistroCobro registroCobro) {

    this(parent, true);

    if (total == null || total.signum() < 0) {
        throw new IllegalArgumentException(
                "El total de la venta no es válido.");
    }

    if (registroCobro == null) {
        throw new IllegalArgumentException(
                "Falta configurar el registro del cobro.");
    }

    this.total = total.setScale(2, RoundingMode.UNNECESSARY);
    this.registroCobro = registroCobro;

    lblPago.setText("$" + dinero(this.total));

    actualizarCambio();
    setLocationRelativeTo(parent);
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblPago = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtPago = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        lblCambio = new javax.swing.JLabel();
        btnCobrar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Cobrar");

        jLabel2.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        jLabel2.setText("Total a Cobrar");

        lblPago.setFont(new java.awt.Font("Segoe UI Symbol", 0, 24)); // NOI18N
        lblPago.setText("$0.00");

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        jLabel4.setText("Su Cambio:");

        jLabel5.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        jLabel5.setText("Pagó con:");

        lblCambio.setFont(new java.awt.Font("Segoe UI Symbol", 0, 24)); // NOI18N
        lblCambio.setText("$0.00");

        btnCobrar.setText("Cobrar");

        btnCancelar.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\cerrar.png")); // NOI18N
        btnCancelar.setText("Cancelar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPago, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCambio)))
                .addGap(105, 105, 105))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(151, 151, 151)
                        .addComponent(lblPago))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(btnCancelar)
                        .addGap(18, 18, 18)
                        .addComponent(btnCobrar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPago)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblCambio))
                .addGap(14, 78, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnCobrar))
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(DlgCobrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgCobrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgCobrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgCobrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgCobrar dialog = new DlgCobrar(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCobrar;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblCambio;
    private javax.swing.JLabel lblPago;
    private javax.swing.JTextField txtPago;
    // End of variables declaration//GEN-END:variables
}

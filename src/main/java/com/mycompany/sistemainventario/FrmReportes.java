package com.mycompany.sistemainventario;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author Alexa
 */
public class FrmReportes extends javax.swing.JFrame {
    /**
     * Creates new form FrmReportes
     */
    private boolean configurandoReportes = false;
    
    public FrmReportes() {
        initComponents();
        if (!Sesion.activa()) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Debes iniciar sesión para consultar reportes.");

        dispose();
        return;
    }

    configurandoReportes = true;

    try {
        if (Sesion.esAdministrador()) {

            cmbTipoReporte.setModel(
                    new javax.swing.DefaultComboBoxModel<>(
                            new String[]{
                                "Inventario Actual",
                                "Stock Bajo (Alertas)",
                                "Productos por Proveedor",
                                "Consolidado General",
                                "Historial de Movimientos",
                                "Niveles de Stock",
                                "Historial de Ventas"
                            }
                    )
            );

        } else if (Sesion.puedeVentas()) {

            cmbTipoReporte.setModel(
                    new javax.swing.DefaultComboBoxModel<>(
                            new String[]{
                                "Historial de Ventas"
                            }
                    )
            );

        } else if (Sesion.puedeInventario()) {

            cmbTipoReporte.setModel(
                    new javax.swing.DefaultComboBoxModel<>(
                            new String[]{
                                "Inventario Actual",
                                "Stock Bajo (Alertas)",
                                "Productos por Proveedor",
                                "Consolidado General",
                                "Historial de Movimientos",
                                "Niveles de Stock"
                            }
                    )
            );
        }

    } finally {
        configurandoReportes = false;
    }

        cargarReporteSeleccionado();
    }
    
    public void cargarReporteSeleccionado() {
    if (configurandoReportes
            || cmbTipoReporte.getSelectedItem() == null) {
        return;
    }

    String reporteSeleccionado =
            cmbTipoReporte.getSelectedItem().toString();

    boolean reporteVentas =
            "Historial de Ventas".equals(reporteSeleccionado);

    if ((reporteVentas && !Sesion.puedeVentas())
            || (!reporteVentas && !Sesion.puedeInventario())) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Tu rol no puede consultar este reporte.");
        return;
    }

    String sql = "";
    
    switch (reporteSeleccionado) {
        case "Inventario Actual":
            sql = "SELECT * FROM inventarioactual";
            break;
        case "Stock Bajo (Alertas)":
            sql = "SELECT * FROM stockbajo";
            break;
        case "Productos por Proveedor":
            sql = "SELECT * FROM porproveedor";
            break;
        case "Consolidado General":
            sql = "SELECT * FROM consolidado";
            break;
        case "Historial de Movimientos":
            sql = "SELECT * FROM historialmovimientos";
            break;
        case "Niveles de Stock":
            sql = "SELECT * FROM nivelesstock";
            break;
            
        case "Historial de Ventas":
            sql = "SELECT Id_Venta AS Folio, "
            + "Fecha_Hora, Productos_Vendidos, "
            + "Unidades_Vendidas, Vendedor, Total, "
            + "Metodo_Pago, Dinero_Recibido, Cambio, Estado "
            + "FROM historialventasreportes "
            + "ORDER BY Fecha_Hora DESC, Id_Venta DESC";
        break;
        
        default:
            return;
    }
    
    javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel();
    
    try {
        Conexion cc = new Conexion(); 
        java.sql.Connection con = cc.getConnection(); 
        
        if (con != null) {
            java.sql.Statement st = con.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);
            
            java.sql.ResultSetMetaData rsmd = rs.getMetaData();
            int numeroColumnas = rsmd.getColumnCount();
            
            for (int i = 1; i <= numeroColumnas; i++) {
                String nombreColumna = rsmd.getColumnLabel(i);
                nombreColumna = nombreColumna.replace("_", " ");
                modelo.addColumn(nombreColumna);
            }
            
            while (rs.next()) {
                Object[] fila = new Object[numeroColumnas];
                for (int i = 0; i < numeroColumnas; i++) {
                    fila[i] = rs.getObject(i + 1); 
                }
                modelo.addRow(fila);
            }
            
            tblDatos.setModel(modelo);
            
            if (!"Historial de Ventas".equals(reporteSeleccionado)
                    && tblDatos.getColumnCount() > 0) {

            tblDatos.getColumnModel().getColumn(0).setMinWidth(0);
            tblDatos.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblDatos.getColumnModel().getColumn(0).setMaxWidth(0);
        }
            rs.close();
            st.close();
            con.close();
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "No se pudo establecer la conexión con StyleKeeper.");
        }
        
    } catch (java.sql.SQLException e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Error en la base de datos para [" + reporteSeleccionado + "]: " + e.getMessage());
    }
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
        cmbTipoReporte = new javax.swing.JComboBox<>();
        btnGenerarReporte = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnExcel = new javax.swing.JButton();
        btnPDF = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setPreferredSize(new java.awt.Dimension(1000, 650));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros de Búsqueda"));

        jLabel1.setText("Tipo de reporte:");
        jPanel1.add(jLabel1);

        cmbTipoReporte.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Inventario Actual", "Stock Bajo (Alertas)", "Productos por Proveedor", "Consolidado General", "Historial de Movimientos", "Niveles de Stock", "Historial de Ventas" }));
        cmbTipoReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoReporteActionPerformed(evt);
            }
        });
        jPanel1.add(cmbTipoReporte);

        btnGenerarReporte.setText("Generar reporte");
        btnGenerarReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarReporteActionPerformed(evt);
            }
        });
        jPanel1.add(btnGenerarReporte);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        tblDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "", "", "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblDatos);
        if (tblDatos.getColumnModel().getColumnCount() > 0) {
            tblDatos.getColumnModel().getColumn(0).setResizable(false);
            tblDatos.getColumnModel().getColumn(1).setResizable(false);
            tblDatos.getColumnModel().getColumn(2).setResizable(false);
            tblDatos.getColumnModel().getColumn(3).setResizable(false);
        }

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnExcel.setBackground(new java.awt.Color(0, 153, 0));
        btnExcel.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\excel.png")); // NOI18N
        btnExcel.setText("Exportar Excel");
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });
        jPanel2.add(btnExcel);

        btnPDF.setBackground(new java.awt.Color(229, 0, 0));
        btnPDF.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\pdf.png")); // NOI18N
        btnPDF.setText("Exportar PDF");
        btnPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPDFActionPerformed(evt);
            }
        });
        jPanel2.add(btnPDF);

        btnCerrar.setIcon(new javax.swing.ImageIcon("C:\\Users\\franc\\Downloads\\Iconos para proyecto\\cerrar.png")); // NOI18N
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel2.add(btnCerrar);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        if (tblDatos.getRowCount() == 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "No hay datos en la tabla para exportar.", "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        javax.swing.JFileChooser selectorArchivo = new javax.swing.JFileChooser();
        selectorArchivo.setDialogTitle("Guardar Reporte en Excel");
        String nombreSugerido = "Reporte_" + cmbTipoReporte.getSelectedItem().toString().replace(" ", "_") + ".xlsx";
        selectorArchivo.setSelectedFile(new java.io.File(nombreSugerido));

        int seleccion = selectorArchivo.showSaveDialog(this);

        if (seleccion == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File archivoDestino = selectorArchivo.getSelectedFile();

            if (!archivoDestino.getName().endsWith(".xlsx")) {
                archivoDestino = new java.io.File(archivoDestino.getAbsolutePath() + ".xlsx");
            }

            try (org.apache.poi.ss.usermodel.Workbook libro = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
                String nombreHoja = cmbTipoReporte.getSelectedItem().toString().replaceAll("[^a-zA-Z0-9 ]", "");
                if(nombreHoja.length() > 30) nombreHoja = nombreHoja.substring(0, 30);
                org.apache.poi.ss.usermodel.Sheet hoja = libro.createSheet(nombreHoja);

                org.apache.poi.ss.usermodel.Font fuenteEncabezado = libro.createFont();
                fuenteEncabezado.setBold(true);
                fuenteEncabezado.setColor(org.apache.poi.ss.usermodel.IndexedColors.WHITE.getIndex());

                org.apache.poi.ss.usermodel.CellStyle estiloEncabezado = libro.createCellStyle();
                estiloEncabezado.setFont(fuenteEncabezado);
                estiloEncabezado.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.DARK_BLUE.getIndex());
                estiloEncabezado.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
                estiloEncabezado.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);

                org.apache.poi.ss.usermodel.CellStyle estiloCelda = libro.createCellStyle();
                estiloCelda.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
                estiloCelda.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
                estiloCelda.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
                estiloCelda.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);

                org.apache.poi.ss.usermodel.Row filaCabecera = hoja.createRow(0);
                for (int i = 0; i < tblDatos.getColumnCount(); i++) {
                    org.apache.poi.ss.usermodel.Cell celda = filaCabecera.createCell(i);
                    celda.setCellValue(tblDatos.getColumnName(i));
                    celda.setCellStyle(estiloEncabezado);
                }

                for (int f = 0; f < tblDatos.getRowCount(); f++) {
                    org.apache.poi.ss.usermodel.Row filaDatos = hoja.createRow(f + 1);
                    for (int c = 0; c < tblDatos.getColumnCount(); c++) {
                        org.apache.poi.ss.usermodel.Cell celda = filaDatos.createCell(c);
                        Object valor = tblDatos.getValueAt(f, c);

                        if (valor != null) {
                            try {
                                double numero = Double.parseDouble(valor.toString());
                                celda.setCellValue(numero);
                            } catch (NumberFormatException e) {
                                celda.setCellValue(valor.toString());
                            }
                        } else {
                            celda.setCellValue("");
                        }
                        celda.setCellStyle(estiloCelda);
                    }
                }

                for (int i = 0; i < tblDatos.getColumnCount(); i++) {
                    hoja.autoSizeColumn(i);
                }

                try (java.io.FileOutputStream fos = new java.io.FileOutputStream(archivoDestino)) {
                    libro.write(fos);
                }

                javax.swing.JOptionPane.showMessageDialog(this, "¡Archivo de Excel generado con éxito!", "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                String reporte = cmbTipoReporte.getSelectedItem().toString();
            } catch (java.io.IOException e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error al generar el archivo de Excel: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnExcelActionPerformed

    private void btnPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDFActionPerformed
        if (tblDatos.getRowCount() == 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "No hay datos en la tabla para exportar.", "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        javax.swing.JFileChooser selectorArchivo = new javax.swing.JFileChooser();
        selectorArchivo.setDialogTitle("Guardar Reporte en PDF");
        String nombreSugerido = "Reporte_" + cmbTipoReporte.getSelectedItem().toString().replace(" ", "_") + ".pdf";
        selectorArchivo.setSelectedFile(new java.io.File(nombreSugerido));

        int seleccion = selectorArchivo.showSaveDialog(this);

        if (seleccion == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File archivoDestino = selectorArchivo.getSelectedFile();

            if (!archivoDestino.getName().endsWith(".pdf")) {
                archivoDestino = new java.io.File(archivoDestino.getAbsolutePath() + ".pdf");
            }

            com.itextpdf.text.Document documento = new com.itextpdf.text.Document(com.itextpdf.text.PageSize.A4.rotate());

            try {
                com.itextpdf.text.pdf.PdfWriter.getInstance(documento, new java.io.FileOutputStream(archivoDestino));
                documento.open();

                com.itextpdf.text.Font fuenteTitulo = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 16, com.itextpdf.text.Font.BOLD, com.itextpdf.text.BaseColor.DARK_GRAY);
                com.itextpdf.text.Font fuenteSubtitulo = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 10, com.itextpdf.text.Font.ITALIC, com.itextpdf.text.BaseColor.GRAY);
                com.itextpdf.text.Font fuenteCabeceraTabla = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 10, com.itextpdf.text.Font.BOLD, com.itextpdf.text.BaseColor.WHITE);
                com.itextpdf.text.Font fuenteCuerpoTabla = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 9, com.itextpdf.text.Font.NORMAL, com.itextpdf.text.BaseColor.BLACK);

                com.itextpdf.text.Paragraph titulo = new com.itextpdf.text.Paragraph("STYLEKEEPER - SISTEMA DE CONTROL DE INVENTARIO", fuenteTitulo);
                documento.add(titulo);

                String tipoReporteActual = cmbTipoReporte.getSelectedItem().toString();
                String fechaActual = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
                com.itextpdf.text.Paragraph subtitulo = new com.itextpdf.text.Paragraph("Reporte Ejecutivo: " + tipoReporteActual + "\nFecha de Emisión: " + fechaActual + "\n\n", fuenteSubtitulo);
                documento.add(subtitulo);

                int numColumnas = tblDatos.getColumnCount();
                com.itextpdf.text.pdf.PdfPTable tablaPdf = new com.itextpdf.text.pdf.PdfPTable(numColumnas);
                tablaPdf.setWidthPercentage(100);

                for (int i = 0; i < numColumnas; i++) {
                    com.itextpdf.text.pdf.PdfPCell celdaCabecera = new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Phrase(tblDatos.getColumnName(i), fuenteCabeceraTabla));
                    celdaCabecera.setBackgroundColor(new com.itextpdf.text.BaseColor(41, 128, 185));
                    celdaCabecera.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    celdaCabecera.setPadding(6);
                    tablaPdf.addCell(celdaCabecera);
                }

                for (int f = 0; f < tblDatos.getRowCount(); f++) {
                    for (int c = 0; c < numColumnas; c++) {
                        Object celdaValor = tblDatos.getValueAt(f, c);
                        String textoCelda = (celdaValor != null) ? celdaValor.toString() : "";

                        com.itextpdf.text.pdf.PdfPCell celdaCuerpo = new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Phrase(textoCelda, fuenteCuerpoTabla));
                        celdaCuerpo.setPadding(5);
                        celdaCuerpo.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);

                        if (f % 2 != 0) {
                            celdaCuerpo.setBackgroundColor(new com.itextpdf.text.BaseColor(245, 247, 250));
                        }

                        tablaPdf.addCell(celdaCuerpo);
                    }
                }

                documento.add(tablaPdf);
                documento.close();

                javax.swing.JOptionPane.showMessageDialog(this, "¡Documento PDF exportado con éxito!", "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                String reporte = cmbTipoReporte.getSelectedItem().toString();
            } catch (com.itextpdf.text.DocumentException | java.io.FileNotFoundException e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error al dar estructura al PDF: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnPDFActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnGenerarReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarReporteActionPerformed
        cargarReporteSeleccionado();
        String reporte = cmbTipoReporte.getSelectedItem().toString();
    }//GEN-LAST:event_btnGenerarReporteActionPerformed

    private void cmbTipoReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoReporteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTipoReporteActionPerformed

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
            java.util.logging.Logger.getLogger(FrmReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmReportes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnGenerarReporte;
    private javax.swing.JButton btnPDF;
    private javax.swing.JComboBox<String> cmbTipoReporte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDatos;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import Entidades.ClsCancion;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import conexion.ClsConexion;

import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import static presentacion.FrmUsuario.rs;
import procedimientos.ClssCancion;
import procedimientos.ClssUsuario;

/**
 *
 * @author Usuario
 */
public class FrmCancion extends javax.swing.JFrame {

    private Connection connection = new ClsConexion().getConection();
    String Total;
    String strCodigo;
    String accion;
    int registros;
    String id[] = new String[50];
    static int intContador;
    //-----------------------------------------------
    public String codigo;
    static Connection conn = null;
    static ResultSet rs = null;
    DefaultTableModel dtm = new DefaultTableModel();
    String criterio, busqueda;
    FrmUsuario usuario = new FrmUsuario();
    JTextField ID = FrmUsuario.txtIDUsuario;

    /**
     * Creates new form FrmCancion
     *
     * @throws java.sql.SQLException
     */
    public FrmCancion() throws SQLException {

        initComponents();
        buttonGroup1.add(rbtnIDCancion);
        buttonGroup1.add(rbtnGenero);
        buttonGroup1.add(rbtnAutor);
        buttonGroup1.add(rbtnTitulo);

        this.setTitle("Cancion");
        mirar();
        //actualizarTabla();
        BuscarCancionUsuario();
        //---------------------ANCHO Y ALTO DEL FORM----------------------
        this.setSize(707, 426);
        CantidadTotal();
    }

    void mirar() {
        tblCancion.setEnabled(true);
        btnNuevo.setEnabled(true);
        btnModificarCancion.setEnabled(true);
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnSalir.setEnabled(true);
        txtGenero.setEnabled(true);
        txtAutor.setEnabled(true);
        txtTitulo.setEnabled(true);
        txtFecha.setDateFormatString(null);
        txtDuracion.setEnabled(true);
        txtUsuario_IDUsuario.setEnabled(false);
        txtUsuario_IDUsuario.setText(ID.getText());
    }

    void CantidadTotal() {
        Total = String.valueOf(tblCancion.getRowCount());
        lblEstado.setText("Se cargaron " + Total + " registros");
    }

    void limpiarCampos() {
        Date date = null;
        txtIDCancion.setText("");
        txtGenero.setText("");
        txtAutor.setText("");
        txtTitulo.setText("");
        txtFecha.setDate(date);
        txtDuracion.setText("");
        txtUsuario_IDUsuario.setText("");
        rbtnIDCancion.setSelected(false);
        rbtnGenero.setSelected(false);
        rbtnTitulo.setSelected(false);
        rbtnAutor.setSelected(false);
    }

    void modificar() {
        tblCancion.setEnabled(false);
        btnNuevo.setEnabled(false);
        btnModificarCancion.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnSalir.setEnabled(false);

        txtGenero.setEnabled(true);
        txtAutor.setEnabled(true);
        txtTitulo.setEnabled(true);
        txtDuracion.setEnabled(true);
        txtUsuario_IDUsuario.setEnabled(false);
        txtUsuario_IDUsuario.setText(ID.getText());
        txtGenero.requestFocus();

    }

    void BuscarCancionUsuario() {
        String titulos[] = {"ID", "Genero", "Autor", "Titulo", "Fecha", "Duracion", "IDUsuario"};
        dtm.setColumnIdentifiers(titulos);

        ClssCancion categoria = new ClssCancion();

        try {
            rs = categoria.listarCancionPorParametro("usuario", FrmUsuario.strCodigoU);
            boolean encuentra = false;
            String Datos[] = new String[7];
            int f, i;
            f = dtm.getRowCount();
            if (f > 0) {
                for (i = 0; i < f; i++) {
                    dtm.removeRow(0);
                }
            }
            while (rs.next()) {
                Datos[0] = (String) rs.getString(1);
                Datos[1] = (String) rs.getString(2);
                Datos[2] = (String) rs.getString(3);
                Datos[3] = (String) rs.getString(4);
                Datos[4] = (String) rs.getString(5);
                Datos[5] = (String) rs.getString(6);
                Datos[6] = (String) rs.getString(7);

                dtm.addRow(Datos);
                encuentra = true;

            }
            if (encuentra = false) {
                JOptionPane.showMessageDialog(null, "¡No se encuentra!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        tblCancion.setModel(dtm);
    }

    void BuscarCancion() {
        String titulos[] = {"ID", "Genero", "Autor", "Titulo", "Fecha", "Duracion", "IDUsuario"};
        dtm.setColumnIdentifiers(titulos);

        ClssCancion categoria = new ClssCancion();
        busqueda = txtBuscar.getText();
        if (rbtnIDCancion.isSelected()) {
            criterio = "id";
        } else if (rbtnGenero.isSelected()) {
            criterio = "genero";
        } else if (rbtnAutor.isSelected()) {
            criterio = "autor";
        } else if (rbtnTitulo.isSelected()) {
            criterio = "titulo";
        } else if (rbtnFecha.isSelected()) {
            criterio = "fecha";
        }
        try {
            rs = categoria.listarCancionPorParametro(criterio, busqueda);
            boolean encuentra = false;
            String Datos[] = new String[7];
            int f, i;
            f = dtm.getRowCount();
            if (f > 0) {
                for (i = 0; i < f; i++) {
                    dtm.removeRow(0);
                }
            }
            while (rs.next()) {
                Datos[0] = (String) rs.getString(1);
                Datos[1] = (String) rs.getString(2);
                Datos[2] = (String) rs.getString(3);
                Datos[3] = (String) rs.getString(4);
                Datos[4] = (String) rs.getString(5);
                Datos[5] = (String) rs.getString(6);
                Datos[6] = (String) rs.getString(7);

                dtm.addRow(Datos);
                encuentra = true;

            }
            if (encuentra = false) {
                JOptionPane.showMessageDialog(null, "¡No se encuentra!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        tblCancion.setModel(dtm);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jDayChooser1 = new com.toedter.calendar.JDayChooser();
        jPanel1 = new javax.swing.JPanel();
        TabCancion = new javax.swing.JTabbedPane();
        pBuscar = new javax.swing.JPanel();
        rbtnIDCancion = new javax.swing.JRadioButton();
        rbtnGenero = new javax.swing.JRadioButton();
        rbtnFecha = new javax.swing.JRadioButton();
        rbtnAutor = new javax.swing.JRadioButton();
        rbtnTitulo = new javax.swing.JRadioButton();
        txtBuscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCancion = new javax.swing.JTable();
        pNuevo = new javax.swing.JPanel();
        lblIDCancion = new javax.swing.JLabel();
        lblGenero = new javax.swing.JLabel();
        lblAutor = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        txtIDCancion = new javax.swing.JTextField();
        txtGenero = new javax.swing.JTextField();
        txtAutor = new javax.swing.JTextField();
        txtTitulo = new javax.swing.JTextField();
        lblDuracion = new javax.swing.JLabel();
        lblUsuario_IDUsuario = new javax.swing.JLabel();
        txtDuracion = new javax.swing.JTextField();
        txtUsuario_IDUsuario = new javax.swing.JTextField();
        txtFecha = new com.toedter.calendar.JDateChooser();
        btnSalir = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnModificarCancion = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pBuscar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rbtnIDCancion.setText("ID Cancion");
        rbtnIDCancion.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbtnIDCancionStateChanged(evt);
            }
        });
        pBuscar.add(rbtnIDCancion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        rbtnGenero.setText("Genero");
        rbtnGenero.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbtnGeneroStateChanged(evt);
            }
        });
        pBuscar.add(rbtnGenero, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, -1, -1));

        rbtnFecha.setText("Fecha");
        rbtnFecha.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbtnFechaStateChanged(evt);
            }
        });
        pBuscar.add(rbtnFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, -1, -1));

        rbtnAutor.setText("Autor");
        rbtnAutor.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbtnAutorStateChanged(evt);
            }
        });
        pBuscar.add(rbtnAutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, -1, -1));

        rbtnTitulo.setText("Titulo");
        rbtnTitulo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbtnTituloStateChanged(evt);
            }
        });
        pBuscar.add(rbtnTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, -1, -1));

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        pBuscar.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 500, 30));

        jLabel1.setText("Criterios de busqueda");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pBuscar.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 540, 90));
        pBuscar.add(lblEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 10, 20));

        tblCancion.setModel(new javax.swing.table.DefaultTableModel(
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
        tblCancion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCancionMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCancion);

        jScrollPane2.setViewportView(jScrollPane1);

        pBuscar.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 540, 250));

        TabCancion.addTab("Buscar", pBuscar);

        pNuevo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblIDCancion.setText("IDCancion");
        pNuevo.add(lblIDCancion, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, -1));

        lblGenero.setText("Genero:");
        pNuevo.add(lblGenero, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        lblAutor.setText("Autor:");
        pNuevo.add(lblAutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        lblTitulo.setText("Titulo:");
        pNuevo.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        lblFecha.setText("Fecha:");
        pNuevo.add(lblFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));

        txtIDCancion.setEditable(false);
        pNuevo.add(txtIDCancion, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 90, -1));
        pNuevo.add(txtGenero, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 200, -1));
        pNuevo.add(txtAutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 200, -1));
        pNuevo.add(txtTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 200, -1));

        lblDuracion.setText("Duracion:");
        pNuevo.add(lblDuracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, -1));

        lblUsuario_IDUsuario.setText("IDUsuario:");
        pNuevo.add(lblUsuario_IDUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, -1));
        pNuevo.add(txtDuracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 80, -1));
        pNuevo.add(txtUsuario_IDUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, 100, -1));

        txtFecha.setDateFormatString("yyyy-MM-dd"); // NOI18N
        txtFecha.setDebugGraphicsOptions(javax.swing.DebugGraphics.LOG_OPTION);
        txtFecha.setMaxSelectableDate(new java.util.Date(253382536911000L));
        txtFecha.setMinSelectableDate(new java.util.Date(-62135747889000L));
        pNuevo.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, -1, -1));

        TabCancion.addTab("Nuevo/Modificar", pNuevo);

        jPanel1.add(TabCancion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 570, 410));

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 270, 80, 30));

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 220, 80, 30));

        btnModificarCancion.setText("Modificar");
        btnModificarCancion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarCancionActionPerformed(evt);
            }
        });
        jPanel1.add(btnModificarCancion, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 170, 80, 30));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 120, 80, 30));

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 70, 80, 30));

        jLabel2.setText("Opciones");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 30, 120, 310));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        accion = "Nuevo";
        //modificar();
        limpiarCampos();
        txtUsuario_IDUsuario.setText(FrmUsuario.strCodigoU);
        tblCancion.setEnabled(false);
        TabCancion.setSelectedIndex(TabCancion.indexOfComponent(pNuevo));
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void tblCancionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCancionMouseClicked
        // TODO add your handling code here:
        int fila;
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        fila = tblCancion.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Se debe seleccionar un registro");
        } else {
            defaultTableModel = (DefaultTableModel) tblCancion.getModel();
            strCodigo = ((String) defaultTableModel.getValueAt(fila, 0));
            txtIDCancion.setText((String) defaultTableModel.getValueAt(fila, 0));
            txtGenero.setText((String) defaultTableModel.getValueAt(fila, 1));
            txtAutor.setText((String) defaultTableModel.getValueAt(fila, 2));
            txtTitulo.setText((String) defaultTableModel.getValueAt(fila, 3));
            txtFecha.setDateFormatString((String) defaultTableModel.getValueAt(fila, 4));
            txtDuracion.setText((String) defaultTableModel.getValueAt(fila, 5));
            txtUsuario_IDUsuario.setText((String) defaultTableModel.getValueAt(fila, 6));
        }
        mirar();
    }//GEN-LAST:event_tblCancionMouseClicked

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (validardatos() == true) {
            if (accion.equals("Nuevo")) {
                try {
                    ClssCancion canciones = new ClssCancion();
                    ClsCancion cancion = new ClsCancion();
                    FrmUsuario usuario = new FrmUsuario();
                    cancion.setStrGenero(txtGenero.getText());
                    cancion.setStrAutor(txtAutor.getText());
                    cancion.setStrTitulo(txtTitulo.getText());
                    cancion.setFecha(txtFecha.getDate());
                    cancion.setDuracion(txtDuracion.getText());
                    cancion.setStrUsuario_Idusuario(txtUsuario_IDUsuario.getText());
                    canciones.agregarCancion(cancion);
                    BuscarCancionUsuario();
                    CantidadTotal();
                } catch (SQLException ex) {
                    Logger.getLogger(FrmCancion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (accion.equals("Modificar")) {
                try {
                    ClssCancion canciones = new ClssCancion();
                    ClsCancion cancion = new ClsCancion();
                    FrmUsuario usuario = new FrmUsuario();
                    cancion.setStrGenero(txtGenero.getText());
                    cancion.setStrAutor(txtAutor.getText());
                    cancion.setStrTitulo(txtTitulo.getText());
                    cancion.setFecha(txtFecha.getDate());
                    cancion.setDuracion(txtDuracion.getText());
                    cancion.setStrUsuario_Idusuario(txtUsuario_IDUsuario.getText());
                    canciones.modificarCancion(codigo, cancion);
                    BuscarCancionUsuario();
                    limpiarCampos();
                    modificar();
                    CantidadTotal();
                } catch (SQLException ex) {
                    Logger.getLogger(FrmCancion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            mirar();
            TabCancion.setSelectedIndex(TabCancion.indexOfComponent(pBuscar));
        }
    }

    /*private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {

    }//GEN-LAST:event_btnGuardarActionPerformed
*/
    private void btnModificarCancionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarCancionActionPerformed
        if (tblCancion.getSelectedRows().length > 0) {
            accion = "Modificar";
            modificar();
            TabCancion.setSelectedIndex(TabCancion.indexOfComponent(pNuevo));
        } else {
            JOptionPane.showMessageDialog(null, "¡Se debe seleccionar un registro!");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarCancionActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        mirar();
        TabCancion.setSelectedIndex(TabCancion.indexOfComponent(pBuscar));

        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // TODO add your handling code here:
        BuscarCancion();
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void rbtnIDCancionStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbtnIDCancionStateChanged
        // TODO add your handling code here:
        txtBuscar.setText("");
    }//GEN-LAST:event_rbtnIDCancionStateChanged

    private void rbtnGeneroStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbtnGeneroStateChanged
        // TODO add your handling code here:
        txtBuscar.setText("");
    }//GEN-LAST:event_rbtnGeneroStateChanged

    private void rbtnAutorStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbtnAutorStateChanged
        // TODO add your handling code here:
        txtBuscar.setText("");
    }//GEN-LAST:event_rbtnAutorStateChanged

    private void rbtnTituloStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbtnTituloStateChanged
        // TODO add your handling code here:
        txtBuscar.setText("");
    }//GEN-LAST:event_rbtnTituloStateChanged

    private void rbtnFechaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbtnFechaStateChanged
        // TODO add your handling code here:
        txtBuscar.setText("");
    }//GEN-LAST:event_rbtnFechaStateChanged

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
            java.util.logging.Logger.getLogger(FrmCancion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCancion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCancion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCancion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new FrmCancion().setVisible(true);
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
            }
        });
    }

    //----------------------VALIDACIÓN DE DATOS-------------------------------------
    public boolean validardatos() {
        if (txtGenero.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese Ganero de cancion");
            txtGenero.requestFocus();
            txtGenero.setBackground(Color.YELLOW);
            return false;

        } else {
            return true;
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TabCancion;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificarCancion;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDayChooser jDayChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAutor;
    private javax.swing.JLabel lblDuracion;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblGenero;
    private javax.swing.JLabel lblIDCancion;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario_IDUsuario;
    private javax.swing.JPanel pBuscar;
    private javax.swing.JPanel pNuevo;
    private javax.swing.JRadioButton rbtnAutor;
    private javax.swing.JRadioButton rbtnFecha;
    private javax.swing.JRadioButton rbtnGenero;
    private javax.swing.JRadioButton rbtnIDCancion;
    private javax.swing.JRadioButton rbtnTitulo;
    private javax.swing.JTable tblCancion;
    private javax.swing.JTextField txtAutor;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtDuracion;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JTextField txtGenero;
    private javax.swing.JTextField txtIDCancion;
    private javax.swing.JTextField txtTitulo;
    private javax.swing.JTextField txtUsuario_IDUsuario;
    // End of variables declaration//GEN-END:variables
}
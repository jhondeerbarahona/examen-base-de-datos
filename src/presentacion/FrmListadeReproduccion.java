/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import Entidades.ClsListadeReproduccion;
import conexion.ClsConexion;
import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import procedimientos.ClssListadeReproduccion;

/**
 *
 * @author Usuario
 */
public class FrmListadeReproduccion extends javax.swing.JFrame {

    private Connection connection = new ClsConexion().getConection();
    String Total;
    String strCodigo;
    String accion;
    int registros;
    String id[] = new String[50];
    static int intContador;
    String criterio, busqueda;

    //-----------------------------------------------
    public String codigo;
    public String Id;
    static Connection conn = null;
    static ResultSet rs = null;
    DefaultTableModel dtm = new DefaultTableModel();
    FrmUsuario usuario = new FrmUsuario();

    /**
     * Creates new form FrmUsuario
     *
     * @throws java.sql.SQLException
     */
    public FrmListadeReproduccion() throws SQLException {
        initComponents();
        buttonGroup1.add(rbtnIDLista);
        buttonGroup1.add(rbtnNombredeLista);
        Id = usuario.UsuarioId;
        this.setTitle("Lista de Reproduccion");
        mirar();
        BuscarListaUsuario();

        //actualizarTablaCanciones();
        //---------------------ANCHO Y ALTO DEL FORM----------------------
        this.setSize(707, 426);
        CantidadTotal();

    }

    void mirar() {
        tblLista.setEnabled(true);
        btnNuevo.setEnabled(true);
        btnModificar.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnSalir.setEnabled(true);

        txtNombreLista.setEnabled(false);
        txtUsuario_IDUsuario.setEnabled(false);

    }

    void actualizarTabla() throws SQLException {
        String titulos[] = {"ID", "Nombre", "IDUsuario"};
        ClssListadeReproduccion listas = new ClssListadeReproduccion();
        ArrayList<ClsListadeReproduccion> lista = listas.listarListadeReproduccion();
        Iterator iterator = lista.iterator();
        DefaultTableModel defaultTableModel = new DefaultTableModel(null, titulos);
        String fila[] = new String[3];
        while (iterator.hasNext()) {
            ClsListadeReproduccion Lista = new ClsListadeReproduccion();
            Lista = (ClsListadeReproduccion) iterator.next();
            fila[0] = Lista.getStridListadeReproduccion();
            fila[1] = Lista.getStrnombreLista();
            fila[2] = Lista.getStrUsuario_Idusuario();

            defaultTableModel.addRow(fila);

        }
        tblLista.setModel(defaultTableModel);
    }
    void CantidadTotal() {
        Total = String.valueOf(tblLista.getRowCount());
        lblEstado.setText("Se cargaron " + Total + " registros");
    }

    void limpiarCampos() {
        txtIDLista.setText("");
        txtNombreLista.setText("");
        txtUsuario_IDUsuario.setText("");
        rbtnIDLista.setSelected(false);
        rbtnNombredeLista.setSelected(false);
    }

    void modificar() {
        tblLista.setEnabled(false);
        btnNuevo.setEnabled(false);
        btnModificar.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnSalir.setEnabled(false);

        txtIDLista.setEnabled(true);
        txtNombreLista.setEnabled(true);
        txtUsuario_IDUsuario.setEnabled(true);
        txtNombreLista.requestFocus();
    }

    void CrearTabla() {
        //--------------------PRESENTACION DE JTABLE----------------------

        TableCellRenderer render = new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                //aqui obtengo el render de la calse superior 
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                //Determinar Alineaciones   
                if (column == 0 || column == 2 || column == 3 || column == 5) {
                    l.setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    l.setHorizontalAlignment(SwingConstants.LEFT);
                }

                //Colores en Jtable        
                if (isSelected) {
                    l.setBackground(new Color(203, 159, 41));
                    //l.setBackground(new Color(168, 198, 238));
                    l.setForeground(Color.WHITE);
                } else {
                    l.setForeground(Color.BLACK);
                    if (row % 2 == 0) {
                        l.setBackground(Color.WHITE);
                    } else {
                        //l.setBackground(new Color(232, 232, 232));
                        l.setBackground(new Color(254, 227, 152));
                    }
                }
                return l;
            }
        };

        //Agregar Render
        for (int i = 0; i < tblLista.getColumnCount(); i++) {
            tblLista.getColumnModel().getColumn(i).setCellRenderer(render);
        }

        //Activar ScrollBar
        tblLista.setAutoResizeMode(tblLista.AUTO_RESIZE_OFF);

        //Anchos de cada columna
        int[] anchos = {50, 200, 80, 80, 150, 80, 200};
        for (int i = 0; i < tblLista.getColumnCount(); i++) {
            tblLista.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }
    void BuscarListaUsuario() throws SQLException {
        String titulos[] = {"ID", "Nombre", "IDUsuario"};
        dtm.setColumnIdentifiers(titulos);

        ClssListadeReproduccion categoria = new ClssListadeReproduccion();

        try {
            rs = categoria.listarListadeReproduccionPorParametro("usuario", FrmUsuario.strCodigoU);
            boolean encuentra = false;
            String Datos[] = new String[3];
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

                dtm.addRow(Datos);
                encuentra = true;

            }
            if (encuentra = false) {
                JOptionPane.showMessageDialog(null, "¡No se encuentra!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        tblLista.setModel(dtm);
    }

    void BuscarCancion() throws SQLException {
        String titulos[] = {"ID", "Nombre", "IDUsuario"};
        dtm.setColumnIdentifiers(titulos);

        ClssListadeReproduccion categoria = new ClssListadeReproduccion();
        busqueda = txtBuscar.getText();
        if (rbtnIDLista.isSelected()) {
            criterio = "id";
        } else if (rbtnNombredeLista.isSelected()) {
            criterio = "nombre";
        }
        try {
            
            rs = categoria.listarListadeReproduccionPorParametro(criterio, busqueda);
            boolean encuentra = false;
            String Datos[] = new String[3];
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

                dtm.addRow(Datos);
                encuentra = true;

            }
            if (encuentra = false) {
                JOptionPane.showMessageDialog(null, "¡No se encuentra!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        tblLista.setModel(dtm);
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
        jPanel1 = new javax.swing.JPanel();
        tabLista = new javax.swing.JTabbedPane();
        pBuscar = new javax.swing.JPanel();
        rbtnIDLista = new javax.swing.JRadioButton();
        rbtnNombredeLista = new javax.swing.JRadioButton();
        txtBuscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLista = new javax.swing.JTable();
        pNuevo = new javax.swing.JPanel();
        lblIDLista = new javax.swing.JLabel();
        lblNombreLista = new javax.swing.JLabel();
        lblUsuario_IDUsuario = new javax.swing.JLabel();
        txtIDLista = new javax.swing.JTextField();
        txtNombreLista = new javax.swing.JTextField();
        txtUsuario_IDUsuario = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pBuscar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rbtnIDLista.setText("ID Lista De Reproduccion");
        pBuscar.add(rbtnIDLista, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        rbtnNombredeLista.setText("Nombre de Lista");
        pBuscar.add(rbtnNombredeLista, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 30, -1, -1));

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        pBuscar.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 510, 30));

        jLabel1.setText("Criterios de busqueda");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pBuscar.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 540, 90));
        pBuscar.add(lblEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 10, 20));

        tblLista.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblLista);

        jScrollPane2.setViewportView(jScrollPane1);

        pBuscar.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 540, 260));

        tabLista.addTab("Buscar", pBuscar);

        pNuevo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblIDLista.setText("IDLista de Reproduccion");
        pNuevo.add(lblIDLista, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, -1));

        lblNombreLista.setText("Nombre:");
        pNuevo.add(lblNombreLista, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        lblUsuario_IDUsuario.setText("IDUsuario:");
        pNuevo.add(lblUsuario_IDUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        txtIDLista.setEditable(false);
        pNuevo.add(txtIDLista, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 90, -1));
        pNuevo.add(txtNombreLista, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 200, -1));

        txtUsuario_IDUsuario.setEditable(false);
        pNuevo.add(txtUsuario_IDUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 60, -1));

        tabLista.addTab("Nuevo/Modificar", pNuevo);

        jPanel1.add(tabLista, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 570, 420));

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 60, 80, 30));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 110, 80, 30));

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jPanel1.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 160, 80, 30));

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 210, 80, 30));

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 260, 80, 30));

        jLabel2.setText("Opciones");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 19, 120, 340));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        accion = "Nuevo";
        modificar();
        limpiarCampos();
        txtUsuario_IDUsuario.setText(FrmUsuario.strCodigoU);

        tblLista.setEnabled(false);
        tabLista.setSelectedIndex(tabLista.indexOfComponent(pNuevo));

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (validardatos() == true) {
            if (accion.equals("Nuevo")) {
                try {
                    ClssListadeReproduccion listas = new ClssListadeReproduccion();
                    ClsListadeReproduccion lista = new ClsListadeReproduccion();
                    lista.setStrnombreLista(txtNombreLista.getText());
                    lista.setStrUsuario_Idusuario(txtUsuario_IDUsuario.getText());
                    listas.agregarListadeReproduccion(lista);
                    BuscarListaUsuario();
                    CantidadTotal();
                } catch (SQLException ex) {
                    Logger.getLogger(FrmListadeReproduccion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (accion.equals("Modificar")) {
                try {
                    ClssListadeReproduccion listas = new ClssListadeReproduccion();
                    ClsListadeReproduccion lista = new ClsListadeReproduccion();
                    lista.setStrnombreLista(txtNombreLista.getText());
                    lista.setStrUsuario_Idusuario(txtUsuario_IDUsuario.getText());
                    listas.modificarListadeReproduccion(codigo, lista);
                    BuscarListaUsuario();
                    limpiarCampos();
                    modificar();
                    CantidadTotal();
                } catch (SQLException ex) {
                    Logger.getLogger(FrmListadeReproduccion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //CrearTabla();
            mirar();
            tabLista.setSelectedIndex(tabLista.indexOfComponent(pBuscar));
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        mirar();
        tabLista.setSelectedIndex(tabLista.indexOfComponent(pBuscar));
        // TODO add your handling code here:
    }

    public boolean validardatos() {
        if (txtNombreLista.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese Nombre de lista");
            txtNombreLista.requestFocus();
            txtNombreLista.setBackground(Color.YELLOW);
            return false;

        } else {
            return true;
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        if (tblLista.getSelectedRows().length > 0) {
            accion = "Modificar";
            modificar();
            tabLista.setSelectedIndex(tabLista.indexOfComponent(pNuevo));
        } else {
            JOptionPane.showMessageDialog(null, "¡Se debe seleccionar un registro!");
        }

    }//GEN-LAST:event_btnModificarActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(FrmListadeReproduccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmListadeReproduccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmListadeReproduccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmListadeReproduccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new FrmListadeReproduccion().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(FrmListadeReproduccion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblIDLista;
    private javax.swing.JLabel lblNombreLista;
    private javax.swing.JLabel lblUsuario_IDUsuario;
    private javax.swing.JPanel pBuscar;
    private javax.swing.JPanel pNuevo;
    private javax.swing.JRadioButton rbtnIDLista;
    private javax.swing.JRadioButton rbtnNombredeLista;
    private javax.swing.JTabbedPane tabLista;
    private javax.swing.JTable tblLista;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtIDLista;
    private javax.swing.JTextField txtNombreLista;
    private javax.swing.JTextField txtUsuario_IDUsuario;
    // End of variables declaration//GEN-END:variables
}

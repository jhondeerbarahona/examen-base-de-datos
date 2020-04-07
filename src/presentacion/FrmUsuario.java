/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import Entidades.ClsCancion;
import musica.FrmPrincipal;
import Entidades.ClsUsuario;
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
import procedimientos.ClssCancion;
import procedimientos.ClssUsuario;

/**
 *
 * @author Usuario
 */
public class FrmUsuario extends javax.swing.JInternalFrame {

    private Connection connection = new ClsConexion().getConection();
    String Total;
     static String strCodigoU;
    String Mostrar;
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
    String ID;
    public String UsuarioId;

    public FrmUsuario() throws SQLException {
        initComponents();
        buttonGroup1.add(rbtnID);
        buttonGroup1.add(rbtnNombre);
        buttonGroup1.add(rbtnApellido);

        this.setTitle("Usuario");
        mirar();
        actualizarTabla();

        this.setSize(707, 426);
        CantidadTotal();

    }

    void mirar() {
        tblUsuario.setEnabled(true);
        btnNuevo.setEnabled(true);
        btnModificar.setEnabled(true);
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(false);
        btnSalir.setEnabled(true);
        txtIDUsuario.setEnabled(false);
        txtNombreUsuario.setEnabled(true);
        txtApellido.setEnabled(true);
        txtEmail.setEnabled(true);
        passContraseña.setEnabled(true);

    }

    void actualizarTabla() {
        String titulos[] = {"ID", "Nombre", "Apellido", "Email"};

        ClssUsuario usuarios = new ClssUsuario();
        ArrayList<ClsUsuario> usuario = usuarios.listarUsuario();
        Iterator iterator = usuario.iterator();
        DefaultTableModel defaultTableModel = new DefaultTableModel(null, titulos);

        String fila[] = new String[4];
        while (iterator.hasNext()) {
            ClsUsuario Usuario1 = new ClsUsuario();
            Usuario1 = (ClsUsuario) iterator.next();
            fila[0] = Usuario1.getStrIdusuario();
            fila[1] = Usuario1.getStrNombreUsuario();
            fila[2] = Usuario1.getStrApellidoUsuario();
            fila[3] = Usuario1.getStrCorreoUsuario();
            //fila[4]=Usuario.getStrPaswordUsuario();
            defaultTableModel.addRow(fila);
        }
        tblUsuario.setModel(defaultTableModel);

    }

    void CantidadTotal() {
        Total = String.valueOf(tblUsuario.getRowCount());
    }

    void limpiarCampos() {
        txtIDUsuario.setText("");
        txtNombreUsuario.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        passContraseña.setText("");
        rbtnID.setSelected(false);
        rbtnNombre.setSelected(false);
        rbtnApellido.setSelected(false);
    }

    void modificar() {
        tblUsuario.setEnabled(false);
        btnNuevo.setEnabled(false);
        btnModificar.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnSalir.setEnabled(false);

        txtNombreUsuario.setEnabled(true);
        txtApellido.setEnabled(true);
        txtEmail.setEnabled(true);
        passContraseña.setEnabled(false);
        txtNombreUsuario.requestFocus();
    }

    void BuscarCliente() {
        String titulos[] = {"ID", "Nombre", "Apellido", "Email"};
        dtm.setColumnIdentifiers(titulos);

        ClssUsuario categoria = new ClssUsuario();
        busqueda = txtBuscar.getText();
        if (rbtnID.isSelected()) {
            criterio = "id";
        } else if (rbtnNombre.isSelected()) {
            criterio = "nombre";
        } else if (rbtnApellido.isSelected()) {
            criterio = "apellido";
        }
        try {
            rs = categoria.listarUsuarioPorParametro(criterio, busqueda);
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

                dtm.addRow(Datos);
                encuentra = true;

            }
            if (encuentra = false) {
                JOptionPane.showMessageDialog(null, "¡No se encuentra!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        tblUsuario.setModel(dtm);
    }

    void listardatos() {
        String estado;
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        if (registros == -1) {
            JOptionPane.showMessageDialog(null, "Se debe seleccionar un registro");
        } else {
            defaultTableModel = (DefaultTableModel) tblUsuario.getModel();
            strCodigoU = ((String) defaultTableModel.getValueAt(registros, 0));
            txtIDUsuario.setText((String) defaultTableModel.getValueAt(registros, 0));
            txtNombreUsuario.setText((String) defaultTableModel.getValueAt(registros, 1));
            txtApellido.setText((String) defaultTableModel.getValueAt(registros, 2));
            txtEmail.setText((String) defaultTableModel.getValueAt(registros, 3));
            tblUsuario.setRowSelectionInterval(registros, registros);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        tabUsuario = new javax.swing.JTabbedPane();
        pBuscar = new javax.swing.JPanel();
        rbtnID = new javax.swing.JRadioButton();
        rbtnNombre = new javax.swing.JRadioButton();
        txtBuscar = new javax.swing.JTextField();
        rbtnApellido = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuario = new javax.swing.JTable();
        pNuevo = new javax.swing.JPanel();
        lblIDUsuario = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblApellido = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblContraseña = new javax.swing.JLabel();
        txtIDUsuario = new javax.swing.JTextField();
        txtNombreUsuario = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        passContraseña = new javax.swing.JPasswordField();
        btnCancion = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lblprueba = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pBuscar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rbtnID.setText("ID Usuario");
        rbtnID.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbtnIDStateChanged(evt);
            }
        });
        pBuscar.add(rbtnID, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        rbtnNombre.setText("Nombre");
        rbtnNombre.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbtnNombreStateChanged(evt);
            }
        });
        pBuscar.add(rbtnNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, -1, -1));

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        pBuscar.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 510, 30));

        rbtnApellido.setText("Apellido");
        rbtnApellido.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbtnApellidoStateChanged(evt);
            }
        });
        pBuscar.add(rbtnApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, -1, -1));

        jLabel1.setText("Criterios de busqueda");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pBuscar.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 540, 90));

        tblUsuario.setModel(new javax.swing.table.DefaultTableModel(
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
        tblUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuario);

        jScrollPane2.setViewportView(jScrollPane1);

        pBuscar.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 540, 220));

        tabUsuario.addTab("Buscar", pBuscar);

        pNuevo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblIDUsuario.setText("IDUsuario");
        pNuevo.add(lblIDUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, -1));

        lblNombre.setText("Nombre:");
        pNuevo.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        lblApellido.setText("Apellido:");
        pNuevo.add(lblApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        lblEmail.setText("Email:");
        pNuevo.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        lblContraseña.setText("Contraseña:");
        pNuevo.add(lblContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));
        pNuevo.add(txtIDUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 90, -1));
        pNuevo.add(txtNombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 200, -1));
        pNuevo.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 200, -1));
        pNuevo.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 200, -1));
        pNuevo.add(passContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, 150, -1));

        btnCancion.setText("Agregar Cancion");
        btnCancion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancionActionPerformed(evt);
            }
        });
        pNuevo.add(btnCancion, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 140, 30));

        jButton2.setText("Agregar \nLista de Reproduccion"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        pNuevo.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, 140, 50));

        lblprueba.setText("*");
        pNuevo.add(lblprueba, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, 60, -1));

        tabUsuario.addTab("Nuevo/Modificar", pNuevo);

        getContentPane().add(tabUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 570, 380));
        tabUsuario.getAccessibleContext().setAccessibleName("");

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        getContentPane().add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 60, 90, 30));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 110, 90, 30));

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 160, 90, 30));

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 210, 90, 30));

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 260, 90, 30));

        jLabel2.setText("Opciones");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 30, 110, 270));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        accion = "Nuevo";
        mirar();
        limpiarCampos();
        tblUsuario.setEnabled(false);
        tabUsuario.setSelectedIndex(tabUsuario.indexOfComponent(pNuevo));
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        if (validardatos() == true) {
            if (accion.equals("Nuevo")) {
                ClssUsuario usuarios = new ClssUsuario();
                ClsUsuario usuario = new ClsUsuario();
                usuario.setStrNombreUsuario(txtNombreUsuario.getText());
                usuario.setStrApellidoUsuario(txtApellido.getText());
                usuario.setStrCorreoUsuario(txtEmail.getText());
                usuario.setStrPaswordUsuario(passContraseña.getText());
                usuarios.agregarUsuario(usuario);
                actualizarTabla();
                CantidadTotal();
            }
            if (accion.equals("Modificar")) {
                ClssUsuario clientes = new ClssUsuario();
                ClsUsuario cliente = new ClsUsuario();
                cliente.setStrNombreUsuario(txtNombreUsuario.getText());
                cliente.setStrApellidoUsuario(txtApellido.getText());
                cliente.setStrCorreoUsuario(txtEmail.getText());
                cliente.setStrPaswordUsuario(passContraseña.getText());
                clientes.modificarUsuario(strCodigoU, cliente);
                actualizarTabla();
                limpiarCampos();
                modificar();
                CantidadTotal();
            }
            mirar();
            tabUsuario.setSelectedIndex(tabUsuario.indexOfComponent(pBuscar));
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        if (tblUsuario.getSelectedRows().length > 0) {
            accion = "Modificar";
            modificar();
            tabUsuario.setSelectedIndex(tabUsuario.indexOfComponent(pNuevo));
        } else {
            JOptionPane.showMessageDialog(null, "¡Se debe seleccionar un registro!");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        mirar();
        tabUsuario.setSelectedIndex(tabUsuario.indexOfComponent(pBuscar));
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        presentacion.FrmListadeReproduccion list1;
        try {
            list1 = new FrmListadeReproduccion();
            list1.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(FrmUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnCancionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancionActionPerformed
        try {
            presentacion.FrmCancion cancion = new FrmCancion();

            cancion.setVisible(true);

            // TODO add your handling code here:
        } catch (SQLException ex) {
            Logger.getLogger(FrmUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCancionActionPerformed

    private void tblUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuarioMouseClicked
        // TODO add your handling code here:
        int fila;
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        fila = tblUsuario.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Se debe seleccionar un registro");
        } else {
            defaultTableModel = (DefaultTableModel) tblUsuario.getModel();
            strCodigoU = ((String) defaultTableModel.getValueAt(fila, 0));
            txtIDUsuario.setText((String) defaultTableModel.getValueAt(fila, 0));
            txtNombreUsuario.setText((String) defaultTableModel.getValueAt(fila, 1));
            txtApellido.setText((String) defaultTableModel.getValueAt(fila, 2));
            txtEmail.setText((String) defaultTableModel.getValueAt(fila, 3));
            //passContraseña.setText((String) defaultTableModel.getValueAt(fila, 4));
        }
        mirar();
    }//GEN-LAST:event_tblUsuarioMouseClicked

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        BuscarCliente();
        CantidadTotal();
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void rbtnIDStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbtnIDStateChanged
        txtBuscar.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnIDStateChanged

    private void rbtnNombreStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbtnNombreStateChanged
        txtBuscar.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnNombreStateChanged

    private void rbtnApellidoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbtnApellidoStateChanged
        txtBuscar.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnApellidoStateChanged
    public boolean validardatos() {
        if (txtNombreUsuario.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese Nombre de usuario");
            txtNombreUsuario.requestFocus();
            txtNombreUsuario.setBackground(Color.YELLOW);
            return false;

        } else {
            return true;
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCancion;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblContraseña;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblIDUsuario;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblprueba;
    private javax.swing.JPanel pBuscar;
    private javax.swing.JPanel pNuevo;
    private javax.swing.JPasswordField passContraseña;
    private javax.swing.JRadioButton rbtnApellido;
    private javax.swing.JRadioButton rbtnID;
    private javax.swing.JRadioButton rbtnNombre;
    private javax.swing.JTabbedPane tabUsuario;
    private javax.swing.JTable tblUsuario;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtEmail;
    public static javax.swing.JTextField txtIDUsuario;
    private javax.swing.JTextField txtNombreUsuario;
    // End of variables declaration//GEN-END:variables
}

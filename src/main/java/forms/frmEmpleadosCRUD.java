/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package forms;

import entidades.EmpleadoEntidad;
import entidades.EstatusEntidad;
import enumeradores.AccionCatalogoEnumerador;
import java.util.List;
import javax.swing.JOptionPane;
import persistencia.ConexionBD;
import persistencia.EstatusDAO;
import persistencia.IConexionBD;
import persistencia.IEmpleadoDAO;
import persistencia.IEstatusDAO;
import utilerias.ComboItems;

/**
 *
 * @author Edgar Alonso
 */
public class frmEmpleadosCRUD extends javax.swing.JFrame {

    private EmpleadoEntidad empleado;
    private AccionCatalogoEnumerador accion;
    private final IEmpleadoDAO empleadoDAO;

    /**
     * Creates new form frmEmpleadosCRUD
     */
    public frmEmpleadosCRUD(EmpleadoEntidad empleado, AccionCatalogoEnumerador accion, IEmpleadoDAO empleadoDAO) {
        initComponents();

        this.empleado = empleado;
        this.accion = accion;
        this.empleadoDAO = empleadoDAO;

        //Cargamos los metodos iniciales de la pantalla
        this.MetodosIniciales();
    }

    //Cargamos los metodos iniciales de la pantalla
    private void MetodosIniciales() {
        //Cargamos la configuración inicial de la pantalla
        this.ConfiguracionInicialPantalla();

        //Metodo para buscar la lista de estatus para el combo
        this.buscarListaCombo();

        //Metodo para ingresar el empleado a los controles
        this.setEmpleadoControles();
    }

    //Cargamos la configuración inicial de la pantalla
    private void ConfiguracionInicialPantalla() {
        this.setLocationRelativeTo(null);

        if (this.accion == AccionCatalogoEnumerador.NUEVO) {
            lblTituloPantalla.setText("Nuevo registro de empleado");
            btnAccion.setText("Guardar registro");
        }

        if (this.accion == AccionCatalogoEnumerador.EDITAR) {
            lblTituloPantalla.setText("Editar registro de empleado");
            btnAccion.setText("Editar registro");
        }

        if (this.accion == AccionCatalogoEnumerador.ELIMINAR) {
            lblTituloPantalla.setText("Eliminar registro de empleado");
            btnAccion.setText("Eliminar registro");
        }
    }

    //Metodo para buscar la lista de estatus para el combo
    private void buscarListaCombo() {
        IConexionBD conexionBD = new ConexionBD();
        IEstatusDAO estatusDAO = new EstatusDAO(conexionBD);

        List<EstatusEntidad> lstEstatus = estatusDAO.buscarListaCombo();

        if (lstEstatus == null) {
            JOptionPane.showMessageDialog(this, "La lista de estatus no se pudo obtener",
                    "Información", JOptionPane.ERROR_MESSAGE);
        }

        //Metodo para llenar el combo con los estatus
        this.llenarComboBox(lstEstatus);
    }

    //Metodo para llenar el combo con los estatus
    private void llenarComboBox(List<EstatusEntidad> lstEstatus) {
        cboEstatus.removeAllItems();

        if (lstEstatus != null) {
            lstEstatus.forEach(item -> {

                String nombre = item.getNombre();
                String id = Integer.toString(item.getIdEstatus());

                cboEstatus.addItem(new ComboItems(id, nombre));
            });
        }
    }

    //Metodo para ingresar el empleado a los controles
    private void setEmpleadoControles() {
        txtNombres.setText(this.empleado.getNombre());
        txtApellidoPaterno.setText(this.empleado.getApellidoPaterno());
        txtApellidoMaterno.setText(this.empleado.getApellidoMaterno());

        String id = Integer.toString(this.empleado.getIdEstatus());

        cboEstatus.setSelectedItem(new ComboItems(id));
    }

    //Metodo para obtener el empleado de los controles
    private EmpleadoEntidad getEmpleadoControles() {
        ComboItems opcion = (ComboItems) this.cboEstatus.getSelectedItem();

        int idEstatus = Integer.parseInt(opcion.getId());

        return new EmpleadoEntidad(this.empleado.getIdEmpleado(), txtNombres.getText(), txtApellidoPaterno.getText(), txtApellidoMaterno.getText(), idEstatus);
    }

    //Metodo para guardar un nuevo registro de empleado
    private void guardar() {
        EmpleadoEntidad empleadoNuevo = this.empleadoDAO.guardar(getEmpleadoControles());
        if (empleadoNuevo != null) {
            JOptionPane.showMessageDialog(this, "Se agregó el nuevo empleado",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No fue posible agregar al empleado",
                    "Información", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Metodo para editar el registro de empleado
    private void editar() {
        EmpleadoEntidad empleadoNuevo = this.empleadoDAO.editar(getEmpleadoControles());
        if (empleadoNuevo != null) {
            JOptionPane.showMessageDialog(this, "Se edito el empleado",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No fue posible editar al empleado",
                    "Información", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Metodo para elininar el registro de empleado
    private void eliminar() {
        EmpleadoEntidad empleadoNuevo = this.empleadoDAO.eliminar(getEmpleadoControles());
        if (empleadoNuevo != null) {
            JOptionPane.showMessageDialog(this, "Se elimino el empleado",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No fue posible eliminar al empleado",
                    "Información", JOptionPane.ERROR_MESSAGE);
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

        lblTituloPantalla = new javax.swing.JLabel();
        lblNombres = new javax.swing.JLabel();
        txtNombres = new javax.swing.JTextField();
        lblApellidoPaterno = new javax.swing.JLabel();
        txtApellidoPaterno = new javax.swing.JTextField();
        txtApellidoMaterno = new javax.swing.JTextField();
        lblApellidoMaterno = new javax.swing.JLabel();
        btnAccion = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        cboEstatus = new javax.swing.JComboBox<>();
        lblEstatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        lblTituloPantalla.setText("titulo pantalla");

        lblNombres.setText("Nombres:");

        lblApellidoPaterno.setText("Apellido paterno:");

        lblApellidoMaterno.setText("Apellido materno:");

        btnAccion.setText("Accion");
        btnAccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccionActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        lblEstatus.setText("Estatus:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(lblApellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtApellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblApellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNombres)
                                .addComponent(txtApellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAccion)
                        .addGap(3, 3, 3))
                    .addComponent(lblTituloPantalla, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblEstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboEstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblTituloPantalla)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombres)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblApellidoPaterno)
                    .addComponent(txtApellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblApellidoMaterno)
                    .addComponent(txtApellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboEstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEstatus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAccion)
                    .addComponent(btnCancelar))
                .addGap(32, 32, 32))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccionActionPerformed
        if (accion == accion.NUEVO) {
            guardar();
        }
        if (accion == accion.EDITAR) {
            editar();
        }
        if (accion == accion.ELIMINAR) {
            eliminar();
        }
    }//GEN-LAST:event_btnAccionActionPerformed

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
            java.util.logging.Logger.getLogger(frmEmpleadosCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmEmpleadosCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmEmpleadosCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmEmpleadosCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new frmEmpleadosCRUD().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccion;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox<ComboItems> cboEstatus;
    private javax.swing.JLabel lblApellidoMaterno;
    private javax.swing.JLabel lblApellidoPaterno;
    private javax.swing.JLabel lblEstatus;
    private javax.swing.JLabel lblNombres;
    private javax.swing.JLabel lblTituloPantalla;
    private javax.swing.JTextField txtApellidoMaterno;
    private javax.swing.JTextField txtApellidoPaterno;
    private javax.swing.JTextField txtNombres;
    // End of variables declaration//GEN-END:variables
}

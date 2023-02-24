/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package forms;

import entidades.EmpleadoEntidad;
import enumeradores.AccionCatalogoEnumerador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import persistencia.IEmpleadoDAO;
import utilerias.JButtonCellEditor;
import utilerias.JButtonRenderer;
import utilerias.Utilidades;

/**
 *
 * @author Edgar Alonso
 */
public class frmEmpleadosList extends javax.swing.JFrame {

    private final IEmpleadoDAO iEmpleadoDAO;
    private int pagina = 1;
    private int limite = 2;

    /**
     * Creates new form frmEmpleadosList
     */
    public frmEmpleadosList(IEmpleadoDAO iEmpleadoDAO) {
        initComponents();

        this.iEmpleadoDAO = iEmpleadoDAO;

        //Cargamos los metodos iniciales de la pantalla
        this.MetodosIniciales();
    }

    //Cargamos los metodos iniciales de la pantalla
    private void MetodosIniciales() {
        //Cargamos la configuración inicial de la pantalla
        this.ConfiguracionInicialPantalla();

        //Configuracion inicial de la tabla de empleados
        this.configuracionInicialTablaEmpleados();

        //Metodo para buscar a los empleados y mostrarlos en su tabla
        this.buscarEmpleadosTabla();
    }

    //Cargamos la configuración inicial de la pantalla
    private void ConfiguracionInicialPantalla() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    //Configuracion inicial de la tabla de empleados
    private void configuracionInicialTablaEmpleados() {
        ActionListener onEditarClickListener = new ActionListener() {
            final int columnaId = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                //Metodo para editar un empleado
                editar();
            }
        };
        int indiceColumnaEditar = 4;
        TableColumnModel modeloColumnas = this.tblEmpleados.getColumnModel();
        modeloColumnas.getColumn(indiceColumnaEditar)
                .setCellRenderer(new JButtonRenderer("Editar"));
        modeloColumnas.getColumn(indiceColumnaEditar)
                .setCellEditor(new JButtonCellEditor("Editar",
                        onEditarClickListener));

        ActionListener onEliminarClickListener = new ActionListener() {
            final int columnaId = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                //Metodo para eliminar un empleado
                eliminar();
            }
        };
        int indiceColumnaEliminar = 5;
        modeloColumnas = this.tblEmpleados.getColumnModel();
        modeloColumnas.getColumn(indiceColumnaEliminar)
                .setCellRenderer(new JButtonRenderer("Eliminar"));
        modeloColumnas.getColumn(indiceColumnaEliminar)
                .setCellEditor(new JButtonCellEditor("Eliminar",
                        onEliminarClickListener));
    }

    //Metodo para buscar a los empleados y mostrarlos en su tabla
    public void buscarEmpleadosTabla() {
        Utilidades utilidades = new Utilidades();
        int offset = utilidades.RegresarOFFSETMySQL(this.limite, this.pagina);

        String filtro = txtBusqueda.getText();
        List<EmpleadoEntidad> lstEmpleados = this.iEmpleadoDAO.buscarListaTabla(filtro, this.limite, offset);

        if (lstEmpleados == null) {
            JOptionPane.showMessageDialog(this, "Empleados no obtenidos",
                    "Información", JOptionPane.ERROR_MESSAGE);
        }

        //Metodo para llenar la tabla de empleados
        this.llenarTablaEmpleados(lstEmpleados);
    }

    //Metodo para llenar la tabla de empleados
    private void llenarTablaEmpleados(List<EmpleadoEntidad> lstEmpleados) {
        DefaultTableModel modeloTabla = (DefaultTableModel) this.tblEmpleados.getModel();

        if (modeloTabla.getRowCount() > 0) {
            for (int i = modeloTabla.getRowCount() - 1; i > -1; i--) {
                modeloTabla.removeRow(i);
            }
        }

        if (lstEmpleados != null) {
            lstEmpleados.forEach(row -> {
                Object[] fila = new Object[4];
                fila[0] = row.getIdEmpleado();
                fila[1] = row.getNombre();
                fila[2] = row.getApellidoPaterno();
                fila[3] = row.getApellidoMaterno();

                modeloTabla.addRow(fila);
            });
        }
    }

    //Método para obtener el titulo de la paginacion
    private void ObtenerTituloPaginacion() {
        String paginaUsuario = Integer.toString(pagina);
        lblNumeroPaginacion.setText(paginaUsuario);
    }

    //Metodo para regresar el empleado seleccionado de la tabla empleados
    private int getIdEmpleadoSeleccionadoTabla() {
        int indiceFilaSeleccionada = this.tblEmpleados.getSelectedRow();
        if (indiceFilaSeleccionada != -1) {
            DefaultTableModel modelo = (DefaultTableModel) this.tblEmpleados.getModel();
            int indiceColumnaId = 0;
            int idSocioSeleccionado = (int) modelo.getValueAt(indiceFilaSeleccionada,
                    indiceColumnaId);
            return idSocioSeleccionado;
        } else {
            return 0;
        }
    }

    //Metodo para obtener el empleado por su id
    private EmpleadoEntidad buscarPorIdEmpleado(int idEmpleado) {
        EmpleadoEntidad empleado = this.iEmpleadoDAO.buscarPorIdEmpleado(idEmpleado);
        return empleado;
    }

    //Metodo para editar un empleado
    private void editar() {
        //Metodo para regresar el empleado seleccionado de la tabla empleados
        int idEmpleado = this.getIdEmpleadoSeleccionadoTabla();

        //Metodo para obtener el empleado por su id
        EmpleadoEntidad empleado = this.buscarPorIdEmpleado(idEmpleado);

        frmEmpleadosCRUD objFrmEmpleadosCRUD = new frmEmpleadosCRUD(empleado, AccionCatalogoEnumerador.EDITAR, iEmpleadoDAO);
        objFrmEmpleadosCRUD.show();
    }

    //Metodo para eliminar un empleado
    private void eliminar() {
        //Metodo para regresar el empleado seleccionado de la tabla empleados
        int idEmpleado = this.getIdEmpleadoSeleccionadoTabla();

        EmpleadoEntidad empleado = this.buscarPorIdEmpleado(idEmpleado);

        frmEmpleadosCRUD objFrmEmpleadosCRUD = new frmEmpleadosCRUD(empleado, AccionCatalogoEnumerador.ELIMINAR, iEmpleadoDAO);
        objFrmEmpleadosCRUD.show();
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
        txtBusqueda = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        lblTituloPaginacion = new javax.swing.JLabel();
        lblNumeroPaginacion = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmpleados = new javax.swing.JTable();
        btnNuevo = new javax.swing.JButton();
        btnPaginaSiguiente = new javax.swing.JButton();
        btnPaginaAnterior = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTituloPantalla.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTituloPantalla.setText("Administración de empleados");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        lblTituloPaginacion.setText("Pagina:");

        lblNumeroPaginacion.setText("1");

        tblEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Clave", "Nombres", "A. Paterno", "A. Materno", "Editar", "Eliminar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblEmpleados);
        if (tblEmpleados.getColumnModel().getColumnCount() > 0) {
            tblEmpleados.getColumnModel().getColumn(0).setResizable(false);
            tblEmpleados.getColumnModel().getColumn(1).setResizable(false);
            tblEmpleados.getColumnModel().getColumn(2).setResizable(false);
            tblEmpleados.getColumnModel().getColumn(3).setResizable(false);
        }

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnPaginaSiguiente.setText("Página siguiente");
        btnPaginaSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaginaSiguienteActionPerformed(evt);
            }
        });

        btnPaginaAnterior.setText("Página anterior");
        btnPaginaAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaginaAnteriorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTituloPantalla, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnNuevo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 371, Short.MAX_VALUE)
                                .addComponent(lblTituloPaginacion, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtBusqueda, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNumeroPaginacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnPaginaAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPaginaSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblTituloPantalla)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTituloPaginacion)
                    .addComponent(lblNumeroPaginacion)
                    .addComponent(btnNuevo))
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPaginaSiguiente)
                    .addComponent(btnPaginaAnterior))
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPaginaAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaginaAnteriorActionPerformed
        if (this.pagina > 1) {
            this.pagina--;

            //Método para obtener el titulo de la pagina
            this.ObtenerTituloPaginacion();

            //Metodo para buscar a los empleados y mostrarlos en su tabla
            this.buscarEmpleadosTabla();
        }
    }//GEN-LAST:event_btnPaginaAnteriorActionPerformed

    private void btnPaginaSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaginaSiguienteActionPerformed
        this.pagina++;

        //Método para obtener el titulo de la pagina
        this.ObtenerTituloPaginacion();

        //Metodo para buscar a los empleados y mostrarlos en su tabla
        this.buscarEmpleadosTabla();
    }//GEN-LAST:event_btnPaginaSiguienteActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        //Metodo buscar los empleados y mostrarlos en su tabla
        this.buscarEmpleadosTabla();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        frmEmpleadosCRUD objFrmEmpleadosCRUD = new frmEmpleadosCRUD(new EmpleadoEntidad(), AccionCatalogoEnumerador.NUEVO, iEmpleadoDAO);
        objFrmEmpleadosCRUD.show();
    }//GEN-LAST:event_btnNuevoActionPerformed

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
            java.util.logging.Logger.getLogger(frmEmpleadosList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmEmpleadosList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmEmpleadosList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmEmpleadosList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new frmEmpleadosList().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnPaginaAnterior;
    private javax.swing.JButton btnPaginaSiguiente;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblNumeroPaginacion;
    private javax.swing.JLabel lblTituloPaginacion;
    private javax.swing.JLabel lblTituloPantalla;
    private javax.swing.JTable tblEmpleados;
    private javax.swing.JTextField txtBusqueda;
    // End of variables declaration//GEN-END:variables
}

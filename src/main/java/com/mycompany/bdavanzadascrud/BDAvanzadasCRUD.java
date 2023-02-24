package com.mycompany.bdavanzadascrud;

import forms.frmEmpleadosList;
import persistencia.ConexionBD;
import persistencia.EmpleadoDAO;
import persistencia.IConexionBD;
import persistencia.IEmpleadoDAO;

/**
 *
 * @author Edgar Alonso
 */
public class BDAvanzadasCRUD {

    public static void main(String[] args) {
        IConexionBD conexionBD = new ConexionBD();
        IEmpleadoDAO iEmpleadoDAO = new EmpleadoDAO(conexionBD);        
        frmEmpleadosList obj = new frmEmpleadosList(iEmpleadoDAO);
        obj.show();
    }
}

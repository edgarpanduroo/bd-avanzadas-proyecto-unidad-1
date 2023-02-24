/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import entidades.EmpleadoEntidad;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edgar Alonso
 */
public class EmpleadoDAO implements IEmpleadoDAO {

    private IConexionBD conexionBD;

    public EmpleadoDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public List<EmpleadoEntidad> buscarListaTabla(String filtro, int limit, int offset) {

        List<EmpleadoEntidad> lstEmpleados = null;

        try {
            Connection conexion = this.conexionBD.crearConexion();

            Statement comandoSQL = conexion.createStatement();

            String where = "WHERE nombres LIKE '%" + filtro + "%' OR apellidoPaterno LIKE '%" + filtro + "%' OR apellidoMaterno LIKE '%" + filtro + "%'";
            String orderBy = "ORDER BY nombres, ApellidoPaterno, ApellidoMaterno";
            String codigoSQL = "SELECT * FROM tblempleados " + where + orderBy + " LIMIT " + limit + " OFFSET " + offset;

            ResultSet resultado = comandoSQL.executeQuery(codigoSQL);

            while (resultado.next()) {

                if (lstEmpleados == null) {
                    lstEmpleados = new ArrayList<>();
                }

                int idEmpleado = resultado.getInt("idEmpleado");
                String nombre = resultado.getString("nombres");
                String apellidoPaterno = resultado.getString("apellidoPaterno");
                String apellidoMaterno = resultado.getString("apellidoMaterno");
                int idEstatus = resultado.getInt("idEstatus");

                EmpleadoEntidad empleado = new EmpleadoEntidad(idEmpleado, nombre, apellidoPaterno, apellidoMaterno, idEstatus);
                lstEmpleados.add(empleado);
            }

            conexion.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return lstEmpleados;
    }

    @Override
    public EmpleadoEntidad buscarPorIdEmpleado(int idEmpleado) {
        EmpleadoEntidad empleado = null;

        try {
            Connection conexion = this.conexionBD.crearConexion();

            Statement comandoSQL = conexion.createStatement();

            String codigoSQL = String.format("SELECT * FROM tblempleados WHERE idEmpleado = " + idEmpleado);

            ResultSet resultado = comandoSQL.executeQuery(codigoSQL);

            while (resultado.next()) {

                String nombre = resultado.getString("nombres");
                String apellidoPaterno = resultado.getString("apellidoPaterno");
                String apellidoMaterno = resultado.getString("apellidoMaterno");
                int idEstatus = resultado.getInt("idEstatus");

                empleado = new EmpleadoEntidad(idEmpleado, nombre, apellidoPaterno, apellidoMaterno, idEstatus);
            }

            conexion.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return empleado;
    }

    @Override
    public EmpleadoEntidad guardar(EmpleadoEntidad empleado) {
        EmpleadoEntidad empleadoGuardado = null;

        try {

            Connection conexion = this.conexionBD.crearConexion();

            // DEFINIMOS UN OBJETO STATEMENT PARA ENVIAR COMANDOS SQL
            Statement comandoSql = conexion.createStatement();

            String codigoSQL = String.format("INSERT INTO `tblempleados` (`nombres`,`apellidoPaterno`,`apellidoMaterno`,`idEstatus`) VALUES ('" + empleado.getNombre() + "','" + empleado.getApellidoPaterno() + "','" + empleado.getApellidoMaterno() + "'," + empleado.getIdEstatus() + ");");

            // ESTE MÉTODO SE UTILIZA PARA HACER OPERACIONES QUE ALTEREN LOS DATOS (INSERT, DELETE, UPDATE)
            int numeroRegistrosAfectados = comandoSql.executeUpdate(codigoSQL, Statement.RETURN_GENERATED_KEYS);

            ResultSet resultado = comandoSql.getGeneratedKeys();
            while (resultado.next()) {
                empleado.setIdEmpleado(resultado.getInt(1));
            }

            // SOLICITAR CERRAR EXPLICITAMENTE LA CONEXIÓN HACIA LA BD
            conexion.close();

            empleadoGuardado = empleado;

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return empleadoGuardado;
        }

        return empleadoGuardado;
    }

    @Override
    public EmpleadoEntidad editar(EmpleadoEntidad empleado) {
        EmpleadoEntidad empleadoEditado = null;

        try {

            Connection conexion = this.conexionBD.crearConexion();

            // DEFINIMOS UN OBJETO STATEMENT PARA ENVIAR COMANDOS SQL
            Statement comandoSql = conexion.createStatement();

            String codigoSQL = String.format("UPDATE `tblempleados` SET `nombres` = '" + empleado.getNombre() + "', `apellidoPaterno` = '" + empleado.getApellidoPaterno() + "', `apellidoMaterno` = '" + empleado.getApellidoMaterno() + "', `idEstatus` = " + empleado.getIdEstatus() + " WHERE (`idEmpleado` = '" + empleado.getIdEmpleado() + "');");

            // ESTE MÉTODO SE UTILIZA PARA HACER OPERACIONES QUE ALTEREN LOS DATOS (INSERT, DELETE, UPDATE)
            int numeroRegistrosAfectados = comandoSql.executeUpdate(codigoSQL);

            // SOLICITAR CERRAR EXPLICITAMENTE LA CONEXIÓN HACIA LA BD
            conexion.close();

            empleadoEditado = empleado;

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        }

        return empleadoEditado;
    }

    @Override
    public EmpleadoEntidad eliminar(EmpleadoEntidad empleado) {
        EmpleadoEntidad empleadoEliminado = null;

        try {

            Connection conexion = this.conexionBD.crearConexion();

            // DEFINIMOS UN OBJETO STATEMENT PARA ENVIAR COMANDOS SQL
            Statement comandoSql = conexion.createStatement();

            String codigoSQL = String.format("DELETE FROM `tblempleados` WHERE (`idEmpleado` = '" + empleado.getIdEmpleado() + "');");

            // ESTE MÉTODO SE UTILIZA PARA HACER OPERACIONES QUE ALTEREN LOS DATOS (INSERT, DELETE, UPDATE)
            int numeroRegistrosAfectados = comandoSql.executeUpdate(codigoSQL);

            // SOLICITAR CERRAR EXPLICITAMENTE LA CONEXIÓN HACIA LA BD
            conexion.close();

            empleadoEliminado = empleado;

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        }

        return empleadoEliminado;
    }
}

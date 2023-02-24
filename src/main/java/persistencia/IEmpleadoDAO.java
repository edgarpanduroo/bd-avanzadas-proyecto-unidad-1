/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import entidades.EmpleadoEntidad;
import java.util.List;

/**
 *
 * @author Edgar Alonso
 */
public interface IEmpleadoDAO {

    public List<EmpleadoEntidad> buscarListaTabla(String filtro, int limit, int offset);

    public EmpleadoEntidad buscarPorIdEmpleado(int idEmpleado);

    public EmpleadoEntidad guardar(EmpleadoEntidad empleado);

    public EmpleadoEntidad editar(EmpleadoEntidad empleado);

    public EmpleadoEntidad eliminar(EmpleadoEntidad empleado);

}

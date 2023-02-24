/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import entidades.EstatusEntidad;
import java.util.List;

/**
 *
 * @author Edgar Alonso
 */
public interface IEstatusDAO {
     public List<EstatusEntidad> buscarListaCombo();
}

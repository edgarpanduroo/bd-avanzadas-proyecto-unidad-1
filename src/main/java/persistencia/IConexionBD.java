/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Connection;
import java.sql.SQLException;
/**
 *
 * @author Edgar Alonso
 */
public interface IConexionBD {
    public Connection crearConexion() throws SQLException;
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;
import entidades.EstatusEntidad;
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
public class EstatusDAO implements IEstatusDAO {
    private IConexionBD conexionBD;
    
    public EstatusDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }
    
    @Override
    public List<EstatusEntidad> buscarListaCombo() {
       List<EstatusEntidad> lstEstatus = null;
        
        try {
            Connection conexion = this.conexionBD.crearConexion();
            
            Statement comandoSQL = conexion.createStatement();
            
            String codigoSQL = String.format("SELECT * FROM practica.tblEstatus ORDER BY nombre");

            ResultSet resultado = comandoSQL.executeQuery(codigoSQL);
            
            while(resultado.next()){
                
                if(lstEstatus == null) {
                    lstEstatus = new ArrayList<>();
                }
                
                int idEstatus = resultado.getInt("idEstatus");
                String nombre = resultado.getString("nombre");
                
                EstatusEntidad estatus = new EstatusEntidad(idEstatus, nombre);
                lstEstatus.add(estatus);
            }
            
            conexion.close();
            
        }catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return lstEstatus;
    }
    
}

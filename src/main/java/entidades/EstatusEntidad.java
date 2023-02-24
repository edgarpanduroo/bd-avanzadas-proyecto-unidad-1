/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author Edgar Alonso
 */
public class EstatusEntidad {
    
    private int idEstatus;
    private String Nombre;
    
    public EstatusEntidad() {
        
    }
    
    public EstatusEntidad(int idEstatus, String nombre) {
        this.idEstatus = idEstatus;
        this.Nombre = nombre;
    }

    public int getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(int idEstatus) {
        this.idEstatus = idEstatus;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
}

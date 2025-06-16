/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.labintec.model;


import dev.labintec.model.entities.Tramite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP Micaela
 */
public class RepositoryTransaction implements IRepo <Tramite>{ //CRUD
    
    private Connection connection;

    public RepositoryTransaction(Connection connection) {
        this.connection = connection;//recibe una conecccion para interactuar con la base de datos.
    }
    
    /**
     * Método que permite insertar un nuevo tramite en la base de datos.
     * Recibe un objeto Tramite y guarda sus datos (tipo,estado y descripcion).
     */
    
    @Override
    public void create(Tramite entity) {
        try {
            String query = "insert into tramite (tipo, estado, descripcion) values (?,?,?)";
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, entity.getTipo());
            psmt.setString(2, entity.getEstado());
            psmt.setString(3, entity.getDescripcion());
            psmt.executeUpdate();
            psmt.close();
            System.out.println("TRAMITE CREADO CON EXITO. ");
        } catch (SQLException ex) {
            System.out.println("NO SE PUEDO CREAR LA CONSULTA. " + ex.getMessage());
        }
    }
    
     /**
     * Método para obtener un Tramite por su ID.
     * Retorna un objeto Tramite si lo encuentra, o null si no existe.
     */

    @Override
    public Tramite read(int id) {
        Tramite t = null;
        try {
            String query = "select * from tramite where id = ?";
            PreparedStatement psmt =connection.prepareStatement(query);
            psmt.setInt(1, id);
            ResultSet rs = psmt.executeQuery();
            
            if (rs.next()) { 
                t = new Tramite(rs.getInt("id"), rs.getString("tipo"), rs.getString("estado"), rs.getString("descripcion"));
            }
            rs.close();
            psmt.close();
        } catch (SQLException ex) {
            System.out.println("ERROR AL CONECTAR A LA BASE DE DATOS. " + ex.getMessage());
        }
        return t;
    }
    
    /**
     * Método que obtiene todos los Tramites de la base de datos.
     * Retorna una lista de objetos Tramite.
     */
    
    @Override
    public List<Tramite> readAll() {
        List<Tramite> tramite = new ArrayList();
        try {
            String query = "select * from tramite";
            PreparedStatement psmt = connection.prepareStatement(query);
            ResultSet rs = psmt.executeQuery();
               
            while(rs.next()) {
                Tramite t = new Tramite();
                t.setId(rs.getInt("id"));
                t.setTipo(rs.getString("tipo"));
                t.setEstado(rs.getString("estado"));
                t.setDescripcion(rs.getString("descripcion"));
                tramite.add(t);
            }
            psmt.close();
        } catch (SQLException ex) {
            System.out.println("ERROR AL CONECTAR A LA BASE DE DATOS. " + ex.getMessage());
        }
        return tramite;
    }
    
     /**
     * Método para actualizar los datos de un tramite.
     * Modifica el tipo,estado y descripcion del tramite con el ID especificado.
     */

    @Override
    public void update(Tramite entity) {
                  try {
        String query = "UPDATE Tramite SET tipo = ?, estado = ?, descripcion =? WHERE id = ?";
        PreparedStatement psmt = connection.prepareStatement(query);
        psmt.setString(1, entity.getTipo()); 
        psmt.setString(2, entity.getEstado());
        psmt.setString(3, entity.getDescripcion());
        psmt.setInt(4, entity.getId());          
            psmt.executeUpdate();
            psmt.close();
        } catch (SQLException ex) {
            System.out.println("NO SE PUDO CREAR LA CONSULTA. " + ex.getMessage());
        }
    }
    
     /**
     * Método para eliminar un tramite de la base de datos según su ID.
     */

    @Override
    public void delete(int  id) {
        try {
            String query = "delete from Tramite where id = ?";
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setInt(1, id);
            psmt.executeUpdate();
            psmt.close();      
        } catch (SQLException ex) {
            System.out.println("NO SE PUDO  ELIMINAR TRAMITE.");
        }
    }
    
    public Tramite readByTipo(String tipo) {
    Tramite t = null;
    try {
        String query = "SELECT * FROM tramite WHERE tipo = ?";
        PreparedStatement psmt = connection.prepareStatement(query);
        psmt.setString(1, tipo);
        ResultSet rs = psmt.executeQuery();
        while (rs.next()) {
            t = new Tramite(
                rs.getInt("id"),
                rs.getString("tipo"),
                rs.getString("estado"),
                rs.getString("descripcion")
            );
        }
        psmt.close();
    } catch (SQLException ex) {
        System.out.println("ERROR AL LEER TRAMITE POR TIPO: " + ex.getMessage());
    }
    return t;
    }
}

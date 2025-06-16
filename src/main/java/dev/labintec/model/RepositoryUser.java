/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.labintec.model;

import dev.labintec.model.entities.Usuario;
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
public class RepositoryUser implements IRepo <Usuario> { //CRUD
      
    private Connection connection; 

    public RepositoryUser() {
    }

    public RepositoryUser(Connection connection) {
        this.connection = connection; //recibe una conecccion para interactuar con la base de datos.
    }
    
    /**
     * Método que permite insertar un nuevo usuario en la base de datos.
     * Recibe un objeto Usuario y guarda sus datos (username y password).
     */
    
    @Override
    public void create(Usuario entity) {
        try {
            String query = "insert into usuario (username, passw) values (?,?)";
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, entity.getUsername());
            psmt.setString(2, entity.getPassword());
            psmt.executeUpdate();
            psmt.close();
            System.out.println("USUARIO CREADO CON EXITO. ");
        } catch (SQLException ex) {
            System.out.println("NO SE PUEDO CREAR LA CONSULTA. " + ex.getMessage());
        }
    }
    
     /**
     * Método para obtener un usuario por su ID.
     * Retorna un objeto Usuario si lo encuentra, o null si no existe.
     */

    @Override
    public Usuario read(int id) {
        Usuario u = null;
        try {
            String query = "select * from usuario where id = ?";
            PreparedStatement psmt =connection.prepareStatement(query);
            psmt.setInt(1, id); // Setea el parámetro ID en la consulta
            ResultSet rs = psmt.executeQuery();// Ejecuta la consulta
            if (rs.next()) { // Si se encontró un resultado
                u = new Usuario(rs.getInt("id"), rs.getString("username"), rs.getString("passw"));
            }
            rs.close();
            psmt.close();
        } catch (SQLException ex) {
            System.out.println("ERROR AL CONECTAR A LA BASE DE DATOS. " + ex.getMessage());
        }
        return u;
    }
     
    /**
     * Método que obtiene todos los usuarios de la base de datos.
     * Retorna una lista de objetos Usuario.
     */

    @Override
    public List<Usuario> readAll() {
        List<Usuario> usuarios = new ArrayList();// Lista para almacenar los usuarios
        try {
            String query = "select * from usuario";
            PreparedStatement psmt = connection.prepareStatement(query);
            ResultSet rs = psmt.executeQuery();
            while(rs.next()) {
                Usuario u = new Usuario();// Crea un nuevo objeto usuario
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                usuarios.add(u);// Agrega el usuario a la lista
            }
            psmt.close();
        } catch (SQLException ex) {
            System.out.println("ERROR AL CONECTAR A LA BASE DE DATOS. " + ex.getMessage());
        }
        return usuarios;
    }

    /**
     * Método para actualizar los datos de un usuario.
     * Modifica el username y password del usuario con el ID especificado.
     */
    
    @Override
    public void update(Usuario entity) { 
          try {
        String query = "UPDATE usuario SET username = ?, passw = ? WHERE id = ?";
        PreparedStatement psmt = connection.prepareStatement(query);
        psmt.setString(1, entity.getUsername());
        psmt.setString(2, entity.getPassword()); 
        psmt.setInt(3, entity.getId());          
        psmt.executeUpdate();// Ejecuta la actualización
        psmt.close();
        } catch (SQLException ex) {
            System.out.println("NO SE PUDO CREAR LA CONSULTA. " + ex.getMessage());
        }
    }

    /**
     * Método para eliminar un usuario de la base de datos según su ID.
     */
    @Override
    public void delete(int id) {
        try {
            String query = "delete from usuario where id = ?";
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setInt(1, id);// Setea el ID del usuario a eliminar
            psmt.executeUpdate();
            psmt.close();      
        } catch (SQLException ex) {
            System.out.println("NO SE PUDO  ELIMINAR USUARIO.");
        }
    }
    
    /**
     * Método adicional que permite obtener un usuario por su nombre de usuario (username).
     * Útil para autenticación o validaciones.
     */
    
    public Usuario readByUsername(String username) {
        Usuario u = null;
        try {
            String query = "select * from usuario where username = ?";
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, username);
            ResultSet rs = psmt.executeQuery();
            while(rs.next()) {
                u = new Usuario(rs.getInt("id"), rs.getString("username"),rs.getString("passw"));
            }
            psmt.close();
        } catch (SQLException ex) {
            System.out.println(" ERROR AL LEER USUARIO POR USERNAME: " + ex.getMessage() );
        }
        return u;
    }
}

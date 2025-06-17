/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.labintec.model;

import dev.labintec.model.entities.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author HP Micaela
 */
public class RepositoryUser implements IRepo <Usuario> { //CRUD
      

    private EntityManagerFactory emf;
    private EntityManager em;

    public RepositoryUser() {
        emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        em = emf.createEntityManager();
    }

    public RepositoryUser(EntityManager em) {
        this.em = em;
    }
    
    /**
     * Método que permite insertar un nuevo usuario en la base de datos.
     * Abre una transacción, guarda el objeto y la confirma.
     */
    
    @Override
    public void create(Usuario entity) {
        try {
          em.getTransaction().begin();
          em.persist(entity);
          em.getTransaction().commit();
            System.out.println("USUARIO CREADO CON EXITO.");
        } catch (Exception ex) {
            em.getTransaction().rollback(); // Si hay error, se revierte
            System.out.println("NO SE PUEDO CREAR LA CONSULTA. " + ex.getMessage());
        }
    }
    
    /**
     * Método que busca un usuario por su ID.
     * Utiliza el método find de JPA.
     */

    @Override
    public Usuario read(int id) {
        Usuario u = null;
        try {
            return em.find(Usuario.class, id);
        } catch (Exception ex) {
            
            System.out.println("ERROR AL LEER USUARIO POR ID. " + ex.getMessage());
        }
        return u;
    }
     
    /**
     * Método que recupera todos los usuarios de la base de datos.
     * Utiliza JPQL (Java Persistence Query Language).
     */
    @Override
    public List<Usuario> readAll() {
          List<Usuario> usuarios = new ArrayList<>();
         try {
             usuarios = em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
         } catch (Exception ex) {
             System.out.println("ERROR AL LEER USUARIOS. " + ex.getMessage());
        }
        return usuarios;
    }


    /**
     * Método que actualiza los datos de un usuario.
     * Usa merge para sincronizar el objeto actualizado con la base de datos.
     */
    
    @Override
    public void update(Usuario entity) { 
          try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("NO SE PUDO CREAR LA CONSULTA. " + ex.getMessage());
        }
    }

    /**
     * Método que elimina un usuario por su ID.
     * Primero busca el objeto y luego lo elimina dentro de una transacción.
     */
    @Override
    public void delete(int id) {
        Usuario u = em.find(Usuario.class, id);
        if (u != null) {
            em.getTransaction().begin();
            em.remove(u);
            em.getTransaction().commit();
        }
    }
    
    /**
     * Método adicional para buscar un usuario por su username.
     * Devuelve el objeto si lo encuentra o null si no existe.
     */
    
    public Usuario readByUsername(String username) {
        Usuario u = null;
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.username = :username", Usuario.class)
                     .setParameter("username", username)
                     .getSingleResult();
        } catch (NoResultException e) {
            return u;
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.labintec.model;


import dev.labintec.model.entities.Tramite;
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
public class RepositoryTransaction implements IRepo <Tramite>{ //CRUD
    
//    private EntityManagerFactory emf;
//    private EntityManager em;
//
//    public RepositoryTransaction() {
//        emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
//        em = emf.createEntityManager();
//    }
//
//    public RepositoryTransaction(EntityManager em) {
//        this.em = em;
//    }
     private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
     EntityManager em = emf.createEntityManager();  
    /**
     * Método que permite insertar un nuevo tramite en la base de datos.
     * Abre una transacción, guarda el objeto y la confirma.
     */
    
    @Override
    public void create(Tramite entity) {
        EntityManager em = emf.createEntityManager();  
        try {
          em.getTransaction().begin();
          em.persist(entity);
          em.getTransaction().commit();
            System.out.println("TRAMITE CREADO CON EXITO.");
        } catch (Exception ex) {
            em.getTransaction().rollback(); // Si hay error, se revierte
            System.out.println("NO SE PUEDO CREAR LA CONSULTA. " + ex.getMessage());
        }
    }
    
    /**
     * Método que busca un tramite por su ID.
     * Utiliza el método find de JPA.
     */

    @Override
    public Tramite read(int id) {
        EntityManager em = emf.createEntityManager();  
        Tramite u = null;
        try {
            return em.find(Tramite.class, id);
        } catch (Exception ex) {
            
            System.out.println("ERROR AL LEER TRAMITE POR ID. " + ex.getMessage());
        }
        return u;
    }
    
    /**
     * Método que obtiene todos los Tramites de la base de datos.
     * Retorna una lista de objetos Tramite.
     */
    
    @Override
    public List<Tramite> readAll() {
        EntityManager em = emf.createEntityManager();  
          List<Tramite> tramites = new ArrayList<>();
         try {
             tramites = em.createQuery("SELECT u FROM Tramite u", Tramite.class).getResultList();
         } catch (Exception ex) {
             System.out.println("ERROR AL LEER TRAMITES. " + ex.getMessage());
        }
        return tramites;
    }
    
    /**
     * Método que actualiza los datos de un tramite.
     * Usa merge para sincronizar el objeto actualizado con la base de datos.
     */

    @Override
    public void update(Tramite entity) {
        EntityManager em = emf.createEntityManager();  
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("NO SE PUDO CREAR LA CONSULTA. " + ex.getMessage());
        }
    }
    
    /**
     * Método que elimina un tramite por su ID.
     * Primero busca el objeto y luego lo elimina dentro de una transacción.
     */
    @Override
    public void delete(int  id) {
        EntityManager em = emf.createEntityManager();  
        Usuario u = em.find(Usuario.class, id);
        if (u != null) {
            em.getTransaction().begin();
            em.remove(u);
            em.getTransaction().commit();
        }
    }
    
    /**
     * Método adicional para buscar un tramite por su tipo.
     * Devuelve el objeto si lo encuentra o null si no existe.
     */
    public Tramite readByTipo(String tipo) {
        EntityManager em = emf.createEntityManager();  
      try {
            return em.createQuery("SELECT t FROM Tramite t WHERE t.tipo = :tipo", Tramite.class)
                    .setParameter("tipo", tipo)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception ex) {
            System.out.println("ERROR AL LEER TRAMITE POR TIPO. " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
 }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package dev.labintec.demo;

 

import dev.labintec.model.RepositoryTransaction;
import dev.labintec.model.entities.Tramite;
import java.util.List;

import dev.labintec.business.security.AuthenticationService;
import dev.labintec.database.DBHandler;
import dev.labintec.model.RepositoryUser;
import dev.labintec.model.entities.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.sql.Connection;
import java.util.Scanner;


/**
 *
 * @author HP Micaela
 */
public class App {

    public static void main(String[] args) {
     
 int opc;
        Scanner scanner = new Scanner(System.in);

        // Inicializar JPA (Hibernate)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        EntityManager em = emf.createEntityManager();

        // Repositorios usando JPA
        RepositoryUser repoUser = new RepositoryUser(em);
        RepositoryTransaction repoTramite = new RepositoryTransaction();
        AuthenticationService authService = new AuthenticationService(repoUser);

        do {
            System.out.println("\nMENU:");
            System.out.println("1_ VERIFICACIÓN DE CONEXIÓN USUARIO Y TRÁMITE");
            System.out.println("2_ AUTENTICACIÓN");
            System.out.println("3_ SALIR");
            System.out.print("SELECCIONE UNA OPCIÓN: ");
            opc = scanner.nextInt();

            switch (opc) {
                case 1:
                    // Crear usuarios
                    Usuario user1 = new Usuario("Mica", "123");
                    Usuario user2 = new Usuario("Marti", "123");
                    repoUser.create(user1);
                    repoUser.create(user2);

                    // Actualizar usuario
                    Usuario userToUpdate = repoUser.readByUsername("Mica");
                    if (userToUpdate != null) {
                        userToUpdate.setUsername("Mica123");
                        repoUser.update(userToUpdate);
                    }

                    // Listar todos los usuarios
                    List<Usuario> usuarios = repoUser.readAll();
                    for (Usuario u : usuarios) {
                        System.out.println(u);
                    }

                    // Crear trámites
                    Tramite t1 = new Tramite("Inscripcion", "Pendiente", "Trámite de inscripción");
                    Tramite t2 = new Tramite("Solicitud de certificado", "Pendiente", "Trámite de certificado");
                    repoTramite.create(t1);
                    repoTramite.create(t2);

                    // Actualizar estado de trámite
                    Tramite tramiteToUpdate = repoTramite.readByTipo("Inscripcion");
                    if (tramiteToUpdate != null) {
                        tramiteToUpdate.setEstado("Entregado");
                        repoTramite.update(tramiteToUpdate);
                    }

                    // Listar trámites
                    List<Tramite> tramites = repoTramite.readAll();
                    for (Tramite t : tramites) {
                        System.out.println(t);
                    }
                    break;

                case 2:
                    // Escenario 1
                    System.out.println("AGREGAR USUARIO");
                    Usuario u1 = new Usuario("Micaela", "1234");
                    repoUser.create(u1);

                    // Escenario 2
                    System.out.println("AGREGAR OTRO USUARIO CON MISMO USERNAME");
                    Usuario u2 = new Usuario("Micaela", "5678");
                    repoUser.create(u2);

                    // Escenario 3
                    System.out.println("AGREGAR OTRO USUARIO DIFERENTE");
                    Usuario u3 = new Usuario("Martina", "1234");
                    repoUser.create(u3);

                    // Escenario 4
                    System.out.println("LISTAR TODOS LOS USUARIOS");
                    List<Usuario> allUsers = repoUser.readAll();
                    for (Usuario u : allUsers) {
                        System.out.println(u);
                    }

                    // Escenario 5
                    System.out.println("ELIMINAR USUARIO POR USERNAME");
                    Usuario toDelete = repoUser.readByUsername("Micaela");
                    if (toDelete != null) {
                        repoUser.delete(toDelete.getId());
                        System.out.println("Usuario 'Micaela' eliminado.");
                    }

                    // Escenario 6
                    System.out.println("INICIAR SESIÓN CON USUARIO VÁLIDO");
                    long id = authService.signin("Martina", "1234");
                    if (id != -1) {
                        System.out.println("USUARIO AUTENTICADO CON ID: " + id);
                    } else {
                        System.out.println("NO SE PUDO AUTENTICAR EL USUARIO.");
                    }

                    // Escenario 7
                    System.out.println("INICIAR SESIÓN CON CONTRASEÑA INCORRECTA");
                    long invalidId = authService.signin("Martina", "mica");
                    if (invalidId == -1) {
                        System.out.println("NO SE PUDO INICIAR SESIÓN.");
                    }

                    // Escenario 8
                    System.out.println("ACTUALIZAR USUARIO");
                    Usuario updatedUser = repoUser.readByUsername("Martina");
                    if (updatedUser != null) {
                        updatedUser.setUsername("Micaela");
                        updatedUser.setPassword("56788");
                        repoUser.update(updatedUser);
                    }

                    // Escenario 9
                    System.out.println("LISTAR USUARIOS POST-ACTUALIZACIÓN");
                    List<Usuario> updatedUsers = repoUser.readAll();
                    for (Usuario user : updatedUsers) {
                        System.out.println(user);
                    }
                    break;

                case 3:
                    System.out.println("SALIENDO DEL SISTEMA...");
                    break;

                default:
                    System.out.println("LA OPCIÓN ELEGIDA NO ES VÁLIDA.");
                    break;
            }

        } while (opc != 3);

        // Cerrar recursos
        em.close();
        emf.close();
  }
}



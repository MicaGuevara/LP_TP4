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
import java.sql.Connection;
import java.util.Scanner;


/**
 *
 * @author HP Micaela
 */
public class App {

    public static void main(String[] args) {
     
        int OPC;
        Scanner scanner = new Scanner(System.in);
        DBHandler handler = DBHandler.getInstancia();
        Connection c = handler.getConnection();
        
        do { 
           
        System.out.println("MENU: ");
        System.out.println("1_ VERIFICACION DE CONECCION USUARIO Y TRAMITE");
        System.out.println("2_ AUTENTICACION");
        System.out.println("3_SALIR");
        System.out.print("SELECCIONE UNA OPCION: ");
        OPC = scanner.nextInt();
        switch (OPC){
            case 1: 
                   System.out.println("Conexión: " + c);
                   //Verificación de conección Usuario
                   RepositoryUser crud= new RepositoryUser(c);
                   Usuario user= new Usuario("Mica","123");
                   crud.create(user);
                   Usuario user2= new Usuario("Marti","123");
                   crud.create(user2);
   
                   user = crud.readByUsername("Mica");
                   user.setUsername("Mica123");
                   crud.update(user);
                   List<Usuario> usuarios = crud.readAll();
    
                   for (Usuario u : usuarios) {
                   System.out.println(u);
                   } 
                   //c.close();
                   //Verificación de coneccion Tramite
                   RepositoryTransaction crud2= new RepositoryTransaction(c);
                   Tramite Tram = new Tramite("Inscripcion","Pendiente","Trámite de inscripción en el sistema");
                   crud2.create(Tram);
                   Tramite Tram2 = new Tramite("Solicitud de certificado ","Pendiente","Trámite de solicitud de certificado.");
                   crud2.create(Tram2);
                   
                   Tram = crud2.readByTipo("Inscripcion");
                   Tram.setEstado("Entregado");
                   crud2.update(Tram);
                   crud2.update(Tram);
                   List<Tramite> tramite = crud2.readAll();
    
                   for (Tramite t : tramite) {
                   System.out.println(t);
                   } 
                   
                   break;
            case 2: 
                 // Obtener la conexión
                 Connection connection = handler.getConnection();

                 // Crear el repositorio y el servicio de autenticación
                 RepositoryUser repoUser = new RepositoryUser(connection);
                 AuthenticationService AS = new AuthenticationService(repoUser);

                 // ESCENARIO 1
                 System.out.println(" AGREGAR USUARIO ");
                 Usuario u1 = new Usuario("Micaela", "1234");
                 repoUser.create(u1);
                 
                 // ESCENARIO 2
                System.out.println("AGREGAR OTRO USUARIO CON MISMO USERNAME");
                 Usuario u2 = new Usuario("Micaela", "5678");
                 repoUser.create(u2); // Se espera error por duplicado
                 
                // ESCENARIO 3
                System.out.println("AGREGAR OTRO USUARIO DIFERENTE");
                Usuario u3 = new Usuario("Martina", "1234");
                repoUser.create(u3);
                
                // ESCENARIO 4
                System.out.println("LISTAR TODOS LOS USUARIOS");
                List<Usuario> Us= repoUser.readAll();
                for (Usuario use : Us) {
                    System.out.println(use);
                }
                
                // ESCENARIO 5
                System.out.println("ELIMINAR USUARIO POR ID");
                Usuario UE = repoUser.readByUsername("Micaela");
                if (UE != null) {
                     repoUser.delete(UE.getId());
                     System.out.println("Usuario 'Micaela' eliminado.");
                }
                
                //ESCENARIO 6
                System.out.println("INICIAR SESION CON USUARIO VALIDO");
                long id = AS.signin("Martina", "1234");
                if (id != -1) {
                     System.out.println("USUARIO AUTENTICADO CON ID: " + id);
                } else {
                     System.out.println("NO SE PUDO AUTENTICAR EL USUARIO.");
                }

                 // ESCENARIO 7
                System.out.println("INICIAR SESION CON CONTRASEÑA INCORRECTA");
                long ID = AS.signin("Martina", "mica");
                if (ID == -1) {
                     System.out.println("NO SE PUDO INICIAR SESIÓN.");
                }
                 
                // ESCENARIO 8
                System.out.println(" ACTUALIZAR USUARIO");
                Usuario userToUpdate = repoUser.readByUsername("Martina");
                if (userToUpdate != null) {
                     userToUpdate.setUsername("Micaela");
                     userToUpdate.setPassword("56788");
                     repoUser.update(userToUpdate);
                }
               
                  // ESCENARIO 9: LISTAR NUEVAMENTE LOS USUARIOS
                System.out.println("LISTAR USUARIOS POST-ACTUALIZACION");
                usuarios = repoUser.readAll();
                for (Usuario useri : usuarios) {
                   System.out.println(useri);
                }
                 break;
            case 3:
                System.out.println("SALIENDO DEL SISTEMA..");
                break;
            default:
                System.out.println("LA OPCION ELEGIDA NO ES VALIDA.");
                break;
        }
                  
    }while (OPC != 3);
  }
}



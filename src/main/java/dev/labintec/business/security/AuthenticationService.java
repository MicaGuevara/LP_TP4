/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.labintec.business.security;


import dev.labintec.model.RepositoryUser;
import dev.labintec.model.entities.Usuario;


/**
 *
 * @author HP Pavilion
 */
public class AuthenticationService implements IAuthService{
    
    private RepositoryUser Repo;

    public AuthenticationService() {
    }

    public AuthenticationService(RepositoryUser Repo) {
        this.Repo = Repo;
    }
 
    @Override
    public long signin(String username, String password) {
          Usuario u = Repo.readByUsername(username);//Busca el usuario segun en username, devulve null en caso de no encontrar
        if (u != null && u.getPassword().equals(password)) {
            return u.getId();
        }
        return -1;
    }
}

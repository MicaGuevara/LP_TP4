/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.labintec.model.entities;

/**
 *
 * @author Micaela
 */
public class Usuario {//POJO
   private int id;
   private String username;
   private String password; //bd passw

    public Usuario() {
    }

    public Usuario(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId(){
     return this.id;
    }
    public void setId(int id){
     this.id=id;
    }

   public String getUsername(){
      return this.username;
   }
   public void setUsername(String username){
      this.username= username;
   }

   public String getPassword(){
      return this.password;
   }
   public void setPassword(String password){
      this.password=password;
   } 

    @Override
    public String toString() {
        return "USUARIO {" + "ID=" + id + ", USERNAME=" + username + ", PASSWORD=" + password + '}';
    } 
}

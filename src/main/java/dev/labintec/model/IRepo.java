/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dev.labintec.model;

import java.util.List;

/**
 *
 * @author HP Micaela
 */
public interface IRepo <T> { //CRUD
    void create(T entity);
    T read(int id);
    List<T> readAll();
    void update(T entity);
    void delete(int id);
}

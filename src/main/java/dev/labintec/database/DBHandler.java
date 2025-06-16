/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.labintec.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author HP Pavilion
 */
public class DBHandler {
    
    private Connection connection; // Atributo que representa la conexión a la base de datos
    private static DBHandler instancia;  // Instancia única de la clase (patrón Singleton)
    /**
     * Constructor privado para que no se pueda instanciar desde fuera de la clase.
     * Al crearse, establece la conexión con la base de datos.
     */
    private DBHandler(){
        createConnection();
    }
     /**
     * Método estático que devuelve la única instancia de DBHandler.
     * Si no existe, la crea.
     */
    public static DBHandler getInstancia() {
        if (instancia == null) {
            instancia = new DBHandler();
        }
        return instancia;
    }
     /**
     * Establece la conexión a la base de datos leyendo los datos desde un archivo de configuración.
     * El archivo debe contener las propiedades necesarias para la conexión (URL, user, password).
     */
    private void createConnection() {
        try {
            // Carga el archivo de propiedades
            FileInputStream fis = new FileInputStream("src/main/java/dev/labintec/configuration/config.properties");
            Properties prop = new Properties();
            prop.load(fis);
            // Usa el archivo para establecer la conexión
            connection = DriverManager.getConnection(prop.getProperty("database"), prop);
        } catch (FileNotFoundException ex) {
            System.out.println("ARCHIVO NO ENCONTRADO. " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("NO SE PUDO LEER EL ARCHIVO. " + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("NO SE PUDO CONECTAR A LA BASE DE DATOS. " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }   
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class Usuario {
    // Atributos de la clase Usuario que representan el nombre de usuario y la contraseña
    private String nombreUsuario;
    private String contrasena;

    // Constructor de la clase Usuario que inicializa los atributos con los valores proporcionados
    public Usuario(String nombreUsuario, String contrasena) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    // Método get para obtener el nombre de usuario
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    // Método set para establecer el nombre de usuario
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    // Método get para obtener la contraseña
    public String getContrasena() {
        return contrasena;
    }

    // Método set para establecer la contraseña
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    // Método sobrescrito toString para representar el objeto Usuario como una cadena de texto
    @Override
    public String toString() {
        return nombreUsuario + "_" + contrasena;
    }
}

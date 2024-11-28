/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.ControladorLogin;

public class Ejecucion {
    public static void main(String[] args) {
        // Crear una instancia del controlador de login
        ControladorLogin controladorLogin = new ControladorLogin();
        
        // Llamar al método que gestiona el acceso al sistema
        controladorLogin.imprimirBienvenidaLogin();
        controladorLogin.accesoSistema();
    }
}

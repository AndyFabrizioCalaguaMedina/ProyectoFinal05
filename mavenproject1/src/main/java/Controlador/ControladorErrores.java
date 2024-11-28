/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Errores;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//Controlador gestiona los errores del sistema

public class ControladorErrores {
    //Guarda los errores en el archivo plano, el objeto Errores contiene la información del error
    public static void guardarError(Errores error) {
        String nombreArchivo = "E:/Proyecto/errores.log";
        String texto = error.toString();
        File archivo = new File(nombreArchivo);

        try (FileWriter escritor = new FileWriter(archivo, true)) { // true es para agregar al archivo existente
            escritor.write(texto + "\n");
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
}

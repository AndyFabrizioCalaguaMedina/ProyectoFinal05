/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Errores;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// Gestiona la distribucion de edad y sexo de los encuestados
public class ControladorGeneroEdad {
    private List<String[]> datosEncuesta;
    private String[] sexo;

    public ControladorGeneroEdad() {
        inicializarTipoGeneros();
        leerDatosEncuesta();
    }
    
    // Inicializa tipos de genero, sexo
    private void inicializarTipoGeneros() {
        sexo = new String[8];
        sexo[0] = "Missing Value";
        sexo[1] = "Hombre";
        sexo[2] = "Mujer";
    }

    //Lee los datos del archivo CSV
    private void leerDatosEncuesta() {
        datosEncuesta = new ArrayList<>();
        String archivoCSV = "E:/Proyecto/Trim Feb-Mar-Abr22_Muestra.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            boolean primeraFila = true;
            while ((linea = br.readLine()) != null) {
                if (primeraFila) {
                    primeraFila = false;
                    continue; // Omitir la primera fila
                }
                String[] datos = linea.split(",");
                datosEncuesta.add(datos);
            }
        } catch (IOException e) {
            manejarError(e, "Error al leer el archivo CSV, porfavor vuelva al menu principal");
        }
    }
    //Maneja errores gracias a IOExcption y da un mensaje descriptivo del error
    private static void manejarError(IOException e, String mensaje) {
        System.err.println(mensaje + ": " + e.getMessage());
        Errores error = new Errores(e.getMessage(), "No se logro ubicar el archivo", LocalDate.now().toString(), LocalTime.now().toString(), "Usuario");
        ControladorErrores.guardarError(error);
    }
    
    //Imprime el cuadro de la distribucion de sexo y edad
    public void imprimirPantalla() {
        System.out.println("Nº\tSEXO\t        EDAD");
        System.out.println("============================================");

        int contador = 1;
        for (String[] registro : datosEncuesta) {
            String sexo = obtenerTipoGeneros(registro[14]);
            System.out.printf("%02d\t%-10s\t%-15s\n", 
                    contador++, sexo, registro[15]);
        }
        System.out.println("============================================");
        System.out.println("");
    }
    
    //Exporta la distribucion de los datos de sexo y edad a un archivo TXT
    public void exportarArchivo() {
        String archivoExportado = "E:/Proyecto/DistribucionGeneroEdad.txt";

        try (FileWriter escritor = new FileWriter(archivoExportado)) {
            escritor.write("Nº\tSEXO\t        EDAD\n");
            escritor.write("============================================\n");

            int contador = 1;
            for (String[] registro : datosEncuesta) {
                String sexo = obtenerTipoGeneros(registro[14]);
                escritor.write(String.format("%02d\t%-10s\t%-15s\n", 
                        contador++, sexo, registro[15]));
            }
            escritor.write("============================================\n");
            escritor.write("\n");

        } catch (IOException e) {
            manejarError(e, "Error al escribir en el archivo");
        }
    }

    //Obtiene el genero
    private String obtenerTipoGeneros(String codigo) {
        try {
            int index = Integer.parseInt(codigo);
            if (index < 0 || index >= sexo.length) {
                return "Missing Value";
            }
            return sexo[index];
        } catch (NumberFormatException e) {
            return "Missing Value";
        }
    }
}

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
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// Controlador para gestionar los ingresos por encuestado
public class ControladorIngresos {
    private List<String[]> datosEncuesta;
    private String[] tiposTrabajo;

    public ControladorIngresos() {
        inicializarTiposTrabajo();
        leerDatosEncuesta();
    }

    // Inicializar valores por tipo de trabajo en un arreglo
    private void inicializarTiposTrabajo() {
        tiposTrabajo = new String[8]; // El tamaño del arreglo es 8 para incluir los índices del 0 al 7
        tiposTrabajo[1] = "Empleador o patrono";
        tiposTrabajo[2] = "Trabajador Independiente";
        tiposTrabajo[3] = "Empleado";
        tiposTrabajo[4] = "Obrero";
        tiposTrabajo[5] = "Trabajador familiar";
        tiposTrabajo[6] = "Trabajador del hogar";
        tiposTrabajo[7] = "Otro";
        tiposTrabajo[0] = ""; // El 0 para valores vacíos o no válidos
    }

    // Lee los datos del archivo
    private void leerDatosEncuesta() {
        datosEncuesta = new ArrayList<>();
        String archivoCSV = "Trim Feb-Mar-Abr22_Muestra.csv"; // Ruta relativa

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
            manejarError(e, "Error al leer el archivo CSV, por favor vuelva al menú principal");
        }
    }

    // Maneja errores de entrada y salida con la excepción IOException con un mensaje descriptivo del error
    private static void manejarError(IOException e, String mensaje) {
        System.err.println(mensaje + ": " + e.getMessage());
        Errores error = new Errores(e.getMessage(), "No se logró ubicar el archivo", LocalDate.now().toString(), LocalTime.now().toString(), "Usuario");
        ControladorErrores.guardarError(error);
    }

    // Imprime en pantalla los datos de los ingresos por encuestado además de su tipo de trabajo
    public void imprimirPantalla() {
        System.out.println("Nº\tMES\tTIPO DE TRABAJO\t                INGRESOS");
        System.out.println("========================================================");

        int contador = 1;
        for (String[] registro : datosEncuesta) {
            String tipoTrabajo = obtenerTipoTrabajo(registro[47]);
            System.out.printf("%02d\t%s\t%-25s\t%-50.6s\n", 
                contador++, registro[1], tipoTrabajo, registro[102]);
        }
        System.out.println("========================================================");
        System.out.println("");
    }
    
    // Exporta los mismos datos mencionados a un archivo TXT
    public void exportarArchivo() {
        String archivoExportado = "IngresosMensuales.txt"; // Ruta relativa

        try (FileWriter escritor = new FileWriter(archivoExportado)) {
            escritor.write("Nº\tMES\tTIPO DE TRABAJO\t                INGRESOS\n");
            escritor.write("========================================================\n");

            int contador = 1;
            for (String[] registro : datosEncuesta) {
                String tipoTrabajo = obtenerTipoTrabajo(registro[47]);
                escritor.write(String.format("%02d\t%s\t%-25s\t%-50.6s\n", 
                    contador++, registro[1], tipoTrabajo, registro[102]));
            }
            escritor.write("========================================================\n");
            escritor.write("\n");

        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
            Errores error = new Errores(e.getMessage(), "Error",
                LocalDate.now().toString(), LocalTime.now().toString(), "Usuario");
            ControladorErrores.guardarError(error);
        }
    }

    //Obtiene el tipo de trabajo
    private String obtenerTipoTrabajo(String codigo) {
        try {
            int index = Integer.parseInt(codigo);
            if (index < 0 || index >= tiposTrabajo.length) {
                return "Missing Value";
            }
            return tiposTrabajo[index];
        } catch (NumberFormatException e) {
            return "Missing Value";
        }
    }
}
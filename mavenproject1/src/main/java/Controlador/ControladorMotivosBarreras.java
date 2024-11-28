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

//Controlador que gestiona los motivos y barreras de los encuestados

public class ControladorMotivosBarreras {
    private List<String[]> datosEncuesta;
    private String[] motivos;
    private String[] barreras;

    public ControladorMotivosBarreras() {
        inicializarMotivos();
        inicializarBarreras();
        leerDatosEncuesta();
    }
    
    //inicializa los motivos y barreras

    private void inicializarMotivos() {
        motivos = new String[3]; 
        motivos[1] = "Si";
        motivos[2] = "No";
        motivos[0] = "Missing Value"; // Usamos el índice 0 para valores vacíos o no válidos
    }

    private void inicializarBarreras() {
        barreras = new String[12]; 
        barreras[1] = "No hay trabajo";
        barreras[2] = "Se cansó de buscar";
        barreras[3] = "Por edad";
        barreras[4] = "Los quehaceres del hogar no le permiten";
        barreras[5] = "Sus estudios no le permiten";
        barreras[6] = "Falta de experiencia";
        barreras[7] = "Razones de salud";
        barreras[8] = "Falta de capital";
        barreras[9] = "Otro";
        barreras[10] = "Ya encontró";
        barreras[11] = "Sigue buscando";
        barreras[0] = "Missing Value"; // Usamos el índice 0 para valores vacíos o no válidos
    }

    //Lee los datos de la encuesta del archivo CSV
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
    //Manejo de errores y excpeciones
    private static void manejarError(IOException e, String mensaje) {
    System.err.println(mensaje + ": " + e.getMessage());
    Errores error = new Errores(e.getMessage(), "No se logro ubicar el archivo", LocalDate.now().toString(), LocalTime.now().toString(), "Usuario");
    ControladorErrores.guardarError(error);
}
    //Imprime los datos de motivos y barreras en un formato ASCII
    public void imprimirPantalla() {
        System.out.println("Nº\tMES\tHUBO MOTIVO PARA BUSCAR?\tBARRERA");
        System.out.println("====================================================================");

        int contador = 1;
        for (String[] registro : datosEncuesta) {
            String motivo = obtenerMotivo(registro[74]);
            String barrera = obtenerBarrera(registro[78]);
            System.out.printf("%02d\t%s\t%-25s\t%-25s\n", 
                contador++, registro[1], motivo, barrera);
        }
        System.out.println("====================================================================");
        System.out.println("");
    }

    //Exporta los datos a un archivo de texto
    public void exportarArchivo() {
        String archivoExportado = "E:/Proyecto/MotivosBarreras.txt";

        try (FileWriter escritor = new FileWriter(archivoExportado)) {
            escritor.write("Nº\tMES\tHUBO MOTIVO PARA BUSCAR?\tBARRERA\n");
            escritor.write("====================================================================\n");

            int contador = 1;
            for (String[] registro : datosEncuesta) {
                String motivo = obtenerMotivo(registro[74]);
                String barrera = obtenerBarrera(registro[78]); // Suponiendo que las barreras están en la columna 48
                escritor.write(String.format("%02d\t%s\t%-25s\t%-25s\n", 
                    contador++, registro[1], motivo, barrera));
            }
            escritor.write("====================================================================\n");
            escritor.write("\n");

        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
            Errores error = new Errores(e.getMessage(), "Error",
                LocalDate.now().toString(), LocalTime.now().toString(), "Usuario");
            ControladorErrores.guardarError(error);
        }
    }
    
    //Obtenemos motivos y barreras correspondientes

    private String obtenerMotivo(String codigo) {
        try {
            int index = Integer.parseInt(codigo);
            if (index < 0 || index >= motivos.length) {
                return "Missing Value";
            }
            return motivos[index];
        } catch (NumberFormatException e) {
            return "Missing Value";
        }
    }

    private String obtenerBarrera(String codigo) {
        try {
            int index = Integer.parseInt(codigo);
            if (index < 0 || index >= barreras.length) {
                return "Missing Value";
            }
            return barreras[index];
        } catch (NumberFormatException e) {
            return "Missing Value";
        }
    }
}

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

//Controlador que gestiona el nivel educativo alcanzdo por los encuestados 
//Se declara como una List de arreglos de String. Esto se hace para almacenar múltiples registros de la encuesta, donde cada registro es un arreglo de String
public class ControladorNivelEducativo {
    private List<String[]> datosEncuesta;
    private String[] nivelesEducativos;

    public ControladorNivelEducativo() {
        inicializarNivelesEducativos();
        leerDatosEncuesta();
    }

    //Inicializa los niveles educativos (Son muchos :/ )
    private void inicializarNivelesEducativos() {
        nivelesEducativos = new String[11];
        nivelesEducativos[1] = "Sin educación";
        nivelesEducativos[2] = "Inicial";
        nivelesEducativos[3] = "Primaria Incompleta";
        nivelesEducativos[4] = "Primaria Completa";
        nivelesEducativos[5] = "Secundaria Incompleta";
        nivelesEducativos[6] = "Secundaria Completa";
        nivelesEducativos[7] = "Educación Superior No Universitaria Incompleta";
        nivelesEducativos[8] = "Educación Superior No Universitaria Completa";
        nivelesEducativos[9] = "Educación Superior Universitaria Incompleta";
        nivelesEducativos[10] = "Educación Superior Universitaria Completa";
        nivelesEducativos[0] = "Missing Value"; // Usamos el índice 0 para valores vacíos o no válidos
    }

    //Lee los datos de la encuesta que esta en archivo CSV
    //Se inicializa datosEncuesta como un ArrayList, que permite almacenar una lista dinámica de objetos (cuyo tamaño puede cambiar en tiempo de ejecución si modifico algo)
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
            manejarError(e, "Error al leer el archivo CSV, porfavor retorne al menu principal");
            
        }
    }
    
    //Manejo de errores, excepcion IOException y mensaje descriptivo del error
    private static void manejarError(IOException e, String mensaje) {
    System.err.println(mensaje + ": " + e.getMessage());
    Errores error = new Errores(e.getMessage(), "No se logro ubicar el archivo", LocalDate.now().toString(), LocalTime.now().toString(), "Usuario");
    ControladorErrores.guardarError(error);
}
 
    //Inprime en consola los datos solicitados, en este caso el nivel educativo de los encuestados
    public void imprimirPantalla() {
        System.out.println("Nº\tNIVEL EDUCATIVO");
        System.out.println("====================================================");

        int contador = 1;
        for (String[] registro : datosEncuesta) {
            String nivelEducativo = obtenerNivelEducativo(registro[17]); 
            System.out.printf("%02d\t%-35s\n", 
                contador++, nivelEducativo);
        }
        System.out.println("====================================================");
        System.out.println("");
    }

    //Exportamos esos mismos datos solicitados a un archivo de texto TXT
    public void exportarArchivo() {
        String archivoExportado = "E:/Proyecto/NivelEducativo.txt";

        try (FileWriter escritor = new FileWriter(archivoExportado)) {
            escritor.write("Nº\tNIVEL EDUCATIVO\n");
            escritor.write("====================================================\n");

            int contador = 1;
            for (String[] registro : datosEncuesta) {
                String nivelEducativo = obtenerNivelEducativo(registro[17]);
                escritor.write(String.format("%02d\t%-35s\n", 
                    contador++, nivelEducativo));
            }
            escritor.write("====================================================\n");
            escritor.write("\n");

        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
            Errores error = new Errores(e.getMessage(), "Error",
                LocalDate.now().toString(), LocalTime.now().toString(), "Usuario");
            ControladorErrores.guardarError(error);
        }
    }

    //Obtenemos el nivel educativo, el cual es convertido de String a numero, si es invalido, nos devolvera "MissingValue"
    //El Integer toma una cadena y lo vuelve a entero, osea int
    private String obtenerNivelEducativo(String codigo) {
        try {
            int index = Integer.parseInt(codigo);
            if (index < 0 || index >= nivelesEducativos.length) {
                return "Missing Value";
            }
            return nivelesEducativos[index];
        } catch (NumberFormatException e) {
            return "Missing Value";
        }
    }
}
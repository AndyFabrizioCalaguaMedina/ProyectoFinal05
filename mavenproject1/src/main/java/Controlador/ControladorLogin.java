/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Usuario;
import Modelo.Errores;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class ControladorLogin {
    private Usuario usuario;
    private int intentos = 0;

    // Constructor qu inicializa el usuario con nombre de usuario y contraseña vacíos
    public ControladorLogin() {
        this.usuario = new Usuario("", "");
    }

    // Imprime un mensaje de bienvenida y solicita las credenciales de inicio de sesión
    public void imprimirBienvenidaLogin() {
        System.out.println("====================================");
        System.out.println("BIENVENIDO, INGRESE SUS CREDENCIALES");
        System.out.println("====================================");
    }

    // Método para manejar el acceso al sistema
    public void accesoSistema() {
        Scanner scanner = new Scanner(System.in);

        // Permite hasta 5 intentos de inicio de sesión
        while (intentos < 5) {
            System.out.print("Ingrese su nombre de usuario: ");
            String nombreUsuario = scanner.nextLine();
            System.out.print("Ingrese su contraseña: ");
            String contrasena = scanner.nextLine();

            usuario.setNombreUsuario(nombreUsuario);
            usuario.setContrasena(contrasena);

            // Verifica las credenciales del usuario
            if (validarUsuario()) {
                System.out.println("¡BIENVENIDO, " + nombreUsuario + "!");
                System.out.println("");
                mostrarMenuPrincipal();
                return;
            } else {
                intentos++;
                System.out.println("Credenciales incorrectas. Intento " + intentos + " de 5.");
            }
        }

        // Si se exceden los 5 intentos, se cierra el programa
        if (intentos >= 5) {
            System.out.println("Número de intentos excedido. El programa se cerrará.");
        }
    }

    // Método para validar las credenciales del usuario leyendo de un archivo de texto
    private boolean validarUsuario() {
        try (BufferedReader br = new BufferedReader(new FileReader("E:/Proyecto/usuarios.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                // Compara las credenciales ingresadas con las del archivo
                if (partes[0].equals(usuario.getNombreUsuario()) && partes[1].equals(usuario.getContrasena())) {
                    return true;
                }
            }
            // Si no se encuentra una coincidencia, retorna false
            return false;
        } catch (IOException e) {
            System.err.println("Error al leer la entrada del usuario: " + e.getMessage());
            // Registra el error en un archivo de errores
            Errores error = new Errores(e.getMessage(), "Error",
                LocalDate.now().toString(), LocalTime.now().toString(), usuario.getNombreUsuario());
            ControladorErrores.guardarError(error);
            return false;
        }
    }

    // Muestra el menú principal del sistema
    private void mostrarMenuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        // Bucle para mostrar el menú principal hasta que se seleccione la opción de salir
        do {
            System.out.println("--------------------------------------------------------");
            System.out.println("                     MENU PRINCIPAL                     ");
            System.out.println("--------------------------------------------------------");
            System.out.println("1. Distribución de género y edad en la población encuestada");
            System.out.println("2. Nivel educativo alcanzado por la población");
            System.out.println("3. Ingresos mensuales por tipo de trabajo");
            System.out.println("4. Motivos y barreras para buscar trabajo");
            System.out.println("0. FIN DEL PROGRAMA");
            System.out.println("--------------------------------------------------------");
            System.out.print("Ingrese opción [1 – 4]: ");
            System.out.println("");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        mostrarMenuModulo(new ControladorGeneroEdad());
                        break;
                    case 2:
                        mostrarMenuModulo(new ControladorNivelEducativo());
                        break;
                    case 3:
                        mostrarMenuModulo(new ControladorIngresos());
                        break;
                    case 4:
                        mostrarMenuModulo(new ControladorMotivosBarreras());
                        break;
                    case 0:
                        System.out.println("Fin del programa.");
                        break;
                    default:
                        System.out.println("Opción inválida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Entrada no válida. Por favor, ingrese un número entre 0 y 4.");
                // Registra el error en un archivo de errores
                Errores error = new Errores("Entrada no válida en el menú principal", "Error",
                    LocalDate.now().toString(), LocalTime.now().toString(), usuario.getNombreUsuario());
                ControladorErrores.guardarError(error);
            }
        } while (opcion != 0);
    }

    // Muestra el menú de opciones específicas de cada módulo
    private void mostrarMenuModulo(Object controlador) {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        // Bucle para mostrar el menú del módulo hasta que se seleccione la opción de volver al menú principal
        do {
            System.out.println("--------------------------------------------------------");
            System.out.println("             MODULO DE OPCIONES                         ");
            System.out.println("--------------------------------------------------------");
            System.out.println("1. Imprimir por pantalla.");
            System.out.println("2. Exportar a archivo plano.");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("--------------------------------------------------------");
            System.out.print("Ingrese opción [1-2]: ");
            System.out.println("");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        if (controlador instanceof ControladorGeneroEdad) {
                            ((ControladorGeneroEdad) controlador).imprimirPantalla();
                        } else if (controlador instanceof ControladorNivelEducativo) {
                            ((ControladorNivelEducativo) controlador).imprimirPantalla();
                        } else if (controlador instanceof ControladorIngresos) {
                            ((ControladorIngresos) controlador).imprimirPantalla();
                        } else if (controlador instanceof ControladorMotivosBarreras) {
                            ((ControladorMotivosBarreras) controlador).imprimirPantalla();
                        }
                        break;
                    case 2:
                        if (controlador instanceof ControladorGeneroEdad) {
                            ((ControladorGeneroEdad) controlador).exportarArchivo();
                            System.out.println("Exportando a archivo plano");
                            System.out.println("");
                        } else if (controlador instanceof ControladorNivelEducativo) {
                            ((ControladorNivelEducativo) controlador).exportarArchivo();
                            System.out.println("Exportando a archivo plano");
                            System.out.println("");
                        } else if (controlador instanceof ControladorIngresos) {
                            ((ControladorIngresos) controlador).exportarArchivo();
                            System.out.println("Exportando a archivo plano");
                            System.out.println("");
                        } else if (controlador instanceof ControladorMotivosBarreras) {
                            ((ControladorMotivosBarreras) controlador).exportarArchivo();
                            System.out.println("Exportando a archivo plano");
                            System.out.println("");
                        }
                        break;
                    case 0:
                        System.out.println("Volviendo al Menú Principal.");
                        System.out.println("");
                        break;
                    default:
                        System.out.println("Opción inválida. Intente de nuevo.");
                        System.out.println("");
                }
            } catch (NumberFormatException e) {
                System.err.println("Entrada no válida. Por favor, ingrese un número entre 0 y 2.");
                // Registra el error en un archivo de errores
                Errores error = new Errores("Entrada no válida en el menú del módulo", "Error",
                    LocalDate.now().toString(), LocalTime.now().toString(), usuario.getNombreUsuario());
                ControladorErrores.guardarError(error);
            }
        } while (opcion != 0);
    }
}
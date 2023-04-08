package minesalb.controlador;

import java.util.Scanner;

import minesalb.model.Model;


public class Controlador {
    private static Scanner scan = new Scanner(System.in);

    /**
     * Es una función llamada jugar donde iniciamos el juego de la clase modelo.
     * @param f le entran las filas indicadas en la clase modelo y funcion initJuego
     * @param c le entran las columnas indicadas en la clase modelo y funcion initJuego
     * @param b le entran las bombas indicadas en la clase modelo y funcion initJuego
     */
    public static void jugar(int f, int c, int b) {

        Model.initJuego(f, c, b);

        boolean opcionSalir = false;

        while (!Model.juegoFinalizado() && !opcionSalir) {
            System.out.println();

            System.out.print("¿Que quieres hacer?(T,B,A) ");
            String opcion = scan.nextLine().toUpperCase();

            System.out.println();

            int fila, columna;
            char l;
            switch (opcion) {
                case "T":
                    System.out.print("Introduce la fila: ");
                    l = scan.nextLine().trim().toUpperCase().charAt(0);
                    fila = l - 'A';
                    System.out.print("Introduce la columna: ");
                    columna = scan.nextInt();
                    scan.nextLine();
                    if (Model.verificarFilaColumna(fila, columna)) {
                        Model.pisarCasilla(fila, columna);
                    }
                    break;
                case "B":
                    System.out.print("Introduce la fila: ");
                    fila = scan.nextInt();
                    scan.nextLine();
                    System.out.print("Introduce la columna: ");
                    columna = scan.nextInt();
                    scan.nextLine();
                    if (Model.verificarFilaColumna(fila, columna)) {
                        Model.ponerBandera(fila, columna);
                    }
                    break;
                case "A":
                    System.out.println("Saliendo...");
                    opcionSalir = true;
                    break;
                default:
                    System.out.println("Has introducido una opción no correspondiente");
            }
        }

        System.out.println();

        System.out.println("Juego finalizado");
    }

    /**
     * Funcion que sirve para mostrar un menu y que el usuario seleccione a que nivel quiere jugar.
     */
    public static void seleccionJuego() {

        System.out.println("Buscaminas");

        System.out.println();

        System.out.println("1 - 8x8 (10 bombes)");
        System.out.println("2 - 16x16 (40 bombes)");
        System.out.println("3 - 16x30 (99 bombes)");
        System.out.println("S - Sortir del programa");

        System.out.println();

        System.out.print("Selecciona una opción: ");
        String seleccion = scan.nextLine().toUpperCase();

        System.out.println();

        switch (seleccion) {
            case "1":
                jugar(8, 8, 10);
                break;
            case "2":
                jugar(16, 16, 40);
                break;
            case "3":
                jugar(16, 30, 99);
                break;
            case "S":
                System.out.println("Saliendo...");
                break;
            default:
                System.out.println("Has introducido una opción no correspondiente");
        }
    }
}


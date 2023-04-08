package minesalb.model;

import minesalb.vista.Vista;

public class Model {

    private static int filas;
    private static int columnas;
    private static int bombas;
    private static boolean finalizado;

    private static char[][] campoOculto;
    private static char[][] campoVisible;

    /**
     * Esta función inicializa el juego teniendo dos arrays bidimensionales de tipo char llamadas campoOculto y campoVisible.
     * También inicializamos los campos de minas tanto para el campoOculto que será un espacio en blanco, comoel campoVisible que sera un punto.
     *
     * @param f entrara un valor de tipo entero, en este caso hace referencia a las filas que indicara el usuario que tendrá en el tablero.
     * @param c entrara un valor de tipo entero, en este caso hace referencia a las columnas que indicara el usuario que tendrá en el tablero.
     * @param b entrara un valor de tipo entero, en este caso hace referencia a las bombas que indicara el usuario que tendrá en el tablero.
     */
    public static void initJuego(int f, int c, int b) {

        filas = f;
        columnas = c;
        bombas = b;

        campoOculto = new char[f][c];
        campoVisible = new char[f][c];

        initCampoMinas(campoOculto, ' ');
        initCampoMinas(campoVisible, '.');

        generarBombas();
        contarBombas();

        Vista.mostrarCampoMinas(campoVisible);
    }

    /**
     * Funcion que sirve para verificar si la fila y columna esta dentro del rango disponible
     *
     * @param fila    filas elegidas por el jugador en el momento de seleccion de juego.
     * @param columna columnas elegidas por el jugador en el momento de seleccion de juego.
     * @return boolean de si es true o false, si es false indica al usuario que no es correcto.
     */
    public static boolean verificarFilaColumna(int fila, int columna) {
        if (fila <= filas && columna <= columnas && fila >= 0 && columna >= 0) {
            return true;
        } else {
            System.out.println();
            System.out.println("No es una fila y/o columna que este dentro del rango disponible");
            return false;
        }
    }

    /**
     * Indica si se ha pisado casilla o no, si es una bomba finaliza el juego. Si no es '.' es que ya se ha pisado o tiene bandera.
     * Sino es un caso u el otro es que ha ganado,
     *
     * @param f filas de campoOculto
     * @param c columnas de campoOculto
     */
    public static void pisarCasilla(int f, int c) {
        if (campoOculto[f][c] == 'B') {
            finalizado = true;
            mostrarSolucion();
        } else if (campoVisible[f][c] != '.') {
            System.out.println("Ya se ha pisado o tiene bandera");
        } else {
            pisarAlrededor(f,c);
            Vista.mostrarCampoMinas(campoVisible);
            finalizado = haGanado();
        }
    }

    /**
     * Funcion que sirve para hacer un Switch que es para colocar o quitar bandera, a no ser que la casilla ya este pisada
     *
     * @param f la fila de campoVisible
     * @param c la columna de campoVisible
     */
    public static void ponerBandera(int f, int c) {

        switch (campoVisible[f][c]) {
            case '.':
                campoVisible[f][c] = 'B';
                System.out.println("Bandera colocada");
                Vista.mostrarCampoMinas(campoVisible);
                finalizado = haGanado();
                break;
            case 'B':
                campoVisible[f][c] = '.';
                System.out.println("Bandera quitada");
                Vista.mostrarCampoMinas(campoVisible);
                break;
            default:
                System.out.println("Casilla ya pisada");
                Vista.mostrarCampoMinas(campoVisible);
        }
    }

    /**
     * Creamos una funcion boolean para comprobar si la partida ha finalizado o no.
     *
     * @return devolverla un boolean segun si ha finalizado (true) o no (false).
     */

    public static boolean juegoFinalizado() {

        return finalizado;
    }

    private static void initCampoMinas(char[][] campo, char c) {
        for (int i = 0; i < filas; ++i) {
            for (int j = 0; j < columnas; ++j) {
                campo[i][j] = c;
            }
        }
    }

    private static void generarBombas() {
        for (int i = 0; i < bombas; ++i) {
            int fila = (int) (Math.random() * filas);
            int columna = (int) (Math.random() * columnas);

            if (campoOculto[fila][columna] == 'B') {
                i--;
            } else {
                campoOculto[fila][columna] = 'B';
            }
        }
    }

    // Aqui recorremos todas las filas y columnas para contar las bombas, creamos la variable numeroBombas que son el numero de bombas

    private static void contarBombas() {
        for (int i = 0; i < filas; ++i) {
            for (int j = 0; j < columnas; ++j) {
                if (campoOculto[i][j] != 'B') {
                    int numeroBombas = contarBombasPosicion(i, j);
                    if (numeroBombas == 0) campoOculto[i][j] = ' ';
                    else campoOculto[i][j] = (char) (numeroBombas + 48);
                }
            }
        }
    }

    /* Aquí miramos si hay bombas alrededor, f-1,c-1 f1 c1  */
    private static int contarBombasPosicion(int fila, int columna) {
        int contador = 0;

        try {
            if (campoOculto[fila - 1][columna - 1] == 'B') {
                ++contador;
            }
            if (campoOculto[fila - 1][columna] == 'B') {
                ++contador;
            }
            if (campoOculto[fila - 1][columna + 1] == 'B') {
                ++contador;
            }
            if (campoOculto[fila][columna - 1] == 'B') {
                ++contador;
            }
            if (campoOculto[fila][columna + 1] == 'B') {
                ++contador;
            }
            if (campoOculto[fila + 1][columna - 1] == 'B') {
                ++contador;
            }
            if (campoOculto[fila + 1][columna] == 'B') {
                ++contador;
            }
            if (campoOculto[fila + 1][columna + 1] == 'B') {
                ++contador;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        return contador;
    }

    private static boolean haGanado() {

        boolean sonIguales = true;
        for (int i = 0; i < filas; ++i) {
            for (int j = 0; j < columnas; ++j) {
                if (campoOculto[i][j] != campoVisible[i][j]) {
                    sonIguales = false;
                    break;
                }
            }
        }
        return sonIguales;
    }

    private static void mostrarSolucion() {
        System.out.println();
        Vista.mostrarCampoMinas(campoOculto);
        System.out.println();
        System.out.println("Te has encontrado con una bomba, has perdido");
    }

    private static void pisarAlrededor(int fila, int columna) {


        if (campoVisible[fila][columna] != '.') {
            return;
        }
        campoVisible[fila][columna] = campoOculto[fila][columna];

        if (campoVisible[fila][columna] == ' ') {
            campoVisible[fila - 1][columna - 1] = campoOculto[fila - 1][columna - 1];
            campoVisible[fila - 1][columna] = campoOculto[fila - 1][columna];
            campoVisible[fila - 1][columna + 1] = campoOculto[fila - 1][columna + 1];
            campoVisible[fila][columna - 1] = campoOculto[fila][columna + 1];
            campoVisible[fila][columna + 1] = campoOculto[fila][columna + 1];
            campoVisible[fila + 1][columna - 1] = campoOculto[fila + 1][columna - 1];
            campoVisible[fila + 1][columna] = campoOculto[fila + 1][columna];
            campoVisible[fila + 1][columna + 1] = campoOculto[fila + 1][columna + 1];
        }
    }
}




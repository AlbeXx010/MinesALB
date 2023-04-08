package minesalb.vista;

public class Vista {

    /**
     * Funcion que sirve para mostrar la parte visual del tablero.
     *
     * @param campo Array bidimensional que es el del campoVisible.
     */
    public static void mostrarCampoMinas(char[][] campo) {
        System.out.print("    ");

        for (int i = 0; i < campo[0].length; i++) {
            System.out.printf("%2d ",i);
        }

        System.out.println();
      
        System.out.println("    - - - - - - - -"); //Todo: hacer bucle como el de arriba para ---

        for (int i = 0; i < campo.length; i++) {
            System.out.print((char) ('A' + i) + " | ");

            for (int j = 0; j < campo[i].length; j++) {
                System.out.print(" "+ campo[i][j] + " ");
            }

            System.out.println();
        }
    }

    /**
     * Función que sirve para mostrar el mensaje correspondiente al jugador.
     * @param mensaje es un String
     */
    public static void mostrarMensaje(String mensaje) {

        System.out.println(mensaje);
    }
}

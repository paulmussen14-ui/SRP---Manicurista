package utils;

import java.util.Locale;
import java.util.Scanner;

// Utilidades compartidas por los menus: formato de dinero, confirmaciones y metodo de pago.
public class Consola {
    public static final String[] METODOS_PAGO = { "Efectivo", "Yape", "Plin", "Tarjeta" };

    private Consola() {
    }

    // 25 -> "S/ 25.00"
    public static String dinero(double monto) {
        return String.format(Locale.US, "S/ %.2f", monto);
    }

    // Pregunta si/no. Devuelve true solo si responde "si" o "s".
    public static boolean confirmar(Scanner teclado, String pregunta) {
        System.out.print(pregunta + " (si/no): ");
        String respuesta = teclado.nextLine().trim();
        return respuesta.equalsIgnoreCase("si") || respuesta.equalsIgnoreCase("s");
    }

    // Muestra los metodos de pago y devuelve el elegido, o null si la opcion no es valida.
    public static String pedirMetodoPago(Scanner teclado) {
        for (int i = 0; i < METODOS_PAGO.length; i++) {
            System.out.println("  [" + (i + 1) + "] " + METODOS_PAGO[i]);
        }
        System.out.print("Metodo de pago: ");
        try {
            int numero = Integer.parseInt(teclado.nextLine().trim());
            if (numero >= 1 && numero <= METODOS_PAGO.length) {
                return METODOS_PAGO[numero - 1];
            }
        } catch (NumberFormatException e) {
            // no era un numero
        }
        return null;
    }
}
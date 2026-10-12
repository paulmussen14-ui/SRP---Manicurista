package ui;

import java.util.Scanner;

import models.Boleta;
import models.Reserva;
import models.Venta;
import services.ReservaServicio;
import services.VentaServicio;

public class MenuVentas {
    private static final String LINEA = "============================================";

    private VentaServicio ventas;
    private ReservaServicio reservas;

    public MenuVentas(VentaServicio ventas, ReservaServicio reservas) {
        this.ventas = ventas;
        this.reservas = reservas;
    }

    public void mostrar(Scanner teclado) {
        String opcion = "";
        while (!opcion.equals("0")) {
            System.out.println();
            System.out.println("+========================================+");
            System.out.println("|                 VENTAS                 |");
            System.out.println("+========================================+");
            System.out.println("|   [1]  Cobrar una reserva              |");
            System.out.println("|   [2]  Ver boleta por numero           |");
            System.out.println("|   [3]  Ventas del dia                  |");
            System.out.println("|   [4]  Ver todas las ventas            |");
            System.out.println("|   [5]  Total vendido                   |");
            System.out.println("|   [0]  Volver                          |");
            System.out.println("+========================================+");
            System.out.print("Elige una opcion: ");
            opcion = teclado.nextLine().trim();

            switch (opcion) {
                case "1":
                    cobrarReserva(teclado);
                    break;
                case "2":
                    verBoleta(teclado);
                    break;
                case "3":
                    System.out.println(LINEA);
                    ventas.mostrarVentasDelDia(ventas.hoy());
                    System.out.println(LINEA);
                    break;
                case "4":
                    System.out.println(LINEA);
                    ventas.mostrarVentas();
                    System.out.println(LINEA);
                    break;
                case "5":
                    System.out.println(LINEA);
                    System.out.println("Total vendido: S/ " + ventas.totalVendido());
                    System.out.println(LINEA);
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private String pedirMetodoPago(Scanner teclado) {
        System.out.println("  [1] Efectivo");
        System.out.println("  [2] Yape");
        System.out.println("  [3] Plin");
        System.out.println("  [4] Tarjeta");
        System.out.print("Metodo de pago: ");
        switch (teclado.nextLine().trim()) {
            case "1":
                return "Efectivo";
            case "2":
                return "Yape";
            case "3":
                return "Plin";
            case "4":
                return "Tarjeta";
            default:
                return null;
        }
    }

    private void cobrarReserva(Scanner teclado) {
        System.out.println(LINEA);
        System.out.println("-------COBRAR RESERVA-------\n");
        System.out.print("Codigo de la reserva: ");
        int codigo;
        try {
            codigo = Integer.parseInt(teclado.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("El codigo debe ser un numero.");
            return;
        }

        Reserva r = reservas.buscarReserva(codigo);
        if (r == null) {
            System.out.println("La reserva no existe.");
            return;
        }
        if (ventas.reservaYaVendida(r)) {
            System.out.println("Esa reserva ya fue cobrada.");
            return;
        }
        if (!r.getEstado().equals(Reserva.ATENDIDA)) {
            System.out.println("Solo se cobra una reserva atendida. Estado actual: " + r.getEstado());
            return;
        }

        System.out.println("Clienta: " + r.getCita().getCliente().getNombreCompleto());
        System.out.println("Saldo a cobrar: S/ " + r.calcularSaldo());

        String metodo = pedirMetodoPago(teclado);
        if (metodo == null) {
            System.out.println("Metodo no valido. No se registro la venta.");
            return;
        }

        Venta venta = ventas.registrarVenta(r, metodo);
        if (venta == null) {
            System.out.println("No se pudo registrar la venta.");
            return;
        }

        System.out.println("Venta registrada.\n");
        ventas.buscarBoleta(venta.getCodigo()).mostrarBoleta();
        System.out.println(LINEA);
    }

    private void verBoleta(Scanner teclado) {
        System.out.println(LINEA);
        System.out.println("-------VER BOLETA-------\n");
        System.out.print("Numero de boleta (solo el numero): ");
        try {
            int numero = Integer.parseInt(teclado.nextLine().trim());
            Boleta b = ventas.buscarBoleta(numero);
            if (b == null) {
                System.out.println("La boleta no existe.");
            } else {
                b.mostrarBoleta();
            }
        } catch (NumberFormatException e) {
            System.out.println("Debes escribir un numero.");
        }
        System.out.println(LINEA);
    }
}
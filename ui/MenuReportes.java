package ui;

import utils.Consola;

import java.util.ArrayList;
import java.util.Scanner;

import models.Cliente;
import models.ClienteVIP;
import models.Reserva;
import models.Venta;
import services.ReservaServicio;
import services.VentaServicio;

public class MenuReportes {
    private static final String LINEA = "============================================";

    private VentaServicio ventas;
    private ReservaServicio reservas;

    public MenuReportes(VentaServicio ventas, ReservaServicio reservas) {
        this.ventas = ventas;
        this.reservas = reservas;
    }

    public void mostrar(Scanner teclado) {
        String opcion = "";
        while (!opcion.equals("0")) {
            System.out.println();
            System.out.println("+========================================+");
            System.out.println("|                REPORTES                |");
            System.out.println("+========================================+");
            System.out.println("|   [1]  Reservas por dia                |");
            System.out.println("|   [2]  Ingresos por dia                |");
            System.out.println("|   [3]  Ingresos por metodo de pago     |");
            System.out.println("|   [4]  Clientas VIP atendidas          |");
            System.out.println("|   [0]  Volver                          |");
            System.out.println("+========================================+");
            System.out.print("Elige una opcion: ");
            opcion = teclado.nextLine().trim();

            switch (opcion) {
                case "1":
                    reservasPorDia();
                    break;
                case "2":
                    ingresosPorDia();
                    break;
                case "3":
                    ingresosPorMetodo();
                    break;
                case "4":
                    clientasVip();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private void reservasPorDia() {
        System.out.println(LINEA);
        System.out.println("-------RESERVAS POR DIA-------\n");
        ArrayList<String> fechas = new ArrayList<>();
        ArrayList<Integer> cantidades = new ArrayList<>();

        for (Reserva r : reservas.getReservas()) {
            if (!r.estaActiva()) {
                continue;
            }
            String fecha = r.getCita().getFecha();
            int pos = fechas.indexOf(fecha);
            if (pos == -1) {
                fechas.add(fecha);
                cantidades.add(1);
            } else {
                cantidades.set(pos, cantidades.get(pos) + 1);
            }
        }

        if (fechas.isEmpty()) {
            System.out.println("No hay reservas.");
        }
        for (int i = 0; i < fechas.size(); i++) {
            System.out.println(fechas.get(i) + " -> " + cantidades.get(i) + " reserva(s)");
        }
        System.out.println(LINEA);
    }

    private void ingresosPorDia() {
        System.out.println(LINEA);
        System.out.println("-------INGRESOS POR DIA-------\n");
        ArrayList<String> fechas = new ArrayList<>();
        ArrayList<Double> totales = new ArrayList<>();

        for (Venta v : ventas.getVentas()) {
            int pos = fechas.indexOf(v.getFecha());
            if (pos == -1) {
                fechas.add(v.getFecha());
                totales.add(v.getTotal());
            } else {
                totales.set(pos, totales.get(pos) + v.getTotal());
            }
        }

        if (fechas.isEmpty()) {
            System.out.println("No hay ventas.");
        }
        for (int i = 0; i < fechas.size(); i++) {
            System.out.println(fechas.get(i) + " -> " + Consola.dinero(totales.get(i)));
        }
        System.out.println(LINEA);
    }

    private void ingresosPorMetodo() {
        System.out.println(LINEA);
        System.out.println("-------INGRESOS POR METODO DE PAGO-------\n");
        String[] metodos = Consola.METODOS_PAGO;
        double[] sumas = new double[metodos.length];
        int[] cantidades = new int[metodos.length];

        // Cada venta se compone del adelanto (al reservar) y del saldo (al cobrar)
        for (Venta v : ventas.getVentas()) {
            for (int i = 0; i < metodos.length; i++) {
                if (metodos[i].equals(v.getReserva().getMetodoAdelanto())) {
                    sumas[i] += v.getReserva().getAdelanto();
                    cantidades[i]++;
                }
                if (metodos[i].equals(v.getMetodoPago())) {
                    sumas[i] += v.calcularPrecio();
                    cantidades[i]++;
                }
            }
        }

        double total = 0;
        for (int i = 0; i < metodos.length; i++) {
            System.out.println(metodos[i] + " -> " + Consola.dinero(sumas[i]) + " (" + cantidades[i] + " cobro(s))");
            total += sumas[i];
        }
        System.out.println("TOTAL -> " + Consola.dinero(total) + " (adelantos + saldos de ventas cobradas)");
        System.out.println(LINEA);
    }

    private void clientasVip() {
        System.out.println(LINEA);
        System.out.println("-------CLIENTAS VIP ATENDIDAS-------\n");
        ArrayList<String> clientas = new ArrayList<>();
        ArrayList<Integer> veces = new ArrayList<>();

        for (Venta v : ventas.getVentas()) {
            Cliente c = v.getReserva().getCita().getCliente();
            if (c instanceof ClienteVIP) {
                String clave = c.getNombreCompleto() + " (" + c.getTelefono() + ")";
                int pos = clientas.indexOf(clave);
                if (pos == -1) {
                    clientas.add(clave);
                    veces.add(1);
                } else {
                    veces.set(pos, veces.get(pos) + 1);
                }
            }
        }

        if (clientas.isEmpty()) {
            System.out.println("Aun no se atendio a ninguna clienta VIP.");
        }
        for (int i = 0; i < clientas.size(); i++) {
            System.out.println(clientas.get(i) + " -> " + veces.get(i) + " atencion(es)");
        }
        System.out.println(LINEA);
    }
}
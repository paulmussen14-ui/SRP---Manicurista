package services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import models.Boleta;
import models.Reserva;
import models.Venta;

public class VentaServicio {
    private ArrayList<Venta> ventas = new ArrayList<>();
    private ArrayList<Boleta> boletas = new ArrayList<>();
    private int siguienteCodigo = 1;

    public String hoy() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public boolean reservaYaVendida(Reserva reserva) {
        for (Venta v : ventas) {
            if (v.getReserva() == reserva) {
                return true;
            }
        }
        return false;
    }

    public Venta registrarVenta(Reserva reserva, String metodoPago) {
        if (reserva == null || metodoPago == null || metodoPago.isEmpty()) {
            return null;
        }
        if (!reserva.getEstado().equals(Reserva.ATENDIDA)) {
            return null;
        }
        if (reservaYaVendida(reserva)) {
            return null;
        }

        Venta venta = new Venta(siguienteCodigo, reserva, metodoPago, hoy());
        String numero = String.format("B-%04d", siguienteCodigo);
        boletas.add(new Boleta(numero, venta, hoy()));
        ventas.add(venta);
        siguienteCodigo++;
        return venta;
    }

    public Venta buscarVenta(int codigo) {
        for (Venta v : ventas) {
            if (v.getCodigo() == codigo) {
                return v;
            }
        }
        return null;
    }
    
    public ArrayList<Venta> getVentas() {
        return new ArrayList<>(ventas);
    }

    public Boleta buscarBoleta(int numero) {
        for (Boleta b : boletas) {
            if (b.getVenta().getCodigo() == numero) {
                return b;
            }
        }
        return null;
    }

    public void mostrarVentas() {
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }
        for (Venta v : ventas) {
            v.mostrarResumen();
        }
    }

    public double totalVendido() {
        double suma = 0;
        for (Venta v : ventas) {
            suma += v.getTotal();
        }
        return suma;
    }

    public void mostrarVentasDelDia(String fecha) {
        double suma = 0;
        int cantidad = 0;
        for (Venta v : ventas) {
            if (v.getFecha().equals(fecha)) {
                v.mostrarResumen();
                suma += v.getTotal();
                cantidad++;
            }
        }
        if (cantidad == 0) {
            System.out.println("No hay ventas en esa fecha.");
        } else {
            System.out.println("Ventas del dia: " + cantidad + " | Total: S/ " + suma);
        }
    }
}
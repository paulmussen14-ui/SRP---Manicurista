package models;

import utils.Consola;

import interfaces.Cobrable;

public class Venta implements Cobrable {
    private int codigo;
    private Reserva reserva;
    private String metodoPago;
    private String fecha;
    private double saldoCobrado;

    public Venta(int codigo, Reserva reserva, String metodoPago, String fecha) {
        this.codigo = codigo;
        this.reserva = reserva;
        this.metodoPago = metodoPago;
        this.fecha = fecha;
        this.saldoCobrado = reserva.calcularSaldo();
    }

    public int getCodigo() {
        return codigo;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public String getFecha() {
        return fecha;
    }

    public double getTotal() {
        return reserva.calcularPrecio();
    }

    @Override
    public double calcularPrecio() {
        return saldoCobrado;
    }

    public void mostrarResumen() {
        System.out.println("Venta #" + codigo + " | " + fecha
                + " | " + reserva.getCita().getCliente().getNombreCompleto()
                + " | Total: " + Consola.dinero(getTotal())
                + " | Saldo cobrado: " + Consola.dinero(saldoCobrado)
                + " | " + metodoPago);
    }
}
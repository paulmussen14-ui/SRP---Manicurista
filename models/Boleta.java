package models;

import utils.Consola;

public class Boleta {
    private String numero;
    private Venta venta;
    private String fechaEmision;

    public Boleta(String numero, Venta venta, String fechaEmision) {
        this.numero = numero;
        this.venta = venta;
        this.fechaEmision = fechaEmision;
    }

    public String getNumero() {
        return numero;
    }

    public Venta getVenta() {
        return venta;
    }

    public void mostrarBoleta() {
        Reserva r = venta.getReserva();
        System.out.println("========== BOLETA " + numero + " ==========");
        System.out.println("Fecha de emision: " + fechaEmision);
        r.getCita().mostrarInformacion();
        System.out.println("Subtotal: " + Consola.dinero(r.getCita().calcularPrecio()));
        System.out.println("Descuento: " + Consola.dinero(r.calcularDescuento()));
        System.out.println("Total: " + Consola.dinero(r.calcularPrecio()));
        System.out.println("Adelanto pagado: " + Consola.dinero(r.getAdelanto()));
        System.out.println("Saldo pagado: " + Consola.dinero(venta.calcularPrecio()));
        System.out.println("Metodo de pago: " + venta.getMetodoPago());
        System.out.println("Gracias por tu visita - Marie Nails");
        System.out.println("=====================================");
    }
}
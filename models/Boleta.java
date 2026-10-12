package models;

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
        System.out.println("Subtotal: S/ " + r.getCita().calcularPrecio());
        System.out.println("Descuento: S/ " + r.calcularDescuento());
        System.out.println("Total: S/ " + r.calcularPrecio());
        System.out.println("Adelanto pagado: S/ " + r.getAdelanto());
        System.out.println("Saldo pagado: S/ " + venta.calcularPrecio());
        System.out.println("Metodo de pago: " + venta.getMetodoPago());
        System.out.println("Gracias por tu visita - Marie Nails");
        System.out.println("=====================================");
    }
}
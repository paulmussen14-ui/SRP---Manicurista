package models;

import utils.Consola;

import interfaces.Cobrable;
import interfaces.Descontable;

public class Reserva implements Cobrable {
    public static final String PENDIENTE = "Pendiente";
    public static final String CONFIRMADA = "Confirmada";
    public static final String ATENDIDA = "Atendida";
    public static final String CANCELADA = "Cancelada";

    private int codigo;
    private Cita cita;
    private double adelanto;
    private String metodoAdelanto = "";
    private String estado;


    public Reserva(Cita cita) {
        this(0, cita);
    }
    public Reserva(int codigo, Cita cita) {
        this.codigo = codigo;
        this.cita = cita;
        this.adelanto = 0;
        this.estado = PENDIENTE;
    }

    public int getCodigo() {
        return codigo;
    }

    public Cita getCita() {
        return cita;
    }

    public double getAdelanto() {
        return adelanto;
    }

    public String getMetodoAdelanto() {
        return metodoAdelanto;
    }

    public void setMetodoAdelanto(String metodoAdelanto) {
        if (metodoAdelanto != null) {
            this.metodoAdelanto = metodoAdelanto;
        }
    }

    public String getEstado() {
        return estado;
    }

    public boolean estaActiva() {
        return !estado.equals(CANCELADA);
    }

    public double calcularDescuento() {
        double subtotal = cita.calcularPrecio();
        if (cita.getCliente() instanceof Descontable) {
            Descontable d = (Descontable) cita.getCliente();
            return d.calcularDescuento(subtotal);
        }
        return 0;
    }

    @Override
    public double calcularPrecio() {
        return cita.calcularPrecio() - calcularDescuento();
    }

    public double getAdelantoMinimo() {
        return calcularPrecio() * 50 / 100;
    }

    public boolean registrarAdelanto(double monto) {
        if (estado.equals(CANCELADA) || estado.equals(ATENDIDA)) {
            return false;
        }
        if (monto < getAdelantoMinimo() || monto > calcularPrecio()) {
            return false;
        }
        adelanto = monto;
        estado = CONFIRMADA;
        return true;
    }

    public boolean cancelar() {
        if (estado.equals(CANCELADA) || estado.equals(ATENDIDA)) {
            return false;
        }
        estado = CANCELADA;
        return true;
    }

    public boolean marcarAtendida() {
        if (estado.equals(CANCELADA) || estado.equals(ATENDIDA)) {
            return false;
        }
        estado = ATENDIDA;
        return true;
    }

    public double calcularSaldo() {
        return calcularPrecio() - adelanto;
    }

    public void mostrarComprobante() {
        System.out.println("--- COMPROBANTE DE RESERVA ---");
        System.out.println("Codigo: " + codigo);
        cita.mostrarInformacion();
        System.out.println("Subtotal: " + Consola.dinero(cita.calcularPrecio()));
        System.out.println("Descuento: " + Consola.dinero(calcularDescuento()));
        System.out.println("Total: " + Consola.dinero(calcularPrecio()));
        String via = metodoAdelanto.isEmpty() ? "" : " (" + metodoAdelanto + ")";
        System.out.println("Adelanto: " + Consola.dinero(adelanto) + via);
        System.out.println("Saldo pendiente: " + Consola.dinero(calcularSaldo()));
        System.out.println("Estado: " + estado);
    }

    public void mostrarResumen() {
        System.out.println("Codigo: " + codigo);
        System.out.println("Clienta: " + cita.getCliente().getNombreCompleto());
        System.out.println("Cita: " + cita.getFecha() + " " + cita.getHora());
        System.out.println("Estado: " + estado);
        System.out.println("----------------------------------------");
    }
}
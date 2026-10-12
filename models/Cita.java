package models;

import java.util.ArrayList;

import interfaces.Cobrable;

public class Cita implements Cobrable {
    private Cliente cliente;
    private Sistemas principal;
    private Manicurista manicurista;
    private ArrayList<Sistemas> extras = new ArrayList<>();
    private String fecha;
    private String hora;

    public Cita(Cliente cliente, Sistemas principal, Manicurista manicurista, String fecha, String hora) {
        this.cliente = cliente;
        this.principal = principal;
        this.manicurista = manicurista;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Sistemas getPrincipal() {
        return principal;
    }

    public Manicurista getManicurista() {
        return manicurista;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public ArrayList<Sistemas> getExtras() {
        return new ArrayList<>(extras);
    }

    public void agregarExtra(Sistemas extra) {
        if (extra != null) {
            extras.add(extra);
        }
    }

    @Override
    public double calcularPrecio() {
        double total = principal.calcularPrecio();
        for (Sistemas e : extras) {
            total += e.calcularPrecio();
        }
        return total;
    }

    public void mostrarInformacion() {
        System.out.println("Clienta: " + cliente.getNombreCompleto());
        System.out.println("Manicurista: " + manicurista.getNombreCompleto());
        System.out.println("Servicio: " + principal.getNombre() + " - S/ " + principal.calcularPrecio());
        for (Sistemas e : extras) {
            System.out.println("  + " + e.getNombre() + " (" + e.getTipo() + ") - S/ " + e.calcularPrecio());
        }
        System.out.println("Fecha: " + fecha);
        System.out.println("Hora: " + hora);
    }
}
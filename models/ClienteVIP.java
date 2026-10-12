package models;

import interfaces.Descontable;

public class ClienteVIP extends Cliente implements Descontable {

    private String beneficio;
    private double porcentajeDescuento;

    public ClienteVIP(String nombre, String apellido, String telefono, String beneficio,
            double porcentajeDescuento) {
        super(nombre, apellido, telefono, "VIP");
        this.beneficio = beneficio;
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public String getBeneficio() {
        return beneficio;
    }

    public void setBeneficio(String beneficio) {
        this.beneficio = beneficio;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    @Override
    public double calcularDescuento(double precio) {
        return precio * porcentajeDescuento / 100;
    }

    @Override
    public String getRol() {
        return "Cliente VIP";
    }
}
package models;

public class Efecto extends Sistemas {

    public Efecto(String nombre, double precio) {
        super(nombre, precio);
    }

    @Override
    public String getTipo() {
        return "Efecto";
    }
}
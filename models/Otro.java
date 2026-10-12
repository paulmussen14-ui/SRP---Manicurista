package models;

public class Otro extends Sistemas {

    public Otro(String nombre, double precio) {
        super(nombre, precio);
    }

    @Override
    public String getTipo() {
        return "Otro";
    }
}
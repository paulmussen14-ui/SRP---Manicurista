package models;

public class Cliente extends Persona {
    private String categoria;

    public Cliente(String nombre, String apellido, String telefono, String categoria) {
        super(nombre, apellido, telefono);
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String getRol() {
        return "Cliente";
    }

}

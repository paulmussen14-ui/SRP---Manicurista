package models;

public class Manicurista extends Usuario {
    private String especialidad;

    public Manicurista(String nombre, String apellido, String telefono, String nombreUsuario, String clave,
            boolean activo, String especialidad) {
        super(nombre, apellido, telefono, nombreUsuario, clave, activo);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public String getRol() {
        return "Manicurista";
    }
}

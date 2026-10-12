package models;

public class Administrador extends Usuario {

    public Administrador(String nombre, String apellido, String telefono, String nombreUsuario, String clave,
            boolean activo) {
        super(nombre, apellido, telefono, nombreUsuario, clave, activo);
    }

    @Override
    public String getRol() {
        return "Administrador";
    }

}

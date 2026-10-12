package models;

import interfaces.Autenticable;

public abstract class Usuario extends Persona implements Autenticable {
    private String nombreUsuario;
    private String clave;
    private boolean activo;

    public Usuario(String nombre, String apellido, String telefono, String nombreUsuario, String clave,
            boolean activo) {
        super(nombre, apellido, telefono);
        this.nombreUsuario = nombreUsuario;
        this.clave = clave;
        this.activo = activo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public boolean validarClave(String clave) {
        return this.clave.equals(clave);
    }

}

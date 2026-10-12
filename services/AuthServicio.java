package services;

import models.Usuario;

public class AuthServicio {
    private UsuarioServicio usuarioServicio;
    private Usuario sesionActual;

    public AuthServicio(UsuarioServicio usuarioServicio, Usuario sesionActual) {
        this.usuarioServicio = usuarioServicio;
        this.sesionActual = sesionActual;
    }

    public boolean login(String nombreUsuario, String clave) {
        Usuario u = usuarioServicio.buscarUsuario(nombreUsuario);
        if (u == null) {
            return false;
        }
        if (!u.isActivo()) {
            return false;
        }
        if (!u.validarClave(clave)) {
            return false;
        }
        sesionActual = u;
        return true;
    }

    public void cerrarSesion() {
        sesionActual = null;
    }

    public boolean haySesion() {
        return sesionActual != null;
    }

    public Usuario getSesionActual() {
        return sesionActual;
    }
}

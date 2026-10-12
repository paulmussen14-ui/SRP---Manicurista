package services;

import java.util.ArrayList;

import models.Administrador;
import models.Usuario;
import models.Manicurista;

public class UsuarioServicio {
    private ArrayList<Usuario> usuarios = new ArrayList<>();

    public UsuarioServicio() {
        usuarios.add(new Administrador("Camile", "Flores", "999888777", "camile", "1234", true));
    }

    public Usuario buscarUsuario(String nombreUsuario) {
        for (Usuario u : usuarios) {
            if (u.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                return u;
            }
        }
        return null;
    }

    public boolean agregarUsuario(Usuario nuevo) {
        if (nuevo == null || buscarUsuario(nuevo.getNombreUsuario()) != null) {
            return false;
        }
        usuarios.add(nuevo);
        return true;
    }

    public boolean eliminarUsuario(String nombreUsuario) {
        Usuario u = buscarUsuario(nombreUsuario);
        if (u == null) {
            return false;
        }
        usuarios.remove(u);
        return true;
    }

    public ArrayList<Manicurista> getManicuristas() {
    ArrayList<Manicurista> lista = new ArrayList<>();
    for (Usuario u : usuarios) {
        if (u instanceof Manicurista) {
            lista.add((Manicurista) u);
        }
    }
    return lista;
}

    public boolean editarUsuario(String nombreUsuario, String nombre, String apellido, String telefono, String clave) {
        Usuario u = buscarUsuario(nombreUsuario);
        if (u == null) {
            return false;
        }
        if (!nombre.isEmpty()) {
            u.setNombre(nombre);
        }
        if (!apellido.isEmpty()) {
            u.setApellido(apellido);
        }
        if (!telefono.isEmpty()) {
            u.setTelefono(telefono);
        }
        if (!clave.isEmpty()) {
            u.setClave(clave);
        }
        return true;
    }

    public void mostrarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        for (Usuario u : usuarios) {
            System.out.println("Nombre Completo: " + u.getNombreCompleto());
            System.out.println("Rol: " + u.getRol());
            System.out.println("Telefono: " + u.getTelefono());
            System.out.println("Es Activo: " + u.isActivo());
            System.out.println("=========================================\n");
        }
    }
}
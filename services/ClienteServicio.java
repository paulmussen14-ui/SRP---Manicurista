package services;

import java.util.ArrayList;

import models.Cliente;

public class ClienteServicio {
    private ArrayList<Cliente> clientes = new ArrayList<>();

    public Cliente buscarCliente(String telefono) {
        for (Cliente c : clientes) {
            if(c.getTelefono().equals(telefono)) {
                return c;
            }
        }
        return null;
    }

    public boolean agregarCliente(Cliente nuevo) {
        if(nuevo == null || nuevo.getTelefono().isEmpty()){
            return false;
        }

        if(buscarCliente(nuevo.getTelefono()) != null) {
            return false;
        }
        clientes.add(nuevo);
        return true;
    }

    public boolean eliminarCliente(String telefono) {
        Cliente c = buscarCliente(telefono);
        if(c == null) {
            return false;
        }
        clientes.remove(c);
        return true;
    }

    public boolean editarCliente(String telefonoActual, String nombre, String apellido,
        String telefonoNuevo) {
    Cliente c = buscarCliente(telefonoActual);
    if (c == null) {
        return false;
    }

    if (!telefonoNuevo.isEmpty()) {
        Cliente otro = buscarCliente(telefonoNuevo);
        if (otro != null && otro != c) {
            return false;
        }
        c.setTelefono(telefonoNuevo);
    }

    if (!nombre.isEmpty()) {
        c.setNombre(nombre);
    }
    if (!apellido.isEmpty()) {
        c.setApellido(apellido);
    }
    return true;
}

    public void mostrarClientes() {
        if(clientes.isEmpty()) {
            System.out.println("No hay Clientes Registrados.");
            return;
        }
        int numero = 1;
        for(Cliente c : clientes) {
            System.out.println("=========================================\n");
            System.out.println("Cliente #" + numero);
            System.out.println("Nombre: " + c.getNombre());
            System.out.println("Apellido: " + c.getApellido());
            System.out.println("Telefono: " + c.getTelefono());
            numero++;
        }
        System.out.println("=========================================\n");
    }
}

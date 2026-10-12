package ui;

import java.util.Scanner;

import models.Cliente;
import models.ClienteVIP;
import services.ClienteServicio;

public class MenuClientes {
    private static final String LINEA = "============================================";

    private ClienteServicio clientes;

    public MenuClientes(ClienteServicio clientes) {
        this.clientes = clientes;
    }

public void mostrar(Scanner teclado) {
    String opcion = "";
    while (!opcion.equals("0")) {
        System.out.println();
        System.out.println("+========================================+");
        System.out.println("|            GESTION DE CLIENTES         |");
        System.out.println("+========================================+");
        System.out.println("|   [1]  Registrar cliente               |");
        System.out.println("|   [2]  Registrar cliente VIP           |");
        System.out.println("|   [3]  Ver todos los clientes          |");
        System.out.println("|   [4]  Buscar por telefono             |");
        System.out.println("|   [5]  Editar cliente                  |");
        System.out.println("|   [6]  Eliminar cliente                |");
        System.out.println("|   [0]  Volver                          |");
        System.out.println("+========================================+");
        System.out.print("Elige una opcion: ");
        opcion = teclado.nextLine().trim();

        switch (opcion) {
            case "1":
                registrarCliente(teclado);
                break;
            case "2":
                registrarVip(teclado);
                break;
            case "3":
                clientes.mostrarClientes();
                break;
            case "4":
                buscarCliente(teclado);
                break;
            case "5":
                editarCliente(teclado);
                break;
            case "6":
                eliminarCliente(teclado);
                break;
            case "0":
                break;
            default:
                System.out.println("Opcion no valida.");
        }
    }
}

    private void registrarCliente(Scanner teclado) {
        System.out.println(LINEA);
        System.out.println("-------REGISTRAR CLIENTE-------\n");
        System.out.print("Nombre: ");
        String nombre = teclado.nextLine().trim();
        System.out.print("Apellido: ");
        String apellido = teclado.nextLine().trim();
        System.out.print("Telefono: ");
        String telefono = teclado.nextLine().trim();
        System.out.print("Categoria: ");
        String categoria = teclado.nextLine().trim();
        System.out.println(LINEA);

        if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty()) {
            System.out.println("Nombre, apellido y telefono son obligatorios.");
            return;
        }

        Cliente cliente = new Cliente(nombre, apellido, telefono, categoria);

        if (clientes.agregarCliente(cliente)) {
            System.out.println("Cliente registrado correctamente. Dale la bienvenida.");
        } else {
            System.out.println("Ya existe un cliente con ese telefono.");
        }
        System.out.println(LINEA);
    }

    private void registrarVip(Scanner teclado) {
        System.out.println(LINEA);
        System.out.println("-------REGISTRAR CLIENTE VIP-------\n");
        System.out.print("Nombre: ");
        String nombre = teclado.nextLine().trim();
        System.out.print("Apellido: ");
        String apellido = teclado.nextLine().trim();
        System.out.print("Telefono: ");
        String telefono = teclado.nextLine().trim();
        System.out.print("Beneficio (ej: Diseño gratis): ");
        String beneficio = teclado.nextLine().trim();
        System.out.print("Descuento en % (0-100): ");
        String textoDescuento = teclado.nextLine().trim();
        System.out.println(LINEA);

        if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty()) {
            System.out.println("Nombre, apellido y telefono son obligatorios.");
            return;
        }

        double descuento;
        try {
            descuento = Double.parseDouble(textoDescuento);
        } catch (NumberFormatException e) {
            System.out.println("El descuento debe ser un numero.");
            return;
        }
        if (descuento < 0 || descuento > 100) {
            System.out.println("El descuento debe estar entre 0 y 100.");
            return;
    }

    ClienteVIP vip = new ClienteVIP(nombre, apellido, telefono, beneficio, descuento);

        if (clientes.agregarCliente(vip)) {
            System.out.println("Cliente VIP registrado correctamente. Dale la bienvenida.");
        } else {
            System.out.println("Ya existe un cliente con ese telefono.");
        }
    System.out.println(LINEA);
    }

    private void buscarCliente(Scanner teclado) {
        System.out.println(LINEA);
        System.out.println("-------BUSCAR CLIENTE-------\n");
        System.out.print("Telefono: ");
        String telefono = teclado.nextLine().trim();

        Cliente c = clientes.buscarCliente(telefono);

        if (c == null) {
            System.out.println("El cliente no existe.");
        } else {
            System.out.println("Nombre: " + c.getNombreCompleto());
            System.out.println("Telefono: " + c.getTelefono());
            System.out.println("Tipo: " + c.getRol());
        }
        System.out.println(LINEA);
    }

    private void editarCliente(Scanner teclado) {
        System.out.println(LINEA);
        System.out.println("-------EDITAR CLIENTE-------\n");
        System.out.print("Telefono actual del cliente: ");
        String telefono = teclado.nextLine().trim();

        if (clientes.buscarCliente(telefono) == null) {
            System.out.println("El cliente no existe.");
            return;
        }

        System.out.println("Deja vacio lo que no quieras cambiar.");
        System.out.print("Nuevo nombre: ");
        String nuevoNombre = teclado.nextLine().trim();
        System.out.print("Nuevo apellido: ");
        String nuevoApellido = teclado.nextLine().trim();
        System.out.print("Nuevo telefono: ");
        String nuevoTelefono = teclado.nextLine().trim();

        if (clientes.editarCliente(telefono, nuevoNombre, nuevoApellido, nuevoTelefono)) {
            System.out.println("Cliente editado correctamente.");
        } else {
            System.out.println("No se pudo editar: ese telefono ya lo usa otro cliente.");
        }
        System.out.println(LINEA);
    }

    private void eliminarCliente(Scanner teclado) {
        System.out.println(LINEA);
        System.out.println("-------ELIMINAR CLIENTE-------\n");
        System.out.print("Telefono del cliente a eliminar: ");
        String telefono = teclado.nextLine().trim();

        Cliente c = clientes.buscarCliente(telefono);
        if (c == null) {
            System.out.println("El cliente no existe.");
            return;
        }

        System.out.print("Seguro que quieres eliminar a " + c.getNombreCompleto() + "? (si o no): ");
        String confirmar = teclado.nextLine().trim();

        if (confirmar.equalsIgnoreCase("si")) {
            clientes.eliminarCliente(telefono);
            System.out.println("Cliente eliminado.");
        } else {
            System.out.println("Operacion cancelada.");
        }
        System.out.println(LINEA);
    }
}

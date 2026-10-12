package ui;

import java.util.Scanner;

import models.Administrador;
import models.Manicurista;
import models.Usuario;
import services.AuthServicio;
import services.UsuarioServicio;

public class MenuPrincipal {
    private static final String LINEA = "============================================";

    private AuthServicio auth;
    private UsuarioServicio usuarios;
    private MenuClientes menuClientes;
    private MenuReservas menuReservas;
    private MenuVentas menuVentas;
    private MenuReportes menuReportes;
    private MenuCatalogo menuCatalogo;

    public MenuPrincipal(AuthServicio auth, UsuarioServicio usuarios, MenuClientes menuClientes,
        MenuReservas menuReservas, MenuVentas menuVentas, MenuReportes menuReportes, MenuCatalogo menuCatalogo) {
        this.auth = auth;
        this.usuarios = usuarios;
        this.menuClientes = menuClientes;
        this.menuReservas = menuReservas;
        this.menuVentas = menuVentas;
        this.menuReportes = menuReportes;
        this.menuCatalogo = menuCatalogo;
    }

    public void mostrar(Scanner teclado) {
        while (auth.haySesion()) {
            if (auth.getSesionActual() instanceof Administrador) {
                menuAdministrador(teclado);
            } else {
                menuManicurista(teclado);
            }
        }
    }

    private void menuAdministrador(Scanner teclado) {
        System.out.println();
        System.out.println("+========================================+");
        System.out.println("|        PANEL DEL ADMINISTRADOR         |");
        System.out.println("+========================================+");
        System.out.println("|   [1]  Ver usuarios                    |");
        System.out.println("|   [2]  Agregar manicurista             |");
        System.out.println("|   [3]  Agregar administrador           |");
        System.out.println("|   [4]  Editar usuario                  |");
        System.out.println("|   [5]  Eliminar usuario                |");
        System.out.println("|   [6]  Cambiar mi clave                |");
        System.out.println("|   [7]  Gestionar clientes              |");
        System.out.println("|   [8]  Gestionar reservas              |");
        System.out.println("|   [9]  Ventas                          |");
        System.out.println("|   [10] Reportes                        |");
        System.out.println("|   [11] Gestionar catalogo              |");
        System.out.println("|   [0]  Cerrar sesion                   |");
        System.out.println("+========================================+");

        System.out.println("Seleccione una opcion: ");
        String opcion = teclado.nextLine().trim();

        switch (opcion) {
            case "1":
                System.out.println();
                usuarios.mostrarUsuarios();
                break;
            case "2":
                agregarManicurista(teclado);
                break;
            case "3":
                agregarAdministrador(teclado);
                break;
            case "4":
                editarUsuario(teclado);
                break;
            case "5":
                eliminarUsuario(teclado);
                break;
            case "6":
                cambiarMiClave(teclado);
                break;
            case "7":
                menuClientes.mostrar(teclado);
                break;
            case "8":
                menuReservas.mostrar(teclado);
                break;
            case "9":
                menuVentas.mostrar(teclado);
                break;
            case "10":
                menuReportes.mostrar(teclado);
                break;
            case "11":
                menuCatalogo.mostrar(teclado);
                break;
            case "0":
                auth.cerrarSesion();
                break;
            default:
                System.out.println("Opcion no valida.");
        }
    }

    private void agregarManicurista(Scanner teclado) {
        System.out.println(LINEA);
        System.out.println("--- NUEVA MANICURISTA ---");
        System.out.println("Nombre: ");
        String nombre = teclado.nextLine().trim();
        System.out.println("Apellido: ");
        String apellido = teclado.nextLine().trim();
        System.out.println("Telefono: ");
        String telefono = teclado.nextLine().trim();
        System.out.println("Especialidad: ");
        String especialidad = teclado.nextLine().trim();
        System.out.println("Usuario: ");
        String usuario = teclado.nextLine().trim();
        System.out.println("Clave: ");
        String clave = teclado.nextLine().trim();
        System.out.println(LINEA);

        if (nombre.isEmpty() || usuario.isEmpty() || clave.isEmpty()) {
            System.out.println("Nombre, Usuario y Clave son obligatorias");
            return;
        }

        if (usuarios.agregarUsuario(new Manicurista(nombre, apellido, telefono, usuario, clave, true, especialidad))) {
            System.out.println("Manicurista registrada correctamente.");
        } else {
            System.out.println("Ese nombre de usuario ya existe.");
        }

    }

    private void agregarAdministrador(Scanner teclado) {
        System.out.println(LINEA);
        System.out.println("--- NUEVA ADMINISTRADOR ---");
        System.out.println("Nombre: ");
        String nombre = teclado.nextLine().trim();
        System.out.println("Apellido: ");
        String apellido = teclado.nextLine().trim();
        System.out.println("Telefono: ");
        String telefono = teclado.nextLine().trim();
        System.out.println("Usuario: ");
        String usuario = teclado.nextLine().trim();
        System.out.println("Clave: ");
        String clave = teclado.nextLine().trim();
        System.out.println(LINEA);

        if (nombre.isEmpty() || usuario.isEmpty() || clave.isEmpty()) {
            System.out.println("Nombre, Usuario y Clve son Obligatorios");
            return;
        }

        if (usuarios.agregarUsuario(new Administrador(nombre, apellido, telefono, usuario, clave, true))) {
            System.out.println("Administrador registrado correctamente.");
        } else {
            System.out.println("Ese nombre de usuario ya existe.");
        }
    }

    private void editarUsuario(Scanner teclado) {
        System.out.println(LINEA);
        System.out.println("--- EDITAR USUARIO ---");
        System.out.println("Usuario a editar: ");
        String usuario = teclado.nextLine().trim();

        if (usuarios.buscarUsuario(usuario) == null) {
            System.out.println("El usuario no existe.");
            return;
        }

        System.out.println("Recomendacion: Deja Vacio lo que no quieres cambiar\n.");
        System.out.println("Nuevo Nombre: ");
        String nuevoNombre = teclado.nextLine().trim();
        System.out.println("Nuevo Apellido: ");
        String nuevoApellido = teclado.nextLine().trim();
        System.out.println("Nuevo Telefono: ");
        String nuevoTelefono = teclado.nextLine().trim();
        System.out.println("Nueva Clave: ");
        String nuevaClave = teclado.nextLine().trim();

        usuarios.editarUsuario(usuario, nuevoNombre, nuevoApellido, nuevoTelefono, nuevaClave);

    }

    private void eliminarUsuario(Scanner teclado) {
        System.out.println(LINEA);
        System.out.println("--- ELIMINAR USUARIO ---\n");
        System.out.println("Usuario a Eliminar: ");
        String usuario = teclado.nextLine().trim();

        if (usuario.equalsIgnoreCase(auth.getSesionActual().getNombreUsuario())) {
            System.out.println("No puedes eliminar tu propio usuario mientras estas en sesion.");
            return;
        }

        System.out.println("Seguro que quieres eliminar a " + usuario + "? (si o no):");
        String confirmar = teclado.nextLine().trim();

        if (confirmar.equalsIgnoreCase("si")) {
            usuarios.eliminarUsuario(usuario);
        } else {
            System.out.println("Operacion Cancelada.");
        }

    }

    private void menuManicurista(Scanner teclado) {
        System.out.println();
        System.out.println("+========================================+");
        System.out.println("|          PANEL DE MANICURISTA          |");
        System.out.println("+========================================+");
        System.out.println("|   [1]  Ver mis datos                   |");
        System.out.println("|   [2]  Cambiar mi clave                |");
        System.out.println("|   [3]  Gestionar clientes              |");
        System.out.println("|   [4]  Gestionar reservas              |");
        System.out.println("|   [5]  Gestionar Ventas                |");
        System.out.println("|   [6]  Reportes                        |");
        System.out.println("|   [0]  Cerrar sesion                   |");
        System.out.println("+========================================+");

        System.out.print("Seleccione una opcion: ");
        String opcion = teclado.nextLine().trim();

        switch (opcion) {
            case "1":
                verMisDatos();
                break;
            case "2":
                cambiarMiClave(teclado);
                break;
            case "3":
                menuClientes.mostrar(teclado);
                break;
            case "4":
                menuReservas.mostrar(teclado);
                break;
            case "5":
                menuVentas.mostrar(teclado);
                break;
            case "6":
                menuReportes.mostrar(teclado);
                break;
            case "0":
                auth.cerrarSesion();
                break;
            default:
                System.out.println("Opcion no valida.");
        }
    }

    private void verMisDatos() {
        Usuario u = auth.getSesionActual();
        System.out.println();
        System.out.println("--- MIS DATOS ---");
        System.out.println("Rol: " + u.getRol());
        System.out.println("Nombre: " + u.getNombreCompleto());
        System.out.println("Usuario: " + u.getNombreUsuario());
        System.out.println("Telefono: " + u.getTelefono());
    }

    private void cambiarMiClave(Scanner teclado) {
        Usuario u = auth.getSesionActual();
        System.out.println();
        System.out.println("------CAMBIAR MI CLAVE------\n");
        System.out.println("Clave Actual: ");
        String actual = teclado.nextLine().trim();

        if (!u.validarClave(actual)) {
            System.out.println("La Clave Actual es incorrecta.");
            return;
        }

        System.out.println("Nueva Clave: ");
        String nueva = teclado.nextLine().trim();
        System.out.println("Repita la nueva Clave: ");
        String repetida = teclado.nextLine().trim();

        if (nueva.isEmpty()) {
            System.out.println("La clave no puede estar vacia.");
            return;
        }
        if (!nueva.equals(repetida)) {
            System.out.println("Las claves no coinciden. No se hizo ningun cambio.");
            return;
        }
        usuarios.editarUsuario(u.getNombreUsuario(), "", "", "", nueva);
    }

}
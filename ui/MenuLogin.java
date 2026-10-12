package ui;

import java.util.Scanner;

import models.Usuario;
import models.Manicurista;

import services.AuthServicio;
import services.UsuarioServicio;

public class MenuLogin {
    private static final String LINEA = "============================================";

    private AuthServicio auth;
    private UsuarioServicio usuarios;

    public MenuLogin(AuthServicio auth, UsuarioServicio usuarios) {
        this.auth = auth;
        this.usuarios = usuarios;
    }

    public void registrarme(Scanner teclado) {
        System.out.println(LINEA);
        System.out.println("-------REGISTRAR UNA NUEVA CUENTA-------\n");
        System.out.print("Nombre: ");
        String nombre = teclado.nextLine().trim();
        System.out.print("Apellido: ");
        String apellido = teclado.nextLine().trim();
        System.out.print("Telefono: ");
        String telefono = teclado.nextLine().trim();
        System.out.print("Especialidad: ");
        String especialidad = teclado.nextLine().trim();
        System.out.print("Nombre de usuario: ");
        String usuario = teclado.nextLine().trim();
        System.out.print("Clave: ");
        String clave = teclado.nextLine().trim();
        System.out.println(LINEA);

        if (nombre.isEmpty() || usuario.isEmpty() || clave.isEmpty()) {
            System.out.println("Nombre, Usuario y Clave son obligatorios.");
            return;
        }

        Manicurista nueva = new Manicurista(nombre, apellido, telefono, usuario, clave, true, especialidad);
        usuarios.agregarUsuario(nueva);

        if (usuarios.agregarUsuario(nueva)) {
            System.out.println("Registro exitoso. Ya puedes iniciar sesion.");
        } else {
            System.out.println("Ese nombre de usuario ya existe. Elige otro.");
        }
    }

    public boolean ingresoLogin(Scanner teclado) {
        System.out.println(LINEA);
        System.out.println("-------INICIAR SESION-------\n");
        for (int intento = 1; intento <= 3; intento++) {
            System.out.print("Usuario: ");
            String usuario = teclado.nextLine().trim();
            System.out.print("Clave: ");
            String clave = teclado.nextLine().trim();

            if (auth.login(usuario, clave)) {
                System.out.println("\nBienvenida, " + auth.getSesionActual().getNombreCompleto()
                        + " (" + auth.getSesionActual().getRol() + ")");
                return true;
            }
            System.out.println("Usuario o clave incorrectos (" + intento + "/3)");
        }
        System.out.println(LINEA);
        return false;
    }

    public void recuperarClave(Scanner teclado) {
        System.out.println(LINEA);
        System.out.println("-------RECUPERAR CLAVE-------\n");
        System.out.print("Nombre de usuario: ");
        String nombreUsuario = teclado.nextLine().trim();
        System.out.print("Telefono registrado: ");
        String telefono = teclado.nextLine().trim();

        Usuario u = usuarios.buscarUsuario(nombreUsuario);

        if (u == null || !u.getTelefono().equals(telefono)) {
            System.out.println("Los Datos no Coinciden.");
            return;
        }

        System.out.print("Nueva clave: ");
        String nueva = teclado.nextLine().trim();
        System.out.print("Repite la nueva clave: ");
        String repetida = teclado.nextLine().trim();

        if (nueva.isEmpty()) {
            System.out.println("La Clave no puede estar vacia.");
            return;
        }
        if (!nueva.equals(repetida)) {
            System.out.println("Las Claves no coinciden. No se hizo ningun cambio.");
            return;
        }

        usuarios.editarUsuario(nombreUsuario, "", "", "", nueva);
        System.out.println("Clave actualizada. Ya puedes iniciar sesion.");

    }

    public boolean mostrarInicio(Scanner teclado) {
        String opcion = "";
        while (!opcion.equals("0")) {
            mostrarBienvenida();
            System.out.print("Elige una opcion: ");
            opcion = teclado.nextLine().trim();

            if (opcion.equals("1")) {
                if (ingresoLogin(teclado)) {
                    return true;
                }
                System.out.println("Demasiados intentos fallidos.");
            } else if (opcion.equals("2")) {
                registrarme(teclado);
            } else if (opcion.equals("3")) {
                recuperarClave(teclado);
            } else if (!opcion.equals("0")) {
                System.out.println("Opcion no valida.");
            }
        }
        return false;
    }

    private void mostrarBienvenida() {
        System.out.println();
        System.out.println("+" + LINEA + "+");
        System.out.println("|                                             |");
        System.out.println("|            BIENVENIDO AL SISTEMA            |");
        System.out.println("|                  MARIE NAILS                |");
        System.out.println("|                                             |");
        System.out.println("+" + LINEA + "+");
        System.out.println("|   [1]  Iniciar sesion                       |");
        System.out.println("|   [2]  Registrarme                          |");
        System.out.println("|   [3]  Recuperar clave                      |");
        System.out.println("|   [0]  Salir                                |");
        System.out.println("+" + LINEA + "+");
    }

}

package ui;

import java.util.ArrayList;
import java.util.Scanner;

import models.Cita;
import models.Cliente;
import models.Manicurista;
import models.Reserva;
import models.Sistemas;
import services.Catalogo;
import services.ClienteServicio;
import services.ReservaServicio;
import services.UsuarioServicio;

public class MenuReservas {
    private static final String LINEA = "============================================";

    private ReservaServicio reservas;
    private ClienteServicio clientes;
    private Catalogo catalogo;
    private UsuarioServicio usuarios;

    public MenuReservas(ReservaServicio reservas, ClienteServicio clientes, Catalogo catalogo,
            UsuarioServicio usuarios) {
        this.reservas = reservas;
        this.clientes = clientes;
        this.catalogo = catalogo;
        this.usuarios = usuarios;
    }

    public void mostrar(Scanner teclado) {
        String opcion = "";
        while (!opcion.equals("0")) {
            System.out.println();
            System.out.println("+========================================+");
            System.out.println("|            GESTION DE RESERVAS         |");
            System.out.println("+========================================+");
            System.out.println("|   [1]  Nueva reserva                   |");
            System.out.println("|   [2]  Cancelar reserva                |");
            System.out.println("|   [3]  Atender reserva (completar)     |");
            System.out.println("|   [4]  Ver todas las reservas          |");
            System.out.println("|   [5]  Ver comprobante por codigo      |");
            System.out.println("|   [0]  Volver                          |");
            System.out.println("+========================================+");
            System.out.print("Elige una opcion: ");
            opcion = teclado.nextLine().trim();

            switch (opcion) {
                case "1":
                    nuevaReserva(teclado);
                    break;
                case "2":
                    cancelarReserva(teclado);
                    break;
                case "3":
                    atenderReserva(teclado);
                    break;
                case "4":
                    reservas.mostrarReservas();
                    break;
                case "5":
                    verComprobante(teclado);
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }
    
    private Reserva pedirReserva(Scanner teclado) {
        System.out.print("Codigo de la reserva: ");
        try {
            int codigo = Integer.parseInt(teclado.nextLine().trim());
            Reserva r = reservas.buscarReserva(codigo);
            if (r == null) {
                System.out.println("La reserva no existe.");
            }
            return r;
        } catch (NumberFormatException e) {
            System.out.println("El codigo debe ser un numero.");
            return null;
        }
    }

    private Manicurista elegirManicurista(Scanner teclado) {
        ArrayList<Manicurista> lista = usuarios.getManicuristas();
        if (lista.isEmpty()) {
            System.out.println("No hay manicuristas registradas. Agrega una desde el panel del administrador.");
            return null;
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + lista.get(i).getNombreCompleto());
        }
        System.out.print("Numero de la manicurista: ");
        try {
            int numero = Integer.parseInt(teclado.nextLine().trim());
            if (numero >= 1 && numero <= lista.size()) {
                return lista.get(numero - 1);
            }
        } catch (NumberFormatException e) {
            // no era un numero
        }
        System.out.println("Numero no valido.");
        return null;
    }

    private void nuevaReserva(Scanner teclado) {
        System.out.println(LINEA);
        System.out.println("-------NUEVA RESERVA-------\n");
        System.out.print("Telefono de la clienta: ");
        Cliente cliente = clientes.buscarCliente(teclado.nextLine().trim());
        if (cliente == null) {
            System.out.println("El cliente no existe. Registralo primero.");
            return;
        }

        catalogo.mostrarLista(catalogo.getServicios());
        System.out.print("Numero del servicio: ");
        Sistemas servicio = catalogo.elegirServicio(teclado.nextLine());
        if (servicio == null) {
            System.out.println("Numero no valido.");
            return;
        }

        Manicurista manicurista = elegirManicurista(teclado);
        if (manicurista == null) {
            return;
        }

        String fecha;
        while (true) {
            System.out.print("Fecha (dd/MM/aaaa, ej: 25/12/2026): ");
            fecha = teclado.nextLine().trim();
            if (reservas.fechaValida(fecha)) {
                break;
            }
            System.out.println("Fecha invalida o ya paso.");
        }

        String hora;
        while (true) {
            System.out.print("Hora (HH:mm, ej: 15:30): ");
            hora = teclado.nextLine().trim();
            if (!reservas.horaValida(hora)) {
                System.out.println("Hora invalida.");
            } else if (!reservas.fechaHoraValida(fecha, hora)) {
                System.out.println("Esa hora ya paso.");
            } else {
                break;
            }
        }

        Reserva reserva = reservas.crearReserva(new Cita(cliente, servicio, manicurista, fecha, hora));
        if (reserva == null) {
            System.out.println("No se pudo crear: esa manicurista ya tiene una reserva a esa fecha y hora.");
            return;
        }

        System.out.println("Reserva confirmada. Adelanto cobrado: S/ " + reserva.getAdelanto());
        reserva.mostrarComprobante();
        System.out.println(LINEA);
    }

    private void cancelarReserva(Scanner teclado) {
        System.out.println(LINEA);
        System.out.println("-------CANCELAR RESERVA-------\n");
        Reserva r = pedirReserva(teclado);
        if (r != null) {
            if (r.cancelar()) {
                System.out.println("Reserva cancelada.");
            } else {
                System.out.println("No se pudo cancelar: ya estaba cancelada o atendida.");
            }
        }
        System.out.println(LINEA);
    }

    // Durante la atencion se agregan los efectos y se completa el total
    private void atenderReserva(Scanner teclado) {
        System.out.println(LINEA);
        System.out.println("-------ATENDER RESERVA-------\n");
        Reserva r = pedirReserva(teclado);
        if (r == null) {
            return;
        }
        if (r.getEstado().equals(Reserva.CANCELADA) || r.getEstado().equals(Reserva.ATENDIDA)) {
            System.out.println("La reserva esta cancelada o ya fue atendida.");
            return;
        }

        System.out.println("Clienta: " + r.getCita().getCliente().getNombreCompleto()
                + " | Servicio: " + r.getCita().getPrincipal().getNombre()
                + " | Manicurista: " + r.getCita().getManicurista().getNombreCompleto());

        agregarEfectos(teclado, r.getCita());
        r.marcarAtendida();

        System.out.println("\n--- RESUMEN FINAL ---");
        r.mostrarComprobante();
        System.out.println("Saldo a cobrar: S/ " + r.calcularSaldo());
        System.out.println(LINEA);
    }

    // Muestra la lista y repite hasta escribir 0
    private void agregarEfectos(Scanner teclado, Cita cita) {
        catalogo.mostrarLista(catalogo.getEfectos());
        while (true) {
            System.out.print("Numero del efecto (0 para terminar): ");
            String texto = teclado.nextLine().trim();
            if (texto.equals("0")) {
                break;
            }
            Sistemas efecto = catalogo.elegirEfecto(texto);
            if (efecto == null) {
                System.out.println("Numero no valido.");
            } else {
                cita.agregarExtra(efecto);
                System.out.println("Agregado: " + efecto.getNombre());
            }
        }
    }

    private void verComprobante(Scanner teclado) {
        System.out.println(LINEA);
        System.out.println("-------COMPROBANTE-------\n");
        Reserva r = pedirReserva(teclado);
        if (r != null) {
            r.mostrarComprobante();
        }
        System.out.println(LINEA);
    }
}
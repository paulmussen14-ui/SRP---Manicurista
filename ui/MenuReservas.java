package ui;

import utils.Consola;

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
    private MenuVentas menuVentas;

    public MenuReservas(ReservaServicio reservas, ClienteServicio clientes, Catalogo catalogo,
            UsuarioServicio usuarios, MenuVentas menuVentas) {
        this.reservas = reservas;
        this.clientes = clientes;
        this.catalogo = catalogo;
        this.usuarios = usuarios;
        this.menuVentas = menuVentas;
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
        }
        System.out.println("Numero no valido.");
        return null;
    }

    private Cliente elegirCliente(Scanner teclado) {
        System.out.print("Telefono o nombre de la clienta: ");
        String texto = teclado.nextLine().trim();
        ArrayList<Cliente> encontrados = clientes.buscarClientes(texto);
        if (encontrados.isEmpty()) {
            System.out.println("No se encontro ninguna clienta con \"" + texto + "\".");
            if (Consola.confirmar(teclado, "Registrarla ahora?")) {
                return registrarClienta(teclado);
            }
            return null;
        }
        if (encontrados.size() == 1) {
            Cliente unica = encontrados.get(0);
            System.out.println("Clienta: " + unica.getNombreCompleto() + " - " + unica.getTelefono());
            return unica;
        }

        System.out.println("Se encontraron varias clientas:");
        for (int i = 0; i < encontrados.size(); i++) {
            Cliente c = encontrados.get(i);
            System.out.println("  [" + (i + 1) + "] " + c.getNombreCompleto() + " - " + c.getTelefono());
        }
        System.out.print("Numero de la clienta: ");
        try {
            int numero = Integer.parseInt(teclado.nextLine().trim());
            if (numero >= 1 && numero <= encontrados.size()) {
                return encontrados.get(numero - 1);
            }
        } catch (NumberFormatException e) {
            // no era un numero
        }
        System.out.println("Numero no valido.");
        return null;
    }

    // Registro rapido de una clienta nueva sin salir de la reserva
    private Cliente registrarClienta(Scanner teclado) {
        System.out.println("--- REGISTRO RAPIDO DE CLIENTA ---");
        System.out.print("Nombre: ");
        String nombre = teclado.nextLine().trim();
        System.out.print("Apellido: ");
        String apellido = teclado.nextLine().trim();
        System.out.print("Telefono: ");
        String telefono = teclado.nextLine().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty()) {
            System.out.println("Nombre, apellido y telefono son obligatorios.");
            return null;
        }

        Cliente nueva = new Cliente(nombre, apellido, telefono, "Regular");
        if (!clientes.agregarCliente(nueva)) {
            System.out.println("Ya existe una clienta con ese telefono.");
            return null;
        }
        System.out.println("Clienta registrada: " + nueva.getNombreCompleto());
        return nueva;
    }

    private void nuevaReserva(Scanner teclado) {
        System.out.println(LINEA);
        System.out.println("-------NUEVA RESERVA-------\n");
        Cliente cliente = elegirCliente(teclado);
        if (cliente == null) {
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

        if (!reservas.manicuristaLibre(manicurista, fecha, hora)) {
            System.out.println("Esa manicurista ya tiene una reserva a esa fecha y hora. Intenta con otra hora.");
            return;
        }

        Cita cita = new Cita(cliente, servicio, manicurista, fecha, hora);
        System.out.println("Adelanto a cobrar (50%): " + Consola.dinero(new Reserva(cita).getAdelantoMinimo()));
        String metodo = Consola.pedirMetodoPago(teclado);
        while (metodo == null) {
            System.out.println("Opcion no valida. Elige un numero de la lista.");
            metodo = Consola.pedirMetodoPago(teclado);
        }

        Reserva reserva = reservas.crearReserva(cita);
        if (reserva == null) {
            System.out.println("No se pudo crear la reserva.");
            return;
        }
        reserva.setMetodoAdelanto(metodo);

        System.out.println("Reserva confirmada. Adelanto cobrado: " + Consola.dinero(reserva.getAdelanto()));
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
        agregarOtros(teclado, r.getCita());
        r.marcarAtendida();

        System.out.println("\n--- RESUMEN FINAL ---");
        r.mostrarComprobante();
        System.out.println("Saldo a cobrar: " + Consola.dinero(r.calcularSaldo()));
        System.out.println(LINEA);

        if (Consola.confirmar(teclado, "Cobrar ahora")) {
            menuVentas.cobrar(teclado, r);
        } else {
            System.out.println("Queda pendiente de cobro en el menu Ventas.");
        }
    }

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

    private void agregarOtros(Scanner teclado, Cita cita) {
        System.out.println("\n--- OTROS ---");
        catalogo.mostrarLista(catalogo.getOtros());
        while (true) {
            System.out.print("Numero de otro (0 para terminar): ");
            String texto = teclado.nextLine().trim();
            if (texto.equals("0")) {
                break;
            }
            Sistemas otro = catalogo.elegirOtro(texto);
            if (otro == null) {
                System.out.println("Numero no valido.");
            } else {
                cita.agregarExtra(otro);
                System.out.println("Agregado: " + otro.getNombre());
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
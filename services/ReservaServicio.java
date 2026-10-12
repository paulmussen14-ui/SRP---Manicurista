package services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

import models.Cita;
import models.Reserva;

public class ReservaServicio {
    private static final DateTimeFormatter FMT_FECHA = DateTimeFormatter.ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter FMT_HORA = DateTimeFormatter.ofPattern("HH:mm");

    private ArrayList<Reserva> reservas = new ArrayList<>();
    private int siguienteCodigo = 1;

    // Crea la reserva y cobra la mitad como adelanto. Devuelve null si no se puede.
    public Reserva crearReserva(Cita cita) {
        if (cita == null || cita.getManicurista() == null) {
            return null;
        }
        if (!fechaHoraValida(cita.getFecha(), cita.getHora())) {
            return null;
        }

        String usuario = cita.getManicurista().getNombreUsuario();
        for (Reserva r : reservas) {
            Cita otra = r.getCita();
            if (r.estaActiva()
                    && otra.getFecha().equals(cita.getFecha())
                    && otra.getHora().equals(cita.getHora())
                    && otra.getManicurista().getNombreUsuario().equals(usuario)) {
                return null;
            }
        }

        Reserva nueva = new Reserva(siguienteCodigo, cita);
        nueva.registrarAdelanto(nueva.getAdelantoMinimo());
        reservas.add(nueva);
        siguienteCodigo++;
        return nueva;
    }

    // Formato dd/MM/aaaa y que no sea un dia pasado
    public boolean fechaValida(String fecha) {
        try {
            return !LocalDate.parse(fecha, FMT_FECHA).isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // Solo el formato HH:mm
    public boolean horaValida(String hora) {
        try {
            LocalTime.parse(hora, FMT_HORA);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // Fecha y hora juntas, y que no sean pasadas
    public boolean fechaHoraValida(String fecha, String hora) {
        try {
            LocalDate d = LocalDate.parse(fecha, FMT_FECHA);
            LocalTime t = LocalTime.parse(hora, FMT_HORA);
            return !LocalDateTime.of(d, t).isBefore(LocalDateTime.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public Reserva buscarReserva(int codigo) {
        for (Reserva r : reservas) {
            if (r.getCodigo() == codigo) {
                return r;
            }
        }
        return null;
    }

    public boolean cancelarReserva(int codigo) {
        Reserva r = buscarReserva(codigo);
        if (r == null) {
            return false;
        }
        return r.cancelar();
    }

    public ArrayList<Reserva> getReservas() {
        return new ArrayList<>(reservas);
    }

    public void mostrarReservas() {
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas por el momento.");
            return;
        }
        for (Reserva r : reservas) {
            r.mostrarResumen();
        }
    }
}
package services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import models.Cita;
import models.Cliente;
import models.ClienteVIP;
import models.Manicurista;
import models.Reserva;

public class DatosDemo {

    private DatosDemo() {
    }

    public static void cargar(UsuarioServicio usuarios, ClienteServicio clientes,
            ReservaServicio reservas, Catalogo catalogo) {
        Manicurista lucia = new Manicurista("Lucia", "Paredes", "987654321", "lucia", "1234", true, "Acrilicas");
        Manicurista valeria = new Manicurista("Valeria", "Rojas", "976543210", "valeria", "1234", true,
                "Pedicure y Spa");
        usuarios.agregarUsuario(lucia);
        usuarios.agregarUsuario(valeria);

        Cliente ana = new Cliente("Ana", "Torres", "912345678", "Regular");
        clientes.agregarCliente(ana);
        clientes.agregarCliente(new Cliente("Carla", "Mendoza", "934567890", "Regular"));
        clientes.agregarCliente(new ClienteVIP("Sofia", "Vargas", "955123456", "Retoque gratis", 10));

        String manana = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Cita cita = new Cita(ana, catalogo.getServicios().get(7), lucia, manana, "10:00");
        Reserva reserva = reservas.crearReserva(cita);
        if (reserva != null) {
            reserva.setMetodoAdelanto("Yape");
        }
    }
}
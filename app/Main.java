package app;

import java.util.Scanner;

import services.AuthServicio;
import services.ClienteServicio;
import services.UsuarioServicio;
import services.ReservaServicio;
import services.Catalogo;
import services.VentaServicio;

import ui.MenuVentas;
import ui.MenuLogin;
import ui.MenuPrincipal;
import ui.MenuClientes;
import ui.MenuReservas;
import ui.MenuReportes;
import ui.MenuCatalogo;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        UsuarioServicio usuarios = new UsuarioServicio();
        ReservaServicio reservas = new ReservaServicio();
        Catalogo catalogo = new Catalogo();
        AuthServicio auth = new AuthServicio(usuarios, null);
        ClienteServicio clientes = new ClienteServicio();

        MenuLogin menuLogin = new MenuLogin(auth, usuarios);
        MenuClientes menuClientes = new MenuClientes(clientes);
        VentaServicio ventas = new VentaServicio();
        MenuVentas menuVentas = new MenuVentas(ventas, reservas);
        MenuReportes menuReportes = new MenuReportes(ventas, reservas);
        MenuReservas menuReservas = new MenuReservas(reservas, clientes, catalogo, usuarios);
        MenuCatalogo menuCatalogo = new MenuCatalogo(catalogo);
        MenuPrincipal menuPrincipal = new MenuPrincipal(auth, usuarios, menuClientes, menuReservas,
                menuVentas, menuReportes, menuCatalogo);

        while (menuLogin.mostrarInicio(teclado)) {
            menuPrincipal.mostrar(teclado);
        }
        System.out.println("Saliendo del Sistema.");
        teclado.close();
    }
}
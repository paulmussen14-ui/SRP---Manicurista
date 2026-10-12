package ui;

import java.util.Scanner;

import models.Sistemas;
import services.Catalogo;

public class MenuCatalogo {
    private static final String LINEA = "============================================";

    private Catalogo catalogo;

    public MenuCatalogo(Catalogo catalogo) {
        this.catalogo = catalogo;
    }

    public void mostrar(Scanner teclado) {
        String opcion = "";
        while (!opcion.equals("0")) {
            System.out.println();
            System.out.println("+========================================+");
            System.out.println("|            GESTION DEL CATALOGO        |");
            System.out.println("+========================================+");
            System.out.println("|   [1]  Administrar servicios           |");
            System.out.println("|   [2]  Administrar efectos             |");
            System.out.println("|   [3]  Administrar otros               |");
            System.out.println("|   [0]  Volver                          |");
            System.out.println("+========================================+");
            System.out.print("Elige una opcion: ");
            opcion = teclado.nextLine().trim();

            switch (opcion) {
                case "1":
                    administrar(teclado, Catalogo.SERVICIO);
                    break;
                case "2":
                    administrar(teclado, Catalogo.EFECTO);
                    break;
                case "3":
                    administrar(teclado, Catalogo.OTRO);
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private void administrar(Scanner teclado, String tipo) {
        String opcion = "";
        while (!opcion.equals("0")) {
            System.out.println();
            System.out.println("+========================================+");
            if (tipo.equals(Catalogo.EFECTO)) {
                System.out.println("|          EFECTOS DEL CATALOGO          |");
            } else if (tipo.equals(Catalogo.OTRO)) {
                System.out.println("|           OTROS DEL CATALOGO           |");
            } else {
                System.out.println("|         SERVICIOS DEL CATALOGO         |");
            }
            System.out.println("+========================================+");
            System.out.println("|   [1]  Ver lista                       |");
            System.out.println("|   [2]  Agregar                         |");
            System.out.println("|   [3]  Editar                          |");
            System.out.println("|   [4]  Eliminar                        |");
            System.out.println("|   [0]  Volver                          |");
            System.out.println("+========================================+");
            System.out.print("Elige una opcion: ");
            opcion = teclado.nextLine().trim();

            switch (opcion) {
                case "1":
                    System.out.println(LINEA);
                    verLista(tipo);
                    System.out.println(LINEA);
                    break;
                case "2":
                    agregar(teclado, tipo);
                    break;
                case "3":
                    editar(teclado, tipo);
                    break;
                case "4":
                    eliminar(teclado, tipo);
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private void verLista(String tipo) {
        if (tipo.equals(Catalogo.EFECTO)) {
            catalogo.mostrarLista(catalogo.getEfectos());
        } else if (tipo.equals(Catalogo.OTRO)) {
            catalogo.mostrarLista(catalogo.getOtros());
        } else {
            catalogo.mostrarLista(catalogo.getServicios());
        }
    }

    private Sistemas elegir(String tipo, String texto) {
        if (tipo.equals(Catalogo.EFECTO)) {
            return catalogo.elegirEfecto(texto);
        }
        if (tipo.equals(Catalogo.OTRO)) {
            return catalogo.elegirOtro(texto);
        }
        return catalogo.elegirServicio(texto);
    }

    private void agregar(Scanner teclado, String tipo) {
        System.out.println(LINEA);
        System.out.println("-------AGREGAR-------\n");
        System.out.print("Nombre: ");
        String nombre = teclado.nextLine().trim();
        System.out.print("Precio: ");
        double precio;
        try {
            precio = Double.parseDouble(teclado.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("El precio debe ser un numero.");
            return;
        }

        boolean ok;
        if (tipo.equals(Catalogo.EFECTO)) {
            ok = catalogo.agregarEfecto(nombre, precio);
        } else if (tipo.equals(Catalogo.OTRO)) {
            ok = catalogo.agregarOtro(nombre, precio);
        } else {
            ok = catalogo.agregarServicio(nombre, precio);
        }

        if (ok) {
            System.out.println("Agregado correctamente.");
        } else {
            System.out.println("No se pudo agregar: nombre vacio, repetido o precio no valido.");
        }
        System.out.println(LINEA);
    }

    private void editar(Scanner teclado, String tipo) {
        System.out.println(LINEA);
        System.out.println("-------EDITAR-------\n");
        verLista(tipo);
        System.out.print("Numero a editar: ");
        String texto = teclado.nextLine().trim();
        Sistemas actual = elegir(tipo, texto);
        if (actual == null) {
            System.out.println("Numero no valido.");
            return;
        }

        System.out.println("Deja vacio lo que no quieras cambiar.");
        System.out.print("Nuevo nombre (" + actual.getNombre() + "): ");
        String nombre = teclado.nextLine().trim();
        if (nombre.isEmpty()) {
            nombre = actual.getNombre();
        }

        System.out.print("Nuevo precio (" + actual.getPrecio() + "): ");
        String textoPrecio = teclado.nextLine().trim();
        double precio = actual.getPrecio();
        if (!textoPrecio.isEmpty()) {
            try {
                precio = Double.parseDouble(textoPrecio);
            } catch (NumberFormatException e) {
                System.out.println("El precio debe ser un numero.");
                return;
            }
        }

        int numero = Integer.parseInt(texto);
        boolean ok;
        if (tipo.equals(Catalogo.EFECTO)) {
            ok = catalogo.editarEfecto(numero, nombre, precio);
        } else if (tipo.equals(Catalogo.OTRO)) {
            ok = catalogo.editarOtro(numero, nombre, precio);
        } else {
            ok = catalogo.editarServicio(numero, nombre, precio);
        }

        if (ok) {
            System.out.println("Editado correctamente.");
        } else {
            System.out.println("No se pudo editar: nombre repetido o precio no valido.");
        }
        System.out.println(LINEA);
    }

    private void eliminar(Scanner teclado, String tipo) {
        System.out.println(LINEA);
        System.out.println("-------ELIMINAR-------\n");
        verLista(tipo);
        System.out.print("Numero a eliminar: ");
        String texto = teclado.nextLine().trim();
        Sistemas actual = elegir(tipo, texto);
        if (actual == null) {
            System.out.println("Numero no valido.");
            return;
        }

        System.out.print("Seguro que quieres eliminar " + actual.getNombre() + "? (si o no): ");
        if (!teclado.nextLine().trim().equalsIgnoreCase("si")) {
            System.out.println("Operacion cancelada.");
            return;
        }

        int numero = Integer.parseInt(texto);
        if (tipo.equals(Catalogo.EFECTO)) {
            catalogo.eliminarEfecto(numero);
        } else if (tipo.equals(Catalogo.OTRO)) {
            catalogo.eliminarOtro(numero);
        } else {
            catalogo.eliminarServicio(numero);
        }
        System.out.println("Eliminado.");
        System.out.println(LINEA);
    }
}
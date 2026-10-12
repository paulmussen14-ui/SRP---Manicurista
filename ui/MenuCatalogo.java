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
            System.out.println("|   [0]  Volver                          |");
            System.out.println("+========================================+");
            System.out.print("Elige una opcion: ");
            opcion = teclado.nextLine().trim();

            switch (opcion) {
                case "1":
                    administrar(teclado, false);
                    break;
                case "2":
                    administrar(teclado, true);
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private void administrar(Scanner teclado, boolean efectos) {
        String opcion = "";
        while (!opcion.equals("0")) {
            System.out.println();
            System.out.println("+========================================+");
            if (efectos) {
                System.out.println("|          EFECTOS DEL CATALOGO          |");
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
                    verLista(efectos);
                    System.out.println(LINEA);
                    break;
                case "2":
                    agregar(teclado, efectos);
                    break;
                case "3":
                    editar(teclado, efectos);
                    break;
                case "4":
                    eliminar(teclado, efectos);
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private void verLista(boolean efectos) {
        if (efectos) {
            catalogo.mostrarLista(catalogo.getEfectos());
        } else {
            catalogo.mostrarLista(catalogo.getServicios());
        }
    }

    private Sistemas elegir(boolean efectos, String texto) {
        if (efectos) {
            return catalogo.elegirEfecto(texto);
        }
        return catalogo.elegirServicio(texto);
    }

    private void agregar(Scanner teclado, boolean efectos) {
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
        if (efectos) {
            ok = catalogo.agregarEfecto(nombre, precio);
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

    private void editar(Scanner teclado, boolean efectos) {
        System.out.println(LINEA);
        System.out.println("-------EDITAR-------\n");
        verLista(efectos);
        System.out.print("Numero a editar: ");
        String texto = teclado.nextLine().trim();
        Sistemas actual = elegir(efectos, texto);
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
        if (efectos) {
            ok = catalogo.editarEfecto(numero, nombre, precio);
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

    private void eliminar(Scanner teclado, boolean efectos) {
        System.out.println(LINEA);
        System.out.println("-------ELIMINAR-------\n");
        verLista(efectos);
        System.out.print("Numero a eliminar: ");
        String texto = teclado.nextLine().trim();
        Sistemas actual = elegir(efectos, texto);
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
        if (efectos) {
            catalogo.eliminarEfecto(numero);
        } else {
            catalogo.eliminarServicio(numero);
        }
        System.out.println("Eliminado.");
        System.out.println(LINEA);
    }
}
package services;

import utils.Consola;

import java.util.ArrayList;

import models.Efecto;
import models.Otro;
import models.Sistemas;

public class Catalogo {
    private ArrayList<Sistemas> servicios = new ArrayList<>();
    private ArrayList<Sistemas> efectos = new ArrayList<>();
    private ArrayList<Sistemas> otros = new ArrayList<>();

    public Catalogo() {
        String[] nombresServicios = {
                "Acrílicas", "Rubber", "Poly Gel", "Soft Gel", "Builder Gel",
                "Híbridas", "Esculpidas", "Esmaltado en Gel", "Dipping",
                "Baño de Acrílico", "Retiro", "Pedicure en Gel",
                "Pedicure en Acrílico", "Pedicure Tradicional", "Pedicure Spa",
                "Pedicure Rubber", "Pedicure con Tips", "Pestañas 1x1", "Botox"
        };
        double[] preciosServicios = { 60, 75, 90, 70, 80, 80, 90, 40, 60, 60,
                20, 50, 70, 25, 50, 60, 65, 25, 50 };
        for (int i = 0; i < nombresServicios.length; i++) {
            servicios.add(new Sistemas(nombresServicios[i], preciosServicios[i]));
        }

        String[] nombresEfectos = { "Espejo", "Ojo de Gato", "Aurora", "Camaleón", "Stickers",
                "Dijes", "Pedrería", "Flores en 3D", "Sellos",
                "Molde de Silicona", "Nail Art", "Baby Glitter", "Pigmentos",
                "Polvo", "Brillo", "Relieves", "Foil", "Baby Boomer", "Baby Glam" };
        double[] preciosEfectos = { 10, 15, 15, 15, 10, 10, 10, 15, 10, 15,
                20, 15, 10, 10, 10, 15, 15, 15, 15 };
        for (int i = 0; i < nombresEfectos.length; i++) {
            efectos.add(new Efecto(nombresEfectos[i], preciosEfectos[i]));
        }

        otros.add(new Otro("Reconstrucción de manos", 5));
        otros.add(new Otro("Reconstrucción de pies", 10));
    }

    public ArrayList<Sistemas> getServicios() {
        return servicios;
    }

    public ArrayList<Sistemas> getEfectos() {
        return efectos;
    }

    public ArrayList<Sistemas> getOtros() {
        return otros;
    }

    private Sistemas elegir(ArrayList<Sistemas> lista, String texto) {
        try {
            int numero = Integer.parseInt(texto.trim());
            if (numero >= 1 && numero <= lista.size()) {
                return lista.get(numero - 1);
            }
        } catch (NumberFormatException e) {
        }
        return null;
    }

    public Sistemas elegirServicio(String texto) {
        return elegir(servicios, texto);
    }

    public Sistemas elegirEfecto(String texto) {
        return elegir(efectos, texto);
    }

    public Sistemas elegirOtro(String texto) {
        return elegir(otros, texto);
    }

    public void mostrarLista(ArrayList<Sistemas> lista) {
        if (lista.isEmpty()) {
            System.out.println("  (no hay elementos)");
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + lista.get(i).getNombre() + " - " + Consola.dinero(lista.get(i).getPrecio()));
        }
    }

    public static final String SERVICIO = "servicio";
    public static final String EFECTO = "efecto";
    public static final String OTRO = "otro";

    private Sistemas crear(String tipo, String nombre, double precio) {
        if (tipo.equals(EFECTO)) {
            return new Efecto(nombre, precio);
        }
        if (tipo.equals(OTRO)) {
            return new Otro(nombre, precio);
        }
        return new Sistemas(nombre, precio);
    }

    private boolean nombreRepetido(ArrayList<Sistemas> lista, String nombre, int ignorar) {
        for (int i = 0; i < lista.size(); i++) {
            if (i != ignorar && lista.get(i).getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    private boolean agregar(ArrayList<Sistemas> lista, String tipo, String nombre, double precio) {
        if (nombre.isEmpty() || precio <= 0 || nombreRepetido(lista, nombre, -1)) {
            return false;
        }
        lista.add(crear(tipo, nombre, precio));
        return true;
    }

    private boolean editar(ArrayList<Sistemas> lista, String tipo, int numero, String nombre, double precio) {
        if (numero < 1 || numero > lista.size() || nombre.isEmpty() || precio <= 0
                || nombreRepetido(lista, nombre, numero - 1)) {
            return false;
        }
        lista.set(numero - 1, crear(tipo, nombre, precio));
        return true;
    }

    private boolean eliminar(ArrayList<Sistemas> lista, int numero) {
        if (numero < 1 || numero > lista.size()) {
            return false;
        }
        lista.remove(numero - 1);
        return true;
    }

    public boolean agregarServicio(String nombre, double precio) {
        return agregar(servicios, SERVICIO, nombre, precio);
    }

    public boolean editarServicio(int numero, String nombre, double precio) {
        return editar(servicios, SERVICIO, numero, nombre, precio);
    }

    public boolean eliminarServicio(int numero) {
        return eliminar(servicios, numero);
    }

    public boolean agregarEfecto(String nombre, double precio) {
        return agregar(efectos, EFECTO, nombre, precio);
    }

    public boolean editarEfecto(int numero, String nombre, double precio) {
        return editar(efectos, EFECTO, numero, nombre, precio);
    }

    public boolean eliminarEfecto(int numero) {
        return eliminar(efectos, numero);
    }

    public boolean agregarOtro(String nombre, double precio) {
        return agregar(otros, OTRO, nombre, precio);
    }

    public boolean editarOtro(int numero, String nombre, double precio) {
        return editar(otros, OTRO, numero, nombre, precio);
    }

    public boolean eliminarOtro(int numero) {
        return eliminar(otros, numero);
    }
}
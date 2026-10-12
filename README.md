# SRP - Manicurista

Sistema de gestión por consola para el salón de uñas **Marie Nails**, desarrollado en **Java** con Programación Orientada a Objetos. Permite administrar clientas, reservar citas con adelanto, atender el servicio, cobrar con boleta y consultar reportes, todo desde menús en la terminal.

Proyecto académico — Jean Paul, Diseño y Desarrollo de Software, Instituto Superior Tecnológico Certus.

## Funcionalidades

- **Usuarios y sesiones**: inicio de sesión, registro de manicuristas, recuperación de clave y dos roles (Administrador y Manicurista).
- **Clientes**: registro de clientas normales y VIP (con descuento), búsqueda, edición y eliminación.
- **Catálogo**: administración de *servicios*, *efectos* y *otros* cargos (agregar, editar y eliminar).
- **Reservas**: nueva reserva buscando a la clienta por teléfono o por nombre, con registro rápido si no existe; cancelar, atender y ver comprobante.
- **Atención**: al atender se agregan efectos y otros cargos a la cita, y se puede cobrar en el mismo momento.
- **Ventas**: cobro del saldo, boleta numerada (`B-0001`), ventas del día y total vendido.
- **Reportes**: reservas por día, ingresos por día, ingresos por método de pago y clientas VIP atendidas.
- **Datos de demostración** opcionales al iniciar, para probar el sistema sin registrar todo a mano.

## Requisitos

- JDK 8 o superior (`java -version` y `javac -version` para comprobarlo).

## Cómo compilar y ejecutar

Desde la carpeta raíz del proyecto.

**Windows (PowerShell):**

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse -Filter *.java).FullName
java -cp out app.Main
```

**Linux / macOS:**

```bash
javac -encoding UTF-8 -d out $(find . -name '*.java')
java -cp out app.Main
```

También se puede abrir la carpeta en un IDE (Eclipse, IntelliJ, VS Code) y ejecutar la clase `app.Main`.

> En la consola de Windows, si las tildes se ven mal, ejecuta antes `chcp 65001`.

## Primeros pasos

Al iniciar, el programa pregunta **"Cargar datos de demostración (si/no)"**. Si respondes `si`, se cargan:

| Tipo | Datos |
|---|---|
| Administrador | usuario `camile`, clave `1234` (siempre existe) |
| Manicuristas | `lucia` y `valeria`, clave `1234` |
| Clientas | Ana Torres, Carla Mendoza y Sofia Vargas (VIP, 10 % de descuento) |
| Reserva | una cita de Ana para mañana a las 10:00 |

Si respondes `no`, solo existe el administrador `camile` y debes registrar manicuristas y clientas desde los menús.

## Recorrido de uso sugerido

1. Iniciar sesión como administrador.
2. **Gestionar reservas → Nueva reserva**: buscar a la clienta por teléfono o nombre, elegir servicio, manicurista, fecha (`dd/MM/aaaa`), hora (`HH:mm`) y método de pago del adelanto.
3. **Atender reserva**: agregar efectos y otros cargos, ver el resumen y elegir "Cobrar ahora".
4. Elegir el método de pago del saldo y se emite la **boleta**.
5. **Reportes**: revisar ingresos por día y por método de pago.

## Menús por rol

**Administrador:** ver, agregar, editar y eliminar usuarios · cambiar clave · gestionar clientes · gestionar reservas · ventas · reportes · gestionar catálogo.

**Manicurista:** ver mis datos · cambiar clave · gestionar clientes · gestionar reservas · ventas · reportes.

## Reglas de negocio

- Al reservar se cobra un **adelanto del 50 %** del total (con descuento VIP si aplica), y se registra con qué método se pagó.
- Estados de una reserva: `Pendiente`, `Confirmada`, `Atendida` y `Cancelada`.
- Una manicurista no puede tener dos reservas activas a la misma fecha y hora.
- La fecha no puede ser pasada y la hora debe tener formato `HH:mm`.
- Solo se cobra una reserva **atendida**, y una sola vez. El cobro es el saldo: total menos adelanto.
- Métodos de pago: Efectivo, Yape, Plin y Tarjeta.
- Las clientas VIP reciben un porcentaje de descuento (entre 0 y 100 %) sobre el subtotal.
- Catálogo inicial: 19 servicios, 19 efectos y 2 otros cargos (reconstrucción de manos y de pies). Los precios están en soles (`S/`).

## Estructura del proyecto

```
SRP---Manicurista/
├── app/
│   └── Main.java              Punto de entrada: crea los servicios y arranca los menús
├── interfaces/
│   ├── Autenticable.java      Contrato para quien inicia sesión
│   ├── Cobrable.java          Contrato para todo lo que tiene precio
│   └── Descontable.java       Contrato para quien recibe descuento
├── models/                    Clases del dominio
│   ├── Persona, Cliente, ClienteVIP
│   ├── Usuario, Administrador, Manicurista
│   ├── Sistemas, Efecto, Otro  (servicio, efecto y otros cargos del catálogo)
│   └── Cita, Reserva, Venta, Boleta
├── services/                  Lógica de negocio y almacenamiento en memoria
│   ├── AuthServicio, UsuarioServicio, ClienteServicio
│   ├── Catalogo, ReservaServicio, VentaServicio
│   └── DatosDemo
├── ui/                        Menús de consola
│   ├── MenuLogin, MenuPrincipal, MenuClientes, MenuCatalogo
│   └── MenuReservas, MenuVentas, MenuReportes
└── utils/
    └── Consola.java           Formato de dinero, confirmaciones y método de pago
```

## Conceptos de POO aplicados

- **Herencia**: `Persona` → `Cliente` → `ClienteVIP`; `Persona` → `Usuario` → `Administrador` / `Manicurista`; `Sistemas` → `Efecto` / `Otro`.
- **Clases abstractas**: `Persona` y `Usuario`.
- **Interfaces**: `Cobrable` (implementada por `Sistemas`, `Cita`, `Reserva` y `Venta`), `Descontable` (`ClienteVIP`) y `Autenticable` (`Usuario`).
- **Polimorfismo**: `calcularPrecio()` se resuelve distinto en cada clase `Cobrable`, y `getTipo()` identifica si un extra es Efecto u Otro.
- **Encapsulamiento**: atributos privados o protegidos con getters y setters que validan datos.
- **Separación de responsabilidades**: la interfaz (`ui`), la lógica (`services`) y los datos (`models`) están en capas distintas, y cada clase tiene una sola responsabilidad.

## Limitaciones y mejoras futuras

- Los datos viven **solo en memoria**: al cerrar el programa se pierden. Una mejora natural es guardarlos en archivos o en una base de datos.
- Las claves se guardan en texto plano; en un sistema real irían con hash.
- La validación de horarios solo evita coincidencias exactas de fecha y hora, sin considerar la duración de cada servicio.

## Autor

Jean Paul — Certus, Lima, Perú.

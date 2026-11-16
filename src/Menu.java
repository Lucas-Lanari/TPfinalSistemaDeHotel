import Clases.*;
import Conexiones.Conexion;
import Enumeradores.EstadoReserva;
import Enumeradores.GuiEstados;
import Enumeradores.RazonesOcupada;
import Enumeradores.RolesCuenta;
import Excepciones.*;
import Gestores.GestorHotel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private GuiEstados estado = GuiEstados.INICIAR_SESION;
    private GestorHotel gestorHotel;
    private Scanner scanner = new Scanner(System.in);

    public Menu(GestorHotel gestorHotel) {
        this.gestorHotel = gestorHotel;
    }

    private MenuOpciones armarMenu() {
        MenuOpciones menu = new MenuOpciones();

        switch (estado) {
            case INICIAR_SESION:
                menu.agregarOpcion("Ingresar", "login");
                menu.agregarOpcion("Salir", "salir", 'X');
                break;

            case MENU_PRINCIPAL:
                menu.agregarOpcion("Cuentas", "irCuentas");
                menu.agregarOpcion("Habitaciones", "irHabitaciones");
                menu.agregarOpcion("Reservas", "irReservas");
                menu.agregarOpcion("Cerrar sesión", "logout", 'X');
                break;

            case GESTION_HABITACIONES:
                menu.agregarOpcion("Ver disponibles", "verDisponibles");
                if (gestorHotel.getCuentaActual().isPermisoManejarHabitaciones()) {
                    menu.agregarOpcion("Ver ocupadas", "verOcupadas");
                    menu.agregarOpcion("Ver no disponibles", "verNoDisponibles");
                    menu.agregarOpcion("Cambiar estado de habitación", "cambiarEstadoHabitacion");
                }
                if (gestorHotel.getCuentaActual().isPermisoCrearHabitaciones()) {
                    menu.agregarOpcion("Crear Habitación", "crearHabitacion");
                    menu.agregarOpcion("Borrar Habitación", "borrarHabitacion");
                }
                menu.agregarOpcion("Volver", "volver", 'X');
                break;

            case GESTION_RESERVAS:
                menu.agregarOpcion("Nueva reserva", "nuevaReserva");

                if (gestorHotel.getCuentaActual().isPermisoManejarReservas()) {
                    menu.agregarOpcion("Ver reservas", "verReservas");
                    menu.agregarOpcion("Modificar reserva", "modificarReserva");
                    menu.agregarOpcion("Cancelar reserva", "cancelarReserva");
                } else {
                    menu.agregarOpcion("Ver reservas", "verReservas");
                    menu.agregarOpcion("Modificar mis reservas", "modificarReserva");
                    menu.agregarOpcion("Cancelar mi reserva", "cancelarReserva");
                }
                menu.agregarOpcion("Volver", "volver", 'X');
                break;

            case GESTION_CUENTAS:
                if (gestorHotel.getCuentaActual().isPermisoManejarCuentas()) {
                    menu.agregarOpcion("Ver cuentas", "verCuentas");
                } else {
                    menu.agregarOpcion("Ver cuenta", "verCuentas");
                }

                if (gestorHotel.getCuentaActual().isPermisoCrearCuentas()) {
                    menu.agregarOpcion("Crear cuenta", "crearCuenta");
                    menu.agregarOpcion("Eliminar cuenta", "eliminarCuenta");
                    menu.agregarOpcion("Restaurar cuenta", "restaurarCuenta");
                }

                menu.agregarOpcion("Volver", "volver", 'X');
                break;

            default:
                break;
        }

        return menu;
    }

    private GuiEstados ejecutarOpcion(String opcion) {
        switch (opcion) {
            case "login":

                System.out.print("Usuario: ");
                String usuario = scanner.nextLine().trim();
                System.out.print("Contraseña: ");
                String contrasenia = scanner.nextLine();

                try {
                    gestorHotel.login(usuario, contrasenia);
                } catch (ErrorLoginException e) {
                    System.out.println(e.getMessage());
                    return estado;
                }

                return GuiEstados.MENU_PRINCIPAL;

            case "volver":
                return GuiEstados.MENU_PRINCIPAL;

            case "irReservas":
                return GuiEstados.GESTION_RESERVAS;

            case "irHabitaciones":
                return GuiEstados.GESTION_HABITACIONES;

            case "irCuentas":
                return GuiEstados.GESTION_CUENTAS;

            case "verDisponibles":
                mostrarTexto(gestorHotel.listarHabitacionesDisponibles(), "No hay habitaciones disponibles.");
                return estado;

            case "verOcupadas":
                mostrarTexto(gestorHotel.listarHabitacionesPorOcupacion(RazonesOcupada.OCUPADA), "No hay habitaciones ocupadas.");
                return estado;

            case "verNoDisponibles":
                mostrarTexto(gestorHotel.listarHabitacionesPorOcupacion(
                                RazonesOcupada.LIMPIANDO,
                                RazonesOcupada.REPARANDO,
                                RazonesOcupada.DESINFECTANDO,
                                RazonesOcupada.SIN),
                        "No hay habitaciones no disponibles.");
                return estado;

            case "crearHabitacion":
                crearHabitacion();
                return estado;

            case "borrarHabitacion":
                borrarHabitacion();
                return estado;

            case "cambiarEstadoHabitacion":
                cambiarEstadoHabitacion();
                return estado;

            case "verTodas":
                mostrarTexto(gestorHotel.listarHabitaciones(), "No hay habitaciones cargadas.");
                return estado;

            case "verCuentas":
                verCuenta();
                return estado;

            case "crearCuenta":
                crearCuenta();
                return estado;

            case "eliminarCuenta":
                cambiarEstadoCuenta(true);
                return estado;

            case "restaurarCuenta":
                cambiarEstadoCuenta(false);
                return estado;

            case "verReservas":
                mostrarTexto(gestorHotel.listarReservas(), "No hay reservas para mostrar.");
                return estado;

            case "nuevaReserva":
                nuevaReserva();
                return estado;

            case "modificarReserva":
                modificarReserva();
                return estado;

            case "cancelarReserva":
                cancelarReserva();
                return estado;

            case "logout":
                gestorHotel.logout();
                return GuiEstados.INICIAR_SESION;

            case "salir":
                return GuiEstados.TERMINADO;

            default:
                return estado;
        }
    }

    public void ejecutar() {
        while (estado != GuiEstados.TERMINADO) {
            System.out.println(estado.getNombreBonito());
            MenuOpciones menu = armarMenu();

            System.out.println(menu.mostrarOpciones());
            System.out.print("? ");

            String linea = scanner.nextLine().trim();
            char tecla = linea.isEmpty() ? '-' : linea.charAt(0);

            String opcion = menu.encontrarOpcion(tecla);

            if (opcion == null) {
                System.out.println("Opción inválida");
            } else {
                estado = ejecutarOpcion(opcion);
            }
        }
    }

    //funciones auxiliares
    private void mostrarTexto(String texto, String mensajeVacio) {
        if (texto == null || texto.isEmpty())
            System.out.println(mensajeVacio);
        else
            System.out.println(texto);
    }

    private void cambiarEstadoCuenta(boolean eliminar) {
        System.out.print("Usuario de la cuenta: ");
        Cuentas cuenta = gestorHotel.buscarCuentaPorUsuario(scanner.nextLine().trim());

        if (cuenta == null) {
            System.out.println("No existe esa cuenta.");
        } else if (eliminar && cuenta.getId().equals(gestorHotel.getCuentaActual().getId())) {
            System.out.println("No podés eliminar tu propia cuenta.");
        } else if (Conexion.eliminarCuenta(cuenta, eliminar)) {   // actualiza base y objeto
            System.out.println(eliminar ? "Cuenta eliminada." : "Cuenta restaurada.");
        } else {
            System.out.println("No se pudo actualizar la cuenta.");
        }
    }

    private void crearCuenta() {
        try {
            System.out.println("Seleccione el tipo de cuenta:");
            System.out.println("1. Administrador");
            System.out.println("2. Staff");
            System.out.println("3. Cliente");

            int opcionCrear = Integer.parseInt(scanner.nextLine().trim());

            if (opcionCrear < 1 || opcionCrear > 3) {
                throw new IllegalArgumentException("Tipo de cuenta inválido.");
            }

            System.out.println("Ingrese DNI:");
            String dni = scanner.nextLine().trim();

            if (!dni.matches("\\d{1,9}")) {
                throw new IllegalArgumentException("El DNI debe contener entre 1 y 9 números.");
            }

            System.out.println("Ingrese nombre:");
            String nombre = scanner.nextLine();

            if (nombre.isBlank()) {
                throw new IllegalArgumentException("El nombre no puede estar vacío.");
            }

            System.out.println("Ingrese nombre de usuario:");
            String usuarioNuevo = scanner.nextLine().trim();

            if (usuarioNuevo.isBlank()) {
                throw new IllegalArgumentException("El usuario no puede estar vacío.");
            }

            System.out.println("Ingrese contraseña:");
            String contraseniaNuevo = scanner.nextLine();

            if (contraseniaNuevo.isBlank()) {
                throw new IllegalArgumentException("La contraseña no puede estar vacía.");
            }

            Cuentas nueva = switch (opcionCrear) {
                case 1 -> new Admins(null, dni, nombre, usuarioNuevo, contraseniaNuevo, false);
                case 2 -> new Staff(null, dni, nombre, usuarioNuevo, contraseniaNuevo, false);
                case 3 -> {
                    System.out.println("Ingrese país de origen:");
                    String pais = scanner.nextLine();

                    if (pais.isBlank()) {
                        throw new IllegalArgumentException("El país no puede estar vacío.");
                    }

                    System.out.println("Ingrese domicilio de origen:");
                    String domicilio = scanner.nextLine();

                    if (domicilio.isBlank()) {
                        throw new IllegalArgumentException("El domicilio no puede estar vacío.");
                    }

                    yield new Clientes(null, dni, nombre, usuarioNuevo,
                            contraseniaNuevo, false, pais, domicilio);
                }
                default -> throw new IllegalArgumentException("Opción inválida.");
            };

            if (gestorHotel.crearCuenta(nueva)) {
                System.out.println("Cuenta creada correctamente.");
            } else {
                System.out.println("Hubo un error al crear la cuenta.");
            }

        } catch (DniExistenteException | UsuarioExistenteException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un número válido.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error en los datos ingresados: " + e.getMessage());
        }
    }

    private void nuevaReserva() {
        try {
            System.out.println("Habitaciones: " + gestorHotel.listarIdsHabitaciones());
            System.out.print("Elegí el id de la habitación: ");
            int idHabitacion = Integer.parseInt(scanner.nextLine().trim());

            if (gestorHotel.buscarPorClave(idHabitacion) == null) {
                System.out.println("No existe esa habitación.");
                return;
            }

            mostrarFechasLibres(null, idHabitacion);

            LocalDateTime inicio = pedirFecha("Fecha de ingreso (dd/MM/yyyy): ").atTime(14, 0);
            LocalDateTime fin = pedirFecha("Fecha de salida (dd/MM/yyyy): ").atTime(10, 0);

            if (inicio.isBefore(LocalDate.now().atStartOfDay())) {
                System.out.println("La fecha de ingreso no puede estar en el pasado.");
                return;
            }
            if (!inicio.isBefore(fin)) {
                System.out.println("La salida debe ser posterior al ingreso.");
                return;
            }
            if (!gestorHotel.fechaDisponible(null, idHabitacion, inicio, fin)) {
                System.out.println("La habitación no está disponible en todo ese período.");
                return;
            }

            ArrayList<String> dnis = new ArrayList<>();
            String dni;

            boolean esCliente = gestorHotel.getCuentaActual().getRol()
                    .equals(RolesCuenta.CLIENTE.name());

            if (esCliente) {
                dnis.add(gestorHotel.getCuentaActual().getDni());
            } else {
                while (true) {
                    System.out.print("DNI del pasajero: ");
                    dni = scanner.nextLine().trim();

                    if (dni.isEmpty()) {
                        System.out.println("El DNI no puede estar vacío.");
                    } else if (!dni.matches("\\d{1,9}")) {
                        System.out.println("El DNI debe contener entre 1 y 9 números.");
                    } else {
                        dnis.add(dni);
                        break;
                    }
                }
            }

            String seguir;
            System.out.print("¿Querés agregar otro pasajero? (S/N): ");
            seguir = scanner.nextLine().trim();

            while (seguir.equalsIgnoreCase("S")) {
                while (true) {
                    System.out.print("DNI del pasajero: ");
                    dni = scanner.nextLine().trim();

                    if (dni.isEmpty()) {
                        System.out.println("El DNI no puede estar vacío.");
                    } else if (!dni.matches("\\d{1,9}")) {
                        System.out.println("El DNI debe contener entre 1 y 9 números.");
                    } else if (dnis.contains(dni)) {
                        System.out.println("Ese DNI ya fue agregado.");
                    } else {
                        break;
                    }
                }

                dnis.add(dni);

                System.out.print("¿Agregar otro pasajero? (S/N): ");
                seguir = scanner.nextLine().trim();
            }

            Reservas nueva = new Reservas(null, idHabitacion, gestorHotel.getCuentaActual().getId(),
                    inicio, fin, EstadoReserva.ACTIVA);

            if (gestorHotel.agregarReserva(nueva)) {
                for (String d : dnis) {
                    gestorHotel.agregarDniReserva(nueva.getId(),d);
                }

                System.out.println("Reserva creada correctamente.");
            } else {
                System.out.println("No se pudo crear la reserva.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un número válido.");
        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Usá dd/MM/yyyy.");
        }
    }

    private LocalDate pedirFecha(String mensaje) {
        System.out.print(mensaje);
        return LocalDate.parse(scanner.nextLine().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private Reservas pedirReserva() throws IdReservaInexistenteException {
        if (gestorHotel.getCuentaActual().isPermisoManejarReservas()) {
            mostrarTexto(gestorHotel.listarReservas(), "No hay reservas para mostrar.");
        } else {
            mostrarTexto(gestorHotel.listarReservasPorPasajero(gestorHotel.getCuentaActual().getDni()), "No se encontraron reservas a su nombre.");
        }

        System.out.print("Id de la reserva: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        Reservas reserva = gestorHotel.buscarReserva(id);
        Cuentas actual = gestorHotel.getCuentaActual();

        boolean esSuya = Integer.valueOf(reserva.getIdCuentaResponsable()).equals(actual.getId());

        if (!actual.isPermisoManejarReservas() && !esSuya) {
            System.out.println("Solo podés gestionar tus propias reservas.");
            return null;
        }

        return reserva;
    }




    private void modificarReserva() {
        try {
            Reservas reserva = pedirReserva();
            if (reserva == null) {
                return;
            }

            if (reserva.getEstado() == EstadoReserva.TERMINADA) {
                System.out.println("Una reserva terminada no se puede modificar.");
                return;
            }

            MenuOpciones submenu = new MenuOpciones();
            submenu.agregarOpcion("Cambiar fecha de ingreso", "ingreso");
            submenu.agregarOpcion("Cambiar fecha de salida", "salida");
            submenu.agregarOpcion("Cambiar ambas fechas", "ambas");
            submenu.agregarOpcion("Cambiar habitación", "habitacion");
            submenu.agregarOpcion("Modificar integrantes", "integrantes");
            submenu.agregarOpcion("Volver", "volver", 'X');

            boolean seguir = true;

            while (seguir) {
                System.out.println("\nReserva actual: " + reserva);
                System.out.println(submenu.mostrarOpciones());
                System.out.print("Elija lo que quiere cambiar:  ");

                String linea = scanner.nextLine().trim();
                char tecla = linea.isEmpty() ? '-' : linea.charAt(0);
                String opcion = submenu.encontrarOpcion(tecla);

                if (opcion == null) {
                    System.out.println("Opción inválida");
                    continue;
                }

                try {
                    switch (opcion) {
                        case "ingreso":
                            cambiarFechaIngreso(reserva);
                            break;
                        case "salida":
                            cambiarFechaSalida(reserva);
                            break;
                        case "ambas":
                            cambiarAmbasFechas(reserva);
                            break;
                        case "habitacion":
                            cambiarHabitacion(reserva);
                            break;
                        case "integrantes":
                            modificarIntegrantes(reserva);
                            break;
                        case "volver":
                            seguir = false;
                            break;
                    }
                } catch (java.time.format.DateTimeParseException e) {
                    System.out.println("Formato de fecha inválido. Usá dd/MM/yyyy.");
                } catch (NumberFormatException e) {
                    System.out.println("Debe ingresar un número válido.");
                }
            }

        } catch (IdReservaInexistenteException e) {
            System.out.println("No existe una reserva con ese id.");
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un número válido.");
        }
    }

    private void cambiarFechaIngreso(Reservas reserva) {
        LocalDateTime nuevoInicio = pedirFecha("Nueva fecha de ingreso (dd/MM/yyyy): ").atTime(14, 0);

        if (nuevoInicio.isBefore(LocalDate.now().atStartOfDay())) {
            System.out.println("La fecha de ingreso no puede estar en el pasado.");
            return;
        }

        aplicarCambios(reserva, reserva.getIdHabitacion(), nuevoInicio, reserva.getCheckOut());
    }

    private void cambiarFechaSalida(Reservas reserva) {
        LocalDateTime nuevoFin = pedirFecha("Nueva fecha de salida (dd/MM/yyyy): ").atTime(10, 0);

        aplicarCambios(reserva, reserva.getIdHabitacion(), reserva.getCheckIn(), nuevoFin);
    }

    private void cambiarAmbasFechas(Reservas reserva) {
        LocalDateTime nuevoInicio = pedirFecha("Nueva fecha de ingreso (dd/MM/yyyy): ").atTime(14, 0);
        LocalDateTime nuevoFin = pedirFecha("Nueva fecha de salida (dd/MM/yyyy): ").atTime(10, 0);

        if (nuevoInicio.isBefore(LocalDate.now().atStartOfDay())) {
            System.out.println("La fecha de ingreso no puede estar en el pasado.");
            return;
        }

        aplicarCambios(reserva, reserva.getIdHabitacion(), nuevoInicio, nuevoFin);
    }

    private void cambiarHabitacion(Reservas reserva) {
        ArrayList<Integer> libres = gestorHotel.buscarHabitacionesFechaDisponibles(
                reserva.getId(), reserva.getCheckIn(), reserva.getCheckOut());

        libres.remove(Integer.valueOf(reserva.getIdHabitacion())); // la actual no es una opción de cambio

        if (libres.isEmpty()) {
            System.out.println("No hay otras habitaciones disponibles para esas fechas.");
            return;
        }

        System.out.println("Habitación actual: " + reserva.getIdHabitacion());
        System.out.println("Habitaciones disponibles: " + libres);
        System.out.print("Id de la nueva habitación: ");
        int nueva = Integer.parseInt(scanner.nextLine().trim());

        if (!libres.contains(nueva)) {
            System.out.println("Esa habitación no está disponible para esas fechas.");
            return;
        }

        aplicarCambios(reserva, nueva, reserva.getCheckIn(), reserva.getCheckOut());
    }

    private void aplicarCambios(Reservas reserva, int idHabitacion, LocalDateTime inicio, LocalDateTime fin) {
        // 1. fechas válidas
        if (!inicio.isBefore(fin)) {
            System.out.println("La fecha de salida debe ser posterior a la de ingreso.");
            return;
        }

        // 2. disponibilidad (ignora la propia reserva)
        if (!gestorHotel.fechaDisponible(reserva.getId(), idHabitacion, inicio, fin)) {
            System.out.println("La habitación " + idHabitacion + " no está disponible en ese período.");
            return;
        }

        // 3. guardar
        try {
            gestorHotel.modificarReserva(reserva.getId(), idHabitacion, inicio, fin);
            System.out.println("Reserva modificada correctamente.");
        } catch (IdReservaInexistenteException | FechaNoDisponibleException | FechaInvalidaException e) {
            System.out.println("No se pudo modificar la reserva.");
        }
    }

    private void cancelarReserva() {
        try {
            Reservas reserva = pedirReserva();
            if (reserva == null) return;

            System.out.print("¿Seguro que querés cancelar la reserva " + reserva.getId() + "? (S/N): ");
            if (!scanner.nextLine().trim().equalsIgnoreCase("S")) {
                System.out.println("Operación cancelada.");
                return;
            }

            if (gestorHotel.eliminarReserva(reserva.getId())) {
                System.out.println("Reserva cancelada.");
            } else {
                System.out.println("No se pudo cancelar la reserva.");
            }

        } catch (IdReservaInexistenteException e) {
            System.out.println("No existe una reserva con ese id.");
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un número válido.");
        }
    }

    private void mostrarFechasLibres(Integer idReserva, int idHabitacion) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        ArrayList<LocalDate> libres = gestorHotel.fechasLibres(idReserva, idHabitacion, 30);

        if (libres.isEmpty()) {
            System.out.println("La habitación " + idHabitacion + " no tiene noches libres en los próximos 30 días.");
            return;
        }

        System.out.println("Noches libres de la habitación " + idHabitacion + " (próximos 30 días):");
        StringBuilder linea = new StringBuilder();
        for (int i = 0; i < libres.size(); i++) {
            linea.append(libres.get(i).format(formato)).append("  ");
            if ((i + 1) % 5 == 0) {
                System.out.println(linea.toString().trim());
                linea.setLength(0);
            }
        }
        if (linea.length() > 0) {
            System.out.println(linea.toString().trim());
        }
        System.out.println("(la salida puede ser el día siguiente a la última noche elegida)");
    }

    public void verCuenta(){
        if (gestorHotel.getCuentaActual().isPermisoManejarCuentas()) {
            mostrarTexto(gestorHotel.listarCuentas(), "No hay Cuentas para mostrar.");
        } else {
            System.out.println(gestorHotel.getCuentaActual());        }
    }

    private void crearHabitacion() {
        try {
            System.out.print("Piso: ");
            int piso = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Número de departamento: ");
            int numero = Integer.parseInt(scanner.nextLine().trim());

            if (gestorHotel.existeHabitacion(piso, numero)) {
                System.out.println("Ya existe una habitación en el piso " + piso + " con el número " + numero + ".");
                return;
            }

            Habitaciones nueva = new Habitaciones(null, piso, numero, RazonesOcupada.LIBRE);

            if (gestorHotel.agregarHabitacion(nueva)) {
                System.out.println("Habitación creada correctamente.");
            } else {
                System.out.println("No se pudo crear la habitación.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un número válido.");
        }
    }

    private void borrarHabitacion() {
        try {
            System.out.println("Habitaciones: " + gestorHotel.listarIdsHabitaciones());
            System.out.print("Id de la habitación a borrar: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("¿Seguro que querés borrar la habitación " + id + "? (S/N): ");
            if (!scanner.nextLine().trim().equalsIgnoreCase("S")) {
                System.out.println("Operación cancelada.");
                return;
            }

            if (gestorHotel.eliminarHabitacion(id)) {
                System.out.println("Habitación borrada.");
            } else {
                System.out.println("No se pudo borrar la habitación.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un número válido.");
        } catch (IdHabitacionNoEncontradoException e) {
            System.out.println("No existe esa habitación.");
        }
    }

    private void cambiarEstadoHabitacion() {
        try {
            System.out.println("Habitaciones: " + gestorHotel.listarIdsHabitaciones());
            System.out.print("Id de la habitación: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            if (gestorHotel.buscarPorClave(id) == null) {
                System.out.println("No existe esa habitación.");
                return;
            }

            System.out.println("1. Libre");
            System.out.println("2. Ocupada");
            System.out.println("3. Limpiando");
            System.out.println("4. Reparando");
            System.out.println("5. Desinfectando");
            System.out.println("6. Sin especificar");
            System.out.print("Nuevo estado: ");
            int eleccion = Integer.parseInt(scanner.nextLine().trim());

            RazonesOcupada nuevo;
            switch (eleccion) {
                case 1:
                    nuevo = RazonesOcupada.LIBRE;
                    break;
                case 2:
                    nuevo = RazonesOcupada.OCUPADA;
                    break;
                case 3:
                    nuevo = RazonesOcupada.LIMPIANDO;
                    break;
                case 4:
                    nuevo = RazonesOcupada.REPARANDO;
                    break;
                case 5:
                    nuevo = RazonesOcupada.DESINFECTANDO;
                    break;
                case 6:
                    nuevo = RazonesOcupada.SIN;
                    break;
                default:
                    System.out.println("Opción inválida.");
                    return;
            }

            gestorHotel.modificarHabitacion(id, nuevo);
            System.out.println("Estado actualizado.");

        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un número válido.");
        } catch (IdHabitacionNoEncontradoException e) {
            System.out.println("No existe esa habitación.");
        }
    }


    private void modificarIntegrantes(Reservas reserva) {
        MenuOpciones submenu = new MenuOpciones();
        submenu.agregarOpcion("Agregar pasajero", "agregar");
        submenu.agregarOpcion("Quitar pasajero", "quitar");
        submenu.agregarOpcion("Volver", "volver", 'X');

        boolean seguir = true;

        while (seguir) {
            System.out.println("\nPasajeros actuales: " + reserva.listarDniIntegrantes());
            System.out.println(submenu.mostrarOpciones());
            System.out.print("? ");

            String linea = scanner.nextLine().trim();
            char tecla = linea.isEmpty() ? '-' : linea.charAt(0);
            String opcion = submenu.encontrarOpcion(tecla);

            if (opcion == null) {
                System.out.println("Opción inválida");
                continue;
            }

            switch (opcion) {
                case "agregar":
                    agregarIntegrante(reserva);
                    break;
                case "quitar":
                    quitarIntegrante(reserva);
                    break;
                case "volver":
                    seguir = false;
                    break;
            }
        }
    }

    private String pedirDni() {
        System.out.print("DNI del pasajero: ");
        String dni = scanner.nextLine().trim();

        if (!dni.matches("\\d{1,9}")) {
            System.out.println("El DNI debe contener entre 1 y 9 números.");
            return null;
        }
        return dni;
    }

    private void agregarIntegrante(Reservas reserva) {
        String dni = pedirDni();
        if (dni == null) return;

        if (reserva.listarDniIntegrantes().contains(dni)) {
            System.out.println("Ese DNI ya está en la reserva.");
            return;
        }

        if (gestorHotel.agregarDniReserva(reserva.getId(), dni)) {
            System.out.println("Pasajero agregado.");
        } else {
            System.out.println("No se pudo agregar el pasajero.");
        }
    }

    private void quitarIntegrante(Reservas reserva) {
        if (reserva.listarDniIntegrantes().size() <= 1) {
            System.out.println("La reserva tiene que tener al menos un pasajero.");
            return;
        }

        String dni = pedirDni();
        if (dni == null) return;

        if (!reserva.listarDniIntegrantes().contains(dni)) {
            System.out.println("Ese DNI no está en la reserva.");
            return;
        }

        if (gestorHotel.eliminarDniReserva(reserva.getId(), dni)) {
            System.out.println("Pasajero quitado.");
        } else {
            System.out.println("No se pudo quitar el pasajero.");
        }
    }

}

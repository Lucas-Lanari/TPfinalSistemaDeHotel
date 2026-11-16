import Clases.*;
import Conexiones.Conexion;
import Enumeradores.EstadoReserva;
import Enumeradores.GuiEstados;
import Excepciones.DniExistenteException;
import Excepciones.UsuarioExistenteException;
import Gestores.GestorHotel;
import Excepciones.ErrorLoginException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private GuiEstados estado = GuiEstados.INICIAR_SESION;
    private GestorHotel gestorHotel;
    private Scanner scanner = new Scanner(System.in);

    public Menu(GestorHotel gestorHotel) {
        this.gestorHotel = gestorHotel;
    }

    private MenuOpciones armarMenu(){
        MenuOpciones menu = new MenuOpciones();

        switch (estado) {
            case INICIAR_SESION:
                menu.agregarOpcion("Ingresar", "login");
                menu.agregarOpcion("Salir", "salir", 'X');
                break;

            case MENU_PRINCIPAL:
                if (gestorHotel.getCuentaActual().isPermisoManejarCuentas()) {
                    menu.agregarOpcion("Cuentas", "irCuentas");
                }
                menu.agregarOpcion("Habitaciones", "irHabitaciones");
                menu.agregarOpcion("Reservas", "irReservas");
                menu.agregarOpcion("Cerrar sesión", "logout", 'X');
                break;

            case GESTION_HABITACIONES:
                menu.agregarOpcion("Ver disponibles", "verDisponibles");
                if (gestorHotel.getCuentaActual().isPermisoManejarHabitaciones()) {
                    menu.agregarOpcion("Ver ocupadas", "verOcupadas");
                    menu.agregarOpcion("Ver no disponibles", "verNoDisponibles");
                }
                menu.agregarOpcion("Volver", "volver", 'X');
                break;

            case GESTION_RESERVAS:
                menu.agregarOpcion("Nueva reserva", "nuevaReserva");

                if (gestorHotel.getCuentaActual().isPermisoManejarReservas()){
                    menu.agregarOpcion("Modificar reserva", "modificarReserva");
                    menu.agregarOpcion("Cancelar reserva", "cancelarReserva");
                } else {
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
                    menu.agregarOpcion("Eliminar cuenta", "eliminarMiCuenta");
                }

                if (gestorHotel.getCuentaActual().isPermisoCrearCuentas()){
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
        switch (opcion){
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

            case "verTodas":
                mostrarTexto(gestorHotel.listarHabitaciones(), "No hay habitaciones cargadas.");
                return estado;

            case "verCuentas":
                mostrarTexto(gestorHotel.listarCuentas(), "No hay cuentas cargadas.");
                return estado;

            case "crearCuenta":
                try {
                    System.out.println("Seleccione el tipo de cuenta:");
                    System.out.println("1. Administrador");
                    System.out.println("2. Staff");
                    System.out.println("3. Cliente");

                    int opcionCrear = scanner.nextInt();
                    scanner.nextLine();

                    if (opcionCrear < 1 || opcionCrear > 3) {
                        throw new IllegalArgumentException("Tipo de cuenta inválido.");
                    }

                    System.out.println("Ingrese DNI:");
                    String dni = scanner.nextLine();

                    if (!dni.matches("\\d{1,9}")) {
                        throw new IllegalArgumentException("El DNI debe contener entre 1 y 9 números.");
                    }

                    System.out.println("Ingrese nombre:");
                    String nombre = scanner.nextLine();

                    if (nombre.isBlank()) {
                        throw new IllegalArgumentException("El nombre no puede estar vacío.");
                    }

                    System.out.println("Ingrese nombre de usuario:");
                    String usuarioNuevo = scanner.nextLine();

                    if (usuarioNuevo.isBlank()) {
                        throw new IllegalArgumentException("El usuario no puede estar vacío.");
                    }

                    System.out.println("Ingrese contraseña:");
                    String contraseniaNuevo = scanner.nextLine();

                    if (contraseniaNuevo.isBlank()) {
                        throw new IllegalArgumentException("La contraseña no puede estar vacía.");
                    }

                    Cuentas nueva = switch (opcionCrear) {
                        case 1 -> new Admins(0, dni, nombre, usuarioNuevo, contraseniaNuevo, false);
                        case 2 -> new Staff(0, dni, nombre, usuarioNuevo, contraseniaNuevo, false);
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

                            yield new Clientes(0, dni, nombre, usuarioNuevo,
                                    contraseniaNuevo, false, pais, domicilio);
                        }
                        default -> throw new IllegalArgumentException("Opción inválida.");
                    };

                    gestorHotel.crearCuenta(nueva);
                    System.out.println("Cuenta creada correctamente.");

                } catch (DniExistenteException | UsuarioExistenteException e) {
                    System.out.println(e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error en los datos ingresados: " + e.getMessage());
                } catch (java.util.InputMismatchException e) {
                    scanner.nextLine();
                    System.out.println("Debe ingresar un número válido.");
                }
                return estado;

            case "eliminarCuenta":
            case "restaurarCuenta": {
                boolean eliminar = opcion.equals("eliminarCuenta");

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
                return estado;
            }

            case "verReservas":
                mostrarTexto(gestorHotel.listarReservas(), "No hay reservas para mostrar.");
                return estado;

            case "nuevaReserva":

                System.out.println("E");

                return estado;

            case "modificarReserva":
                gestorHotel.modificarReserva();
                return estado;

            case "cancelarReserva":
                gestorHotel.eliminarReserva();
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

    private void nuevaReserva() {
        try {
            LocalDateTime inicio = pedirFecha("Fecha de ingreso (yyyy-MM-dd): ").atTime(14, 0);
            LocalDateTime fin = pedirFecha("Fecha de salida (yyyy-MM-dd): ").atTime(10, 0);

            if (!inicio.isBefore(fin)) {
                System.out.println("La salida debe ser posterior al ingreso.");
                return;
            }

            ArrayList<Integer> libres = gestorHotel.buscarHabitacionesFechaDisponibles(null, inicio, fin);

            if (libres.isEmpty()) {
                System.out.println("No hay habitaciones disponibles para ese período.");
                return;
            }

            System.out.println("Habitaciones disponibles: ");
            System.out.println(libres);
            System.out.print("Elegí el id de la habitación: ");
            int idHabitacion = Integer.parseInt(scanner.nextLine().trim());

            if (!libres.contains(idHabitacion)) {
                System.out.println("Esa habitación no está en la lista de disponibles.");
                return;
            }

            ArrayList<String> dnis = new ArrayList<>();
            String seguir;
            do {
                String dni;
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

            } while (seguir.equalsIgnoreCase("S"));

            if (dnis.isEmpty()) {
                System.out.println("Tiene que haber al menos un pasajero.");
                return;
            }


            Reservas nueva = new Reservas(null, idHabitacion, gestorHotel.getCuentaActual().getId(), inicio, fin, EstadoReserva.ACTIVA);

            for (String dni : dnis) {
                nueva.dniIntegrantes.add(dni);
            }

            if (gestorHotel.agregarReserva(nueva)) {
                System.out.println("Reserva creada correctamente.");
            } else {
                System.out.println("No se pudo crear la reserva.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un número válido.");
        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Usá yyyy-MM-dd.");
        }
    }

    private LocalDate pedirFecha(String mensaje) {
        System.out.print(mensaje);
        return LocalDate.parse(scanner.nextLine().trim());
    }

}

import Clases.Admins;
import Clases.Clientes;
import Clases.Cuentas;
import Clases.Staff;
import Enumeradores.GuiEstados;
import Gestores.GestorHotel;
import Excepciones.ErrorLoginException;
import java.util.Scanner;

public class Menu {

    private GuiEstados estado = GuiEstados.INICIAR_SESION;
    private GuiEstados menuPrincipal;      // guarda el menu al que lleva la opción volver en los gestores
    private GestorHotel gestorHotel;
    private Scanner scanner = new Scanner(System.in);

    public Menu(GestorHotel gestorHotel) {
        this.gestorHotel = gestorHotel;
    }

    private MenuOpciones armarMenu(){
        MenuOpciones menu = new MenuOpciones();

        switch (estado){
            case INICIAR_SESION:
                menu.agregarOpcion("Ingresar", "login");
                menu.agregarOpcion("Salir", "salir", 'X');
                break;

            case MENU_STAFF:
                menu.agregarOpcion("Reservas", "irReservas");
                menu.agregarOpcion("Habitaciones", "irHabitaciones");
                menu.agregarOpcion("Cerrar sesión", "logout", 'X');
                break;

            case MENU_CLIENTE:
                menu.agregarOpcion("Mis reservas", "misReservas");
                menu.agregarOpcion("Solicitar reserva", "solicitarReserva");
                menu.agregarOpcion("Cerrar sesión", "logout", 'X');
                break;

            case MENU_ADMIN:
                menu.agregarOpcion("Gestionar cuentas", "irCuentas");
                menu.agregarOpcion("Hacer backup", "backup");
                menu.agregarOpcion("Cerrar sesión", "logout", 'X');
                break;

            case GESTION_HABITACIONES:
                menu.agregarOpcion("Ver disponibles", "verDisponibles");
                menu.agregarOpcion("Ver ocupadas", "verOcupadas");
                menu.agregarOpcion("Ver no disponibles", "verNoDisponibles");
                menu.agregarOpcion("Volver", "volver", 'X');
                break;

            case GESTION_RESERVAS:
                menu.agregarOpcion("Nueva reserva", "nuevaReserva");
                menu.agregarOpcion("Check-in", "checkIn");
                menu.agregarOpcion("Check-out", "checkOut");
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

                Cuentas cuentaActual;

                try {
                    cuentaActual = gestorHotel.login(usuario, contrasenia);
                } catch (ErrorLoginException e) {
                    System.out.println(e.getMessage());
                    return estado;
                }

                if (cuentaActual instanceof Admins)
                    menuPrincipal = GuiEstados.MENU_ADMIN;
                else if (cuentaActual instanceof Staff)
                    menuPrincipal = GuiEstados.MENU_STAFF;
                else if (cuentaActual instanceof Clientes)
                    menuPrincipal = GuiEstados.MENU_CLIENTE;
                else
                    return estado;   // tirar error

                return menuPrincipal;

            case "volver":
                return menuPrincipal;

            case "irReservas":
                return GuiEstados.GESTION_RESERVAS;

            case "irHabitaciones":
                return GuiEstados.GESTION_HABITACIONES;

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
    }

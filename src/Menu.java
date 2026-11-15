import Clases.Admins;
import Clases.Clientes;
import Clases.Cuentas;
import Clases.Staff;
import Enumeradores.GuiEstados;
import Enumeradores.RolesCuenta;
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

            case MENU_PRINCIPAL:
                if (gestorHotel.getCuentaActual().isPermisoCrearReserva()
                        || gestorHotel.getCuentaActual().isPermisoModificarReserva()
                        || gestorHotel.getCuentaActual().isPermisoCancelarReserva())
                    menu.agregarOpcion("Reservas", "irReservas");

                if (gestorHotel.getCuentaActual().isPermisoCrearCuenta()
                        || gestorHotel.getCuentaActual().isPermisoEliminarCuenta())
                    menu.agregarOpcion("Cuentas", "irCuentas");

                menu.agregarOpcion("Habitaciones", "irHabitaciones");
                menu.agregarOpcion("Cerrar sesión", "logout", 'X');
                break;

            case GESTION_HABITACIONES:
                menu.agregarOpcion("Ver disponibles", "verDisponibles");
                menu.agregarOpcion("Ver ocupadas", "verOcupadas");
                menu.agregarOpcion("Ver no disponibles", "verNoDisponibles");
                menu.agregarOpcion("Volver", "volver", 'X');
                break;

            case GESTION_RESERVAS:
                if (gestorHotel.getCuentaActual().isPermisoCrearReserva())
                    menu.agregarOpcion("Nueva reserva", "nuevaReserva");
                if (gestorHotel.getCuentaActual().isPermisoModificarReserva())
                    menu.agregarOpcion("Modificar reserva", "modificarReserva");
                if (gestorHotel.getCuentaActual().isPermisoCancelarReserva())
                    menu.agregarOpcion("Cancelar reserva", "cancelarReserva");
                menu.agregarOpcion("Volver", "volver", 'X');
                break;

            case GESTION_CUENTAS:
                if (gestorHotel.getCuentaActual().isPermisoCrearCuenta())
                    menu.agregarOpcion("Crear cuenta", "crearCuenta");
                if (gestorHotel.getCuentaActual().isPermisoEliminarCuenta())
                    menu.agregarOpcion("Eliminar cuenta", "eliminarCuenta");
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

            case "crearCuenta":
                gestorHotel.agregarCuenta();
                return estado;

            case "eliminarCuenta":
                gestorHotel.eliminarCuenta();
                return estado;

            case "nuevaReserva":
                gestorHotel.agregarReserva();
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

}

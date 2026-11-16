package Gestores;

import Clases.Cuentas;
import Clases.Habitaciones;
import Clases.Reservas;
import Enumeradores.RazonesOcupada;
import Enumeradores.RolesCuenta;
import Excepciones.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GestorHotel {

    private GestorHabitaciones gestorHabitaciones;
    private GestorReservas gestorReservas;
    private GestorCuentas gestorCuentas;

    public GestorHotel() {
        this.gestorHabitaciones = new GestorHabitaciones();
        this.gestorCuentas = new GestorCuentas();
        this.gestorReservas = new GestorReservas();
    }

    //busqueda coordinada de habitaciones para una fecha disponible

    public ArrayList<Integer> buscarHabitacionesFechaDisponibles(Integer idReserva, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        ArrayList<Integer> arrIdsHabitaciones= new ArrayList<>();
        ArrayList<Integer> arrDisponibles= new ArrayList<>();

        arrIdsHabitaciones = gestorHabitaciones.listarIdsHabitacionesHabilitadas();

        for (Integer idHabitacion : arrIdsHabitaciones) {
            if (gestorReservas.fechaDisponible(
                    idReserva,
                    idHabitacion,
                    fechaInicio,
                    fechaFin ))
            {
                arrDisponibles.add(idHabitacion); //agregamos la habitacion que tiene disponibilidad para el periodo ingresado
            }

        }

        return arrDisponibles;
    }


    //metodos para el GestorHabitaciones

    public Habitaciones buscarPorClave(int idClave) {
        return gestorHabitaciones.buscarPorClave(idClave);
    }

    public boolean agregarHabitacion(Habitaciones habitacion) {
        if (buscarPorClave(habitacion.getId()) != null) {
            gestorHabitaciones.agregar(habitacion.getId(), habitacion);
            return true;
        }
        return false;
    }

    public Habitaciones eliminarHabitacion(Habitaciones habitacion) {
        return gestorHabitaciones.quitar(habitacion.getId());
    }

    public String listarHabitaciones() {
        return gestorHabitaciones.listar();
    }

    public String listarHabitacionesDisponibles() {
        return gestorHabitaciones.listarHabitacionesDisponibles();
    }

    public boolean estaLibre(int idClave) {
        return gestorHabitaciones.estaLibre(idClave);
    }

    public boolean modificarHabitacion(int id, int piso, int numeroDepartamento) throws IdHabitacionNoEncontradoException {
        return gestorHabitaciones.modificarHabitacion(id, piso, numeroDepartamento);
    }

    public boolean modificarHabitacion(int id, RazonesOcupada nuevaOcupacion) throws IdHabitacionNoEncontradoException {
        return gestorHabitaciones.modificarHabitacion(id, nuevaOcupacion);
    }

    //metodos para el gestorCuentas

    public boolean crearCuenta(Cuentas nueva) {
        return gestorCuentas.crearCuenta(nueva);
    }

    public void eliminarCuenta (Cuentas elemento) {
        gestorCuentas.eliminar(elemento);
    }

    public String listarCuentas() {
        return gestorCuentas.mostrar();
    }

    public Cuentas buscarCuentaPorId(Integer id) {
        return gestorCuentas.buscarCuentaPorId(id);
    }

    public Cuentas buscarCuentaPorDni(String dni) {
        return gestorCuentas.buscarCuentaPorDni(dni);
    }

    public Cuentas buscarCuentaPorUsuario(String nombreUsuario) {
        return gestorCuentas.buscarCuentaPorUsuario(nombreUsuario);
    }

    public boolean modificarNombre(Integer id, String nombre) {
        return gestorCuentas.modificarNombre(id, nombre);
    }

    public boolean modificarNombreUsuario(Integer id, String nombreUsuario) {
        return gestorCuentas.modificarNombreUsuario(id, nombreUsuario);
    }

    public boolean modificarDni(Integer id, String dni) {
        return gestorCuentas.modificarDni(id, dni);
    }

    public boolean modificarContrasenia(Integer id, String contrasenia) {
        return gestorCuentas.modificarContrasenia(id, contrasenia);
    }

    public boolean modificarEstadoEliminado(Integer id, boolean nuevoEstado) {
        return gestorCuentas.modificarEstadoEliminado(id, nuevoEstado);
    }

    public Cuentas login(String nombreUsuario, String contrasenia) throws ErrorLoginException {
        return gestorCuentas.login(nombreUsuario, contrasenia);
    }

    public void logout() {
        gestorCuentas.logout();
    }

    public Cuentas getCuentaActual() {
        return gestorCuentas.getCuentaActual();
    }

    //metodos del gestorReservas

    public void agregarReserva(Reservas nuevo) {
        gestorReservas.agregar(nuevo);
    }

    public void eliminarReserva(Reservas elemento) {
        gestorReservas.eliminar(elemento);
    }

    public String listarReservas() {
        if (cuentaActualEsCliente())
            return gestorReservas.listarReservasPorCuenta(gestorCuentas.getCuentaActual().getId());
        return gestorReservas.mostrar();
    }


    private boolean cuentaActualEsCliente() {
        Cuentas actual = gestorCuentas.getCuentaActual();
        return actual != null && RolesCuenta.CLIENTE.name().equals(actual.getRol());
    }

    public boolean fechaDisponible(Integer idReserva, Integer idHabitacion,LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return gestorReservas.fechaDisponible(idReserva, idHabitacion, fechaInicio, fechaFin);
    }

    private String listarReservasPorIdHabitacion(Integer idHabitacion) {
        return gestorReservas.listarReservasPorHabitacion(idHabitacion);
    }

    public String listarReservasPorPasajero(String dniPasajero) {
        return gestorReservas.listarReservasPorPasajero(dniPasajero);
    }

    public Reservas buscarReserva(Integer idReserva) throws IdReservaInexistenteException {
        return gestorReservas.buscarReserva(idReserva);
    }

    public boolean modificarReserva(Integer idReserva, Integer idHabitacion, LocalDateTime nuevaFechaInicio, LocalDateTime nuevaFechaFin) throws IdReservaInexistenteException, FechaNoDisponibleException, FechaInvalidaException {
        return gestorReservas.modificarReserva(idReserva, idHabitacion, nuevaFechaInicio, nuevaFechaFin);
    }

    //metodo para listar dentro de un array de Integer habitaciones que puedan reservarse debtri de un periodo
    public ArrayList<Integer> buscarHabitacionesParaReserva(Integer idHabitacion, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return gestorReservas.buscarHabitacionesParaReserva(idHabitacion, fechaInicio, fechaFin);
    }
}

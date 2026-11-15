package Gestores;

import Clases.Cuentas;
import Clases.Habitaciones;
import Clases.Reservas;
import Enumeradores.RazonesOcupada;
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

    public ArrayList<Habitaciones> buscarHabitacionFechaDisponible(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        ArrayList<Integer> arrIdDisponibles= new ArrayList<>();
        ArrayList<Habitaciones> arrHabitacionesDisponibles= new ArrayList<>();

        arrIdDisponibles = gestorReservas.buscarHabitacionesParaReserva(fechaInicio, fechaFin);

        for (int idActual : arrIdDisponibles) {
            if (gestorHabitaciones.estaLibre(idActual))
                arrHabitacionesDisponibles.add(gestorHabitaciones.buscarPorClave(idActual));
        }

        return arrHabitacionesDisponibles;
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

    public void agregarCuenta (Cuentas nuevo) {
        gestorCuentas.agregar(nuevo);
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

    //metodos del gestorReservas

    public void agregarReserva(Reservas nuevo) {
        gestorReservas.agregar(nuevo);
    }

    public void eliminarReserva(Reservas elemento) {
        gestorReservas.eliminar(elemento);
    }

    public String listarReservas() {
        return gestorReservas.mostrar();
    }

    public boolean fechaDisponible(int idReserva, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws FechaNoDisponibleException {
        return gestorReservas.fechaDisponible(idReserva, fechaInicio, fechaFin);
    }

    public boolean fechaDisponibleSinId(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return gestorReservas.fechaDisponibleSinId(fechaInicio, fechaFin);
    }

    private String listarReservasPorIdHabitacion(int idHabitacion) {
        return gestorReservas.listarReservasPorHabitacion(idHabitacion);
    }

    public String listarReservasPorPasajero(String dniPasajero) {
        return gestorReservas.listarReservasPorPasajero(dniPasajero);
    }

    public Reservas buscarReserva(int idReserva) throws IdReservaInexistenteException {
        return gestorReservas.buscarReserva(idReserva);
    }

    public boolean modificarReserva(int idReserva, LocalDateTime nuevaFechaInicio, LocalDateTime nuevaFechaFin) throws IdReservaInexistenteException, FechaNoDisponibleException, FechaInvalidaException {
        return gestorReservas.modificarReserva(idReserva, nuevaFechaInicio, nuevaFechaFin);
    }

    public boolean modificarReserva(int idReserva, Habitaciones nuevaHabitacion , LocalDateTime nuevaFechaInicio, LocalDateTime nuevaFechaFin) throws IdReservaInexistenteException, FechaNoDisponibleException, FechaInvalidaException {
        return gestorReservas.modificarReserva(idReserva, nuevaHabitacion, nuevaFechaInicio, nuevaFechaFin);
    }

    //metodo para listar dentro de un array de Integer habitaciones que puedan reservarse debtri de un periodo
    public ArrayList<Integer> buscarHabitacionesParaReserva(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return gestorReservas.buscarHabitacionesParaReserva(fechaInicio, fechaFin);
    }
}

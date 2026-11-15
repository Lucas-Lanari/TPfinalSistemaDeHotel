package Gestores;

import Clases.Cuentas;
import Clases.Habitaciones;
import Enumeradores.RazonesOcupada;
import Excepciones.ErrorLoginException;
import Excepciones.IdHabitacionNoEncontradoException;

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

    public void quitarCuenta (Cuentas elemento) {
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



}

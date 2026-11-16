package Gestores;


import Clases.Habitaciones;
import Clases.Reservas;
import Conexiones.Conexion;
import Enumeradores.EstadoReserva;
import Enumeradores.RazonesOcupada;
import Excepciones.FechaInvalidaException;
import Excepciones.FechaNoDisponibleException;
import Excepciones.HabitacionNoDisponibleException;
import Excepciones.IdReservaInexistenteException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static Conexiones.Conexion.*;

public class GestorReservas extends Contenedor<Reservas> {

    public GestorReservas() throws SQLException {
        super();

        HashSet<Reservas> reservas = Conexion.listarReservas();

        for (Reservas reserva : reservas) {
            super.agregar(reserva);
        }

    }

    public boolean agregarReserva(Reservas reserva) {

        if (!Conexion.agregarReserva(reserva))
            return false;

        super.agregar(reserva);

        return true;
    }

    public boolean eliminarReserva(int id) {

        Reservas aEliminar;

        try {
            aEliminar = buscarReserva(id);
        } catch (IdReservaInexistenteException e) {
            return false;
        }

        if (!Conexion.eliminarReserva(aEliminar))
            return false;

        eliminar(aEliminar);

        return true;
    }

    public boolean fechaDisponible(Integer idReserva, Integer idHabitacion, LocalDateTime fechaInicio, LocalDateTime fechaFin) {

        if (fechaInicio == null || fechaFin == null || !fechaInicio.isBefore(fechaFin)) {
            return false;
        }

        for (Reservas actual : lista) {
            if (actual.getIdHabitacion().equals(idHabitacion)  //verificamos que la habitacion pasada por parametro tenga esa fecha disponible
                    &&  actual.getEstado() != EstadoReserva.TERMINADA //la reserva no debe tener estado terminado/eliminado
                    && ( idReserva == null || !idReserva.equals(actual.getId()) ) //la reserva no debe compararse con si misma
                    && ( fechaInicio.isBefore(actual.getCheckOut()) && fechaFin.isAfter(actual.getCheckIn()) ) ) //verificamos si la fecha nueva tiene superposicion con otra
            {
                return false;
            }
        }

        return true;
    }

    public String listarReservasPorHabitacion(Integer idHabitacion) {
        StringBuilder respuesta = new StringBuilder();
        respuesta.append("Las reservas para la habitacion "+ idHabitacion + " son:\n");

        for (Reservas actual : lista) {
            if (idHabitacion.equals(actual.getIdHabitacion()) ) {
                respuesta.append(actual.toString());
                respuesta.append("\n");
            }
        }

        return respuesta.toString();
    }

    public String listarReservasPorPasajero(String dniPasajero) {
        StringBuilder respuesta = new StringBuilder();
        respuesta.append("Las reservas para el pasajero "+ dniPasajero + " son:\n");

        //agregamos las reservas donde se aparezca el dni del pasajero
        for (Reservas actual : lista) {
            for (String dni : actual.dniIntegrantes) {
                if (dniPasajero.equals(dni)) {
                    respuesta.append(actual.toString());
                    respuesta.append("\n");
                }
            }
        }



        return respuesta.toString();
    }

    public String listarReservasPorCuenta(Integer idCuenta) {
        StringBuilder respuesta = new StringBuilder();

        for (Reservas actual : lista) {
            if (actual.getIdCuentaResponsable() == idCuenta) {
                respuesta.append(actual.toString());
                respuesta.append("\n");
            }
        }

        return respuesta.toString();
    }

    public Reservas buscarReserva(Integer idReserva) throws IdReservaInexistenteException {
        for (Reservas actual : lista) {
            if (idReserva.equals(actual.getId())) {
                return actual;
            }
        }

        throw new IdReservaInexistenteException();
    }

    public boolean modificarReserva(int idReserva, int idHabitacion, LocalDateTime nuevaFechaInicio, LocalDateTime nuevaFechaFin) throws IdReservaInexistenteException, FechaNoDisponibleException, FechaInvalidaException {

        //verificamos que la nueva fecha de Inicio este antes de la fecha final
        if (nuevaFechaInicio == null || nuevaFechaFin == null || !nuevaFechaInicio.isBefore(nuevaFechaFin)) {
            throw new FechaInvalidaException();
        }

        Reservas reservaElegida = buscarReserva(idReserva);


        if (reservaElegida.getEstado() == EstadoReserva.TERMINADA) {
            throw new FechaNoDisponibleException();
        }

        if (!fechaDisponible(idReserva, idHabitacion,nuevaFechaInicio, nuevaFechaFin)) {
            throw new FechaNoDisponibleException();
        }

        reservaElegida.setIdHabitacion(idHabitacion);
        reservaElegida.setCheckIn(nuevaFechaInicio);
        reservaElegida.setCheckOut(nuevaFechaFin);

        Conexion.actualizarReserva(reservaElegida);

        return true;
    }

    //metodo para listar dentro de un array de Integer habitaciones que puedan reservarse debtri de un periodo
    public ArrayList<Integer> buscarHabitacionesParaReserva(int idHabitacion, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        ArrayList<Integer> arrHabitacionesOcupadas = new ArrayList<>();

        for (Reservas actual : lista) {
            if (actual.getEstado() != EstadoReserva.TERMINADA && (fechaInicio.isBefore(actual.getCheckOut()) && actual.getIdHabitacion() != idHabitacion && fechaFin.isAfter(actual.getCheckIn())) ) {
                arrHabitacionesOcupadas.add(actual.getIdHabitacion());
            }
        }

        return arrHabitacionesOcupadas;
    }

}

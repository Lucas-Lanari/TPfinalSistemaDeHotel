package Gestores;


import Clases.Habitaciones;
import Clases.Reservas;
import Enumeradores.RazonesOcupada;
import Excepciones.FechaInvalidaException;
import Excepciones.FechaNoDisponibleException;
import Excepciones.HabitacionNoDisponibleException;
import Excepciones.IdReservaInexistenteException;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GestorReservas extends Contenedor<Reservas> {

    public boolean fechaDisponible(int idReserva, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws FechaNoDisponibleException {
        for (Reservas actual : lista) {
            if ( idReserva != actual.getId() && ( fechaInicio.isBefore(actual.getCheckOut()) && fechaFin.isAfter(actual.getCheckIn()) ) )
                throw new FechaNoDisponibleException();
        }

        return true;
    }

    public boolean fechaDisponibleSinId(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        for (Reservas actual : lista) {
            if ( fechaInicio.isBefore(actual.getCheckOut()) && fechaFin.isAfter(actual.getCheckIn()) )
                return false;
        }

        return true;
    }

    public String listarReservasPorHabitacion(int idHabitacion) {
        StringBuilder respuesta = new StringBuilder();
        respuesta.append("Las reservas para la habitacion "+ idHabitacion + " son:\n");

        for (Reservas actual : lista) {
            if (idHabitacion == actual.getIdHabitacion()) {
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

    public Reservas buscarReserva(int idReserva) throws IdReservaInexistenteException {
        for (Reservas actual : lista) {
            if (idReserva == actual.getId()) {
                return actual;
            }
        }

        throw new IdReservaInexistenteException();
    }

    public boolean modificarReserva(int idReserva, LocalDateTime nuevaFechaInicio, LocalDateTime nuevaFechaFin) throws IdReservaInexistenteException, FechaNoDisponibleException, FechaInvalidaException {

        //verificamos que la nueva fecha de Inicio este antes de la fecha final
        if (nuevaFechaInicio.isBefore(nuevaFechaFin)) {
            throw new FechaInvalidaException();
        }

        Reservas reservaElegida = buscarReserva(idReserva);

        if (fechaDisponible(idReserva, nuevaFechaInicio, nuevaFechaFin)) {
            reservaElegida.setCheckIn(nuevaFechaInicio);
            reservaElegida.setCheckOut(nuevaFechaFin);
        }

        return true;
    }

    public boolean modificarReserva(int idReserva, Habitaciones nuevaHabitacion , LocalDateTime nuevaFechaInicio, LocalDateTime nuevaFechaFin) throws IdReservaInexistenteException, FechaNoDisponibleException, FechaInvalidaException {

        //verificamos que la nueva fecha de Inicio este antes de la fecha final
        if (nuevaFechaFin.isBefore(nuevaFechaInicio)) {
            throw new FechaInvalidaException();
        }

        if (nuevaHabitacion.getOcupacion() != RazonesOcupada.LIBRE) {
            throw new HabitacionNoDisponibleException();
        }

        Reservas reservaElegida = buscarReserva(idReserva);

        if (fechaDisponible(idReserva, nuevaFechaInicio, nuevaFechaFin)) {
            reservaElegida.setIdHabitacion(nuevaHabitacion.getId());
            reservaElegida.setCheckIn(nuevaFechaInicio);
            reservaElegida.setCheckOut(nuevaFechaFin);
        }

        return true;
    }

    //metodo para listar dentro de un array de Integer habitaciones que puedan reservarse debtri de un periodo
    public ArrayList<Integer> buscarHabitacionesParaReserva(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        ArrayList<Integer> arrHabitaciones = new ArrayList<>();

        for (Reservas actual : lista) {
            if (fechaDisponibleSinId(fechaInicio, fechaFin)) {
                arrHabitaciones.add(actual.getIdHabitacion());
            }
        }

        return arrHabitaciones;
    }

}

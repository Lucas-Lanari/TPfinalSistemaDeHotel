package Clases;

import jdk.jshell.execution.LoaderDelegate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public class GestorHotel {

    private GestorHabitaciones gestorHabitaciones;
    private GestorReservas gestorReservas;


    //contenedor podría ser usado por reserva, ocupación y usuario


    public ArrayList<Habitaciones> buscarHabitacionFechaDisponible(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        ArrayList<Integer> arrIdDisponibles= new ArrayList<>();
        ArrayList<Habitaciones> arrHabitacionesDisponibles= new ArrayList<>();

        arrIdDisponibles = gestorReservas.buscarHabitacionesParaReserva(fechaInicio, fechaFin);

        for (int idActual : arrIdDisponibles) {
            if (gestorHabitaciones.estaLibre(idActual))
                arrHabitacionesDisponibles.add(gestorHabitaciones.buscarPorClava(idActual));
        }

        return arrHabitacionesDisponibles;
    }
}

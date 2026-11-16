package Gestores;

import Clases.Habitaciones;
import Conexiones.Conexion;
import Enumeradores.RazonesOcupada;
import Excepciones.IdHabitacionNoEncontradoException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class GestorHabitaciones extends ContenedorMapa<Integer, Habitaciones> {


    public GestorHabitaciones() throws SQLException {
        super();

        HashSet<Habitaciones> habitaciones= Conexion.listarHabitaciones();

        for (Habitaciones habitacion : habitaciones) {
            super.agregar(habitacion.getId(), habitacion);
        }

    }

    public ArrayList<Integer> listarIdsHabitaciones() {

        ArrayList<Integer> resultados = new ArrayList<>(mapa.keySet());

        return resultados;
    }

    public boolean agregar(Habitaciones habitacion) {
        if (mapa.containsKey(habitacion.getId()))
            return false;

        if (!Conexion.crearHabitacion(habitacion))
            return false;

        super.agregar(habitacion.getId(),habitacion);

        return true;
    }

    public boolean eliminar(int idHabitacion) throws IdHabitacionNoEncontradoException {
        if (!mapa.containsKey(idHabitacion))
            throw new IdHabitacionNoEncontradoException();

        try {
            if (Conexion.eliminarHabitacion(buscarPorClave(idHabitacion)))
                return false;
        } catch (SQLException e) {
            return false;
        }

        super.quitar(idHabitacion);

        return true;
    }

    public String listarHabitacionesDisponibles() {
        StringBuilder respuesta = new StringBuilder();

        Iterator<Map.Entry<Integer, Habitaciones>> it = mapa.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<Integer, Habitaciones> entrada = it.next();
            Habitaciones valor = entrada.getValue();

            //agregamos las habitaciones que no están ocupadas al StringBuilder
            if (valor.getOcupacion().equals(RazonesOcupada.LIBRE)) {
                respuesta.append(valor.toString());
                respuesta.append('\n');
            }
        }

        return respuesta.toString();
    }

    //verifica el estado actual de una habitacion por id
    public boolean estaLibre(int id) {
        if (mapa.get(id).getOcupacion() == RazonesOcupada.LIBRE) {
            return true;
        }
        else
            return false;
    }

    public boolean modificarHabitacion(int id, int piso, int numeroDepartamento) throws IdHabitacionNoEncontradoException {
        Habitaciones elemento = mapa.get(id);

        if (elemento == null) {
            throw new IdHabitacionNoEncontradoException();
        }

        elemento.setPiso(piso);
        elemento.setNumeroDepartamento(numeroDepartamento);

        Conexion.actualizarHabitacion(elemento);

        return true;
    }

    public boolean modificarHabitacion(int id, RazonesOcupada nuevaOcupacion) throws IdHabitacionNoEncontradoException {
        Habitaciones elemento = mapa.get(id);

        if (elemento == null) {
            throw new IdHabitacionNoEncontradoException();
        }

        elemento.setOcupacion(nuevaOcupacion);

        Conexion.actualizarHabitacion(elemento);

        return true;
    }




}

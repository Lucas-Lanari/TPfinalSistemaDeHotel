package Clases;

import Enumeradores.RazonOcupada;
import Enumeradores.RazonesOcupada;
import Excepciones.IdHabitacionNoEncontradoException;

import java.util.Iterator;
import java.util.Map;

public class GestorHabitaciones extends ContenedorMapa<Integer, Habitaciones> {

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

        return true;
    }

    public boolean modificarHabitacion(int id, RazonesOcupada nuevaOcupacion) throws IdHabitacionNoEncontradoException {
        Habitaciones elemento = mapa.get(id);

        if (elemento == null) {
            throw new IdHabitacionNoEncontradoException();
        }

        elemento.setOcupacion(nuevaOcupacion);

        return true;
    }




}

package Clases;

import Enumeradores.RazonOcupada;
import Enumeradores.RazonesOcupada;

import java.util.Iterator;
import java.util.Map;

public class GestorHabitaciones extends ContenedorMapa<Integer, Habitaciones> {

    public String habitacionesDisponibles() {
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

}

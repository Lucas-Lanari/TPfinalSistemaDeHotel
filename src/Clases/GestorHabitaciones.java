package Clases;

import Enumeradores.RazonOcupada;

import java.util.Iterator;
import java.util.Map;

public class GestorHabitaciones extends ContenedorMapa<Integer, Habitacion> {

    public String habitacionesDisponibles() {
        StringBuilder respuesta = new StringBuilder();

        Iterator<Map.Entry<Integer, Habitacion>> it = mapa.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<Integer, Habitacion> entrada = it.next();
            Habitacion valor = entrada.getValue();

            //agregamos las habitaciones que no están ocupadas al StringBuilder
            if (valor.getOcupada().equals(RazonOcupada.LIBRE.getMotivo())) {
                respuesta.append(valor.toString());
                respuesta.append('\n');
            }
        }

        return respuesta.toString();
    }


}

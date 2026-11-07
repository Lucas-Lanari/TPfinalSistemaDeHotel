package Clases;

import java.util.ArrayList;
import java.util.List;

public class Contenedor<T> {

    private List<T> elementos;

    public Contenedor() {
        this.elementos = new ArrayList<>();
    }

    public void agregar(T nuevo) {
        elementos.add(nuevo);
    }

    public void eliminar(T elemento) {
        elementos.remove(elemento);
    }

    public String mostrar() {

        StringBuilder respuesta = new StringBuilder();

        for (T elemento : elementos) {
            respuesta.append(elemento.toString());  //usa el toString propio del elemento de la coleccion
            respuesta.append("\n");
        }

        return respuesta.toString();
    }

    //podría definirse un buscar por cierto atributo
}

package Clases;

import Enumeradores.RazonOcupada;

public class Habitacion {

    private int id;
    private int Piso;
    private int nroDepartamento;
    private RazonOcupada ocupada;


    public Habitacion(RazonOcupada ocupada) {
        this.ocupada = ocupada;
    }

    public RazonOcupada getOcupada() {
        return ocupada;
    }
}

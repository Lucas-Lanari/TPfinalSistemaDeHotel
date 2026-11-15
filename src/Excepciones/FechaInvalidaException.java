package Excepciones;

public class FechaInvalidaException extends Exception {

    public FechaInvalidaException() {
        super("La fecha ingresada no es valida");
    }
}

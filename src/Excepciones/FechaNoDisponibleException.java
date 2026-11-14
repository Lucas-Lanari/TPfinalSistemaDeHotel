package Excepciones;

public class FechaNoDisponibleException extends Exception {

    public FechaNoDisponibleException() {
        super("La fecha de la reserva ingresada no se encuentra disponible");
    }
}

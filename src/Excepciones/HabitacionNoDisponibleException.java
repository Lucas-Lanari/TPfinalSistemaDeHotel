package Excepciones;

public class HabitacionNoDisponibleException extends RuntimeException {

    public HabitacionNoDisponibleException() {
        super("La habitacion seleccionada no se encuentra disponible actualmente");
    }
}

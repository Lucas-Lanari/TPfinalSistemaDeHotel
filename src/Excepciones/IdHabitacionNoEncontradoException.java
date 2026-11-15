package Excepciones;

public class IdHabitacionNoEncontradoException extends RuntimeException {

    public IdHabitacionNoEncontradoException() {
        super("El id ingresado no se corresponde con una habitacion");
    }

}

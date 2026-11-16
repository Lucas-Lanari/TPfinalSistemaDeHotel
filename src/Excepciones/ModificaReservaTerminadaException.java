package Excepciones;

public class ModificaReservaTerminadaException extends RuntimeException {
    public ModificaReservaTerminadaException() {
        super("La reserva que intenta modificar ya ha terminado");
    }
}

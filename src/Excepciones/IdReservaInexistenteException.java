package Excepciones;

public class IdReservaInexistenteException extends Exception {

    public IdReservaInexistenteException() {
        super("El numero de reserva ingresado no se encuentra en el sistema");
    }
}

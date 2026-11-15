package Excepciones;

public class UsuarioEliminadoException extends ErrorLoginException {
    public UsuarioEliminadoException(String message) {
        super(message);
    }
}

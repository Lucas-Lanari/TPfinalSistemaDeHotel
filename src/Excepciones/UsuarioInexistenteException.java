package Excepciones;

public class UsuarioInexistenteException extends ErrorLoginException {
    public UsuarioInexistenteException(String message) {
        super(message);
    }
}

package Excepciones;

public class ContraseniaIncorrectaException extends ErrorLoginException {
    public ContraseniaIncorrectaException(String message) {
        super(message);
    }
}

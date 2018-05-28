package pl.lodz.p.pl.Exceptions;

public class InvalidValueException extends IllegalArgumentException {
    public InvalidValueException(String message, Throwable cause){
        super(message,cause);
    }
}

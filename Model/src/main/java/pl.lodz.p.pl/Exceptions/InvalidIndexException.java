package pl.lodz.p.pl.Exceptions;

public class InvalidIndexException extends IllegalArgumentException {
    public InvalidIndexException(String message, Throwable cause){
        super(message,cause);
    }
    public InvalidIndexException(String message ){
        super(message);
    }
}

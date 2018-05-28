package pl.lodz.p.pl.Exceptions;

public class BoardException extends IllegalArgumentException {
    public BoardException(String message, Throwable cause){
        super(message,cause);
    }
    public BoardException(String message){
        super(message);
    }
}

package entities.exceptions;

public class InvalidNameException extends RuntimeException{
    public InvalidNameException(String msg){
        super(msg);
    }
}

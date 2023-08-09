package exceptions;

public class NotSymmetricNumberOfColumnsException extends Exception{
    public NotSymmetricNumberOfColumnsException(){
        super("The number of columns is not the same in all rows");
        System.out.println(getMessage());
    }
}

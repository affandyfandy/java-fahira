package lab3;

public class VowelNotFoundException extends RuntimeException{
    public VowelNotFoundException(){}

    public VowelNotFoundException(String message){
        super(message);
    }
}

package lab2;

// Extends runtime exception (unchecked)
class InputOutOfBoundsException extends RuntimeException{
    public InputOutOfBoundsException(){}

    public InputOutOfBoundsException(String message) {
        super(message);
    }
}


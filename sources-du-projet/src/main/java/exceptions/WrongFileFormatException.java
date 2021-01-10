package exceptions;

public class WrongFileFormatException extends Exception {
    private static final long serialVersionUID = 1L;
    public WrongFileFormatException() {
        super();
    }

    public WrongFileFormatException(String s) {
        super(s);
    }
}
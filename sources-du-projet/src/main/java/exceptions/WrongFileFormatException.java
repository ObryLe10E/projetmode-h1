package exceptions;

public class WrongFileFormatException extends Exception {
    // juste pour suppr les warnings chiants
    private static final long serialVersionUID = 1L;
    // juste pour suppr les warnings chiants

    public WrongFileFormatException() {
        super();
    }

    public WrongFileFormatException(String s) {
        super(s);
    }
}
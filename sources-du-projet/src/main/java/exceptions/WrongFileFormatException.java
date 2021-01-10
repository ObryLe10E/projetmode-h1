package exceptions;

/**
 * Exception liée à un fichier ply ne respectant pas le format requis
 * @author Thomas
 *
 */
@SuppressWarnings("serial")
public class WrongFileFormatException extends Exception {
	public WrongFileFormatException() {
        super();
    }

	/**
	 * Crée une exception avec un message donné
	 * @param message
	 */
    public WrongFileFormatException(String message) {
        super(message);
    }
}
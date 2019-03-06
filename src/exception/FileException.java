package exception;

public class FileException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FileException(String myMessage) {
        super(myMessage);
    }
}

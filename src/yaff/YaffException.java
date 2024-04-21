package yaff;

public class YaffException extends Exception {
    public YaffException(String message) {
        super(message);
    }

    public YaffException(String message, Throwable cause) {
        super(message, cause);
    }
}

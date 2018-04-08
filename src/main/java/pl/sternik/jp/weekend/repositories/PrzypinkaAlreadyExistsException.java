package pl.sternik.jp.weekend.repositories;

public class PrzypinkaAlreadyExistsException extends Exception {
    private static final long serialVersionUID = -4576295597218170159L;

    public PrzypinkaAlreadyExistsException() {     
    }

    public PrzypinkaAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PrzypinkaAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrzypinkaAlreadyExistsException(String message) {
        super(message);
    }

    public PrzypinkaAlreadyExistsException(Throwable cause) {
        super(cause);
    }
    
}

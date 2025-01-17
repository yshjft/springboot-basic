package org.prgms.voucheradmin.global.exception.customexception;

public class WrongInputException extends RuntimeException{
    private static final String WRONG_INPUT_EXCEPTION = "wrong input";


    public WrongInputException() {
        super(WRONG_INPUT_EXCEPTION);
    }

    public WrongInputException(String message) {
        super(message);
    }

    public WrongInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongInputException(Throwable cause) {
        super(cause);
    }

    public WrongInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

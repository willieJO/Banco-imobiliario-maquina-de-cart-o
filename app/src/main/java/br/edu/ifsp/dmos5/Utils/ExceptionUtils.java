package br.edu.ifsp.dmos5.Utils;

public class ExceptionUtils extends  Exception {
    public ExceptionUtils() {}

    // Constructor that accepts a message
    public ExceptionUtils(String message)
    {
        super(message);
    }
    public ExceptionUtils (Throwable cause) {
        super (cause);
    }

    public ExceptionUtils (String message, Throwable cause) {
        super (message, cause);
    }
}

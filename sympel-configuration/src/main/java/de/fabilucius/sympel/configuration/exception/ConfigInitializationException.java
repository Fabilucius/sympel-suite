package de.fabilucius.sympel.configuration.exception;

public class ConfigInitializationException extends RuntimeException {

    public ConfigInitializationException(String message) {
        super(message);
    }

    public ConfigInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

}

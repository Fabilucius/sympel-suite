package de.fabilucius.sympel.command.exception;

import de.fabilucius.sympel.command.command.AbstractCommand;
import de.fabilucius.sympel.command.command.AbstractSubCommand;

/**
 * This exception is thrown whenever a plugin fails to properly implement an {@link AbstractCommand} or
 * {@link AbstractSubCommand}.
 * The reasoning is provided by the message passed in the constructor of this exception.
 */
public class CommandInitializationException extends RuntimeException {

    public CommandInitializationException(String message) {
        super(message);
    }

}

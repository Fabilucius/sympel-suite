package de.fabilucius.sympel.configuration.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class ConfigurationLogger {

    private static final Logger LOGGER = Logger.getLogger("configuration-logger");

    public static void log(Level level, Object content) {
        LOGGER.log(level, content.toString());
    }

    public static void log(Level level, Object content, Exception exception) {
        LOGGER.log(level, content.toString(), exception);
    }
}

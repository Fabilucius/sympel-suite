package de.fabilucius.sympel.configuration.value;

import de.fabilucius.sympel.configuration.Config;

public interface Value<T> {

    Config getConfig();

    String getConfigurationKey();

    String getDescription();

    Class<T> getTypeClass();

}

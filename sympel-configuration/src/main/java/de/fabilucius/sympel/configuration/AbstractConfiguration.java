package de.fabilucius.sympel.configuration;

import de.fabilucius.sympel.configuration.logging.ConfigurationLogger;
import de.fabilucius.sympel.configuration.node.types.ReturnNode;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

public abstract class AbstractConfiguration implements Configuration {

    /* Generating unique identifier for each configuration */
    private final UUID uuid = UUID.randomUUID();

    protected final void loadConfiguration() {
        try {
            this.getConfig().load(this.getFile());
        } catch (IOException ioException) {
            ConfigurationLogger.log(Level.SEVERE, "An error occurred " +
                    "while loading a configuration during a I/O operation:", ioException);
        } catch (InvalidConfigurationException invalidConfigurationException) {
            ConfigurationLogger.log(Level.SEVERE, "An error occurred " +
                    "while loading a configuration from file:", invalidConfigurationException);
        }
    }

    protected final void createFileDirectory() {
        if (!this.getFile().getParentFile().exists()) {
            this.getFile().getParentFile().mkdirs();
        }
    }

    @Override
    public ReturnNode returnFrom(String configKey) {
        return new ReturnNode(this.getConfig(), configKey);
    }

    @Override
    public void setValue(String configKey, Object value) {
        this.getConfig().set(configKey, value);
    }

    @Override
    public void saveConfiguration() {
        try {
            this.getConfig().save(this.getFile());
        } catch (IOException ioException) {
            ConfigurationLogger.log(Level.SEVERE, "An error occurred while saving " +
                    "the content of the in memory configuration to its associated file during a I/O operation:", ioException);
        }
    }

    /* the getter and setter of the class */

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public abstract FileConfiguration getConfig();

    @Override
    public abstract File getFile();

}

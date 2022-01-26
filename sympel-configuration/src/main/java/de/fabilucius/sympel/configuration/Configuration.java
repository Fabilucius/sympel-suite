package de.fabilucius.sympel.configuration;

import de.fabilucius.sympel.configuration.node.types.ReturnNode;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.UUID;

public interface Configuration {

    /**
     * A unique identifier useful for debug and testing purposes.
     *
     * @return the unique identifier as a uuid object
     */
    UUID getUuid();

    /**
     * The "physical" file that contains the configuration.
     * While the configuration is still loading or extracting this resembles the output path for the configuration file.
     *
     * @return the instance of the file that contains the data
     */
    File getFile();

    /**
     * The instance of the configuration file when in memory.
     * To save changes made to the configuration to the file you will need to call  {@link Configuration#saveConfiguration()}.
     *
     * @return
     */
    FileConfiguration getConfig();

    /**
     * This method is used to retrieve a value from the configuration.
     * Returns an instance of {@link ReturnNode} from which the return type of this operation gets determined by
     * {@link ReturnNode#getAs(Class)}.
     *
     * @param configKey the config key from the value that should get returned
     * @return an instance of return node from which you can decide the return type of the value associated with the config key
     */
    ReturnNode returnFrom(String configKey);

    /**
     * This method just forwards the setValue request to the unmodified {@link FileConfiguration#set(String, Object)} method.
     *
     * @param configKey the config key whose value should be updated set or removed
     * @param value     the actual value that should replace the current value associated with the config key
     */
    void setValue(String configKey, Object value);

    /**
     * This method is used to flush existing changes made to the in memory configuration to the file.
     * Be aware that this method will trigger I/O operations which will significantly slow down the Thread its running on.
     */
    void saveConfiguration();

}

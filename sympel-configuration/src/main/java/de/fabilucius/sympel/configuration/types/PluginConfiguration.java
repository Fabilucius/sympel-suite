package de.fabilucius.sympel.configuration.types;

import com.google.common.base.Preconditions;
import de.fabilucius.sympel.configuration.AbstractConfiguration;
import de.fabilucius.sympel.configuration.logging.ConfigurationLogger;
import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

public class PluginConfiguration extends AbstractConfiguration {

    /**
     * To make sure that file and config are final and no constructor is needed for AbstractConfiguration these values are declared
     * in every subclass of AbstractConfiguration instead of the parent itself.
     */
    private final File file;
    private final FileConfiguration configuration;

    protected PluginConfiguration(@NotNull Plugin plugin, @NotNull String filePathInJar) {
        this.file = new File(plugin.getDataFolder(), filePathInJar);
        this.configuration = new YamlConfiguration();
        this.createFileDirectory();
        this.extractConfigurationFileFromJar(filePathInJar);
        this.loadConfiguration();
    }

    public static PluginConfiguration fromPlugin(Plugin plugin, String filePathInJar) {
        Preconditions.checkNotNull(plugin, "plugin cannot be null.");
        Preconditions.checkNotNull(filePathInJar, "filePathInJar cannot be null.");
        return new PluginConfiguration(plugin, filePathInJar);
    }

    private void extractConfigurationFileFromJar(String fileJarPath) {
        URL resourceUrl = this.getClass().getClassLoader().getResource(fileJarPath);
        Preconditions.checkNotNull(resourceUrl, "resource url cannot be null(missing resource or wrong file path).");
        /* Extracting file from jar */
        if (!this.getFile().exists()) {
            try {
                FileUtils.copyURLToFile(resourceUrl, this.getFile());
            } catch (IOException ioException) {
                ConfigurationLogger.log(Level.SEVERE, "An error " +
                        "occurred while extracting a file from inside a jar:", ioException);
                return;
            }
        }
        /* Loading content of the file into memory */
        try {
            this.getConfig().load(this.getFile());
        } catch (IOException ioException) {
            ConfigurationLogger.log(Level.SEVERE, "An error occurred " +
                    "during an I/O operation while loading the content of a file to memory:", ioException);
        } catch (InvalidConfigurationException configurationException) {
            ConfigurationLogger.log(Level.SEVERE, "An error occurred " +
                    "while loading the content of a file to memory:", configurationException);
        }
    }

    /* the getter and setter of the class */

    @Override
    public FileConfiguration getConfig() {
        return this.configuration;
    }

    @Override
    public File getFile() {
        return this.file;
    }
}

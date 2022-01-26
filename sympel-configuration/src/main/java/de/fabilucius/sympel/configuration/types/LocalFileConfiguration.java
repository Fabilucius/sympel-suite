package de.fabilucius.sympel.configuration.types;

import com.google.common.base.Preconditions;
import de.fabilucius.sympel.configuration.AbstractConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LocalFileConfiguration extends AbstractConfiguration {

    /**
     * To make sure that file and config are final and no constructor is needed for AbstractConfiguration these values are declared
     * in every subclass of AbstractConfiguration instead of the parent itself.
     */
    private final File file;
    private final FileConfiguration config;

    private LocalFileConfiguration(File configFile) {
        this.file = configFile;
        this.config = new YamlConfiguration();
        this.createFileDirectory();
        this.loadConfiguration();
    }

    public static LocalFileConfiguration fromFile(File file) {
        Preconditions.checkNotNull(file, "file cannot be null.");
        return new LocalFileConfiguration(file);
    }

    /* the getter and setter of the class */

    public File getFile() {
        return file;
    }

    public FileConfiguration getConfig() {
        return config;
    }

}

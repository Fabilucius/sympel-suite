package de.fabilucius.sympel.configuration.node.types;

import de.fabilucius.sympel.configuration.node.Node;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ReturnNode implements Node {

    private final UUID uuid = UUID.randomUUID();

    private final FileConfiguration fileConfiguration;
    private final String configKey;


    public ReturnNode(FileConfiguration fileConfiguration, String configKey) {
        this.fileConfiguration = fileConfiguration;
        this.configKey = configKey;
    }

    @NotNull
    public <T> T getAsWithDefault(T defaultValue, Class<T> returnClass) {
        T value = this.getAs(returnClass);
        return value == null ? defaultValue : value;
    }

    @Nullable
    public <T> T getAs(Class<T> returnClass) {
        return returnClass.cast(this.getFileConfiguration().get(this.getConfigKey()));
    }

    /* the getter and setter of the class */

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public String getConfigKey() {
        return configKey;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }
}

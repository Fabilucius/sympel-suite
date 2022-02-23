package de.fabilucius.sympel.command.command;

import com.google.common.collect.Lists;
import de.fabilucius.sympel.command.metadata.Aliases;
import de.fabilucius.sympel.command.metadata.NoPermissionMessage;
import de.fabilucius.sympel.command.metadata.Permission;
import de.fabilucius.sympel.command.utilities.CommandEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public abstract class AbstractSubCommand implements CommandEntity {

    /**
     * The main identifier that is needed for the subcommand to be called from a main command.
     * This needs to be unique from all other identifier or aliases of the subcommands a command tries to register.
     */
    private final String identifier;

    public AbstractSubCommand(String identifier) {
        this.identifier = identifier;
    }

    /* the getter and setter of the class */

    @NotNull
    public final String getIdentifier() {
        return identifier;
    }

    @NotNull
    public final Optional<List<String>> getAliases() {
        if (this.getClass().isAnnotationPresent(Aliases.class)) {
            return Optional.of(Lists.newArrayList(this.getClass().getAnnotation(Aliases.class).aliases()));
        }
        return Optional.empty();
    }

    @NotNull
    public final Optional<String> getNoPermissionMessage() {
        if (this.getClass().isAnnotationPresent(NoPermissionMessage.class)) {
            return Optional.of(this.getClass().getAnnotation(NoPermissionMessage.class).message());
        }
        return Optional.empty();
    }

    @NotNull
    public final Optional<String> getPermission() {
        if (this.getClass().isAnnotationPresent(Permission.class)) {
            return Optional.of(this.getClass().getAnnotation(Permission.class).permission());
        }
        return Optional.empty();
    }

}

package de.fabilucius.sympel.command.types;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import de.fabilucius.sympel.command.Command;
import org.bukkit.command.CommandSender;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

public abstract class AbstractSubCommand implements Command {

    private final List<String> aliases = Lists.newArrayList();
    private final String identifier;
    private final String permission;

    public AbstractSubCommand() {
        Preconditions.checkState(this.getClass().isAnnotationPresent(Details.class),
                "cannot construct abstractSubCommand without details annotation");
        Details details = this.getClass().getAnnotation(Details.class);
        this.getAliases().addAll(Lists.newArrayList(details.aliases()));
        this.identifier = details.identifier();
        this.permission = details.permission();
    }

    @Override
    public abstract void handleCommandExecute(CommandSender commandSender, String... arguments);

    @Override
    public abstract List<String> handleTabComplete(CommandSender commandSender, String... arguments);

    /* the getter and setter of the class */

    public List<String> getAliases() {
        return aliases;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getPermission() {
        return permission;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Details {

        String[] aliases() default "";

        String identifier();

        String permission() default "";

    }
}

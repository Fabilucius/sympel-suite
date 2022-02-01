package de.fabilucius.sympel.command.types;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import de.fabilucius.sympel.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public abstract class AbstractCommand implements Command, CommandExecutor, TabCompleter {

    private static final Logger LOGGER = Bukkit.getLogger();

    private final List<AbstractSubCommand> subCommands = Lists.newArrayList();
    private String identifier;
    private String permission;

    protected AbstractCommand() {
        Preconditions.checkState(this.getClass().isAnnotationPresent(Details.class),
                "cannot construct abstractCommand without details annotation");
        Details details = this.getClass().getAnnotation(Details.class);
        this.identifier = details.identifier();
        this.permission = details.permission();
        Arrays.stream(details.subCommands()).forEach(subCommand -> {
            try {
                AbstractSubCommand abstractSubCommand = subCommand.getDeclaredConstructor().newInstance();
                this.getSubCommands().add(abstractSubCommand);
            } catch (Exception exception) {
                LOGGER.log(Level.SEVERE, "Unable to create an instance of a AbstractSubCommand:", exception);
            }
        });
        this.checkSubCommandIntegrity();
        PluginCommand pluginCommand = Bukkit.getPluginCommand(this.getIdentifier());
        if (pluginCommand != null) {
            pluginCommand.setExecutor(this);
            pluginCommand.setTabCompleter(this);
        }
    }

    private void checkSubCommandIntegrity() {
        List<String> aliases = Lists.newArrayList();
        this.getSubCommands().forEach(subCommand -> aliases.addAll(subCommand.getAliases()));
        Preconditions.checkState(aliases.stream().distinct().count() == aliases.size(), "subCommands " +
                "of command " + this.getClass().getSimpleName() + " has multiple different subCommands registering the same alias");
    }

    @Override
    public abstract void handleCommandExecute(CommandSender commandSender, String... arguments);

    @Override
    public abstract List<String> handleTabComplete(CommandSender commandSender, String... arguments);

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull org.bukkit.command.Command command, @NotNull String s, @NotNull String[] strings) {
        if (this.getPermission() != null && !this.getPermission().isEmpty() && !commandSender.hasPermission(this.getPermission())) {
            commandSender.sendMessage(this.getNoPermissionMessage());
            return false;
        }
        if (strings.length > 0) {
            AbstractSubCommand abstractSubCommand = this.getSubCommands().stream().filter(sCommand -> sCommand.getIdentifier().equalsIgnoreCase(strings[0]) ||
                    sCommand.getAliases().stream().anyMatch(alias -> alias.equalsIgnoreCase(strings[0]))).findAny().orElse(null);
            if (abstractSubCommand != null) {
                if (!commandSender.hasPermission(abstractSubCommand.getPermission())) {
                    commandSender.sendMessage(this.getNoPermissionMessage());
                    return false;
                }
                abstractSubCommand.handleCommandExecute(commandSender, Arrays.copyOfRange(strings, 1, strings.length));
                return false;
            }
        }
        this.handleCommandExecute(commandSender, strings);
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull org.bukkit.command.Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length > 0) {
            AbstractSubCommand possibleSubCommand = this.getSubCommands().stream()
                    .filter(subCommand -> subCommand.getIdentifier().equalsIgnoreCase(strings[0]))
                    .findFirst()
                    .orElse(null);
            if (possibleSubCommand != null) {
                return possibleSubCommand.handleTabComplete(commandSender, Arrays.copyOfRange(strings, 1, strings.length));
            }
            return this.getSubCommands().stream()
                    .map(AbstractSubCommand::getIdentifier)
                    .filter(subCommand -> subCommand.toLowerCase().startsWith(strings[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return this.handleTabComplete(commandSender, strings);
    }

    /* the getter and setter of the class */

    public String getNoPermissionMessage() {
        return "you need to overwrite this method to change it";
    }

    public List<AbstractSubCommand> getSubCommands() {
        return subCommands;
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

        String identifier();

        String permission() default "";

        Class<? extends AbstractSubCommand>[] subCommands() default {};

    }
}

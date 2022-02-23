package de.fabilucius.sympel.command;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin; 

public class SympelCommand extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage("§7The module §9sympel-command §7is now loaded and can be referenced.");
        Bukkit.getConsoleSender().sendMessage("§7To learn more about it you can visit '§8https://www.sypel-command.fabilucius.de/§r§7'.");
        Bukkit.getConsoleSender().sendMessage(" ");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage("§7The module §9sympel-command §7is not active anymore, an thus cannot be referenced anymore.");
        Bukkit.getConsoleSender().sendMessage(" ");
    }
}

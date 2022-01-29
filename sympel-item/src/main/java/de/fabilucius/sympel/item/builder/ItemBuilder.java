package de.fabilucius.sympel.item.builder;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public interface ItemBuilder {

    ItemMeta getItemMeta();

    ItemStack getItemStack();

    /**
     * This method will combine the ItemMeta and the ItemStack to finally form the ItemStack and make it able to be "exported"
     * for use.
     *
     * @return the final build itemStack based on the use of the builder
     */
    ItemStack build();

}

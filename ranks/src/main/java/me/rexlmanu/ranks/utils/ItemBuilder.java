/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2019.
 */
package me.rexlmanu.ranks.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

/******************************************************************************************
 *    Urheberrechtshinweis                                                                *
 *    Copyright © Emmanuel Lampe 2019                                                  *
 *    Erstellt: 29.01.2019 / 00:09                                               *
 *                                                                                        *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    *
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,            *
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                                        *
 *                                                                                        * 
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,                 *
 *    öffentlichen Zugänglichmachung oder andere Nutzung                                  *
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.             *
 ******************************************************************************************/

public final class ItemBuilder {

    private ItemStack itemStack;

    private ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    private ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
    }

    private ItemBuilder(Material material, int amount, short durability) {
        this.itemStack = new ItemStack(material, amount, durability);
    }

    public static ItemBuilder itemStack(Material material) {
        return new ItemBuilder(material);
    }

    public static ItemBuilder itemStack(Material material, int amount) {
        return new ItemBuilder(material, amount);
    }

    public static ItemBuilder itemStack(Material material, int amount, short durability) {
        return new ItemBuilder(material, amount, durability);
    }

    public ItemBuilder setDisplayName(String name) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setDurability(short durability) {
        this.itemStack.setDurability(durability);
        return this;
    }

    public ItemBuilder setOwner(String playerName) {
        SkullMeta itemMeta = (SkullMeta) this.itemStack.getItemMeta();
        itemMeta.setOwner(playerName);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(final String... lore) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setLore(Arrays.asList(lore));
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack build() {
        return this.itemStack;
    }

}

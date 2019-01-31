/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2019.
 */
package me.rexlmanu.ranks;

import lombok.Getter;
import me.rexlmanu.ranks.commands.RanksCommand;
import me.rexlmanu.ranks.commands.TablistCommand;
import me.rexlmanu.ranks.inventory.PermissionInventory;
import me.rexlmanu.ranks.listener.ChatListener;
import me.rexlmanu.ranks.scoreboard.Tablist;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/******************************************************************************************
 *    Urheberrechtshinweis                                                                *
 *    Copyright © Emmanuel Lampe 2019                                                  *
 *    Erstellt: 31.01.2019 / 16:18                                               *
 *                                                                                        *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    *
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,            *
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                                        *
 *                                                                                        * 
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,                 *
 *    öffentlichen Zugänglichmachung oder andere Nutzung                                  *
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.             *
 ******************************************************************************************/

public final class Ranks extends JavaPlugin {

    @Getter
    private static Ranks instance;

    @Getter
    private PermissionInventory permissionInventory;
    @Getter
    private Tablist tablist;

    @Override
    public void onEnable() {
        Ranks.instance = this;

        this.getCommand("ranks").setExecutor(new RanksCommand());
        this.getCommand("tablist").setExecutor(new TablistCommand());
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(this.permissionInventory = new PermissionInventory(), this);

        this.tablist = new Tablist();
    }
}

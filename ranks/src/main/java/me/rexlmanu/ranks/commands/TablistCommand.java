/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2019.
 */
package me.rexlmanu.ranks.commands;

import me.rexlmanu.ranks.Ranks;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/******************************************************************************************
 *    Urheberrechtshinweis                                                                *
 *    Copyright © Emmanuel Lampe 2019                                                  *
 *    Erstellt: 31.01.2019 / 17:43                                               *
 *                                                                                        *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    *
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,            *
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                                        *
 *                                                                                        * 
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,                 *
 *    öffentlichen Zugänglichmachung oder andere Nutzung                                  *
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.             *
 ******************************************************************************************/

public final class TablistCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (! (commandSender instanceof Player)) {
            commandSender.sendMessage("§cYou're not a player.");
            return false;
        }

        if (! commandSender.hasPermission("ranks.tablist")) {
            commandSender.sendMessage("§cYou dont have enough permissions.");
            return false;
        }

        Ranks.getInstance().getTablist().addToTablist();
        commandSender.sendMessage("§7The tablist was successful updated.");
        return true;
    }
}

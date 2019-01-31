/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2019.
 */
package me.rexlmanu.ranks.commands;

import me.rexlmanu.ranks.Ranks;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

/******************************************************************************************
 *    Urheberrechtshinweis                                                                *
 *    Copyright © Emmanuel Lampe 2019                                                  *
 *    Erstellt: 31.01.2019 / 16:19                                               *
 *                                                                                        *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    *
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,            *
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                                        *
 *                                                                                        * 
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,                 *
 *    öffentlichen Zugänglichmachung oder andere Nutzung                                  *
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.             *
 ******************************************************************************************/

public final class RanksCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (! (commandSender instanceof Player)) {
            commandSender.sendMessage("§cYou're not a player.");
            return false;
        }

        if (! commandSender.hasPermission("ranks.use")) {
            commandSender.sendMessage("§cYou dont have enough permissions.");
            return false;
        }

        if (strings.length != 1) {
            commandSender.sendMessage("§cPlease use /ranks <Playername>.");
            return false;
        }

        String targetPlayerName = strings[0];

        Player player = (Player) commandSender;
        player.setMetadata("targetPlayerName", new FixedMetadataValue(Ranks.getInstance(), targetPlayerName));
        Ranks.getInstance().getPermissionInventory().openPermissionMenu(player);
        return false;
    }

}

/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2019.
 */
package me.rexlmanu.testplugin.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Objects;

/******************************************************************************************
 *    Urheberrechtshinweis                                                                *
 *    Copyright © Emmanuel Lampe 2019                                                  *
 *    Erstellt: 31.01.2019 / 16:05                                               *
 *                                                                                        *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    *
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,            *
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                                        *
 *                                                                                        * 
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,                 *
 *    öffentlichen Zugänglichmachung oder andere Nutzung                                  *
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.             *
 ******************************************************************************************/

public final class FindCommand extends Command {

    public FindCommand() {
        super("find");
    }

    public void execute(CommandSender commandSender, String[] strings) {
        if (strings.length != 1) {
            commandSender.sendMessage(new TextComponent("§cPlease use /find <Playername>"));
            return;
        }
        String targetPlayerName = strings[0];
        ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(targetPlayerName);
        if (Objects.isNull(targetPlayer)) {
            commandSender.sendMessage(new TextComponent("§cThe player isnt online."));
            return;
        }
        commandSender.sendMessage(new TextComponent(String.format("§7The player §a%s §7is on server §a%s§8.",
                targetPlayer.getDisplayName(), targetPlayer.getServer().getInfo().getName())));
    }
}

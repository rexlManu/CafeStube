/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2019.
 */
package me.rexlmanu.ranks.scoreboard;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.bridge.event.bukkit.BukkitPlayerUpdateEvent;
import de.dytanic.cloudnet.lib.player.CloudPlayer;
import de.dytanic.cloudnet.lib.player.permission.PermissionGroup;
import me.rexlmanu.ranks.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Random;

/******************************************************************************************
 *    Urheberrechtshinweis                                                                *
 *    Copyright © Emmanuel Lampe 2019                                                  *
 *    Erstellt: 31.01.2019 / 17:37                                               *
 *                                                                                        *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    *
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,            *
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                                        *
 *                                                                                        * 
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,                 *
 *    öffentlichen Zugänglichmachung oder andere Nutzung                                  *
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.             *
 ******************************************************************************************/

public final class Tablist implements Listener {

    public Tablist() {
        Bukkit.getPluginManager().registerEvents(this, Ranks.getInstance());
    }

    @EventHandler
    public void handle(final PlayerJoinEvent event) {
        event.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        this.addToTablist();
    }

    public void addToTablist() {
        for (final Player all : Bukkit.getOnlinePlayers()) this.createScoreboard(all);
    }

    public void createScoreboard(final Player player) {
        final Random random = new Random();
        final Scoreboard scoreboard = player.getScoreboard() == null ? Bukkit.getScoreboardManager().getNewScoreboard() : player.getScoreboard();
        for (final Player all : Bukkit.getOnlinePlayers()) {
            final CloudPlayer cloudPlayer = CloudAPI.getInstance().getOnlinePlayer(all.getUniqueId());
            final PermissionGroup highestPermissionGroup = cloudPlayer.getPermissionEntity().getHighestPermissionGroup(CloudAPI.getInstance().getPermissionPool());
            int tagId = highestPermissionGroup.getTagId();

            final String teamName = tagId + "_" + this.shortName(all.getName()) + random.nextInt(9);
            if (scoreboard.getTeam(teamName) != null) scoreboard.getTeam(teamName).unregister();
            final Team team = scoreboard.registerNewTeam(teamName);
            team.addEntry(all.getName());
            team.setPrefix(highestPermissionGroup.getPrefix());
        }
        player.setScoreboard(scoreboard);
    }

    private String shortName(final String input) {
        if (input.length() > 10) return input.substring(0, 10);
        return input;
    }


    @EventHandler
    public void handle(final BukkitPlayerUpdateEvent event) {
        this.addToTablist();
    }
}

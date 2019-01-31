/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2019.
 */
package me.rexlmanu.ranks.listener;

import de.dytanic.cloudnet.lib.player.permission.PermissionGroup;
import me.rexlmanu.ranks.Ranks;
import me.rexlmanu.ranks.cloud.CloudManager;
import me.rexlmanu.ranks.inventory.PermissionInventory;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Map;

/******************************************************************************************
 *    Urheberrechtshinweis                                                                *
 *    Copyright © Emmanuel Lampe 2019                                                  *
 *    Erstellt: 31.01.2019 / 16:21                                               *
 *                                                                                        *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    *
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,            *
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                                        *
 *                                                                                        * 
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,                 *
 *    öffentlichen Zugänglichmachung oder andere Nutzung                                  *
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.             *
 ******************************************************************************************/

public final class ChatListener implements Listener {

    @EventHandler
    public void handle(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();

        PermissionGroup permissionGroup = CloudManager.getHighestPermissionGroupFromPlayer(player.getName());
        event.setFormat(permissionGroup.getDisplay() + player.getName() + " §8» §7" + event.getMessage().replace("%%", "%"));

        PermissionInventory permissionInventory = Ranks.getInstance().getPermissionInventory();
        final Map<Player, String> groupNameAction = permissionInventory.getGroupNameAction();
        if (! groupNameAction.containsKey(player)) return;
        String message = event.getMessage();
        if (groupNameAction.get(player).equals("none")) {
            event.setCancelled(true);
            if ("cancel".equals(message.toLowerCase())) {
                groupNameAction.remove(player);
                player.sendMessage("§cYou have successful canceled the action.");
                permissionInventory.openPermissionMenu(player);
                return;
            }
            if (! CloudManager.groupExist(message))
                player.sendMessage(String.format("§cThe group with the name %s could not get found.", message));
            else {
                groupNameAction.put(player, message);
                permissionInventory.playSound(player, Sound.ORB_PICKUP);
                permissionInventory.openAddGroupMenu(player);
            }
            ;

        }
    }

}

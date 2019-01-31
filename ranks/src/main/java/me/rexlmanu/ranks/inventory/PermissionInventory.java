/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2019.
 */
package me.rexlmanu.ranks.inventory;

import com.google.common.collect.Maps;
import de.dytanic.cloudnet.lib.player.OfflinePlayer;
import lombok.Getter;
import me.rexlmanu.ranks.Ranks;
import me.rexlmanu.ranks.cloud.CloudManager;
import me.rexlmanu.ranks.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/******************************************************************************************
 *    Urheberrechtshinweis                                                                *
 *    Copyright © Emmanuel Lampe 2019                                                  *
 *    Erstellt: 31.01.2019 / 16:23                                               *
 *                                                                                        *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    *
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,            *
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                                        *
 *                                                                                        * 
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,                 *
 *    öffentlichen Zugänglichmachung oder andere Nutzung                                  *
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.             *
 ******************************************************************************************/

public final class PermissionInventory implements Listener {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH.mm");

    @Getter
    private Map<Player, String> groupNameAction;

    public PermissionInventory() {
        Bukkit.getPluginManager().registerEvents(this, Ranks.getInstance());
        this.groupNameAction = Maps.newHashMap();
    }

    public void openPermissionMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, "§aPermissionEditor");
        String targetPlayerName = player.getMetadata("targetPlayerName").get(0).asString();
        inventory.addItem(
                ItemBuilder.itemStack(Material.SKULL_ITEM, 1, (short) 3)
                        .setOwner(targetPlayerName)
                        .setDisplayName(String.format("§8» §b%s", targetPlayerName))
                        .build(),
                ItemBuilder.itemStack(Material.DIAMOND_BLOCK)
                        .setDisplayName(String.format("§8» §aAdd group"))
                        .build(),
                ItemBuilder.itemStack(Material.REDSTONE_BLOCK)
                        .setDisplayName(String.format("§8» §aRemove group"))
                        .build()
        );
        player.openInventory(inventory);
    }

    public void openAddGroupMenu(Player player) {
        if (! this.groupNameAction.containsKey(player)) {
            player.sendMessage("§7Please type the name of the group in the chat.");
            player.sendMessage("§7Cancle the action with '§cCANCEL§7'.");
            this.playSound(player, Sound.LEVEL_UP);
            this.groupNameAction.put(player, "none");
            player.closeInventory();
            return;
        }
        if (this.groupNameAction.get(player).equals("none")) return;
        Inventory inventory = Bukkit.createInventory(null, 9, "§aAddGroup");
        inventory.addItem(
                ItemBuilder.itemStack(Material.GOLD_BLOCK).setDisplayName("§8» §67 days").build(),
                ItemBuilder.itemStack(Material.GOLD_BLOCK).setDisplayName("§8» §630 days").build(),
                ItemBuilder.itemStack(Material.GOLD_BLOCK).setDisplayName("§8» §690 days").build(),
                ItemBuilder.itemStack(Material.GOLD_BLOCK).setDisplayName("§8» §6180 days").build(),
                ItemBuilder.itemStack(Material.GOLD_BLOCK).setDisplayName("§8» §6360 days").build(),
                ItemBuilder.itemStack(Material.GOLD_BLOCK).setDisplayName("§8» §6Lifetime").build()
        );
        player.openInventory(inventory);
    }

    private void openRemoveGroupMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 18, "§aRemoveGroup");
        String targetPlayerName = player.getMetadata("targetPlayerName").get(0).asString();
        CloudManager.getOfflinePlayer(targetPlayerName).getPermissionEntity().getGroups().forEach(groupEntityData -> {
            inventory.addItem(ItemBuilder.itemStack(Material.PAPER).setDisplayName("§8» §b" + groupEntityData.getGroup()).setLore("§7Available until §a" + SIMPLE_DATE_FORMAT.format(new Date(groupEntityData.getTimeout()))).build());
        });
        player.openInventory(inventory);
    }

    @EventHandler
    public void handle(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (event.getCurrentItem() == null) return;
        if (! (event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        String displayName = event.getCurrentItem().getItemMeta().getDisplayName();
        String targetPlayerName = player.hasMetadata("targetPlayerName") && ! player.getMetadata("targetPlayerName").isEmpty() ? player.getMetadata("targetPlayerName").get(0).asString() : null;
        switch (event.getClickedInventory().getTitle()) {
            case "§aPermissionEditor":
                event.setCancelled(true);
                if ("§8» §aAdd group".equals(displayName)) {
                    this.playSound(player, Sound.CLICK);
                    this.openAddGroupMenu(player);
                    return;
                }
                if ("§8» §aRemove group".equals(displayName)) {
                    this.playSound(player, Sound.CLICK);
                    this.openRemoveGroupMenu(player);
                    return;
                }
                break;
            case "§aAddGroup":
                event.setCancelled(true);
                if (targetPlayerName == null) return;
                if (! displayName.startsWith("§8» §6")) return;
                String rawName = displayName.replace("§8» §6", "");
                int time = 0;
                if (rawName.endsWith("days")) time = Integer.parseInt(rawName.replace(" days", ""));
                if (rawName.equals("Lifetime")) time = - 1;
                String groupName = this.groupNameAction.get(player);
                OfflinePlayer offlinePlayer = CloudManager.getOfflinePlayer(targetPlayerName);
                if (Objects.isNull(offlinePlayer)) {
                    player.sendMessage("§cThis player could not be found on this network.");
                    return;
                }
                CloudManager.addGroupToPlayer(offlinePlayer, groupName, time);
                player.sendMessage(String.format("§7You have added the group §a%s §7to the player §a%s§8 %s§8.",
                        groupName, targetPlayerName,
                        time == - 1 ? "§7forever (§aLifetime§7)" : "§7for §a" + time + "§7 days"));
                player.closeInventory();
                this.playSound(player, Sound.LEVEL_UP);
                this.groupNameAction.remove(player);
                player.removeMetadata("targetPlayerName", Ranks.getInstance());
                Ranks.getInstance().getTablist().addToTablist();
                break;
            case "§aRemoveGroup":
                event.setCancelled(true);
                if (targetPlayerName == null) return;
                if (! displayName.startsWith("§8» §b")) return;
                String rawGroupName = displayName.replace("§8» §b", "");
                if (! CloudManager.groupExist(rawGroupName)) {
                    player.sendMessage("§cThis group dont exist.");
                    return;
                }
                OfflinePlayer targetOfflinePlayer = CloudManager.getOfflinePlayer(targetPlayerName);
                if (Objects.isNull(targetOfflinePlayer)) {
                    player.sendMessage("§cThis player could not be found on this network.");
                    return;
                }
                CloudManager.removeGroupFromPlayer(targetOfflinePlayer, rawGroupName);
                player.sendMessage(String.format("§7You have successful removed the group §a%s§7 from §a%s§7.", rawGroupName, targetPlayerName));
                this.playSound(player, Sound.LEVEL_UP);
                this.groupNameAction.remove(player);
                player.removeMetadata("targetPlayerName", Ranks.getInstance());
                player.closeInventory();
                Ranks.getInstance().getTablist().addToTablist();
                break;
        }
    }

    public void playSound(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 1f, 1f);
    }
}

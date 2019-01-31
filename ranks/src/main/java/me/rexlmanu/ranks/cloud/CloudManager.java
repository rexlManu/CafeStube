/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2019.
 */
package me.rexlmanu.ranks.cloud;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.lib.player.OfflinePlayer;
import de.dytanic.cloudnet.lib.player.permission.GroupEntityData;
import de.dytanic.cloudnet.lib.player.permission.PermissionGroup;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/******************************************************************************************
 *    Urheberrechtshinweis                                                                *
 *    Copyright © Emmanuel Lampe 2019                                                  *
 *    Erstellt: 31.01.2019 / 16:38                                               *
 *                                                                                        *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    *
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,            *
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                                        *
 *                                                                                        * 
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,                 *
 *    öffentlichen Zugänglichmachung oder andere Nutzung                                  *
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.             *
 ******************************************************************************************/

public final class CloudManager {

    public static boolean groupExist(final String groupName) {
        return Objects.nonNull(CloudManager.getGroupByName(groupName));
    }

    public static PermissionGroup getGroupByName(final String groupName) {
        return CloudAPI.getInstance().getPermissionGroup(groupName);
    }

    public static void addGroupToPlayer(final OfflinePlayer offlinePlayer, final String groupName, final int time) {
        offlinePlayer.getPermissionEntity().getGroups()
                .add(new GroupEntityData(groupName,
                        time == - 1 ? - 1 : System.currentTimeMillis() + TimeUnit.DAYS.toMillis(time)));
        CloudAPI.getInstance().updatePlayer(offlinePlayer);
    }

    public static void removeGroupFromPlayer(final OfflinePlayer offlinePlayer, final String groupName) {
        offlinePlayer.getPermissionEntity().getGroups()
                .remove(offlinePlayer.getPermissionEntity()
                        .getGroups().stream()
                        .filter(groupEntityData -> groupEntityData.getGroup().equals(groupName))
                        .findFirst().get());
        CloudAPI.getInstance().updatePlayer(offlinePlayer);
    }

    public static OfflinePlayer getOfflinePlayer(final String name) {
        return CloudAPI.getInstance().getOfflinePlayer(name);
    }

    public static PermissionGroup getHighestPermissionGroupFromPlayer(String name) {
        return getOfflinePlayer(name).getPermissionEntity().getHighestPermissionGroup(CloudAPI.getInstance().getPermissionPool());
    }

}

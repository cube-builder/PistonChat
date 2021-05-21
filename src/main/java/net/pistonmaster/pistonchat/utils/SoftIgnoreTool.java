package net.pistonmaster.pistonchat.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SoftIgnoreTool {
    private final HashMap<UUID, List<UUID>> map = new HashMap<>();

    public SoftReturn softIgnorePlayer(Player player, Player ignored) {
        indexPlayer(new UniqueSender(player));
        indexPlayer(new UniqueSender(ignored));

        List<UUID> list = map.get(player.getUniqueId());

        if (list.contains(ignored.getUniqueId())) {
            list.remove(ignored.getUniqueId());

            return SoftReturn.UNIGNORE;
        } else {
            list.add(ignored.getUniqueId());

            return SoftReturn.IGNORE;
        }
    }

    protected boolean isSoftIgnored(UniqueSender chatter, UniqueSender receiver) {
        indexPlayer(receiver);
        indexPlayer(chatter);

        return map.get(receiver.getUniqueId()).contains(chatter.getUniqueId());
    }

    protected List<OfflinePlayer> getSoftIgnoredPlayers(Player player) {
        indexPlayer(new UniqueSender(player));

        List<UUID> listUUID = map.get(player.getUniqueId());

        List<OfflinePlayer> returnedPlayers = new ArrayList<>();

        for (UUID uuid : listUUID) {
            returnedPlayers.add(Bukkit.getOfflinePlayer(uuid));
        }

        return returnedPlayers;
    }

    private void indexPlayer(UniqueSender player) {
        if (!map.containsKey(player.getUniqueId())) {
            map.put(player.getUniqueId(), new ArrayList<>());
        }
    }

    public enum SoftReturn {
        IGNORE, UNIGNORE
    }
}

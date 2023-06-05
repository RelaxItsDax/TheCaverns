package me.relaxitsdax.thecaverns.game.entities.livingentities.players;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {

    private static final Map<UUID, PlayerData> dataMap = new HashMap<>();

    public static void add(UUID uuid, PlayerData data) {
        if (!(dataMap.containsKey(uuid))) {
            dataMap.put(uuid, data);
        }
    }

    public static PlayerData get(UUID uuid) {
        if (!(dataMap.containsKey(uuid))) return null;

        return dataMap.get(uuid);
    }

    public static boolean contains(Player player) {
        return dataMap.containsKey(player.getUniqueId());
    }

    public static void printData() {

        for (UUID key : dataMap.keySet()) {

            System.out.println("Player: " + key + ", Data: " + dataMap.get(key));

        }

    }

}

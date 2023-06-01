package me.relaxitsdax.thecaverns.PlayerData;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Map;

public class DataManager {

    private static Map<Player, PlayerData> playerDataMap;

    public static void add(Player player, PlayerData data) {
        playerDataMap.put(player, data);
    }

    public static PlayerData get(Player player) {
        if (!(playerDataMap.containsKey(player))) return null;

        return playerDataMap.get(player);
    }

}

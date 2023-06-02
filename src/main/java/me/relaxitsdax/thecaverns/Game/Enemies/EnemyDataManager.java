package me.relaxitsdax.thecaverns.Game.Enemies;

import me.relaxitsdax.thecaverns.PlayerData.PlayerData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EnemyDataManager {

    private static final Map<UUID, EnemyData> dataMap = new HashMap<>();

    public static void add(UUID uuid, EnemyData data) {
        if (!(dataMap.containsKey(uuid))) {
            dataMap.put(uuid, data);
        }
    }

    public static EnemyData get(UUID uuid) {
        if (!(dataMap.containsKey(uuid))) return null;

        return dataMap.get(uuid);
    }

    public static boolean contains(Entity entity) {
        return dataMap.containsKey(entity.getUniqueId());
    }

    public static void printData() {

        for (UUID key : dataMap.keySet()) {

            System.out.println("Player: " + key + ", Data: " + dataMap.get(key));

        }

    }

}

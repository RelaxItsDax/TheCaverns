package me.relaxitsdax.thecaverns.Game.Entities;

import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityDataManager {

    private static final Map<UUID, EntityData> dataMap = new HashMap<>();

    public static void add(UUID uuid, EntityData data) {
        if (!(dataMap.containsKey(uuid))) {
            dataMap.put(uuid, data);
        }
    }

    public static EntityData get(UUID uuid) {
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

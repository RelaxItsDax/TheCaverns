package me.relaxitsdax.thecaverns.Game.entities.livingentities;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LivingEntityManager {

    private static final Map<UUID, LivingEntityData> dataMap = new HashMap<>();

    public static void add(UUID uuid, LivingEntityData data) {
        if (!(dataMap.containsKey(uuid))) {
            dataMap.put(uuid, data);
        }
    }

    public static LivingEntityData get(UUID uuid) {
        if (!(dataMap.containsKey(uuid))) return null;

        return dataMap.get(uuid);
    }

    public static boolean contains(UUID uuid) {
        return dataMap.containsKey(uuid);
    }

}

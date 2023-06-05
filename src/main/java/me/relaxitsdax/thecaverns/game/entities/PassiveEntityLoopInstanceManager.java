package me.relaxitsdax.thecaverns.game.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PassiveEntityLoopInstanceManager {

    private static final Map<UUID, PassiveEntityLoop> dataMap = new HashMap<>();

    public static void add(UUID uuid, PassiveEntityLoop loop) {
        dataMap.put(uuid, loop);
    }

    public static void remove(UUID uuid) {
        dataMap.remove(uuid);
    }

    public static boolean contains(UUID uuid) {
        return dataMap.containsKey(uuid);
    }
}

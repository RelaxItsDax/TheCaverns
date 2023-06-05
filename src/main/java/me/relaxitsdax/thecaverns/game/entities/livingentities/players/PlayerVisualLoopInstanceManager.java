package me.relaxitsdax.thecaverns.game.entities.livingentities.players;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerVisualLoopInstanceManager {

    private static final Map<UUID, PlayerVisualLoop> dataMap = new HashMap<>();

    public static void add(UUID uuid, PlayerVisualLoop loop) {
        dataMap.put(uuid, loop);
    }

    public static void remove(UUID uuid) {
        dataMap.remove(uuid);
    }

    public static boolean contains(UUID uuid) {
        return dataMap.containsKey(uuid);
    }

}

package me.relaxitsdax.thecaverns.game.guis;

import me.relaxitsdax.thecaverns.game.entities.livingentities.LivingEntityData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GUIHandlerManager {

    private static final Map<UUID, GUIHandler> map = new HashMap<>();

    public static void add(UUID uuid, GUIHandler handler) {
        if (!(map.containsKey(uuid))) {
            map.put(uuid, handler);
        }
    }

    public static GUIHandler get(UUID uuid) {
        return map.get(uuid);
    }

    public static void remove(UUID uuid) {
        map.remove(uuid);
    }

    public static boolean contains(UUID uuid) {
        return map.containsKey(uuid);
    }



}

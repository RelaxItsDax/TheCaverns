package me.relaxitsdax.thecaverns.game.abilities.players;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerAbilityExecutorManager {

    private static final Map<UUID, PlayerAbilityExecutor> abilityMap = new HashMap<>();

    public static void add(UUID uuid, PlayerAbilityExecutor executor) {
        abilityMap.put(uuid, executor);
    }

    public static PlayerAbilityExecutor get(UUID uuid) {
        return abilityMap.get(uuid);
    }

    public static void remove(UUID uuid) {
        abilityMap.remove(uuid);
    }

    public static boolean has(UUID uuid) {
        return abilityMap.containsKey(uuid);
    }
}

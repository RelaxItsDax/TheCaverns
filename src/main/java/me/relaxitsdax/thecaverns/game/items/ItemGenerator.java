package me.relaxitsdax.thecaverns.game.items;

import me.relaxitsdax.thecaverns.game.enums.*;
import me.relaxitsdax.thecaverns.util.Util;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemGenerator {

    public static CavernItem generateItemFromChest(Rarity chestRarity, String name) {
        Rarity itemRarity = randomRarityFromChest(chestRarity);

        Abilities rca = Abilities.getWeightedRandom();
        Abilities lca = Abilities.getWeightedRandom();
        Abilities srca = Abilities.getWeightedRandom();
        Abilities slca = Abilities.getWeightedRandom();



        int abilities = randomNumberOfAbilities(chestRarity);

        Rarity[] abilityRarities = new Rarity[2];
        for (int i = 0; i < 2; i++) {
            if (i <= abilities) {
                abilityRarities[i] = randomRarityFromChest(chestRarity);
            } else {
                abilityRarities[i] = null;
            }
        }




        int passives = randomNumberOfPassiveAbilities(chestRarity);
        System.out.println(passives);

        PassiveAbilities[] passiveAbilities = {null, null, null, null, null};
        for (int i = 0; i < passives; i++) {
            passiveAbilities[i] = PassiveAbilities.getWeightedRandom();
        }
        while (!(Util.distinctValues(Arrays.asList(passiveAbilities)))) {
            for (int i = 0; i < passives; i++) {
                passiveAbilities[i] = PassiveAbilities.getWeightedRandom();
            }
        }

        Rarity[] passivesRarities = new Rarity[5];
        for (int i = 0; i < 5; i++) {
            if (i <= passives) passivesRarities[i] = randomRarityFromChest(chestRarity);
            else passivesRarities[i] = null;
        }

        Map<ItemStatBonuses, Double> bonusMap = new HashMap<>();
        bonusMap.put(ItemStatBonuses.DAMAGE, (double) Util.randIntInclusive(20, 30));



        return new CavernItem(UUID.randomUUID(), Material.IRON_SWORD, name, new StatBonuses(bonusMap), itemRarity, abilities >= 1 ? rca : null, null, abilities >= 2 ? rca : null, null, passiveAbilities, abilityRarities[0], null, abilityRarities[1], null, passivesRarities);
    }

    public static CavernItem generateItemFromChest(Rarity chestRarity) {
        return generateItemFromChest(chestRarity, randomName());
    }

    public static String randomName() {
        return "hi";
    }

    public static Rarity randomRarityFromChest(Rarity chestRarity) {
        int r = chestRarity.getNumber();
        int[] favors = ChestRarityFavor.fromRarity(chestRarity).getRarityFavorArray();
        int num = Util.randIntInclusive(0, 1000);
        int target = 0;
        for (int i = 0; i < 8; i++) {
            target += favors[i];
            if (num <= target) {
                return Rarity.getFromNumber(i + 1);
            }
        }
        return null;
    }

    public static int randomNumberOfAbilities(Rarity chestRarity) {
        int[] favors = ChestRarityFavor.fromRarity(chestRarity).getAbilityCountFavorArray();
        int num = Util.randIntInclusive(0, 1000);
        int target = 0;
        for (int i = 0; i < 3; i++) {
            target += favors[i];
            if (num <= target) {
                return i;
            }
        }
        return 0;
    }

    public static int randomNumberOfPassiveAbilities(Rarity chestRarity) {
        int[] favors = ChestRarityFavor.fromRarity(chestRarity).getPassiveCountFavorArray();
        int num = Util.randIntInclusive(0, 1000);
        int target = 0;
        for (int i = 0; i < 6; i++) {
            target += favors[i];
            if (num < target) {
                return i;
            }
        }
        return 0;
    }

}

package me.relaxitsdax.thecaverns.game.enums;

public enum ChestRarityFavor {

    COMMON(Rarity.COMMON, new int[]{750, 200, 40, 9, 1, 0, 0, 0}, new int[]{900, 100, 0}, new int[]{900, 100, 0, 0, 0, 0}),
    UNCOMMON(Rarity.UNCOMMON, new int[]{200, 600, 150, 40, 10, 0, 0 ,0}, new int[]{800, 200, 0}, new int[]{600, 300, 100, 0, 0, 0}),
    RARE(Rarity.RARE, new int[]{50, 100, 645, 150, 50, 5, 0 ,0}, new int[]{500, 500, 0}, new int[]{300, 600, 200, 0, 0, 0}),
    EPIC(Rarity.EPIC, new int[]{0, 40, 100, 650, 190, 20, 0, 0}, new int[]{300, 600, 100}, new int[]{100, 400, 400, 100, 0, 0}),
    LEGENDARY(Rarity.LEGENDARY, new int[]{0, 0, 15, 250, 700, 30, 5 ,0}, new int[]{200, 600, 200}, new int[]{0, 100, 500, 300, 100, 0}),
    MYTHIC(Rarity.MYTHIC, new int[]{0, 0, 0, 0, 180, 800, 20, 0}, new int[]{200, 500, 300}, new int[]{0, 0, 100, 500, 300, 100}),
    FABLED(Rarity.FABLED, new int[]{0, 0, 0, 0, 0, 300, 600, 100}, new int[]{100, 500, 400}, new int[]{0, 0, 0, 200, 600, 200}),
    DIVINE(Rarity.DIVINE, new int[]{0, 0, 0, 0, 0, 100, 300, 600}, new int[]{100, 400, 500}, new int[]{0, 0, 0, 100, 500, 400}),
    ;

    private final Rarity rarity;
    private final int[] rarityFavorArray; //Chance for {Common, Uncommon, Rare, Epic, Legendary, Mythic, Fabled, Divine} Rarity to be selected
    private final int[] abilityCountFavorArray; //Chance for an item to have {0, 1, 2} abilities based on the chest
    private final int[] passiveCountFavorArray; //Chance for an item to have {0, 1, 2, 3, 4, 5} passive abilities based on the chest
    ChestRarityFavor(Rarity rarity, int[] favorArray, int[] abilityCountFavorArray, int[] passiveCountFavorArray) {
        this.rarity = rarity;
        this.rarityFavorArray = favorArray;
        this.abilityCountFavorArray = abilityCountFavorArray;
        this.passiveCountFavorArray = passiveCountFavorArray;
    }

    public static ChestRarityFavor fromRarity(Rarity rarity) {
        for (ChestRarityFavor r : ChestRarityFavor.values()) {
            if (r.getRarity() == rarity) return r;
        }
        return null;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public int[] getRarityFavorArray() {// 0 is common, 7 is divine
        return rarityFavorArray;
    }

    public int[] getAbilityCountFavorArray() {
        return abilityCountFavorArray;
    }

    public int[] getPassiveCountFavorArray() {
        return passiveCountFavorArray;
    }
}

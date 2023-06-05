package me.relaxitsdax.thecaverns.game.items;

import java.util.*;

public class StatBonuses {

    private final Map<ItemStatBonuses, Double> bonuses;

    public StatBonuses(Map<ItemStatBonuses, Double> bonuses) {
        this.bonuses = bonuses;
    }

    public Map<ItemStatBonuses, Double> getBonusesMap() {
        return bonuses;
    }

    public void add(ItemStatBonuses stat, double value) {
        bonuses.put(stat, value);
    }

    public void sort() {
        for (ItemStatBonuses bonus : bonuses.keySet()) {

        }
    }

    public List<String> toLore() {
        sort();

        List<String> list = new ArrayList<>();

        for (ItemStatBonuses bonus : bonuses.keySet()) {
            double num = bonuses.get(bonus);

            String str = bonus.getColor() + "+" + (int) num + " " + bonus.getName();
            list.add(str);
        }

        return list;
    }


}

package me.relaxitsdax.thecaverns.game.items;

import me.relaxitsdax.thecaverns.game.enums.ItemStatBonuses;

import java.util.*;

public class StatBonuses {

    private Map<ItemStatBonuses, Double> bonuses;

    public StatBonuses(Map<ItemStatBonuses, Double> bonuses) {
        this.bonuses = bonuses;
    }

    public Map<ItemStatBonuses, Double> getBonusesMap() {
        return bonuses;
    }

    public void add(ItemStatBonuses stat, double value) {
        bonuses.put(stat, value);
    }

    public double get(ItemStatBonuses bonus) {
        return bonuses.getOrDefault(bonus, 0.0);
    }

    public void sort() {
        Map<Integer, ItemStatBonuses> list = new HashMap<>();
        for (ItemStatBonuses bonus : bonuses.keySet()) {
            list.put(bonus.getPriority(), bonus);
        }
        Map<ItemStatBonuses, Double> sorted = new HashMap<>();
        for (ItemStatBonuses bonusInOrder : list.values()) {
            sorted.put(bonusInOrder, bonuses.get(bonusInOrder));
        }
        this.bonuses = sorted;
    }

    public List<String> toLore() {
        sort();

        List<String> list = new ArrayList<>();

        for (ItemStatBonuses bonus : bonuses.keySet()) {
            double num = bonuses.get(bonus);

            String str = bonus.getColor() + "+" + (int) num + " " + bonus.getName();
            list.add(str);
        }

        list.add("");

        return list;
    }

    public Collection<Double> values() {
        return this.bonuses.values();
    }

    public Collection<ItemStatBonuses> keySet() {
        return this.bonuses.keySet();
    }


}

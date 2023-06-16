package me.relaxitsdax.thecaverns.game.enums;

import org.bukkit.ChatColor;

public enum Rarity {
    COMMON("Common", ChatColor.GRAY, 1),
    UNCOMMON("Uncommon", ChatColor.GREEN, 2),
    RARE("Rare", ChatColor.BLUE, 3),
    EPIC("Epic", ChatColor.DARK_PURPLE, 4),
    LEGENDARY("Legendary", ChatColor.GOLD, 5),
    MYTHIC("Mythic", ChatColor.LIGHT_PURPLE, 6),
    FABLED("Fabled", ChatColor.AQUA, 7),
    DIVINE("Divine", ChatColor.RED, 8),
    ;

    private final String name;
    private final ChatColor color;
    private final int number;

    Rarity(String name, ChatColor color, int number) {
        this.name = name;
        this.color = color;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    public static Rarity getFromName(String name) {
        for (Rarity rarity : Rarity.values()) {
            if (rarity.getName().toLowerCase() == name.toLowerCase()) {
                return rarity;
            }
        }
        return null;
    }

    public static Rarity getFromColor(ChatColor color) {
        for (Rarity rarity : Rarity.values()) {
            if (rarity.getColor() == color) {
                return rarity;
            }
        }
        return null;
    }

    public static Rarity getFromNumber(int n) {
        for (Rarity rarity : Rarity.values()) {
            if (rarity.getNumber() == n) {
                return rarity;
            }
        }
        return null;
    }
}

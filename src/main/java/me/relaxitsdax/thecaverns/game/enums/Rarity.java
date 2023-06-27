package me.relaxitsdax.thecaverns.game.enums;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum Rarity {
    COMMON("Common", ChatColor.GRAY, Material.LIGHT_GRAY_STAINED_GLASS, 1),
    UNCOMMON("Uncommon", ChatColor.GREEN, Material.LIME_STAINED_GLASS, 2),
    RARE("Rare", ChatColor.BLUE, Material.BLUE_STAINED_GLASS, 3),
    EPIC("Epic", ChatColor.DARK_PURPLE, Material.PURPLE_STAINED_GLASS, 4),
    LEGENDARY("Legendary", ChatColor.GOLD, Material.ORANGE_STAINED_GLASS, 5),
    MYTHIC("Mythic", ChatColor.LIGHT_PURPLE, Material.MAGENTA_STAINED_GLASS, 6),
    FABLED("Fabled", ChatColor.AQUA, Material.LIGHT_BLUE_STAINED_GLASS, 7),
    DIVINE("Divine", ChatColor.RED, Material.RED_STAINED_GLASS, 8),
    ;

    private final String name;
    private final ChatColor color;
    private final Material glassRepresent;
    private final int number;

    Rarity(String name, ChatColor color, Material blockRepresent, int number) {
        this.name = name;
        this.color = color;
        this.glassRepresent = blockRepresent;
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

    public Material getGlassRepresent() {
        return glassRepresent;
    }

    public static Rarity getFromName(String name) {
        for (Rarity rarity : Rarity.values()) {
            if (rarity.getName().toLowerCase().equals(name.toLowerCase())) {
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

package me.relaxitsdax.thecaverns.game.items;

import org.bukkit.ChatColor;

public enum Rarity {
    COMMON("Common", ChatColor.GRAY),
    UNCOMMON("Uncommon", ChatColor.GREEN),
    RARE("Rare", ChatColor.BLUE),
    EPIC("Epic", ChatColor.DARK_PURPLE),
    LEGENDARY("Legendary", ChatColor.GOLD),
    MYTHIC("Mythic", ChatColor.LIGHT_PURPLE),
    FABLED("Fabled", ChatColor.AQUA),
    DIVINE("Divine", ChatColor.RED),
    ;

    private final String name;
    private final ChatColor color;

    Rarity(String name, ChatColor color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }
}

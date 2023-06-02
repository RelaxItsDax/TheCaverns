package me.relaxitsdax.thecaverns.PlayerData;

import org.bukkit.ChatColor;

public enum Stats {

    HEALTH("Health", "❤", ChatColor.RED),
    MAX_HEALTH("Max Health", "❤", ChatColor.RED),
    BARRIER("Barrier", "❤", ChatColor.GOLD),
    DEFENSE("Defense", "\uD83D\uDEE1", ChatColor.GREEN),
    DAMAGE("Damage", "", ChatColor.RED),
    MANA("Mana", "★", ChatColor.AQUA),
    MAX_MANA("Max Mana", "★", ChatColor.AQUA);

    private final String statName;
    private final String statIcon;
    private final ChatColor color;


    Stats(String statName, String statIcon, ChatColor color) {

        this.statName = statName;
        this.statIcon = statIcon;
        this.color = color;

    }

    public String getStatName() {
        return statName;
    }

    public String getStatIcon() {
        return statIcon;
    }

    public ChatColor getColor() {
        return color;
    }
}

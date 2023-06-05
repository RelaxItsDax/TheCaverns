package me.relaxitsdax.thecaverns.game.items;

import org.bukkit.ChatColor;

public enum ItemStatBonuses {

    DAMAGE("Damage", ChatColor.RED, "d", 1),
    DAMAGEPERCENT("Damage", ChatColor.RED, "% d", 2),
    MAXHEALTH("Max Health", ChatColor.RED, "h", 3),
    ;


    private final String name;
    private final ChatColor color;
    private final String icon;
    private final int priority;

    ItemStatBonuses(String name, ChatColor color, String icon, int priority) {
        this.name = name;
        this.color = color;
        this.icon = icon;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public String getIcon() {
        return icon;
    }

    public int getPriority() {
        return priority;
    }
}

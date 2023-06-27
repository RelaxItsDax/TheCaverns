package me.relaxitsdax.thecaverns.game.enums;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum ItemStatBonuses {

    DAMAGE("Damage", ChatColor.RED, "d", Material.RED_DYE, 0),
    MAXHEALTH("Max Health", ChatColor.RED, "h", Material.APPLE, 1),
    ;


    private final String name;
    private final ChatColor color;
    private final String icon;
    private final Material matIcon;
    private final int number;

    ItemStatBonuses(String name, ChatColor color, String icon, Material matIcon, int number) {
        this.name = name;
        this.color = color;
        this.icon = icon;
        this.matIcon = matIcon;
        this.number = number;
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

    public Material getMatIcon() {
        return matIcon;
    }

    public int getNumber() {
        return number;
    }



    public static ItemStatBonuses fromString(String str) {
        switch (str.toLowerCase()) {
            case "damage":
                return DAMAGE;
            case "maxhealth":
                return MAXHEALTH;
            default:
                return null;
        }

    }

    public static ItemStatBonuses fromNumber(int i) {
        switch (i) {
            case 0: return DAMAGE;
            case 1: return MAXHEALTH;
            default: return null;
        }
    }
}

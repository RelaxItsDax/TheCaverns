package me.relaxitsdax.thecaverns.game.abilities;

import me.relaxitsdax.thecaverns.game.entities.Stats;
import me.relaxitsdax.thecaverns.game.items.Rarity;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public enum Ability {
    HEAL("Heal", Rarity.EPIC, 50, 100, Arrays.asList(ChatColor.GRAY + "Heal for " + ChatColor.RED + "+10" + Stats.HEALTH.getStatIcon())),
    BARRIER("Barrier", Rarity.LEGENDARY, 20, 100, Arrays.asList(ChatColor.GRAY + "Gain " + ChatColor.GOLD + "+50 Barrier"));


    private final String name;
    private final Rarity rarity;
    private final double manaCost;
    private final int tickCooldown;
    private final List<String> lore;

    Ability(String name, Rarity rarity, double manaCost, int tickCooldown, List<String> lore) {
        this.name = name;
        this.rarity = rarity;
        this.manaCost = manaCost;
        this.tickCooldown = tickCooldown;
        this.lore = lore;
    }


    public void execute(UUID uuid) {
        new AbilityExecutor(uuid).executeNoCD(this);
    }

    public String getName() {
        return name;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public double getManaCost() {
        return manaCost;
    }

    public int getTickCooldown() {
        return tickCooldown;
    }

    public List<String> getLore() {
        return lore;
    }

}

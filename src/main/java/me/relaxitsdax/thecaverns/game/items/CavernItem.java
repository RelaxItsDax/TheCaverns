package me.relaxitsdax.thecaverns.game.items;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CavernItem {

    private Material material;
    private String name;
    private StatBonuses bonuses;
    private Rarity rarity;
    private ItemStack itemstack;

    public CavernItem(Material material, String name, StatBonuses bonuses, Rarity rarity) {
        this.material = material;
        this.name = name;
        this.bonuses = bonuses;
        this.rarity = rarity;

        toItemStack();
    }

    public void toItemStack() {
        this.itemstack = new ItemStack(material);
        ItemMeta meta = itemstack.getItemMeta();
        meta.setDisplayName(rarity.getColor() + name);
        meta.setLore();
    }

    public ItemMeta buildMeta() {

    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatBonuses getBonuses() {
        return bonuses;
    }

    public void setBonuses(StatBonuses bonuses) {
        this.bonuses = bonuses;
    }

}

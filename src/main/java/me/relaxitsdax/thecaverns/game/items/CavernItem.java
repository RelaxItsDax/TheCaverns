package me.relaxitsdax.thecaverns.game.items;

import me.relaxitsdax.thecaverns.TheCaverns;
import me.relaxitsdax.thecaverns.game.enums.CavernItemType;
import me.relaxitsdax.thecaverns.game.enums.Rarity;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class CavernItem {


    private UUID uuid;
    private Material material;
    private String name;
    private Rarity rarity;
    private CavernItemType type;
    private ItemStack itemstack;




    public CavernItem(UUID uuid, Material material, String name, Rarity rarity, CavernItemType type) {
        this.uuid = uuid;
        this.material = material;
        this.name = name;
        this.rarity = rarity;
        this.type = type;
        this.itemstack = toItemStack();
    }

    public CavernItem(Player player, int index) {
        this.itemstack = player.getInventory().getItem(index);
        this.material = itemstack.getType();

        ItemMeta meta = player.getInventory().getItem(index).getItemMeta();

        if (meta.getPersistentDataContainer().get(key("UUID"), new UUIDDataType()) != null) {

            this.uuid = (meta.getPersistentDataContainer().get(key("UUID"), new UUIDDataType()));
            this.rarity = (Rarity.valueOf(meta.getPersistentDataContainer().get(key("Rarity"), PersistentDataType.STRING).toUpperCase()));
            this.name = (meta.getPersistentDataContainer().get(key("Name"), PersistentDataType.STRING));

        }
    }

    public ItemStack toItemStack() {
        ItemStack item = new ItemStack(material);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(rarity.getColor() + name);
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);


        PersistentDataContainer container = meta.getPersistentDataContainer();



        UUID uuid = UUID.randomUUID();

        container.set(key("UUID"), new UUIDDataType(), uuid);

        container.set(key("Rarity"), PersistentDataType.STRING, this.rarity.toString());

        container.set(key("Name"), PersistentDataType.STRING, this.name);
        container.set(key("CavernItemType"), PersistentDataType.STRING, this.type.toString());

        item.setItemMeta(meta);

        return item;
    }



    public static boolean isCavernItem(ItemStack stack) {
        return (stack != null ? stack.getItemMeta().getPersistentDataContainer().has(key("UUID"), new UUIDDataType()) : false);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public CavernItemType getType() {
        return type;
    }

    public void setType(CavernItemType type) {
        this.type = type;
    }

    public ItemStack getItemStack() {
        return itemstack;
    }

    public void setItemStack(ItemStack stack) {
        this.itemstack = stack;
    }

    static NamespacedKey key(String str) {
        return new NamespacedKey(TheCaverns.getInstance(), str);
    }

}

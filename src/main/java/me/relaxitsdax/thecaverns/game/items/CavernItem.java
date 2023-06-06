package me.relaxitsdax.thecaverns.game.items;

import me.relaxitsdax.thecaverns.TheCaverns;
import me.relaxitsdax.thecaverns.game.abilities.Ability;
import me.relaxitsdax.thecaverns.game.abilities.PassiveAbility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EquipmentSlot;
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
    private StatBonuses bonuses;
    private Rarity rarity;
    private ItemStack itemstack;
    private Ability rightClickAbility;
    private Ability leftClickAbility;
    private Ability shiftRightClickAbility;
    private Ability shiftLeftClickAbility;
    private PassiveAbility[] passiveAbilities;



    public CavernItem(UUID uuid, Material material, String name, StatBonuses bonuses, Rarity rarity, Ability rightClickAbility, Ability leftClickAbility, Ability shiftRightClickAbility, Ability shiftLeftClickAbility, PassiveAbility[] passiveAbilities) {
        this.uuid = uuid;
        this.material = material;
        this.name = name;
        this.bonuses = bonuses;
        this.rarity = rarity;
        this.rightClickAbility = rightClickAbility;
        this.leftClickAbility = leftClickAbility;
        this.shiftRightClickAbility = shiftRightClickAbility;
        this.shiftLeftClickAbility = shiftLeftClickAbility;
        this.passiveAbilities = passiveAbilities;

        toItemStack();
    }

    public CavernItem(UUID uuid, Material material, String name, StatBonuses bonuses, Rarity rarity) {
        new CavernItem(uuid, material, name, bonuses, rarity, null, null, null, null, null);
    }

    public CavernItem(Player player, int index) { //For getting existing cavern items
        this.itemstack = player.getInventory().getItem(index);
        this.material = itemstack.getType();

        ItemMeta meta = player.getInventory().getItem(index).getItemMeta();

        if (meta.getPersistentDataContainer().get(key("uuid"), new UUIDDataType()) != null) {

            this.uuid = (meta.getPersistentDataContainer().get(key("uuid"), new UUIDDataType()));
            this.rarity = (Rarity.valueOf(meta.getPersistentDataContainer().get(key("rarity"), PersistentDataType.STRING).toUpperCase()));
            this.name = (meta.getPersistentDataContainer().get(key("name"), PersistentDataType.STRING));

            Map<ItemStatBonuses, Double> bonusMap = new HashMap<>();
            for (ItemStatBonuses bonus : ItemStatBonuses.values()) {
                if (meta.getPersistentDataContainer().has(key(bonus.toString().toLowerCase()), PersistentDataType.DOUBLE)) {
                    bonusMap.put(bonus, meta.getPersistentDataContainer().get(key(bonus.toString().toLowerCase()), PersistentDataType.DOUBLE));
                }
            }
            this.bonuses = new StatBonuses(bonusMap);

        }

    }



    public ItemStack toItemStack() {
        this.itemstack = new ItemStack(material);

        ItemMeta meta = itemstack.getItemMeta();
        meta.setDisplayName(rarity.getColor() + name);
        meta.setLore(this.buildLore());
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);


        PersistentDataContainer container = meta.getPersistentDataContainer();

        UUID uuid = UUID.randomUUID();

        container.set(key("uuid"), new UUIDDataType(), uuid);

        container.set(key("rarity"), PersistentDataType.STRING, this.rarity.toString());

        container.set(key("name"), PersistentDataType.STRING, this.name);

        container.set(key("rightclickability"), PersistentDataType.STRING, this.rightClickAbility.toString());
        container.set(key("leftclickability"), PersistentDataType.STRING, this.leftClickAbility.toString());
        container.set(key("shiftrightclickability"), PersistentDataType.STRING, this.shiftRightClickAbility.toString());
        container.set(key("shiftleftclickability"), PersistentDataType.STRING, this.shiftLeftClickAbility.toString());

        for (int i = 0; i < passiveAbilities.length; i++) {
            container.set(key("ability" + i), PersistentDataType.STRING, this.passiveAbilities[i].toString());
        }

        for (ItemStatBonuses bonus : this.bonuses.keySet()) {
            container.set(key(bonus.toString()), PersistentDataType.DOUBLE, bonuses.getBonusesMap().get(bonus));
        }

        this.itemstack.setItemMeta(meta);

        return this.itemstack;
    }

    public List<String> buildLore() {
        List<String> lore = new ArrayList<>();
        lore.addAll(this.bonuses.toLore());
        lore.add("");
        lore.add(rarity.getColor() + "" + ChatColor.BOLD + "" + rarity.getName());
        return lore;
    }

    public boolean isCavernItem() {
        return (this.itemstack.getItemMeta().getPersistentDataContainer().has(key("uuid"), new UUIDDataType()));
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

    public StatBonuses getBonuses() {
        return bonuses;
    }

    public void setBonuses(StatBonuses bonuses) {
        this.bonuses = bonuses;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public NamespacedKey key(String str) {
        return new NamespacedKey(TheCaverns.getInstance(), str);
    }

}

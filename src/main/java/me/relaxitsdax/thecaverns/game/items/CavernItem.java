package me.relaxitsdax.thecaverns.game.items;

import me.relaxitsdax.thecaverns.TheCaverns;
import me.relaxitsdax.thecaverns.game.abilities.Ability;
import me.relaxitsdax.thecaverns.game.abilities.PassiveAbility;
import org.bukkit.ChatColor;
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
    private StatBonuses bonuses;
    private Rarity rarity;
    private ItemStack itemstack;

    private Ability rightClickAbility;
    private Ability leftClickAbility;
    private Ability sneakRightClickAbility;
    private Ability sneakLeftClickAbility;
    private PassiveAbility[] passiveAbilities;




    public CavernItem(UUID uuid, Material material, String name, StatBonuses bonuses, Rarity rarity, Ability rightClickAbility, Ability leftClickAbility, Ability sneakRightClickAbility, Ability sneakLeftClickAbility, PassiveAbility[] passiveAbilities) {
        this.uuid = uuid;
        this.material = material;
        this.name = name;
        this.bonuses = bonuses;
        this.rarity = rarity;
        this.rightClickAbility = rightClickAbility;
        this.leftClickAbility = leftClickAbility;
        this.sneakRightClickAbility = sneakRightClickAbility;
        this.sneakLeftClickAbility = sneakLeftClickAbility;
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

        if (meta.getPersistentDataContainer().get(key("UUID"), new UUIDDataType()) != null) {

            this.uuid = (meta.getPersistentDataContainer().get(key("UUID"), new UUIDDataType()));
            this.rarity = (Rarity.valueOf(meta.getPersistentDataContainer().get(key("Rarity"), PersistentDataType.STRING).toUpperCase()));
            this.name = (meta.getPersistentDataContainer().get(key("Name"), PersistentDataType.STRING));




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
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);


        PersistentDataContainer container = meta.getPersistentDataContainer();



        UUID uuid = UUID.randomUUID();

        container.set(key("UUID"), new UUIDDataType(), uuid);

        container.set(key("Rarity"), PersistentDataType.STRING, this.rarity.toString());

        container.set(key("Name"), PersistentDataType.STRING, this.name);

        for (ItemStatBonuses bonus : this.bonuses.keySet()) {
            container.set(key(bonus.toString()), PersistentDataType.DOUBLE, bonuses.getBonusesMap().get(bonus));
        }

        container.set(key("RightClickAbility"), PersistentDataType.STRING, this.rightClickAbility != null ? this.rightClickAbility.toString().toUpperCase() : "null");
        container.set(key("LeftClickAbility"), PersistentDataType.STRING, this.leftClickAbility != null ? this.leftClickAbility.toString().toUpperCase() : "null");
        container.set(key("ShiftRightClickAbility"), PersistentDataType.STRING, this.sneakRightClickAbility != null ? this.sneakRightClickAbility.toString().toUpperCase() : "null");
        container.set(key("ShiftLeftClickAbility"), PersistentDataType.STRING, this.sneakLeftClickAbility != null ? this.sneakLeftClickAbility.toString().toUpperCase() : "null");

        if (passiveAbilities != null) {
            for (int i = 0; i < 5; i++) {
                container.set(key("PassiveAbility" + i), PersistentDataType.STRING, this.passiveAbilities[i] != null ? this.passiveAbilities[i].toString().toUpperCase() : "null");
            }
        } else {
            for (int i = 0; i < 5; i++) {
                container.set(key("PassiveAbility" + i), PersistentDataType.STRING, "null");
            }
        }

        meta.setLore(this.buildLore());
        this.itemstack.setItemMeta(meta);

        return this.itemstack;
    }

    public List<String> buildLore() {
        List<String> lore = new ArrayList<>(this.bonuses.toLore());


        if (rightClickAbility != null) {
            lore.add(ChatColor.YELLOW + "" + ChatColor.BOLD + "RIGHT CLICK ABILITY: " + rightClickAbility.getRarity().getColor() + "" + rightClickAbility.getName());
            lore.addAll(rightClickAbility.getLore());
            lore.add(ChatColor.AQUA + "(" + (int) rightClickAbility.getManaCost() + " Mana)");
            lore.add("");
        }

        if (sneakRightClickAbility != null) {
            lore.add(ChatColor.YELLOW + "" + ChatColor.BOLD + "SNEAK RIGHT CLICK ABILITY: " + sneakRightClickAbility.getRarity().getColor() + "" + sneakRightClickAbility.getName());
            lore.addAll(sneakRightClickAbility.getLore());
            lore.add(ChatColor.AQUA + "(" + (int) sneakRightClickAbility.getManaCost() + " Mana)");
            lore.add("");
        }

        if (leftClickAbility != null) {
            lore.add(ChatColor.YELLOW + "" + ChatColor.BOLD + "LEFT CLICK ABILITY: " + leftClickAbility.getRarity().getColor() + "" + leftClickAbility.getName());
            lore.addAll(leftClickAbility.getLore());
            lore.add(ChatColor.AQUA + "(" + (int) leftClickAbility.getManaCost() + " Mana)");
            lore.add("");
        }

        if (sneakLeftClickAbility != null) {
            lore.add(ChatColor.YELLOW + "" + ChatColor.BOLD + "SNEAK LEFT CLICK ABILITY: " + sneakLeftClickAbility.getRarity().getColor() + "" + sneakLeftClickAbility.getName());
            lore.addAll(sneakLeftClickAbility.getLore());
            lore.add(ChatColor.AQUA + "(" + (int) sneakLeftClickAbility.getManaCost() + " Mana)");
            lore.add("");
        }

        lore.add(rarity.getColor() + "" + ChatColor.BOLD + "" + rarity.getName());

        return lore;
    }

    public boolean isCavernItem() {
        return (this.itemstack.getItemMeta().getPersistentDataContainer().has(key("UUID"), new UUIDDataType()));
    }

    public static boolean isCavernItem(ItemStack stack) {
        return (stack != null ? stack.getItemMeta().getPersistentDataContainer().has(key("UUID"), new UUIDDataType()) : false);
    }

    public ItemStack getItemStack() {
        return itemstack;
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

    public Ability getRightClickAbility() {
        String ability = this.getItemStack().getItemMeta().getPersistentDataContainer().get(key("RightClickAbility"), PersistentDataType.STRING);
        return ability.equals("null") ? null : Ability.valueOf(ability);
    }


    public Ability getLeftClickAbility() {
        String ability = this.getItemStack().getItemMeta().getPersistentDataContainer().get(key("LeftClickAbility"), PersistentDataType.STRING);
        return ability.equals("null") ? null : Ability.valueOf(ability);
    }



    public Ability getSneakRightClickAbility() {
        String ability = this.getItemStack().getItemMeta().getPersistentDataContainer().get(key("ShiftRightClickAbility"), PersistentDataType.STRING);
        return ability.equals("null") ? null : Ability.valueOf(ability);
    }


    public Ability getSneakLeftClickAbility() {
        String ability = this.getItemStack().getItemMeta().getPersistentDataContainer().get(key("ShiftLeftClickAbility"), PersistentDataType.STRING);
        return ability.equals("null") ? null : Ability.valueOf(ability);
    }

    public boolean hasRightClickAbility() {
        return !this.getItemStack().getItemMeta().getPersistentDataContainer().get(key("RightClickAbility"), PersistentDataType.STRING).equals("null");
    }

    public boolean hasLeftClickAbility() {
        return !this.getItemStack().getItemMeta().getPersistentDataContainer().get(key("LeftClickAbility"), PersistentDataType.STRING).equals("null");
    }



    public boolean hasSneakRightClickAbility() {
        return !this.getItemStack().getItemMeta().getPersistentDataContainer().get(key("ShiftRightClickAbility"), PersistentDataType.STRING).equals("null");
    }


    public boolean hasSneakLeftClickAbility() {
        return !this.getItemStack().getItemMeta().getPersistentDataContainer().get(key("ShiftLeftClickAbility"), PersistentDataType.STRING).equals("null");
    }



    public PassiveAbility getPassiveAbility(int index) {
        String ability = this.getItemStack().getItemMeta().getPersistentDataContainer().get(key("PassiveAbility" + index), PersistentDataType.STRING);
        return ability.equals("null") ? null : PassiveAbility.valueOf(ability);
    }

    public boolean hasPassiveAbility(int index) {
        return !this.getItemStack().getItemMeta().getPersistentDataContainer().get(key("PassiveAbility" + index), PersistentDataType.STRING).equals("null");
    }


    public static NamespacedKey key(String str) {
        return new NamespacedKey(TheCaverns.getInstance(), str);
    }

}

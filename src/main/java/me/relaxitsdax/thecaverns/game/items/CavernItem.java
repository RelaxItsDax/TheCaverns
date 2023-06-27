package me.relaxitsdax.thecaverns.game.items;

import me.relaxitsdax.thecaverns.TheCaverns;
import me.relaxitsdax.thecaverns.game.enums.Abilities;
import me.relaxitsdax.thecaverns.game.enums.ItemStatBonuses;
import me.relaxitsdax.thecaverns.game.enums.PassiveAbilities;
import me.relaxitsdax.thecaverns.game.enums.Rarity;
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

    private Abilities rightClickAbility;
    private Abilities leftClickAbility;
    private Abilities sneakRightClickAbility;
    private Abilities sneakLeftClickAbility;
    private PassiveAbilities[] passiveAbilities;
    private Rarity rcaRarity;
    private Rarity lcaRarity;
    private Rarity srcaRarity;
    private Rarity slcaRarity;
    private Rarity[] passiveRarities;



    public CavernItem(UUID uuid, Material material, String name, StatBonuses bonuses, Rarity rarity, Abilities rightClickAbility, Abilities leftClickAbility, Abilities sneakRightClickAbility, Abilities sneakLeftClickAbility, PassiveAbilities[] passiveAbilities, Rarity rcaRarity, Rarity lcaRarity, Rarity srcaRarity, Rarity slcaRarity, Rarity[] passiveRarities) {
        this.uuid = uuid;
        this.material = material;
        this.name = name;
        this.bonuses = bonuses;
        this.rarity = rarity;
        this.rightClickAbility = rightClickAbility;
        this.leftClickAbility = leftClickAbility;
        this.sneakRightClickAbility = sneakRightClickAbility;
        this.sneakLeftClickAbility = sneakLeftClickAbility;
        PassiveAbilities[] a = {null, null, null, null, null};
        this.passiveAbilities = passiveAbilities == null ? a : passiveAbilities;
        this.rcaRarity = rcaRarity;
        this.lcaRarity = lcaRarity;
        this.srcaRarity = srcaRarity;
        this.slcaRarity = slcaRarity;
        Rarity[] b = {null, null, null, null, null};
        this.passiveRarities = passiveRarities == null ? b : passiveRarities;

        toItemStack();
    }

    public CavernItem(UUID uuid, Material material, String name, StatBonuses bonuses, Rarity rarity) {
        this.uuid = uuid;
        this.material = material;
        this.name = name;
        this.bonuses = bonuses;
        this.rarity = rarity;
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

    public void give(Player player) {
        player.getInventory().addItem(toItemStack());
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

        container.set(key("RightClickAbilityRarity"), PersistentDataType.INTEGER, this.rcaRarity != null ? this.rcaRarity.getNumber() : 0);
        container.set(key("ShiftLeftClickAbilityRarity"), PersistentDataType.INTEGER, this.lcaRarity != null ? this.lcaRarity.getNumber() : 0);
        container.set(key("ShiftRightClickAbilityRarity"), PersistentDataType.INTEGER, this.srcaRarity != null ? this.srcaRarity.getNumber() : 0);
        container.set(key("ShiftLeftClickAbilityRarity"), PersistentDataType.INTEGER, this.slcaRarity != null ? this.slcaRarity.getNumber() : 0);

        if (passiveAbilities != null && passiveRarities != null) {
            for (int i = 0; i < 5; i++) {
                container.set(key("PassiveAbility" + i), PersistentDataType.STRING, this.passiveAbilities[i] != null ? this.passiveAbilities[i].toString().toUpperCase() : "null");
                container.set(key("PassiveAbilityRarity" + i), PersistentDataType.INTEGER, this.passiveRarities[i] != null ? this.passiveRarities[i].getNumber() : 0);
            }
        } else {
            for (int i = 0; i < 5; i++) {
                container.set(key("PassiveAbility" + i), PersistentDataType.STRING, "null");
                container.set(key("PassiveAbilityRarity" + i), PersistentDataType.INTEGER, 0);
            }
        }

        meta.setLore(this.buildLore());
        this.itemstack.setItemMeta(meta);

        return this.itemstack;
    }

    public List<String> buildLore() {
        List<String> lore = new ArrayList<>(this.bonuses.toLore());
        lore.add("");
        boolean hasAbilities = false;

        if (rightClickAbility != null) {
            lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Right Click: " + rcaRarity.getColor() + rightClickAbility.getName() + " (" + rcaRarity.getName() + ")");
            hasAbilities = true;
        }
        if (leftClickAbility != null) {
            lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Left Click: " + lcaRarity.getColor() + leftClickAbility.getName() + " (" + lcaRarity.getName() + ")");
            hasAbilities = true;
        }
        if (sneakRightClickAbility != null) {
            lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Shift Right Click: " + srcaRarity.getColor() + sneakRightClickAbility.getName() + " (" + srcaRarity.getName() + ")");
            hasAbilities = true;
        }
        if (sneakLeftClickAbility != null) {
            lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Shift Left Click: " + slcaRarity.getColor() + sneakLeftClickAbility.getName() + " (" + slcaRarity.getName() + ")");
            hasAbilities = true;
        }

        if (passiveAbilities != null && passiveAbilities[0] != null) {
            if (hasAbilities) lore.add("");
            lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Passive Abilities:");
            for (int i = 0; i < 5; i++) {
                if (passiveAbilities[i] != null) lore.add(passiveRarities[i].getColor() + "" + passiveAbilities[i].getName() + " (" + passiveRarities[i].getName() + ")");
            }
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

    public Abilities getRightClickAbility() {
        String ability = this.getItemStack().getItemMeta().getPersistentDataContainer().get(key("RightClickAbility"), PersistentDataType.STRING);
        return ability.equals("null") ? null : Abilities.valueOf(ability);
    }


    public Abilities getLeftClickAbility() {
        String ability = this.getItemStack().getItemMeta().getPersistentDataContainer().get(key("LeftClickAbility"), PersistentDataType.STRING);
        return ability.equals("null") ? null : Abilities.valueOf(ability);
    }


    public Abilities getSneakRightClickAbility() {
        String ability = this.getItemStack().getItemMeta().getPersistentDataContainer().get(key("ShiftRightClickAbility"), PersistentDataType.STRING);
        return ability.equals("null") ? null : Abilities.valueOf(ability);
    }


    public Abilities getSneakLeftClickAbility() {
        String ability = this.getItemStack().getItemMeta().getPersistentDataContainer().get(key("ShiftLeftClickAbility"), PersistentDataType.STRING);
        return ability.equals("null") ? null : Abilities.valueOf(ability);
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



    public PassiveAbilities getPassiveAbility(int index) {
        String ability = this.getItemStack().getItemMeta().getPersistentDataContainer().get(key("PassiveAbility" + index), PersistentDataType.STRING);
        return ability.equals("null") ? null : PassiveAbilities.valueOf(ability);
    }

    public boolean hasPassiveAbility(int index) {
        return !this.getItemStack().getItemMeta().getPersistentDataContainer().get(key("PassiveAbility" + index), PersistentDataType.STRING).equals("null");
    }

    public void setRightClickAbility(Abilities rightClickAbility) {
        this.rightClickAbility = rightClickAbility;
    }

    public void setLeftClickAbility(Abilities leftClickAbility) {
        this.leftClickAbility = leftClickAbility;
    }

    public void setSneakRightClickAbility(Abilities sneakRightClickAbility) {
        this.sneakRightClickAbility = sneakRightClickAbility;
    }

    public void setSneakLeftClickAbility(Abilities sneakLeftClickAbility) {
        this.sneakLeftClickAbility = sneakLeftClickAbility;
    }

    public PassiveAbilities[] getPassiveAbilities() {
        return passiveAbilities;
    }

    public void setPassiveAbilities(PassiveAbilities[] passiveAbilities) {
        this.passiveAbilities = passiveAbilities;
    }

    public void setRcaRarity(Rarity rcaRarity) {
        this.rcaRarity = rcaRarity;
    }

    public void setLcaRarity(Rarity lcaRarity) {
        this.lcaRarity = lcaRarity;
    }

    public void setSrcaRarity(Rarity srcaRarity) {
        this.srcaRarity = srcaRarity;
    }

    public void setSlcaRarity(Rarity slcaRarity) {
        this.slcaRarity = slcaRarity;
    }

    public void setPassiveRarities(Rarity[] passiveRarities) {
        this.passiveRarities = passiveRarities;
    }

    public Rarity getRightClickAbilityRarity() {
        return Rarity.getFromNumber(this.itemstack.getItemMeta().getPersistentDataContainer().get(key("RightClickAbilityRarity"), PersistentDataType.INTEGER));
    }

    public Rarity getLeftClickAbilityRarity() {
        return Rarity.getFromNumber(this.itemstack.getItemMeta().getPersistentDataContainer().get(key("LeftClickAbilityRarity"), PersistentDataType.INTEGER));
    }

    public Rarity getShiftRightClickAbilityRarity() {
        return Rarity.getFromNumber(this.itemstack.getItemMeta().getPersistentDataContainer().get(key("ShiftRightClickAbilityRarity"), PersistentDataType.INTEGER));
    }

    public Rarity getShiftLeftClickAbilityRarity() {
        return Rarity.getFromNumber(this.itemstack.getItemMeta().getPersistentDataContainer().get(key("ShiftLeftClickAbilityRarity"), PersistentDataType.INTEGER));
    }

    public Rarity getPassiveAbilityRarity(int index) {
        if (!(index < 5 && index >= 0)) return null;
        return Rarity.getFromNumber(this.itemstack.getItemMeta().getPersistentDataContainer().get(key("PassiveAbilityRarity" + index), PersistentDataType.INTEGER));
    }

    private static NamespacedKey key(String str) {
        return new NamespacedKey(TheCaverns.getInstance(), str);
    }

}

package me.relaxitsdax.thecaverns.game.items;

import me.relaxitsdax.thecaverns.game.enums.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class CavernWeapon extends CavernItem {

    private StatBonuses bonuses;
    private ActiveAbility rca;
    private Rarity rcaRarity;
    private ActiveAbility srca;
    private Rarity srcaRarity;

    private PassiveAbility[] passives;
    private Rarity[] passivesRarities;

    public CavernWeapon(UUID uuid, Material material, String name, Rarity rarity, CavernItemType type, StatBonuses bonuses, ActiveAbility rca, Rarity rcaRarity, ActiveAbility srca, Rarity srcaRarity, PassiveAbility[] passives, Rarity[] passivesRarities) {
        super(uuid, material, name, rarity, type);
        this.bonuses = bonuses;
        this.rca = rca;
        this.rcaRarity = rcaRarity;
        this.srca = srca;
        this.srcaRarity = srcaRarity;
        this.passives = passives;
        this.passivesRarities = passivesRarities;
        setItemStack(buildItemStack());
    }

    public CavernWeapon(UUID uuid, Material material, String name, Rarity rarity, CavernItemType type, StatBonuses bonuses) {
        super(uuid, material, name, rarity, type);
        this.bonuses = bonuses;
    }

    public CavernWeapon(Player player, int index) {
        super(player, index);


        ItemMeta meta = player.getInventory().getItem(index).getItemMeta();

        if (meta.getPersistentDataContainer().get(key("UUID"), new UUIDDataType()) != null) {

            setItemStack(player.getInventory().getItem(index));

            Map<ItemStatBonuses, Double> bonusMap = new HashMap<>();
            for (ItemStatBonuses bonus : ItemStatBonuses.values()) {
                if (meta.getPersistentDataContainer().has(key(bonus.toString().toLowerCase()), PersistentDataType.DOUBLE)) {
                    bonusMap.put(bonus, meta.getPersistentDataContainer().get(key(bonus.toString().toLowerCase()), PersistentDataType.DOUBLE));
                }
            }
            this.bonuses = new StatBonuses(bonusMap);
        }
    }

    public List<String> buildLore() {
        List<String> lore = new ArrayList<>(this.bonuses.toLore());
        lore.add("");
        boolean hasAbilities = false;

        if (rca != null) {
            lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Right Click: " + rcaRarity.getColor() + rca.getName() + " (" + rcaRarity.getName() + ")");
            hasAbilities = true;
        }
        if (srca != null) {
            lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Shift Right Click: " + srcaRarity.getColor() + srca.getName() + " (" + srcaRarity.getName() + ")");
            hasAbilities = true;
        }

        if (passives != null && passives[0] != null) {
            if (hasAbilities) lore.add("");
            lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Passive Abilities:");
            for (int i = 0; i < 5; i++) {
                if (passives[i] != null) lore.add(passivesRarities[i].getColor() + "" + passivesRarities[i].getName() + " (" + passivesRarities[i].getName() + ")");
            }
            lore.add("");
        }


        lore.add(getRarity().getColor() + "" + ChatColor.BOLD + "" + getRarity().getName());

        return lore;
    }

    public ItemStack buildItemStack() {
        ItemStack item = super.getItemStack();
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        meta.setDisplayName(getRarity().getColor() + getName());
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        for (ItemStatBonuses bonus : this.bonuses.keySet()) {
            container.set(key(bonus.toString()), PersistentDataType.DOUBLE, bonuses.getBonusesMap().get(bonus));
        }

        container.set(key("RightClickAbility"), PersistentDataType.STRING, this.rca != null ? this.rca.toString().toUpperCase() : "null");
        container.set(key("ShiftRightClickAbility"), PersistentDataType.STRING, this.srca != null ? this.srca.toString().toUpperCase() : "null");

        container.set(key("RightClickAbilityRarity"), PersistentDataType.INTEGER, this.rcaRarity != null ? this.rcaRarity.getNumber() : 0);
        container.set(key("ShiftRightClickAbilityRarity"), PersistentDataType.INTEGER, this.srcaRarity != null ? this.srcaRarity.getNumber() : 0);

        if (passives != null && passivesRarities != null) {
            for (int i = 0; i < 5; i++) {
                container.set(key("PassiveAbility" + i), PersistentDataType.STRING, this.passives[i] != null ? this.passives[i].toString().toUpperCase() : "null");
                container.set(key("PassiveAbilityRarity" + i), PersistentDataType.INTEGER, this.passivesRarities[i] != null ? this.passivesRarities[i].getNumber() : 0);
            }
        } else {
            for (int i = 0; i < 5; i++) {
                container.set(key("PassiveAbility" + i), PersistentDataType.STRING, "null");
                container.set(key("PassiveAbilityRarity" + i), PersistentDataType.INTEGER, 0);
            }
        }
        meta.setLore(buildLore());
        item.setItemMeta(meta);
        return item;
    }

    public static boolean isCavernWeapon(ItemStack stack) {
        if (stack != null) {
            if (stack.getItemMeta().getPersistentDataContainer().has(key("UUID"), new UUIDDataType())) {
                return (stack.getItemMeta().getPersistentDataContainer().get(key("CavernItemType"), PersistentDataType.STRING).equals(CavernItemType.WEAPON.toString()));
            }
        }
        return false;
    }

    public StatBonuses getBonuses() {
        return bonuses;
    }

    public void setBonuses(StatBonuses bonuses) {
        this.bonuses = bonuses;
    }

    public ActiveAbility getRca() {
        String ability = this.getItemStack().getItemMeta().getPersistentDataContainer().get(key("RightClickAbility"), PersistentDataType.STRING);
        return ability.equals("null") ? null : ActiveAbility.valueOf(ability);
    }

    public boolean hasRca() {
        return !this.getItemStack().getItemMeta().getPersistentDataContainer().get(key("RightClickAbility"), PersistentDataType.STRING).equals("null");
    }

    public Rarity getRcaRarity() {
        return Rarity.getFromNumber(getItemStack().getItemMeta().getPersistentDataContainer().get(key("RightClickAbilityRarity"), PersistentDataType.INTEGER));
    }


    public ActiveAbility getSrca() {
        String ability = this.getItemStack().getItemMeta().getPersistentDataContainer().get(key("ShiftRightClickAbility"), PersistentDataType.STRING);
        return ability.equals("null") ? null : ActiveAbility.valueOf(ability);
    }


    public boolean hasSrca() {
        return !this.getItemStack().getItemMeta().getPersistentDataContainer().get(key("ShiftRightClickAbility"), PersistentDataType.STRING).equals("null");
    }

    public Rarity getSrcaRarity() {
        return Rarity.getFromNumber(getItemStack().getItemMeta().getPersistentDataContainer().get(key("ShiftRightClickAbilityRarity"), PersistentDataType.INTEGER));
    }

    public PassiveAbility getPassiveAbility(int index) {
        String ability = this.getItemStack().getItemMeta().getPersistentDataContainer().get(key("PassiveAbility" + index), PersistentDataType.STRING);
        return ability.equals("null") ? null : PassiveAbility.valueOf(ability);
    }

    public Rarity getPassiveRarity(int index) {
        if (!(index < 5 && index >= 0)) return null;
        return Rarity.getFromNumber(getItemStack().getItemMeta().getPersistentDataContainer().get(key("PassiveAbilityRarity" + index), PersistentDataType.INTEGER));
    }

    public boolean hasPassiveAbility(int index) {
        return !this.getItemStack().getItemMeta().getPersistentDataContainer().get(key("PassiveAbility" + index), PersistentDataType.STRING).equals("null");
    }
}




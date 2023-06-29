package me.relaxitsdax.thecaverns.game.guis.types.createitem;

import me.relaxitsdax.thecaverns.game.enums.ActiveAbility;
import me.relaxitsdax.thecaverns.game.enums.CavernItemType;
import me.relaxitsdax.thecaverns.game.enums.PassiveAbility;
import me.relaxitsdax.thecaverns.game.enums.Rarity;
import me.relaxitsdax.thecaverns.game.guis.GUIHandler;
import me.relaxitsdax.thecaverns.game.guis.GUIHandlerManager;
import me.relaxitsdax.thecaverns.game.guis.types.GUI;
import me.relaxitsdax.thecaverns.game.guis.types.InventoryGUI;
import me.relaxitsdax.thecaverns.game.items.CavernWeapon;
import me.relaxitsdax.thecaverns.game.items.StatBonuses;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateItemInventoryGUI extends InventoryGUI implements Listener {

    private Inventory inv;
    private Rarity rarity;
    private String name;
    private StatBonuses bonuses;
    private ActiveAbility rca;

    private Rarity rcaRar;
    private ActiveAbility srca;
    private Rarity srcaRar;
    private PassiveAbility[] passives;
    private Rarity[] passivesRar;

    public CreateItemInventoryGUI(String name) {
        this.inv = Bukkit.createInventory(null, 27, "Create Item");
        this.rarity = null;
        this.name = name;
        this.bonuses = StatBonuses.empty();
        this.rca = null;
        this.rcaRar = null;
        this.srca = null;
        this.srcaRar = null;
        this.passives = new PassiveAbility[] {null, null, null, null, null};
        this.passivesRar = new Rarity[] {null, null, null, null, null};
    }

    public CreateItemInventoryGUI() {
    }

    public Inventory getInventory() {

        this.inv = Bukkit.createInventory(null, 27, "Create Item");

        setBackground(inv);

        ItemStack setRarityBtn = new ItemStack(this.rarity == null ? Material.GLASS : this.rarity.getGlassRepresent());
        ItemMeta rarityMeta = setRarityBtn.getItemMeta();
        rarityMeta.setDisplayName(this.rarity == null ? ChatColor.WHITE + "Choose Rarity" : this.rarity.getColor() + this.rarity.getName());
        List<String> rarityLore = new ArrayList<>();
        rarityLore.add(ChatColor.YELLOW + "Click to choose Rarity");
        rarityMeta.setLore(rarityLore);
        setRarityBtn.setItemMeta(rarityMeta);

        ItemStack setBonusesBtn = new ItemStack(Material.IRON_INGOT);
        ItemMeta bonusMeta = setBonusesBtn.getItemMeta();
        bonusMeta.setDisplayName(ChatColor.RED + "Edit Stat Bonuses");
        List<String> a = new ArrayList<>();
        a.add(ChatColor.YELLOW + "Click to edit bonuses!");
        if (bonuses.keySet().size() > 0) {
            a.add("");
            a.addAll(bonuses.toLore());
        }
        bonusMeta.setLore(a);
        setBonusesBtn.setItemMeta(bonusMeta);

        ItemStack setAbilitiesBtn = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta abilitiesMeta = setAbilitiesBtn.getItemMeta();
        abilitiesMeta.setDisplayName(ChatColor.RED + "Edit Active Abilities");
        List<String> b = new ArrayList<>();
        b.add(ChatColor.YELLOW + "Click to edit abilities!");
        if (rca != null || srca != null) b.add("");
        if (rca != null) b.add(ChatColor.YELLOW + "Right Click: " + (rcaRar == null ? ChatColor.WHITE : rcaRar.getColor()) + rca.getName());
        if (srca != null) b.add(ChatColor.YELLOW + "Sneak Right Click: " + (srcaRar == null ? ChatColor.WHITE : srcaRar.getColor()) + srca.getName());
        abilitiesMeta.setLore(b);
        setAbilitiesBtn.setItemMeta(abilitiesMeta);

        ItemStack setPassivesBtn = new ItemStack(Material.REDSTONE);
        ItemMeta passivesMeta = setPassivesBtn.getItemMeta();
        passivesMeta.setDisplayName(ChatColor.RED + "Edit Passive Abilities");
        List<String> c = new ArrayList<>();
        c.add(ChatColor.YELLOW + "Click to edit abilities!");
        if (passives[0] != null) {
            c.add("");
            for (int i = 0; i < 5; i++) {
                if (passives[i] == null) break;
                c.add(passivesRar[i].getColor() + passives[i].getName());
            }
        }
        passivesMeta.setLore(c);
        setPassivesBtn.setItemMeta(passivesMeta);

        ItemStack buildItemBtn = new ItemStack(Material.IRON_SWORD);
        ItemMeta buildMeta = buildItemBtn.getItemMeta();
        buildMeta.setDisplayName(ChatColor.YELLOW + "Build Item!");
        buildMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        buildItemBtn.setItemMeta(buildMeta);


        inv.setItem(10, setRarityBtn);
        inv.setItem(11, setBonusesBtn);
        inv.setItem(12, setAbilitiesBtn);
        inv.setItem(13, setPassivesBtn);
        inv.setItem(16, buildItemBtn);
        return inv;
    }

    @Override
    public void open(Player player) {

        this.inv = Bukkit.createInventory(null, 27, "Create Item");
        GUI current = GUIHandlerManager.get(player.getUniqueId()).getCurrentGUI();
        if (current instanceof CreateItemInventoryGUI) {
            CreateItemInventoryGUI gui = (CreateItemInventoryGUI) current;
            this.bonuses = gui.getBonuses();
            this.rarity = gui.getRarity();
        }
        player.openInventory(getInventory());

        GUIHandler handler = GUIHandlerManager.get(player.getUniqueId());
        if (!(handler.getCurrentGUI() instanceof CreateItemInventoryGUI)) {
            handler.setCurrent(this);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase("Create Item")) {
            event.setCancelled(true);

            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || clicked.getType().isAir()) return;
            Player player = (Player) event.getWhoClicked();

            if (event.getRawSlot() == 10) {
                new SetItemRarityInventoryGUI().open(player);
            } else if (event.getRawSlot() == 11) {
                new SetItemBonusesInventoryGUI().open(player);
            } else if (event.getRawSlot() == 12) {
                new SetItemAbilitiesInventoryGUI().open(player);
            } else if (event.getRawSlot() == 13) {
                new SetItemPassiveAbilitiesInventoryGUI().open(player);
            } else if (event.getRawSlot() == 16) {
                GUI current = GUIHandlerManager.get(player.getUniqueId()).getCurrentGUI();
                if (current instanceof CreateItemInventoryGUI) {
                    CreateItemInventoryGUI gui = (CreateItemInventoryGUI) current;
                    rarity = gui.getRarity();
                    bonuses = gui.getBonuses();
                    rca = gui.getRca();
                    rcaRar = gui.getRcaRar();
                    srca = gui.getSrca();
                    srcaRar = gui.getSrcaRar();
                    passives = gui.getPassives();
                    passivesRar = gui.getPassivesRar();
                    if (bonuses != null && rarity != null) {
                        CavernWeapon item = new CavernWeapon(UUID.randomUUID(), Material.IRON_SWORD, "Custom Weapon", rarity, CavernItemType.WEAPON, bonuses, rca, rcaRar, srca, srcaRar, passives, passivesRar);
                        player.getInventory().addItem(item.buildItemStack());
                        player.sendMessage(ChatColor.YELLOW + "Created Cavern Item!");
                    } else {
                        player.sendMessage(ChatColor.RED + "Please include a Rarity and Bonuses!");
                    }
                }
            }

        }
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
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

    public ActiveAbility getRca() {
        return rca;
    }

    public void setRca(ActiveAbility rca) {
        this.rca = rca;
    }

    public Rarity getRcaRar() {
        return rcaRar;
    }

    public void setRcaRar(Rarity rcaRar) {
        this.rcaRar = rcaRar;
    }

    public ActiveAbility getSrca() {
        return srca;
    }

    public void setSrca(ActiveAbility srca) {
        this.srca = srca;
    }

    public Rarity getSrcaRar() {
        return srcaRar;
    }

    public void setSrcaRar(Rarity srcaRar) {
        this.srcaRar = srcaRar;
    }

    public PassiveAbility[] getPassives() {
        return passives;
    }

    public PassiveAbility getPassive(int index) {
        return (index >= 0 && index <= 5) ? this.passives[index] : null;
    }

    public void setPassives(PassiveAbility[] passives) {
        this.passives = passives;
    }

    public void setPassive(PassiveAbility ability, int index) {
        if (index >= 0 && index <= 5) this.passives[index] = ability;
    }

    public Rarity[] getPassivesRar() {
        return passivesRar;
    }

    public Rarity getPassiveRarity(int index) {
        return (index >= 0 && index <= 5) ? this.passivesRar[index] : null;
    }

    public void setPassivesRar(Rarity[] passivesRar) {
        this.passivesRar = passivesRar;
    }

    public void setPassiveRarity(Rarity rarity, int index) {
        if (index >= 0 && index <= 5) this.passivesRar[index] = rarity;
    }
}

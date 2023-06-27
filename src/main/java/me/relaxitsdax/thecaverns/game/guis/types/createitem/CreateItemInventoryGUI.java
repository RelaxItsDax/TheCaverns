package me.relaxitsdax.thecaverns.game.guis.types.createitem;

import me.relaxitsdax.thecaverns.game.enums.Abilities;
import me.relaxitsdax.thecaverns.game.enums.PassiveAbilities;
import me.relaxitsdax.thecaverns.game.enums.Rarity;
import me.relaxitsdax.thecaverns.game.guis.GUIHandler;
import me.relaxitsdax.thecaverns.game.guis.GUIHandlerManager;
import me.relaxitsdax.thecaverns.game.guis.types.GUI;
import me.relaxitsdax.thecaverns.game.guis.types.InventoryGUI;
import me.relaxitsdax.thecaverns.game.items.CavernItem;
import me.relaxitsdax.thecaverns.game.items.StatBonuses;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
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
    private Abilities rca;
    private Abilities srca;
    private Abilities lca;
    private Abilities slca;
    private PassiveAbilities[] passives;
    private Rarity rcaRar;
    private Rarity srcaRar;
    private Rarity lcaRar;
    private Rarity slcaRar;
    private Rarity[] passivesRar;

    public CreateItemInventoryGUI(Rarity itemRarity, String name, StatBonuses bonuses) {
        this.inv = Bukkit.createInventory(null, 27, "Create Item");
        this.rarity = itemRarity;
        this.name = name;
        this.bonuses = (bonuses == null ? StatBonuses.empty() : bonuses);
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
        rarityMeta.setDisplayName(ChatColor.RED + "Edit Stat Bonuses");
        List<String> a = new ArrayList<>();
        a.add(ChatColor.YELLOW + "Click to edit bonuses!");
        if (bonuses.keySet().size() > 0) {
            a.add("");
            a.addAll(bonuses.toLore());
        }
        bonusMeta.setLore(a);
        setBonusesBtn.setItemMeta(bonusMeta);

        ItemStack buildItemBtn = new ItemStack(Material.IRON_SWORD);
        ItemMeta buildMeta = buildItemBtn.getItemMeta();
        buildMeta.setDisplayName(ChatColor.YELLOW + "Build Item!");
        buildItemBtn.setItemMeta(buildMeta);


        inv.setItem(10, setRarityBtn);
        inv.setItem(11, setBonusesBtn);
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
                new SetRarityInventoryGUI().open(player);
            }

            if (event.getRawSlot() == 11) {
                new SetBonusesInventoryGUI().open(player);
            }

            if (event.getRawSlot() == 16) {
                GUI current = GUIHandlerManager.get(player.getUniqueId()).getCurrentGUI();
                if (current instanceof CreateItemInventoryGUI) {
                    CreateItemInventoryGUI gui = (CreateItemInventoryGUI) current;
                    rarity = gui.getRarity();
                    bonuses = gui.getBonuses();
                    if (bonuses != null && rarity != null) {
                        CavernItem item = new CavernItem(UUID.randomUUID(), Material.IRON_SWORD, "Custom Weapon", bonuses, rarity, null, null, null, null, null, null, null, null, null, null);
                        player.getInventory().addItem(item.getItemStack());
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
}

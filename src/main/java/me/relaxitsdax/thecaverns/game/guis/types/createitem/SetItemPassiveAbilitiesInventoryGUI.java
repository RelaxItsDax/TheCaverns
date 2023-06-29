package me.relaxitsdax.thecaverns.game.guis.types.createitem;

import me.relaxitsdax.thecaverns.game.enums.ActiveAbility;
import me.relaxitsdax.thecaverns.game.enums.PassiveAbility;
import me.relaxitsdax.thecaverns.game.enums.Rarity;
import me.relaxitsdax.thecaverns.game.guis.GUIHandlerManager;
import me.relaxitsdax.thecaverns.game.guis.types.GUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SetItemPassiveAbilitiesInventoryGUI extends CreateItemInventoryGUI implements Listener {
    private final Inventory inv;

    public SetItemPassiveAbilitiesInventoryGUI() {
        this.inv = Bukkit.createInventory(null, 18, "Edit Passive Abilities");
    }
    public Inventory getInventory(Player player) {

        setBackground(inv);

        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatColor.RED + "Back");
        back.setItemMeta(backMeta);

        inv.setItem(13, back);

        GUI current = GUIHandlerManager.get(player.getUniqueId()).getCurrentGUI();
        if (current instanceof CreateItemInventoryGUI) {
            CreateItemInventoryGUI gui = (CreateItemInventoryGUI) current;

            PassiveAbility[] passives = gui.getPassives();
            Rarity[] rarities = gui.getPassivesRar();

            for (int i = 0; i < 5; i++) {
                ItemStack btn = new ItemStack(passives[i] == null ? Material.GLASS : passives[i].getIcon());
                ItemMeta meta = btn.getItemMeta();
                meta.setDisplayName((rarities[i] == null ? ChatColor.WHITE : rarities[i].getColor()) + (passives[i] == null ? "Click to choose Passive Ability" : "Passive Ability: " + passives[i].getName()));
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.YELLOW + "Left click to choose Passive Ability!");
                lore.add(ChatColor.YELLOW + "Right click to choose Rarity!");
                lore.add(rarities[i] == null ? ChatColor.RED + "No rarity set!" : rarities[i].getColor() + "Rarity: " + rarities[i].getName());
                meta.setLore(lore);
                btn.setItemMeta(meta);

                inv.setItem(i + 2, btn);
            }
        }

        return inv;
    }

    @Override
    public void open(Player player) {
        player.openInventory(getInventory(player));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase("Edit Passive Abilities")) {
            event.setCancelled(true);

            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || clicked.getType().isAir()) return;

            Player player = (Player) event.getWhoClicked();

            GUI current = GUIHandlerManager.get(player.getUniqueId()).getCurrentGUI();
            if (current instanceof CreateItemInventoryGUI) {

                CreateItemInventoryGUI gui = (CreateItemInventoryGUI) current;

                if (event.getRawSlot() == 13) {

                    boolean allow = true;
                    for (int i = 0; i < 5; i++) {
                        if ((gui.getPassive(i) == null) != (gui.getPassiveRarity(i) == null)) allow = false;
                    }
                    if (allow) {
                        gui.open(player);
                    } else {
                        player.sendMessage(ChatColor.RED + "All of your Passive Abilities need to have a Rarity assigned to them!");
                    }

                } else {
                    if (event.getRawSlot() >= 2 && event.getRawSlot() <= 6) {
                        if (event.getClick() == ClickType.LEFT) new SetItemPassiveAbilityInventoryGUI(event.getRawSlot() - 2).open(player);
                        if (event.getClick() == ClickType.RIGHT) new SetItemPassiveAbilityRarityInventoryGUI(event.getRawSlot() - 2).open(player);
                    }
                }
            }
        }
    }
}

package me.relaxitsdax.thecaverns.game.guis.types.createitem;

import me.relaxitsdax.thecaverns.game.enums.ItemStatBonuses;
import me.relaxitsdax.thecaverns.game.guis.GUIHandlerManager;
import me.relaxitsdax.thecaverns.game.guis.types.GUI;
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

public class SetItemBonusesInventoryGUI extends CreateItemInventoryGUI implements Listener {

    private final Inventory inv;


    public SetItemBonusesInventoryGUI() {
        this.inv = Bukkit.createInventory(null, 18, "Add Bonuses");
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

            int i = 0;
            for (ItemStatBonuses bonus : ItemStatBonuses.values()) {

                double num = gui.getBonuses().get(bonus);

                ItemStack bonusStack = new ItemStack(bonus.getMatIcon());
                ItemMeta bonusMeta = bonusStack.getItemMeta();
                bonusMeta.setDisplayName(bonus.getColor() + bonus.getName());

                List<String> lore = new ArrayList<>();
                lore.add(bonus.getColor() + "+" + (int) num + " " + bonus.getName());
                lore.add("");
                lore.add(ChatColor.GRAY + "Left click to add 1 or Right Click to subtract 1");
                lore.add(ChatColor.GRAY + "Shift click to add/subtract 10");

                bonusMeta.setLore(lore);
                bonusStack.setItemMeta(bonusMeta);
                inv.setItem(i, bonusStack);

                i++;
            }
        }

        return this.inv;


    }

    @Override
    public void open(Player player) {
        player.openInventory(getInventory(player));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase("Add Bonuses")) {
            event.setCancelled(true);

            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || clicked.getType().isAir()) return;
            Player player = (Player) event.getWhoClicked();

            GUI current = GUIHandlerManager.get(player.getUniqueId()).getCurrentGUI();
            if (current instanceof CreateItemInventoryGUI) {

                CreateItemInventoryGUI gui = (CreateItemInventoryGUI) current;

                if (event.getRawSlot() == 13) {

                    gui.open(player);

                } else if (event.getRawSlot() >= 0 && event.getRawSlot() <= 1) {
                    ItemStatBonuses bonus = ItemStatBonuses.fromNumber(event.getRawSlot());

                    double val = gui.getBonuses().get(bonus);

                    if (event.getClick().isLeftClick()) {
                        val += event.isShiftClick() ? 10 : 1;
                    } else if (event.getClick().isRightClick()) {
                        val -= event.isShiftClick() ? 10 : 1;
                    }

                    gui.getBonuses().add(bonus, val);

                    List<String> lore = new ArrayList<>();
                    lore.add(bonus.getColor() + "+" + (int) val + " " + bonus.getName());
                    lore.add("");
                    lore.add(ChatColor.GRAY + "Left click to add 1 or Right Click to subtract 1");
                    lore.add(ChatColor.GRAY + "Shift click to add/subtract 10");

                    ItemMeta meta = event.getCurrentItem().getItemMeta();
                    meta.setLore(lore);
                    event.getCurrentItem().setItemMeta(meta);
                }
            }



        }
    }

}

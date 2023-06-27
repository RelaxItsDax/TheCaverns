package me.relaxitsdax.thecaverns.game.guis.types.createitem;

import me.relaxitsdax.thecaverns.game.enums.Rarity;
import me.relaxitsdax.thecaverns.game.guis.GUIHandlerManager;
import me.relaxitsdax.thecaverns.game.guis.types.GUI;
import me.relaxitsdax.thecaverns.game.guis.types.InventoryGUI;
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

public class SetRarityInventoryGUI extends CreateItemInventoryGUI implements Listener {

    private final Inventory inv;


    public SetRarityInventoryGUI() {
        this.inv = Bukkit.createInventory(null, 9, "Set Rarity");
    }

    @Override
    public Inventory getInventory() {

        setBackground(inv);

        ItemStack none = new ItemStack(Material.GLASS);
        ItemMeta noneMeta = none.getItemMeta();
        noneMeta.setDisplayName(ChatColor.WHITE + "None");
        none.setItemMeta(noneMeta);
        inv.setItem(0, none);

        int i = 1;

        for (Rarity rarity : Rarity.values()) {
            ItemStack stack = new ItemStack(rarity.getGlassRepresent());
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(rarity.getColor() + rarity.getName());
            stack.setItemMeta(meta);
            inv.setItem(i, stack);
            i++;
        }

        return this.inv;
    }

    @Override
    public void open(Player player) {
        player.openInventory(getInventory());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase("Set Rarity")) {
            event.setCancelled(true);

            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || clicked.getType().isAir()) return;
            Player player = (Player) event.getWhoClicked();


            Rarity itemRarity = null;
            if (event.getRawSlot() != 0) {
                for (Rarity rarity : Rarity.values()) {
                    if (rarity.getNumber() == event.getRawSlot()) itemRarity = rarity;
                }
            }
            GUI current = GUIHandlerManager.get(player.getUniqueId()).getCurrentGUI();
            if (current instanceof CreateItemInventoryGUI) {
                CreateItemInventoryGUI gui = (CreateItemInventoryGUI) current;
                gui.setRarity(itemRarity);
                player.sendMessage(itemRarity.toString());
                gui.open(player);
            }
        }
    }

}

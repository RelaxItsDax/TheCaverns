package me.relaxitsdax.thecaverns.game.guis.types.createitem;

import me.relaxitsdax.thecaverns.game.enums.ActiveAbility;
import me.relaxitsdax.thecaverns.game.guis.GUIHandlerManager;
import me.relaxitsdax.thecaverns.game.guis.types.GUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetSrcaInventoryGUI extends SetItemAbilitiesInventoryGUI implements Listener {

    public SetSrcaInventoryGUI() {
    }

    @Override
    public Inventory getInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "Set Sneak Right Click Ability");

        setBackground(inv);

        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatColor.RED + "Back");
        back.setItemMeta(backMeta);

        inv.setItem(22, back);

        GUI current = GUIHandlerManager.get(player.getUniqueId()).getCurrentGUI();
        if (current instanceof CreateItemInventoryGUI) {
            CreateItemInventoryGUI gui = (CreateItemInventoryGUI) current;

            int i = 0;
            for (ActiveAbility ability : ActiveAbility.values()) {
                ItemStack btn = new ItemStack(ability.getMaterialIcon());
                ItemMeta meta = btn.getItemMeta();
                meta.setDisplayName(ChatColor.YELLOW + ability.getName());
                if (gui.getSrca() != null) {
                    if (gui.getSrca() == ability) {
                        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                        meta.setDisplayName(ChatColor.YELLOW + "Selected: " + ability.getName());
                    }
                }
                btn.setItemMeta(meta);
                inv.setItem(i, btn);
                i++;
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
        if (event.getView().getTitle().equalsIgnoreCase("Set Sneak Right Click Ability")) {
            event.setCancelled(true);

            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || clicked.getType().isAir()) return;
            Player player = (Player) event.getWhoClicked();

            GUI current = GUIHandlerManager.get(player.getUniqueId()).getCurrentGUI();
            if (current instanceof CreateItemInventoryGUI) {
                CreateItemInventoryGUI gui = (CreateItemInventoryGUI) current;

                for (int i = 0; i < ActiveAbility.values().length; i++) {
                    if (event.getRawSlot() == i) {
                        gui.setSrca(ActiveAbility.values()[i]);
                        open(player);
                    }
                }

                if (event.getRawSlot() == 22) {
                    new SetItemAbilitiesInventoryGUI().open(player);
                }
            }
        }
    }

}

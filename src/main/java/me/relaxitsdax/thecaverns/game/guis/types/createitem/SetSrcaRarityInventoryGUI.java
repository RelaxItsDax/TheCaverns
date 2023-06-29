package me.relaxitsdax.thecaverns.game.guis.types.createitem;

import me.relaxitsdax.thecaverns.game.enums.Rarity;
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

public class SetSrcaRarityInventoryGUI extends SetItemAbilitiesInventoryGUI implements Listener {

    private final Inventory inv;

    public SetSrcaRarityInventoryGUI() {
        this.inv = Bukkit.createInventory(null, 18, "Set Sneak Right Click Ability Rarity");
    }

    public Inventory getInventory(Player player) {

        setBackground(inv);

        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatColor.RED + "Back");
        back.setItemMeta(backMeta);

        inv.setItem(13, back);

        int i = 0;

        GUI current = GUIHandlerManager.get(player.getUniqueId()).getCurrentGUI();
        if (current instanceof CreateItemInventoryGUI) {
            CreateItemInventoryGUI gui = (CreateItemInventoryGUI) current;


            for (Rarity rarity : Rarity.values()) {
                ItemStack stack = new ItemStack(rarity.getGlassRepresent());
                ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName(rarity.getColor() + rarity.getName());

                if (gui.getSrcaRar() == rarity) {
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                    meta.setDisplayName(rarity.getColor() + "Selected: " + rarity.getName() );
                }

                stack.setItemMeta(meta);
                inv.setItem(i, stack);
                i += i == 3 ? 2 : 1;
            }
        }
        return this.inv;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase("Set Sneak Right Click Ability Rarity")) {
            event.setCancelled(true);

            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || clicked.getType().isAir()) return;
            Player player = (Player) event.getWhoClicked();

            GUI current = GUIHandlerManager.get(player.getUniqueId()).getCurrentGUI();
            if (current instanceof CreateItemInventoryGUI) {
                CreateItemInventoryGUI gui = (CreateItemInventoryGUI) current;

                Rarity rarity = null;
                if (event.getRawSlot() < 9) {
                    for (Rarity r : Rarity.values()) {
                        int n = r.getNumber() - 1;
                        if ((n <= 3 ? n : n + 1) == event.getRawSlot()) rarity = r;
                    }
                    gui.setSrcaRar(rarity);
                    open(player);
                }

                if (event.getRawSlot() == 13) {
                    new SetItemAbilitiesInventoryGUI().open(player);
                }
            }
        }
    }
}

package me.relaxitsdax.thecaverns.game.guis.types.createitem;

import me.relaxitsdax.thecaverns.game.enums.ActiveAbility;
import me.relaxitsdax.thecaverns.game.enums.Rarity;
import me.relaxitsdax.thecaverns.game.guis.GUIHandlerManager;
import me.relaxitsdax.thecaverns.game.guis.types.GUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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

public class SetItemAbilitiesInventoryGUI extends CreateItemInventoryGUI implements Listener {

    private final Inventory inv;

    public SetItemAbilitiesInventoryGUI() {
        this.inv = Bukkit.createInventory(null, 27, "Edit Abilities");
    }
    public Inventory getInventory(Player player) {

        setBackground(inv);

        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatColor.RED + "Back");
        back.setItemMeta(backMeta);

        inv.setItem(22, back);

        GUI current = GUIHandlerManager.get(player.getUniqueId()).getCurrentGUI();
        if (current instanceof CreateItemInventoryGUI) {
            CreateItemInventoryGUI gui = (CreateItemInventoryGUI) current;

            ActiveAbility rca = gui.getRca();
            Rarity rcaR = gui.getRcaRar();
            ActiveAbility srca = gui.getSrca();
            Rarity srcaR = gui.getSrcaRar();

            ItemStack rcaBtn = new ItemStack(rca == null ? Material.GLASS : rca.getMaterialIcon());
            ItemMeta rcaMeta = rcaBtn.getItemMeta();
            rcaMeta.setDisplayName((rcaR == null ? ChatColor.WHITE : rcaR.getColor()) + (rca == null ? "Click to choose Right Click Ability" : "Right Click Ability: " + rca.getName()));
            List<String> rcaLore = new ArrayList<>();
            rcaLore.add(ChatColor.YELLOW + "Left click to choose Ability!");
            rcaLore.add(ChatColor.YELLOW + "Right click to choose Rarity!");
            rcaLore.add(rcaR == null ? ChatColor.RED + "Rarity not yet set!" : rcaR.getColor() + rcaR.getName());
            rcaMeta.setLore(rcaLore);
            rcaBtn.setItemMeta(rcaMeta);

            ItemStack srcaBtn = new ItemStack(srca == null ? Material.GLASS : srca.getMaterialIcon());
            ItemMeta srcaMeta = srcaBtn.getItemMeta();
            srcaMeta.setDisplayName((srcaR == null ? ChatColor.WHITE : srcaR.getColor()) + (srca == null ? "Click to choose Sneak Right Click Ability" : "Sneak Right Click Ability: " + srca.getName()));
            List<String> srcaLore = new ArrayList<>();
            srcaLore.add(ChatColor.YELLOW + "Left click to choose Ability!");
            srcaLore.add(ChatColor.YELLOW + "Right click to choose Rarity!");
            srcaLore.add(srcaR == null ? ChatColor.RED + "Rarity not yet set!" : srcaR.getColor() + srcaR.getName());
            srcaMeta.setLore(srcaLore);
            srcaBtn.setItemMeta(srcaMeta);

            inv.setItem(11, rcaBtn);
            inv.setItem(15, srcaBtn);
        }

        return inv;
    }

    @Override
    public void open(Player player) {
        player.openInventory(getInventory(player));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase("Edit Abilities")) {
            event.setCancelled(true);

            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || clicked.getType().isAir()) return;

            Player player = (Player) event.getWhoClicked();

            GUI current = GUIHandlerManager.get(player.getUniqueId()).getCurrentGUI();
            if (current instanceof CreateItemInventoryGUI) {

                CreateItemInventoryGUI gui = (CreateItemInventoryGUI) current;

                if (event.getRawSlot() == 22) {

                    gui.open(player);

                } else {
                    if (event.getRawSlot() == 11) {
                        if (event.getClick() == ClickType.LEFT) {
                            new SetRcaInventoryGUI().open(player);
                        } else if (event.getClick() == ClickType.RIGHT) {
                            new SetRcaRarityInventoryGUI().open(player);
                        }
                    } else if (event.getRawSlot() == 15) {
                        if (event.getClick() == ClickType.LEFT) {
                            new SetSrcaInventoryGUI().open(player);
                        } else if (event.getClick() == ClickType.RIGHT) {
                            new SetSrcaRarityInventoryGUI().open(player);
                        }
                    }
                }
            }
        }
    }
}

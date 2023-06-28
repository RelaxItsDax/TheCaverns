package me.relaxitsdax.thecaverns.game.items;

import me.relaxitsdax.thecaverns.game.entities.livingentities.players.PlayerData;
import me.relaxitsdax.thecaverns.game.entities.livingentities.players.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class AddStatListener implements Listener {

    @EventHandler
    public void onPlayerSwapItem(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        PlayerData data = PlayerDataManager.get(player.getUniqueId());
        ItemStack stack = player.getInventory().getItem(event.getNewSlot());
        if (stack != null) {
            if (CavernWeapon.isCavernWeapon(stack)) {
                CavernWeapon item = new CavernWeapon(player, event.getNewSlot());
                data.addBonusStats(item.getBonuses());
            } else {
                data.resetBonuses();
            }
        } else {
            data.resetBonuses();
        }
    }

    @EventHandler
    public void onInventoryChange(InventoryInteractEvent event) {

        Player player = (Player) event.getWhoClicked();

        PlayerData data = PlayerDataManager.get(player.getUniqueId());
        ItemStack stack = player.getInventory().getItem(player.getInventory().getHeldItemSlot());
        if (stack != null) {
            if (CavernWeapon.isCavernWeapon(stack)) {
                CavernWeapon item = new CavernWeapon(player, player.getInventory().getHeldItemSlot());
                data.addBonusStats(item.getBonuses());
            } else {
                data.resetBonuses();
            }
        } else {
            data.resetBonuses();
        }
    }
}

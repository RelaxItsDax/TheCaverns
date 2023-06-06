package me.relaxitsdax.thecaverns.game.items;

import me.relaxitsdax.thecaverns.game.entities.livingentities.players.PlayerData;
import me.relaxitsdax.thecaverns.game.entities.livingentities.players.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;

public class AddStatListener implements Listener {

    @EventHandler
    public void onPlayerSwapItem(PlayerItemHeldEvent event) {

        Player player = event.getPlayer();
        PlayerData data = PlayerDataManager.get(player.getUniqueId());
        if (player.getInventory().getItem(event.getNewSlot()) != null) {
            CavernItem item = new CavernItem(player, event.getNewSlot());
            if (item.isCavernItem()) data.addStats(item.getBonuses());
        }

        if (player.getInventory().getItem(event.getPreviousSlot()) != null) {
            CavernItem item = new CavernItem(player, event.getPreviousSlot());
            if (item.isCavernItem()) data.subtractStats(item.getBonuses());
        }
    }

}

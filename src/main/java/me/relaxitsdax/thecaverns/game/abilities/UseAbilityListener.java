package me.relaxitsdax.thecaverns.game.abilities;

import me.relaxitsdax.thecaverns.game.items.CavernItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class UseAbilityListener implements Listener {
    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        CavernItem item = new CavernItem(player, player.getInventory().getHeldItemSlot());
        AbilityExecutor executor = new AbilityExecutor(player.getUniqueId());
        if (item.isCavernItem()) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (!player.isSneaking()) {
                    executor.execute(item.getRightClickAbility());
                } else {
                    if (item.getShiftRightClickAbility() == null) {
                        executor.execute(item.getRightClickAbility());
                    } else {
                        executor.execute(item.getShiftRightClickAbility());
                    }
                }
            }

            if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (!player.isSneaking()) {
                    executor.execute(item.getLeftClickAbility());
                } else {
                    if (item.getShiftLeftClickAbility() == null) {
                        executor.execute(item.getLeftClickAbility());
                    } else {
                        executor.execute(item.getShiftLeftClickAbility());
                    }
                }
            }
        }
    }
}

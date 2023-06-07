package me.relaxitsdax.thecaverns.game.abilities;

import me.relaxitsdax.thecaverns.game.abilities.players.PlayerAbilityExecutor;
import me.relaxitsdax.thecaverns.game.abilities.players.PlayerAbilityExecutorManager;
import me.relaxitsdax.thecaverns.game.items.CavernItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class UseAbilityListener implements Listener {
    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack stack = player.getInventory().getItem(player.getInventory().getHeldItemSlot());
        if (stack != null) {
            if (CavernItem.isCavernItem(stack)) {
            CavernItem item = new CavernItem(player, player.getInventory().getHeldItemSlot());
            PlayerAbilityExecutor executor = PlayerAbilityExecutorManager.get(player.getUniqueId());

                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (!player.isSneaking()) {
                        if (item.hasRightClickAbility()) {
                            executor.playerExecute(item.getRightClickAbility());
                        }
                    } else {
                        if (!item.hasSneakRightClickAbility()) {
                            if (item.hasRightClickAbility()) {
                                executor.playerExecute(item.getRightClickAbility());
                            }
                        } else {
                            executor.playerExecute(item.getSneakRightClickAbility());
                        }
                    }
                }

                if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    if (!player.isSneaking()) {
                        if (item.hasLeftClickAbility()) {
                            executor.playerExecute(item.getLeftClickAbility());
                        }
                    } else {
                        if (!item.hasSneakLeftClickAbility()) {
                            if (item.hasLeftClickAbility()) {
                                executor.playerExecute(item.getLeftClickAbility());
                            }
                        } else {
                            executor.playerExecute(item.getSneakLeftClickAbility());
                        }
                    }
                }
            }
        }
    }
}

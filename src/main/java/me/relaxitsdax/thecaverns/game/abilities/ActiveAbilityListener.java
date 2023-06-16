package me.relaxitsdax.thecaverns.game.abilities;

import me.relaxitsdax.thecaverns.game.entities.livingentities.players.PlayerData;
import me.relaxitsdax.thecaverns.game.entities.livingentities.players.PlayerDataManager;
import me.relaxitsdax.thecaverns.game.items.CavernItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ActiveAbilityListener implements Listener {
    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack stack = player.getInventory().getItem(player.getInventory().getHeldItemSlot());
        PlayerData data = PlayerDataManager.get(player.getUniqueId());
        if (stack != null) {
            if (CavernItem.isCavernItem(stack)) {
            CavernItem item = new CavernItem(player, player.getInventory().getHeldItemSlot());

                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (!player.isSneaking()) {
                        if (item.hasRightClickAbility()) {
                            item.getRightClickAbility().execute(data, item.getRightClickAbilityRarity());
                        }
                    } else {
                        if (!item.hasSneakRightClickAbility()) {
                            if (item.hasRightClickAbility()) {
                                item.getRightClickAbility().execute(data, item.getRightClickAbilityRarity());
                            }
                        } else {
                            item.getSneakRightClickAbility().execute(data, item.getShiftRightClickAbilityRarity());
                        }
                    }
                }

                if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    if (!player.isSneaking()) {
                        if (item.hasLeftClickAbility()) {
                            item.getLeftClickAbility().execute(data, item.getLeftClickAbilityRarity());
                        }
                    } else {
                        if (!item.hasSneakLeftClickAbility()) {
                            if (item.hasLeftClickAbility()) {
                                item.getLeftClickAbility().execute(data, item.getLeftClickAbilityRarity());
                            }
                        } else {
                            item.getSneakLeftClickAbility().execute(data, item.getShiftLeftClickAbilityRarity());
                        }
                    }
                }
            }
        }
    }
}

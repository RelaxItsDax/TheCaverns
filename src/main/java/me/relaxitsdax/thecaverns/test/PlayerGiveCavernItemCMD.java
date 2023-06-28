package me.relaxitsdax.thecaverns.test;

import me.relaxitsdax.thecaverns.game.enums.*;
import me.relaxitsdax.thecaverns.game.items.CavernItem;
import me.relaxitsdax.thecaverns.game.items.CavernWeapon;
import me.relaxitsdax.thecaverns.game.items.ItemGenerator;
import me.relaxitsdax.thecaverns.game.items.StatBonuses;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerGiveCavernItemCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            Map<ItemStatBonuses, Double> map = new HashMap<>();
            map.put(ItemStatBonuses.DAMAGE, 100.0);
            StatBonuses bonuses = new StatBonuses(map);


            if (args.length == 0) {

                PassiveAbility[] a = {PassiveAbility.REGENERATION, PassiveAbility.LIFESTEAL, PassiveAbility.GROWTH, PassiveAbility.PHOENIX, null};
                Rarity[] b = {Rarity.COMMON, Rarity.UNCOMMON, Rarity.FABLED, Rarity.LEGENDARY, null};
                CavernWeapon item = new CavernWeapon(UUID.randomUUID(), Material.DIAMOND_SWORD, "Sord", Rarity.DIVINE, CavernItemType.WEAPON, bonuses, ActiveAbility.HEAL, Rarity.COMMON, ActiveAbility.BARRIER, Rarity.DIVINE, a, b);
                player.getInventory().addItem(item.buildItemStack());
            } else {
                if (Rarity.getFromName(args[0]) != null) {
                    player.getInventory().addItem(ItemGenerator.generateItemFromChest(Rarity.getFromName(args[0])).toItemStack());
                }
            }

        }

        return true;
    }
}

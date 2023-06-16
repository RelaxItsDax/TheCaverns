package me.relaxitsdax.thecaverns.test;

import me.relaxitsdax.thecaverns.game.enums.Abilities;
import me.relaxitsdax.thecaverns.game.enums.PassiveAbilities;
import me.relaxitsdax.thecaverns.game.items.CavernItem;
import me.relaxitsdax.thecaverns.game.enums.ItemStatBonuses;
import me.relaxitsdax.thecaverns.game.enums.Rarity;
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

                PassiveAbilities[] a = {PassiveAbilities.REGENERATION, PassiveAbilities.LIFESTEAL, PassiveAbilities.GROWTH, PassiveAbilities.PHOENIX, null};
                Rarity[] b = {Rarity.COMMON, Rarity.UNCOMMON, Rarity.FABLED, Rarity.LEGENDARY, null};
                CavernItem item = new CavernItem(UUID.randomUUID(), Material.DIAMOND_SWORD, "Sord", bonuses, Rarity.DIVINE, Abilities.HEAL, null, Abilities.BARRIER, null, a, Rarity.COMMON, null, Rarity.DIVINE, null, b);
                player.getInventory().addItem(item.getItemStack());
            } else {
                if (Rarity.getFromName(args[0]) != null) {
                    player.getInventory().addItem(ItemGenerator.generateItemFromChest(Rarity.getFromName(args[0])).toItemStack());
                }
            }

        }

        return true;
    }
}

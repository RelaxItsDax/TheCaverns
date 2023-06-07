package me.relaxitsdax.thecaverns.game.entities.livingentities.players;

import me.relaxitsdax.thecaverns.game.abilities.PassiveAbility;
import me.relaxitsdax.thecaverns.game.abilities.PassiveAbilityProcType;
import me.relaxitsdax.thecaverns.game.abilities.players.PlayerPassiveAbilityExecutor;
import me.relaxitsdax.thecaverns.game.entities.Stats;
import me.relaxitsdax.thecaverns.TheCaverns;
import me.relaxitsdax.thecaverns.game.items.CavernItem;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.units.qual.C;

import java.util.UUID;

public class PlayerLoop {

    private final BukkitTask playerLoop;

    public PlayerLoop(UUID uuid) {
        Player player = TheCaverns.getInstance().getServer().getPlayer(uuid);

        TheCaverns INSTANCE = TheCaverns.getInstance();


            this.playerLoop = new BukkitRunnable() {
                @Override
                public void run() {
                    PlayerData data = PlayerDataManager.get(uuid);

                    //ITEM UPDATE CODE
                    ItemStack stack = player.getInventory().getItem(player.getInventory().getHeldItemSlot());
                    if (stack != null) {
                        if (CavernItem.isCavernItem(stack)) {
                            CavernItem item = new CavernItem(player, player.getInventory().getHeldItemSlot());
                            data.addBonusStats(item.getBonuses());

                            for (int i = 0; i < 5; i++) {
                                PassiveAbility ability =  item.getPassiveAbility(i);
                                if (ability != null) if (ability.getProcType() == PassiveAbilityProcType.WHILEHOLDING) new PlayerPassiveAbilityExecutor(player.getUniqueId()).playerExecute(ability);
                            }

                        } else {
                            data.resetBonuses();
                        }
                    } else {
                        data.resetBonuses();
                    }


                    //XP BAR CODE
                    double manaFraction = data.getMana() / data.getMaxMana();
                    player.setExp((float) manaFraction);
                    player.setLevel(0);

                    //HEALTH BAR CODE
                    double healthFraction = (data.getHealth() / data.getMaxHealth());
                    double hearts = (data.getHealth() == data.getMaxHealth() ? 20 : healthFraction >= 0.1 ? Math.floor(20 * healthFraction) : 1);
                    //If health is equal to max health, then set hearts to 20, otherwise if the hearts are less than .5, set hearts to 0.5, otherwise do the hearts calc
                    player.setHealth(hearts);


                    //ABSORPTION CODE
                    if (data.getBarrier() > 0) {
                        double barrierFraction = (data.getBarrier() / data.getMaxHealth());
                        player.setAbsorptionAmount(data.getBarrier() == data.getMaxHealth() ? 20 : barrierFraction >= 0.1 ? Math.floor(20 * barrierFraction) : 1);
                    } else {
                        player.setAbsorptionAmount(0);
                    }

                    //BAR UPDATE CODE
                    Stats health = Stats.HEALTH;
                    Stats barrier = Stats.BARRIER;
                    Stats defense = Stats.DEFENSE;
                    Stats mana = Stats.MANA;
                    ChatColor color = (data.getBarrier() == 0 ? health.getColor() : barrier.getColor());
                    String healthNum = color + health.getStatIcon() + " " + (int) data.getEffectiveHealth() + health.getColor() + " / " + (int) data.getMaxHealth() + " " + health.getStatIcon();
                    String defenseNum = defense.getColor() + defense.getStatIcon() + " " + (int) data.getDefense() + " " + defense.getStatIcon();
                    String manaNum = mana.getColor() + mana.getStatIcon() + " " + (int) data.getMana() + " / " + (int) data.getMaxMana() + " " + mana.getStatIcon();

                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(healthNum + "  " + defenseNum + "  " + manaNum));

                    //BAR SET CODE
                    String prefix = "";

                    String suffix = ": " + data.getNameBar();

                    data.getTeam().setPrefix(prefix);
                    data.getTeam().setSuffix(suffix);

                }
            }.runTaskTimer(INSTANCE, 0, 5);


        PlayerVisualLoopInstanceManager.add(uuid, this);
    }



    public BukkitTask getPlayerLoop() {
        return playerLoop;
    }

    public void cancel() {
        playerLoop.cancel();
    }
}

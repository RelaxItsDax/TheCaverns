package me.relaxitsdax.thecaverns.Game.entities.livingentities.players;

import me.relaxitsdax.thecaverns.Game.entities.Stats;
import me.relaxitsdax.thecaverns.TheCaverns;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

public class PlayerVisualLoop {

    private final BukkitTask nameLoop;
    private final BukkitTask barLoop;

    public PlayerVisualLoop(UUID uuid) {
        Player player = TheCaverns.getInstance().getServer().getPlayer(uuid);

        player.sendMessage("Loop logic runnin");

        TheCaverns INSTANCE = TheCaverns.getInstance();


            this.nameLoop = new BukkitRunnable() {
                @Override
                public void run() {
                    PlayerData data = PlayerDataManager.get(uuid);

                    String prefix = ChatColor.AQUA + "";

                    String suffix = ": " + data.getNameBar();

                    data.getTeam().setPrefix(prefix);
                    data.getTeam().setSuffix(suffix);
                }
            }.runTaskTimer(INSTANCE, 0, 5);

            this.barLoop = new BukkitRunnable() {
                @Override
                public void run() {
                    PlayerData data = PlayerDataManager.get(uuid);

                    double manaFraction = data.getMana() / data.getMaxMana();
                    player.setExp((float) manaFraction);
                    player.setLevel(0);

                    double healthFraction = (data.getHealth() / data.getMaxHealth());
                    double hearts = (data.getHealth() == data.getMaxHealth() ? 20 : healthFraction >= 0.1 ? Math.floor(20 * healthFraction) : 1);
                    //If health is equal to max health, then set hearts to 20, otherwise if the hearts are less than .5, set hearts to 0.5, otherwise do the hearts calc
                    player.setHealth(hearts);


                    if (data.getBarrier() > 0) {
                        double barrierFraction = (data.getBarrier() / data.getMaxHealth());
                        player.setAbsorptionAmount(data.getBarrier() == data.getMaxHealth() ? 20 : barrierFraction >= 0.1 ? Math.floor(20 * barrierFraction) : 1);
                    } else {
                        player.setAbsorptionAmount(0);
                    }

                    Stats health = Stats.HEALTH;
                    Stats barrier = Stats.BARRIER;
                    Stats defense = Stats.DEFENSE;
                    Stats mana = Stats.MANA;
                    ChatColor color = (data.getEffectiveHealth() - data.getHealth() == 0 ? health.getColor() : barrier.getColor());
                    String healthNum = color + health.getStatIcon() + " " + (int) data.getEffectiveHealth() + health.getColor() + " / " + (int) data.getMaxHealth() + " " + health.getStatIcon();
                    String defenseNum = defense.getColor() + defense.getStatIcon() + " " + (int) data.getDefense() + " " + defense.getStatIcon();
                    String manaNum = mana.getColor() + mana.getStatIcon() + " " + (int) data.getMana() + " / " + (int) data.getMaxMana() + " " + mana.getStatIcon();

                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(healthNum + "  " + defenseNum + "  " + manaNum));

                }
            }.runTaskTimer(INSTANCE, 0, 5);


        PlayerVisualLoopInstanceManager.add(uuid, this);
    }

    public BukkitTask getNameLoop() {
        return nameLoop;
    }

    public BukkitTask getBarLoop() {
        return barLoop;
    }

    public void cancel() {
        barLoop.cancel();
        nameLoop.cancel();
    }
}

package me.relaxitsdax.thecaverns.game.entities.livingentities.players;

import me.relaxitsdax.thecaverns.game.entities.livingentities.LivingEntityData;
import me.relaxitsdax.thecaverns.TheCaverns;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.UUID;

public class PlayerData extends LivingEntityData {

    private Player player;
    private PlayerLoop loop;
    private final Scoreboard scoreboard;
    private final Team team;


    public PlayerData(UUID uuid) {
        super(uuid);

        this.player = TheCaverns.getInstance().getServer().getPlayer(uuid);
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();


        this.team = TheCaverns.getInstance().getServer().getScoreboardManager().getMainScoreboard().registerNewTeam(uuid.toString());
        team.addEntry(player.getDisplayName());

        PlayerDataManager.add(uuid, this);

        TheCaverns.getInstance().getServer().getPlayer(uuid).sendMessage("Base Player Data Made!");
        reloadVisualLoop();
    }

    public PlayerLoop getVisualLoop() {
        return this.loop;
    }

    public void reloadVisualLoop() {
        if (PlayerVisualLoopInstanceManager.contains(getUuid())) PlayerVisualLoopInstanceManager.remove(getUuid());
        this.loop = new PlayerLoop(getUuid());
        PlayerVisualLoopInstanceManager.add(getUuid(), this.loop);
    }

    public void reloadScoreboard() {
        Objective objective = scoreboard.registerNewObjective("title", "", ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "THE CAVERNS");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score score = objective.getScore(ChatColor.RED + "Level: ");

    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public Player getPlayer() {
        return TheCaverns.getInstance().getServer().getPlayer(getUuid());
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Team getTeam() {
        return team;
    }
}

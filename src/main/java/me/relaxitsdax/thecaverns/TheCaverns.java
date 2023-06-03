package me.relaxitsdax.thecaverns;

import me.relaxitsdax.thecaverns.Game.Entities.EntityData;
import me.relaxitsdax.thecaverns.Game.Entities.EntityDataManager;
import me.relaxitsdax.thecaverns.Game.Entities.Players.PlayerDataManager;
import me.relaxitsdax.thecaverns.Game.Entities.Players.JoinLeaveListener;
import me.relaxitsdax.thecaverns.Game.Entities.Players.PlayerData;
import me.relaxitsdax.thecaverns.Test.*;
import me.relaxitsdax.thecaverns.World.DamageEventHandlers;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class TheCaverns extends JavaPlugin {


    public void onEnable() {

        INSTANCE = this;
        playerDataManager = new PlayerDataManager();



        getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);
        getServer().getPluginManager().registerEvents(new DamageEventHandlers(), this);
        getServer().getPluginManager().registerEvents(new CheckEntityListener(), this);

        getCommand("giveentitiesdata").setExecutor(new GiveEnemiesDataCMD());
        getCommand("dealdamagetoself").setExecutor(new DealDamageCMD());
        getCommand("getdata").setExecutor(new GetDataCMD());
        getCommand("setdata").setExecutor(new SetDataCMD());
        getCommand("adddata").setExecutor(new AddStatCMD());


        for (Player player : getServer().getOnlinePlayers()) {
            PlayerData data = new PlayerData(player.getUniqueId());
            //data.getActionBarLoop().start();
        }

        for (World world : getServer().getWorlds()) {
            for (Entity entity : world.getEntities()) {
                EntityData data = new EntityData(entity.getUniqueId());
                data.getEnemyLoop().start();
            }
        }








    }

    public void onDisable() {

    }

    private static TheCaverns INSTANCE;
    public static TheCaverns getInstance() {
        return INSTANCE;
    }

    private static PlayerDataManager playerDataManager;
    public static PlayerDataManager getDataManager(){return playerDataManager;}


}

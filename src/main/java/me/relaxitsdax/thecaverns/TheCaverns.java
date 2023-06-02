package me.relaxitsdax.thecaverns;

import me.relaxitsdax.thecaverns.PlayerData.DataManager;
import me.relaxitsdax.thecaverns.PlayerData.JoinLeaveListener;
import me.relaxitsdax.thecaverns.PlayerData.PlayerData;
import me.relaxitsdax.thecaverns.Test.DealDamageCMD;
import me.relaxitsdax.thecaverns.Test.GetDataCMD;
import me.relaxitsdax.thecaverns.Test.SetDataCMD;
import me.relaxitsdax.thecaverns.World.DisablingListeners;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class TheCaverns extends JavaPlugin {


    public void onEnable() {

        INSTANCE = this;
        dataManager = new DataManager();



        getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);
        getServer().getPluginManager().registerEvents(new DisablingListeners(), this);

        getCommand("dealdamagetoself").setExecutor(new DealDamageCMD());
        getCommand("getdata").setExecutor(new GetDataCMD());
        getCommand("setdata").setExecutor(new SetDataCMD());

        for (Player player : getServer().getOnlinePlayers()) {
            PlayerData data = new PlayerData(player.getUniqueId());
            DataManager.add(player.getUniqueId(), data);
            data.getPlayerLoop().start();
        }







    }

    public void onDisable() {

    }

    private static TheCaverns INSTANCE;
    public static TheCaverns getInstance() {
        return INSTANCE;
    }

    private static DataManager dataManager;
    public static DataManager getDataManager(){return dataManager;}


}

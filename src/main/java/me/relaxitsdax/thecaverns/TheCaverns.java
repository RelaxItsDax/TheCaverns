package me.relaxitsdax.thecaverns;

import com.google.errorprone.annotations.Var;
import me.relaxitsdax.thecaverns.Test.Test;
import me.relaxitsdax.thecaverns.Test.VariableCMD;
import org.bukkit.plugin.java.JavaPlugin;

public final class TheCaverns extends JavaPlugin {

    public void onEnable() {

        INSTANCE = this;

        System.out.println("please work");
        getServer().getPluginManager().registerEvents(new Test(), this);
        getCommand("loaddata").setExecutor(new VariableCMD());




    }

    public void onDisable() {

    }

    private static TheCaverns INSTANCE;
    public static TheCaverns getInstance() {
        return INSTANCE;
    }

}

package me.relaxitsdax.thecaverns.game.guis.types.createitem;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SetAbilitiesGUI extends CreateItemInventoryGUI implements Listener {

    private final Inventory inv;


    public SetAbilitiesGUI() {
        this.inv = Bukkit.createInventory(null, 18, "Add Bonuses");
    }

    public Inventory getInventory() {



        return inv;
    }


}

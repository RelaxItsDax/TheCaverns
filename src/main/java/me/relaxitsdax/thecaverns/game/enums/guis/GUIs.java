package me.relaxitsdax.thecaverns.game.enums.guis;

import me.relaxitsdax.thecaverns.game.guis.types.GUI;
import me.relaxitsdax.thecaverns.game.guis.types.createitem.CreateItemInventoryGUI;
import me.relaxitsdax.thecaverns.game.guis.types.createitem.SetBonusesInventoryGUI;
import me.relaxitsdax.thecaverns.game.guis.types.createitem.SetRarityInventoryGUI;

public enum GUIs {

    ADMIN_CREATE_ITEM(new CreateItemInventoryGUI()),
    ADMIN_CREATE_ITEM_SET_RARITY(new SetRarityInventoryGUI()),
    ADMIN_CREATE_ITEM_SET_BONUSES(new SetBonusesInventoryGUI())
    ;
    private final GUI executor;
    GUIs(GUI executor) {
        this.executor = executor;
    }

    public GUI getExecutor() {
        return executor;
    }
}

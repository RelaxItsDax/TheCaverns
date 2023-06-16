package me.relaxitsdax.thecaverns.game.abilities.passive;

import me.relaxitsdax.thecaverns.game.entities.EntityData;
import me.relaxitsdax.thecaverns.game.enums.AbilityStatus;
import me.relaxitsdax.thecaverns.game.enums.PassiveAbilities;
import me.relaxitsdax.thecaverns.game.enums.Rarity;

import javax.swing.text.html.parser.Entity;

public class GrowthPassiveAbility extends PassiveAbility {

    public GrowthPassiveAbility() {
    }

    @Override
    public void onProc(EntityData data, PassiveAbilities passiveAbility, Rarity abilityRarity) {
        AbilityStatus status = super.getStatus(data, passiveAbility);
        int l = abilityRarity.getNumber();

        if (status == AbilityStatus.SUCCESS) {

            data.addPartBarrier(0.1 + 0.03 * l);
        }
    }

}

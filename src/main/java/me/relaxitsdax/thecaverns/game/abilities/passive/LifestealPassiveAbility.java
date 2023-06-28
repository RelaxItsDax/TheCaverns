package me.relaxitsdax.thecaverns.game.abilities.passive;

import me.relaxitsdax.thecaverns.game.entities.EntityData;
import me.relaxitsdax.thecaverns.game.enums.AbilityStatus;
import me.relaxitsdax.thecaverns.game.enums.PassiveAbility;
import me.relaxitsdax.thecaverns.game.enums.Rarity;

public class LifestealPassiveAbility extends me.relaxitsdax.thecaverns.game.abilities.passive.PassiveAbility {

    public LifestealPassiveAbility() {
    }

    @Override
    public void onProc(EntityData data, PassiveAbility passiveAbility, Rarity abilityRarity) {
        AbilityStatus status = super.getStatus(data, passiveAbility);
        int l = abilityRarity.getNumber();

        if (status == AbilityStatus.SUCCESS) {

            data.addHealth(1 + 0.25 * l);
        }
    }

}

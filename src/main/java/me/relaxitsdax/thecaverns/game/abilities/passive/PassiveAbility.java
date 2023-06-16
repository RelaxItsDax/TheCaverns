package me.relaxitsdax.thecaverns.game.abilities.passive;

import me.relaxitsdax.thecaverns.game.entities.EntityData;
import me.relaxitsdax.thecaverns.game.enums.Abilities;
import me.relaxitsdax.thecaverns.game.enums.AbilityStatus;
import me.relaxitsdax.thecaverns.game.enums.PassiveAbilities;
import me.relaxitsdax.thecaverns.game.enums.Rarity;

public class PassiveAbility {

    public PassiveAbility() {
    }

    public void onProc(EntityData data, PassiveAbilities passiveAbility, Rarity abilityRarity) {
    }

    public AbilityStatus getStatus(EntityData data, PassiveAbilities ability) {
        AbilityStatus status;
        int cooldown = data.getPassiveCooldown(ability);
        if (cooldown <= 0) {
            status = AbilityStatus.SUCCESS;
        } else {
            status = AbilityStatus.ONCOOLDOWN;
        }

        return status;
    }



}

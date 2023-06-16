package me.relaxitsdax.thecaverns.game.abilities.passive;

import me.relaxitsdax.thecaverns.game.entities.EntityData;
import me.relaxitsdax.thecaverns.game.enums.AbilityStatus;
import me.relaxitsdax.thecaverns.game.enums.PassiveAbilities;
import me.relaxitsdax.thecaverns.game.enums.Rarity;

public class RegenerationPassiveAbility extends PassiveAbility {

    public RegenerationPassiveAbility() {
    }

    @Override
    public void onProc(EntityData data, PassiveAbilities passiveAbility, Rarity abilityRarity) {
        AbilityStatus status = super.getStatus(data, passiveAbility);

        if (status == AbilityStatus.SUCCESS) {

            data.addHealth(data.getMaxHealth() * 0.0025);

            data.addPassiveCooldown(passiveAbility, passiveAbility.getTickCooldown());
        }
    }
}
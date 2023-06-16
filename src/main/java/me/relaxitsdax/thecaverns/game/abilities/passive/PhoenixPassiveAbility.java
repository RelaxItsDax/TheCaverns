package me.relaxitsdax.thecaverns.game.abilities.passive;

import me.relaxitsdax.thecaverns.game.entities.EntityData;
import me.relaxitsdax.thecaverns.game.enums.AbilityStatus;
import me.relaxitsdax.thecaverns.game.enums.PassiveAbilities;
import me.relaxitsdax.thecaverns.game.enums.Rarity;

public class PhoenixPassiveAbility extends PassiveAbility{

    @Override
    public void onProc(EntityData data, PassiveAbilities passiveAbility, Rarity abilityRarity) {
        AbilityStatus status = super.getStatus(data, passiveAbility);
        int l = abilityRarity.getNumber();

        if (status == AbilityStatus.SUCCESS) {

            data.setHealth(data.getMaxHealth());

            data.addPartBarrier(0.5 + (0.1 * l));

            data.addPassiveCooldown(passiveAbility, passiveAbility.getTickCooldown());
        }

    }

}

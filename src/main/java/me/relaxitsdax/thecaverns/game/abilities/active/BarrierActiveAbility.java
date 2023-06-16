package me.relaxitsdax.thecaverns.game.abilities.active;

import me.relaxitsdax.thecaverns.game.entities.EntityData;
import me.relaxitsdax.thecaverns.game.entities.livingentities.players.PlayerData;
import me.relaxitsdax.thecaverns.game.enums.Abilities;
import me.relaxitsdax.thecaverns.game.enums.AbilityStatus;
import me.relaxitsdax.thecaverns.game.enums.Rarity;

public class BarrierActiveAbility extends ActiveAbility {

    public BarrierActiveAbility() {
    }

    @Override
    public void onUse(EntityData data, Abilities ability, Rarity abilityRarity) {
        AbilityStatus status = super.getStatus(data, ability);

        if (status == AbilityStatus.SUCCESS) {

            data.useMana(ability.getManaCost());

            data.addBarrier(50);

            data.addCooldown(ability, ability.getTickCooldown());


        }
        if (data instanceof PlayerData) {
            super.sendPlayerMessage((PlayerData) data, ability, status);
        }

    }

}

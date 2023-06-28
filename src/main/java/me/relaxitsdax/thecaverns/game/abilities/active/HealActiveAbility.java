package me.relaxitsdax.thecaverns.game.abilities.active;

import me.relaxitsdax.thecaverns.game.entities.EntityData;
import me.relaxitsdax.thecaverns.game.entities.livingentities.players.PlayerData;
import me.relaxitsdax.thecaverns.game.enums.ActiveAbility;
import me.relaxitsdax.thecaverns.game.enums.AbilityStatus;
import me.relaxitsdax.thecaverns.game.enums.Rarity;

public class HealActiveAbility extends me.relaxitsdax.thecaverns.game.abilities.active.ActiveAbility {

    public HealActiveAbility() {
    }

    @Override
    public void onUse(EntityData data, ActiveAbility ability, Rarity abilityRarity) {
        AbilityStatus status = super.getStatus(data, ability);
        int l = abilityRarity.getNumber();

        if (status == AbilityStatus.SUCCESS) {
            data.useMana(ability.getManaCost());


            data.addPartHealth(0.2 + 0.05 * l);


            data.addCooldown(ability, ability.getTickCooldown());
        }
        if (data instanceof PlayerData) {
            super.sendPlayerMessage((PlayerData) data, ability, status, abilityRarity);
        }

    }
}

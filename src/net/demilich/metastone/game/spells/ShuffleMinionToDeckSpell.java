package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class ShuffleMinionToDeckSpell extends Spell {

	public static SpellDesc create(MinionCard card) {
		return create(EntityReference.NONE, card, 1);
	}

	public static SpellDesc create(EntityReference target, MinionCard card, int amount) {
		Map<SpellArg, Object> arguments = SpellDesc.build(ShuffleMinionToDeckSpell.class);
		arguments.put(SpellArg.CARD, card);
		if (target != null) {
			arguments.put(SpellArg.TARGET, target);	
		}
		arguments.put(SpellArg.VALUE, amount);
		
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		MinionCard targetCard = (MinionCard) desc.get(SpellArg.CARD);

		if (targetCard == null) {
			Minion minion = (Minion) target;
			targetCard = (MinionCard) minion.getSourceCard();
		}

		int amount = desc.getValue();
		for (int i = 0; i < amount; i++) {
			targetCard = (MinionCard) targetCard.clone();

			Card randomCard = player.getDeck().getRandom();
			if (randomCard == null) {
				player.getDeck().add(targetCard);
			} else {
				player.getDeck().addAfter(targetCard, randomCard);
			}

		}
	}

}

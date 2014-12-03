package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class ShuffleMinionToDeckSpell extends Spell {

	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(ShuffleMinionToDeckSpell.class);
		return desc;
	}

	public static SpellDesc create(MinionCard card) {
		SpellDesc desc = new SpellDesc(ShuffleMinionToDeckSpell.class);
		desc.set(SpellArg.CARD, card);
		desc.setTarget(EntityReference.NONE);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		MinionCard targetCard = (MinionCard) desc.get(SpellArg.CARD);
		if (targetCard == null) {
			Minion minion = (Minion) target;
			targetCard = (MinionCard) minion.getSourceCard().clone();
		}

		Card randomCard = player.getDeck().getRandom();
		if (randomCard == null) {
			player.getDeck().add(targetCard);
		} else {
			player.getDeck().addAfter(targetCard, randomCard);
		}

	}

}

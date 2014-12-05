package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral.Chicken;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class GnomishExperimenterSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(GnomishExperimenterSpell.class);
		desc.setTarget(EntityReference.NONE);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Card card = context.getLogic().drawCard(player.getId());
		if (card == null) {
			return;
		}
		if (card.getCardType() == CardType.MINION) {
			player.getHand().remove(card);
			context.getLogic().receiveCard(player.getId(), new Chicken());
		}
	}

}

package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SpellUtils;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class CaptainsParrotSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(CaptainsParrotSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		CardCollection pirateCards = SpellUtils.getCards(player.getDeck(), card -> card.getTag(GameTag.RACE) == Race.PIRATE);
		if (pirateCards.isEmpty()) {
			return;
		}
		Card randomPirateCard = pirateCards.getRandom();
		player.getDeck().remove(randomPirateCard);
		context.getLogic().receiveCard(player.getId(), randomPirateCard);
	}

}

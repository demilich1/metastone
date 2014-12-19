package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.priest.ShadowOfNothing;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class MindGamesSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(MindGamesSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Player opponent = context.getOpponent(player);
		MinionCard minionCard = (MinionCard) opponent.getDeck().getRandomOfType(CardType.MINION);
		if (minionCard == null) {
			minionCard = new ShadowOfNothing();
		}
		context.getLogic().removeCard(player.getId(), minionCard);
		context.getLogic().summon(player.getId(), minionCard.summon());
	}
	
}

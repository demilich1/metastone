package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.concrete.tokens.priest.ShadowOfNothing;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

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

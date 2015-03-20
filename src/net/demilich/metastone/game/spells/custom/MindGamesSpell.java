package net.demilich.metastone.game.spells.custom;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.concrete.tokens.priest.ShadowOfNothing;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class MindGamesSpell extends Spell {
	
	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(MindGamesSpell.class);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Player opponent = context.getOpponent(player);
		MinionCard minionCard = (MinionCard) opponent.getDeck().getRandomOfType(CardType.MINION);
		if (minionCard == null) {
			minionCard = new ShadowOfNothing();
		}
		context.getLogic().removeCard(player.getId(), minionCard);
		context.getLogic().summon(player.getId(), minionCard.summon());
	}
	
}

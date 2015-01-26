package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class SummonRandomSpell extends Spell {

	public static SpellDesc create(MinionCard... minionCards) {
		SpellDesc desc = new SpellDesc(SummonRandomSpell.class);
		desc.set(SpellArg.CARDS, minionCards);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		MinionCard[] minionCards = (MinionCard[]) desc.get(SpellArg.CARDS);
		MinionCard randomMinionCard = minionCards[context.getLogic().random(minionCards.length)];
		context.getLogic().summon(player.getId(), randomMinionCard.summon());
	}

}

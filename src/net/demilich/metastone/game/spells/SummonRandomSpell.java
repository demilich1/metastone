package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class SummonRandomSpell extends Spell {

	public static SpellDesc create(MinionCard... minionCards) {
		Map<SpellArg, Object> arguments = SpellDesc.build(SummonRandomSpell.class);
		arguments.put(SpellArg.CARD, minionCards);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		MinionCard[] minionCards = (MinionCard[]) desc.get(SpellArg.CARD);
		MinionCard randomMinionCard = minionCards[context.getLogic().random(minionCards.length)];
		context.getLogic().summon(player.getId(), randomMinionCard.summon());
	}

}

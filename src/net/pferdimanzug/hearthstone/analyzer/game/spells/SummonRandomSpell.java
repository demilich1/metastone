package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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
		context.getLogic().summon(player.getId(), randomMinionCard.summon(), null, null, false);
	}

}

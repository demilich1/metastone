package net.demilich.metastone.game.spells;

import co.paralleluniverse.fibers.Suspendable;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class PutMinionOnBoardSpell extends Spell {
	@Override
	@Suspendable
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		MinionCard minionCard = (MinionCard) target;

		if (context.getLogic().summon(player.getId(), minionCard.summon(), null, -1, false)) {
			context.getLogic().removeCard(player.getId(), minionCard);
		}
	}
}

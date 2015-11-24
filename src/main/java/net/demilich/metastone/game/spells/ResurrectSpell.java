package net.demilich.metastone.game.spells;

import java.util.ArrayList;
import java.util.List;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class ResurrectSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		List<Minion> deadMinions = new ArrayList<>();
		for (Entity deadEntity : player.getGraveyard()) {
			if (deadEntity.getEntityType() == EntityType.MINION) {
				deadMinions.add((Minion) deadEntity);
			}
		}
		if (deadMinions.isEmpty()) {
			return;
		}
		Minion resurrectedMinion = deadMinions.get(context.getLogic().random(deadMinions.size()));
		MinionCard minionCard = (MinionCard) resurrectedMinion.getSourceCard();
		context.getLogic().summon(player.getId(), minionCard.summon());
	}

}

package net.demilich.metastone.game.spells;

import java.util.ArrayList;
import java.util.List;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;

public class ResurrectSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		List<Minion> deadMinions = new ArrayList<>();
		EntityFilter cardFilter = (EntityFilter) desc.get(SpellArg.CARD_FILTER);
		List<Entity> graveyard = new ArrayList<Entity>();
		graveyard.addAll(player.getGraveyard());
		for (Entity deadEntity : graveyard) {
			if (deadEntity.getEntityType() == EntityType.MINION) {
				if (cardFilter == null || cardFilter.matches(context, player, deadEntity)) {
					deadMinions.add((Minion) deadEntity);
				}
			}
		}
		int count = desc.getValue(SpellArg.VALUE, context, player, target, source, 1);
		for (int i=0; i < count; i++) {
			if (deadMinions.isEmpty()) {
				return;
			}
			Minion resurrectedMinion = deadMinions.get(context.getLogic().random(deadMinions.size()));
			MinionCard minionCard = (MinionCard) resurrectedMinion.getSourceCard();
			context.getLogic().summon(player.getId(), minionCard.summon());
			deadMinions.remove(resurrectedMinion);
		}
	}

}

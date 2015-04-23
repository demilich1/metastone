package net.demilich.metastone.game.spells.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class ResurrectSpell extends Spell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(ResurrectSpell.class);
		return new SpellDesc(arguments);
	}

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

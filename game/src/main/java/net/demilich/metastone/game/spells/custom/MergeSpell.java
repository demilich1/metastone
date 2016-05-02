package net.demilich.metastone.game.spells.custom;

import java.util.List;
import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class MergeSpell extends Spell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(MergeSpell.class);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		List<Entity> entities = context.resolveTarget(player, source, EntityReference.OTHER_FRIENDLY_MINIONS);
		String cardId = desc.getString(SpellArg.CARD);
		for (Entity entity : entities) {
			Minion minion = (Minion) entity;
			if (minion.getSourceCard().getCardId().equalsIgnoreCase(cardId)) {
				context.getLogic().transformMinion(minion, null);
				return;
			}
		}
	}

}
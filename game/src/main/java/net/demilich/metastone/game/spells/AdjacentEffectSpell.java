package net.demilich.metastone.game.spells;

import java.util.List;
import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Summon;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class AdjacentEffectSpell extends Spell {

	public static SpellDesc create(EntityReference target, SpellDesc primarySpell, SpellDesc secondarySpell) {
		Map<SpellArg, Object> arguments = SpellDesc.build(AdjacentEffectSpell.class);
		if (primarySpell != null) {
			arguments.put(SpellArg.SPELL_1, primarySpell);
		}
		if (secondarySpell != null) {
			arguments.put(SpellArg.SPELL_2, secondarySpell);
		}
		if (primarySpell == null && secondarySpell == null) {
			throw new IllegalArgumentException("Both primary- and secondary spell are NULL; at least one of them must be set");
		}
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(SpellDesc primarySpell, SpellDesc secondarySpell) {
		return create(null, primarySpell, secondarySpell);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		EntityReference sourceReference = source != null ? source.getReference() : null;
		List<Summon> adjacentSummons = context.getAdjacentSummons(player, target.getReference());

		SpellDesc primary = (SpellDesc) desc.get(SpellArg.SPELL_1);
		if (primary != null) {
			context.getLogic().castSpell(player.getId(), primary, sourceReference, target.getReference(), true);
		}

		SpellDesc secondary = (SpellDesc) desc.get(SpellArg.SPELL_2);
		if (secondary == null) {
			secondary = primary;
		}
		for (Entity adjacent : adjacentSummons) {
			context.getLogic().castSpell(player.getId(), secondary, sourceReference, adjacent.getReference(), true);
		}
	}

}
package net.demilich.metastone.game.spells.custom;

import java.util.List;
import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class ConeOfColdSpell extends Spell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(ConeOfColdSpell.class);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		List<Actor> affected = context.getAdjacentMinions(player, target.getReference());
		affected.add((Actor) target);

		SpellDesc damage = DamageSpell.create(1);
		SpellDesc freeze = ApplyTagSpell.create(GameTag.FROZEN);
		
		EntityReference sourceReference = source != null ? source.getReference() : null;
		
		for (Entity minion : affected) {
			context.getLogic().castSpell(player.getId(), damage, sourceReference, minion.getReference());
			context.getLogic().castSpell(player.getId(), freeze, sourceReference, minion.getReference());
		}
	}

}
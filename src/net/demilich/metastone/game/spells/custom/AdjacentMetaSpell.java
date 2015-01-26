package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class AdjacentMetaSpell extends Spell {

	public static SpellDesc create(SpellDesc primarySpell, SpellDesc secondarySpell) {
		SpellDesc desc = new SpellDesc(AdjacentMetaSpell.class);
		desc.set(SpellArg.SPELL_1, primarySpell);
		if (secondarySpell != null) {
			desc.set(SpellArg.SPELL_2, secondarySpell);
		}
		if (primarySpell == null && secondarySpell == null) {
			throw new IllegalArgumentException("Both primary- and secondary spell are NULL; at least one of them must be set");
		}
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		SpellDesc primary = (SpellDesc) desc.get(SpellArg.SPELL_1);
		if (primary != null) {
			primary.setSourceEntity(desc.getSourceEntity());
			primary.setTarget(desc.getTarget());
			context.getLogic().castSpell(player.getId(), primary);
		}
		
		SpellDesc secondary = (SpellDesc) desc.get(SpellArg.SPELL_2);
		if (secondary == null) {
			secondary = primary;
		} else {
			secondary.setSourceEntity(desc.getSourceEntity());
		}
		for (Entity adjacent : context.getAdjacentMinions(player, target.getReference())) {
			secondary.setTarget(adjacent.getReference());
			context.getLogic().castSpell(player.getId(), secondary);
		}
	}

}
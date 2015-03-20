package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class CastRandomSpellSpell extends Spell {
	
	public static SpellDesc create(EntityReference target, SpellDesc spell1, SpellDesc spell2, SpellDesc spell3) {
		Map<SpellArg, Object> arguments = SpellDesc.build(CastRandomSpellSpell.class);
		arguments.put(SpellArg.SPELL_1, spell1);
		arguments.put(SpellArg.SPELL_2, spell2);
		arguments.put(SpellArg.SPELL_3, spell3);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		SpellDesc spell = null;
		switch (context.getLogic().random(3)) {
		case 0:
			spell = (SpellDesc) desc.get(SpellArg.SPELL_1);
			break;
		case 1:
			spell = (SpellDesc) desc.get(SpellArg.SPELL_2);
			break;
		case 2:
			spell = (SpellDesc) desc.get(SpellArg.SPELL_3);
			break;
		default:
			break;
		}
		EntityReference sourceReference = source != null ? source.getReference() : null;
		context.getLogic().castSpell(player.getId(), spell, sourceReference, target.getReference());
	}

}

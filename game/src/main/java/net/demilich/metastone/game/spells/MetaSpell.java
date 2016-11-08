package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class MetaSpell extends Spell {
	
	public static SpellDesc create(EntityReference target, boolean randomTarget, SpellDesc... spells) {
		Map<SpellArg, Object> arguments = SpellDesc.build(MetaSpell.class);
		arguments.put(SpellArg.TARGET, target);
		arguments.put(SpellArg.SPELLS, spells);
		arguments.put(SpellArg.RANDOM_TARGET, randomTarget);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		for (SpellDesc spell : (SpellDesc[]) desc.get(SpellArg.SPELLS)) {
			SpellUtils.castChildSpell(context, player, spell, source, target);
		}
	}

}

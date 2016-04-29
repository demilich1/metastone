package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class RandomlyCastSpell extends Spell {

	public static SpellDesc create(EntityReference target, SpellDesc... spells) {
		Map<SpellArg, Object> arguments = SpellDesc.build(RandomlyCastSpell.class);
		arguments.put(SpellArg.SPELLS, spells);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		SpellDesc[] spells = (SpellDesc[]) desc.get(SpellArg.SPELLS);
		
		int i = context.getLogic().random(spells.length);
		SpellUtils.castChildSpell(context, player, spells[i], source, target);
	}

}
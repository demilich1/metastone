package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class CopyDeathrattleSpell extends Spell {

	public static SpellDesc create(EntityReference target) {
		Map<SpellArg, Object> arguments = SpellDesc.build(CopyDeathrattleSpell.class);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Actor copyTo = (Actor) source;
		Actor copyFrom = (Actor) target;
		if (copyFrom.getDeathrattles() == null) {
			return;
		}
		for (SpellDesc deathrattle : copyFrom.getDeathrattles()) {
		  copyTo.addDeathrattle(deathrattle);
		}
	}

}

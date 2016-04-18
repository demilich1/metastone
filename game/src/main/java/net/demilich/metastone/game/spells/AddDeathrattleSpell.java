package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class AddDeathrattleSpell extends Spell {

	public static SpellDesc create(EntityReference target, SpellDesc deathrattle) {
		Map<SpellArg, Object> arguments = SpellDesc.build(AddDeathrattleSpell.class);
		arguments.put(SpellArg.SPELL, deathrattle);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(SpellDesc deathrattle) {
		return create(null, deathrattle);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Actor minion = (Actor) target;
		SpellDesc deathrattle = (SpellDesc) desc.get(SpellArg.SPELL);
		minion.addDeathrattle(deathrattle);
	}

}

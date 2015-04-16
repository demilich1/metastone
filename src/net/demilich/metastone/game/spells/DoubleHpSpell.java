package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class DoubleHpSpell extends Spell {
	
	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(DoubleHpSpell.class);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Actor targetActor = (Actor) target;
		int hpBonus = targetActor.getHp();
		targetActor.modifyHpBonus(hpBonus);
	}
	
}

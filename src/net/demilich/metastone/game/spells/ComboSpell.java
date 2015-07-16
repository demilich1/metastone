package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class ComboSpell extends ConditionalEffectSpell {

	public static SpellDesc create(SpellDesc either, SpellDesc or, boolean exclusive) {
		Map<SpellArg, Object> arguments = SpellDesc.build(ComboSpell.class);
		arguments.put(SpellArg.SPELL_1, either);
		arguments.put(SpellArg.SPELL_2, or);
		arguments.put(SpellArg.EXCLUSIVE, exclusive);
		return new SpellDesc(arguments);
	}

	@Override
	protected boolean isConditionFulfilled(GameContext context, Player player, SpellDesc desc, Entity target) {
		return player.getHero().hasTag(GameTag.COMBO);
	}

}

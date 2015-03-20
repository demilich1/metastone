package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class AddSpellPowerSpell extends Spell {
	
	public static SpellDesc create(EntityReference target, int spellPower) {
		Map<SpellArg, Object> arguments = SpellDesc.build(AddSpellPowerSpell.class);
		arguments.put(SpellArg.VALUE, spellPower);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(int spellPower) {
		return create(null, spellPower);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int power = desc.getValue();
		target.modifyTag(GameTag.SPELL_POWER, power);
	}

}

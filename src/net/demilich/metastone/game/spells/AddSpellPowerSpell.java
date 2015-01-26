package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class AddSpellPowerSpell extends Spell {

	public static SpellDesc create(int spellPower) {
		SpellDesc desc = new SpellDesc(AddSpellPowerSpell.class);
		desc.setValue(spellPower);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int power = desc.getValue();
		target.modifyTag(GameTag.SPELL_POWER, power);
	}

}

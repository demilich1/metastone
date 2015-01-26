package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class MetaSpell extends Spell {
	
	public static SpellDesc create(SpellDesc spell1, SpellDesc spell2) {
		return create(spell1, spell2, null);
	}
	
	public static SpellDesc create(SpellDesc spell1, SpellDesc spell2, SpellDesc spell3) {
		SpellDesc desc = new SpellDesc(MetaSpell.class);
		desc.set(SpellArg.SPELL_1, spell1);
		desc.set(SpellArg.SPELL_2, spell2);
		if (spell3 != null) {
			desc.set(SpellArg.SPELL_3, spell3);
		}
		
		return desc;
	}

	private SpellDesc[] getSpells(SpellDesc desc) {
		SpellDesc[] spells;
		if (desc.get(SpellArg.SPELL_3) != null) {
			spells = new SpellDesc[3];
		} else if (desc.get(SpellArg.SPELL_2) != null) {
			spells = new SpellDesc[2];
		} else {
			spells = new SpellDesc[1];
		}
		
		if (spells.length >= 3) {
			spells[2] = (SpellDesc) desc.get(SpellArg.SPELL_3);
		}
		if (spells.length >= 2) {
			spells[1] = (SpellDesc) desc.get(SpellArg.SPELL_2);
		}
		if (spells.length >= 1) {
			spells[0] = (SpellDesc) desc.get(SpellArg.SPELL_1);
		}
		return spells;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		for (SpellDesc spell : getSpells(desc)) {
			if (!spell.hasPredefinedTarget()) {
				spell.setTarget(target.getReference());
			}
			spell.setSourceEntity(desc.getSourceEntity());
			context.getLogic().castSpell(player.getId(), spell);
		}
	}


}

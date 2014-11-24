package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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
				spell.setTarget(desc.getTarget());
			}
			spell.setSource(desc.getSource());
			spell.setSourceEntity(desc.getSourceEntity());
			context.getLogic().castSpell(player.getId(), spell);
		}
	}


}

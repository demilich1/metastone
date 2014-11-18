package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class CastRandomSpellSpell extends Spell {
	
	public static SpellDesc create(SpellDesc spell1, SpellDesc spell2, SpellDesc spell3) {
		SpellDesc desc = new SpellDesc(CastRandomSpellSpell.class);
		desc.set(SpellArg.SPELL_1, spell1);
		desc.set(SpellArg.SPELL_2, spell2);
		desc.set(SpellArg.SPELL_3, spell3);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		SpellDesc spell = null;
		switch (context.getLogic().random(3)) {
		case 0:
			spell = (SpellDesc) desc.get(SpellArg.SPELL_1);
			break;
		case 1:
			spell = (SpellDesc) desc.get(SpellArg.SPELL_2);
			break;
		case 2:
			spell = (SpellDesc) desc.get(SpellArg.SPELL_3);
			break;
		default:
			break;
		}
		spell.setTarget(target.getReference());
		spell.setSource(desc.getSource());
		spell.setSourceEntity(desc.getSourceEntity());
		context.getLogic().castSpell(player.getId(), spell);
	}

}

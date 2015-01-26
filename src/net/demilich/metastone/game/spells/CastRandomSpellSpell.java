package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

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
		spell.setSourceEntity(desc.getSourceEntity());
		context.getLogic().castSpell(player.getId(), spell);
	}

}

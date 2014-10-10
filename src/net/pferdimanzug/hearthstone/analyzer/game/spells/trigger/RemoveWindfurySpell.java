package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class RemoveWindfurySpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(RemoveWindfurySpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		if (!target.hasStatus(GameTag.WINDFURY)) {
			return;
		}
		target.removeTag(GameTag.WINDFURY);
		target.modifyTag(GameTag.NUMBER_OF_ATTACKS, -1);
	}

}

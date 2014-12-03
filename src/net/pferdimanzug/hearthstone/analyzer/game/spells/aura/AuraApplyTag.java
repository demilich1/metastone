package net.pferdimanzug.hearthstone.analyzer.game.spells.aura;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.RemoveTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class AuraApplyTag extends Aura {

	public AuraApplyTag(GameTag tag, EntityReference targetSelection) {
		super(ApplyTagSpell.create(tag), RemoveTagSpell.create(tag), targetSelection);
	}

}

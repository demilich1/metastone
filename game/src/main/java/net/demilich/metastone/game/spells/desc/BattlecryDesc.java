package net.demilich.metastone.game.spells.desc;

import net.demilich.metastone.game.spells.desc.condition.ConditionDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class BattlecryDesc {

	public SpellDesc spell;
	public TargetSelection targetSelection;
	public ConditionDesc condition;
	public boolean resolvedLate;
	public String description;

	public TargetSelection getTargetSelection() {
		return targetSelection != null ? targetSelection : TargetSelection.NONE;
	}

}

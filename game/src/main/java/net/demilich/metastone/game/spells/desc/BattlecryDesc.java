package net.demilich.metastone.game.spells.desc;

import java.io.Serializable;

import net.demilich.metastone.game.spells.desc.condition.ConditionDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class BattlecryDesc implements Serializable {

	public SpellDesc spell;
	public TargetSelection targetSelection;
	public ConditionDesc condition;
	public String description;

	public TargetSelection getTargetSelection() {
		return targetSelection != null ? targetSelection : TargetSelection.NONE;
	}

}

package net.demilich.metastone.game.spells.desc;

import net.demilich.metastone.game.targeting.TargetSelection;

public class BattlecryDesc {
	
	public SpellDesc spell;
	public TargetSelection targetSelection;
	public boolean resolvedLate;
	

	public TargetSelection getTargetSelection() {
		return targetSelection != null ? targetSelection : TargetSelection.NONE;
	}

}

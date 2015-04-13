package net.demilich.metastone.game.spells.desc;

import net.demilich.metastone.game.targeting.TargetSelection;

public class BattlecryDesc {
	
	private final SpellDesc spell;
	private final TargetSelection targetSelection;
	
	public BattlecryDesc(SpellDesc spell) {
		this(spell, TargetSelection.NONE);
	}

	public BattlecryDesc(SpellDesc spell, TargetSelection targetSelection) {
		this.spell = spell;
		this.targetSelection = targetSelection;
	}

	public SpellDesc getSpell() {
		return spell;
	}

	public TargetSelection getTargetSelection() {
		return targetSelection != null ? targetSelection : TargetSelection.NONE;
	}

}

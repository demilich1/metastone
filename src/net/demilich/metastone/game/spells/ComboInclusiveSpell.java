package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.spells.desc.ISpellConditionChecker;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class ComboInclusiveSpell extends ConditionalEffectSpell {
	
	public static SpellDesc create(SpellDesc noCombo, SpellDesc combo) {
		return ConditionalEffectSpell.create(combo, noCombo, (context, player, target) -> player.getHero().hasStatus(GameTag.COMBO));
	}
	
	@Override
	protected ISpellConditionChecker getCondition(SpellDesc desc) {
		return (context, player, target) -> player.getHero().hasStatus(GameTag.COMBO);
	}

}

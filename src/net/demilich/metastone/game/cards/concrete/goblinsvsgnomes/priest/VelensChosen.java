package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.priest;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.AddSpellPowerSpell;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class VelensChosen extends SpellCard {

	public VelensChosen() {
		super("Velen's Chosen", Rarity.COMMON, HeroClass.PRIEST, 3);
		setDescription("Give a minion +2/+4 and Spell Damage +1");
		
		SpellDesc buffSpell = BuffSpell.create(2, 4);
		SpellDesc spellPowerSpell = AddSpellPowerSpell.create(1);
		setSpell(MetaSpell.create(buffSpell, spellPowerSpell));
		setTargetRequirement(TargetSelection.MINIONS);
	}



	@Override
	public int getTypeId() {
		return 565;
	}
}

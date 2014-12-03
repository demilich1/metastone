package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AddSpellPowerSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class VelensChosen extends SpellCard {

	public VelensChosen() {
		super("Velen's Chosen", Rarity.COMMON, HeroClass.PRIEST, 3);
		setDescription("Give a minion +2/+4 and Spell Damage +1");
		
		SpellDesc buffSpell = BuffSpell.create(2, 4);
		SpellDesc spellPowerSpell = AddSpellPowerSpell.create(1);
		setSpell(MetaSpell.create(buffSpell, spellPowerSpell));
		setTargetRequirement(TargetSelection.MINIONS);
	}

}

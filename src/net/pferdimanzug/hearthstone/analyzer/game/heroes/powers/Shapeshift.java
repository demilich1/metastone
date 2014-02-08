package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AddSpellTriggerSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffHeroSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Shapeshift extends HeroPower {
	
	public Shapeshift() {
		super("Shapeshift", HeroClass.DRUID);
		SpellTrigger endBuffTrigger = new SpellTrigger(new TurnEndTrigger(), new BuffHeroSpell(-1, 0), true);
		Spell buff = new BuffHeroSpell(+1, +1);
		Spell endBuff = new AddSpellTriggerSpell(endBuffTrigger);
		MetaSpell spell = new MetaSpell(buff, endBuff); 
		spell.setTarget(EntityReference.FRIENDLY_HERO);
		setSpell(spell);
		
		setTargetRequirement(TargetSelection.NONE);
	}

}

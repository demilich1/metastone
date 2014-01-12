package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AddSpellTriggerSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetKey;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class SavageRoar extends SpellCard {

	public SavageRoar() {
		super("Savage Roar", Rarity.FREE, HeroClass.DRUID, 3);
		SpellTrigger endBuffTrigger = new SpellTrigger(new TurnEndTrigger(), new BuffSpell(-2, 0));
		Spell buff = new BuffSpell(+2, 0);
		buff.setTarget(TargetKey.FRIENDLY_CHARACTERS);
		Spell endBuff = new AddSpellTriggerSpell(endBuffTrigger);
		endBuff.setTarget(TargetKey.FRIENDLY_CHARACTERS);
		setSpell(new MetaSpell(buff, endBuff));
		setTargetRequirement(TargetSelection.NONE);
	}

}

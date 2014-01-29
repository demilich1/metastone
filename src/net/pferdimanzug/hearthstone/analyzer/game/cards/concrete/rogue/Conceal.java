package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AddSpellTriggerSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.RemoveTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Conceal extends SpellCard {

	public Conceal() {
		super("Conceal", Rarity.COMMON, HeroClass.ROGUE, 1);
		SpellTrigger endStealthTrigger = new SpellTrigger(new TurnStartTrigger(), new RemoveTagSpell(GameTag.STEALTHED));
		Spell stealth = new ApplyTagSpell(GameTag.STEALTHED);
		stealth.setTarget(EntityReference.FRIENDLY_MINIONS);
		Spell endStealth = new AddSpellTriggerSpell(endStealthTrigger);
		endStealth.setTarget(EntityReference.FRIENDLY_MINIONS);
		setSpell(new MetaSpell(stealth, endStealth));
		setTargetRequirement(TargetSelection.NONE);
	}

}

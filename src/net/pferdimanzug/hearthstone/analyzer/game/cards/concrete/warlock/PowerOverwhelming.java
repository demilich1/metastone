package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AddSpellTriggerSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class PowerOverwhelming extends SpellCard {

	public PowerOverwhelming() {
		super("PowerOverwhelming", Rarity.COMMON, HeroClass.WARLOCK, 1);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), new DestroySpell());
		setSpell(new MetaSpell(new BuffSpell(4, 4), new AddSpellTriggerSpell(trigger)));
		setTargetRequirement(TargetSelection.FRIENDLY_MINIONS);
	}

}

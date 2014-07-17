package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AddSpellTriggerSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Corruption extends SpellCard {

	public Corruption() {
		super("Corruption", Rarity.FREE, HeroClass.WARLOCK, 1);
		setDescription("Choose an enemy minion. At the start of your turn, destroy it.");
		Spell destroySpell = new DestroySpell();
		destroySpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new TurnStartTrigger(), destroySpell, true);
		setSpell(new AddSpellTriggerSpell(trigger));
		setTargetRequirement(TargetSelection.ENEMY_MINIONS);
	}
	


	@Override
	public int getTypeId() {
		return 336;
	}
}

package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.AddSpellTriggerSpell;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnStartTrigger;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Corruption extends SpellCard {

	public Corruption() {
		super("Corruption", Rarity.FREE, HeroClass.WARLOCK, 1);
		setDescription("Choose an enemy minion. At the start of your turn, destroy it.");
		SpellDesc destroySpell = DestroySpell.create(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new TurnStartTrigger(), destroySpell, true);
		setSpell(AddSpellTriggerSpell.create(trigger));
		setTargetRequirement(TargetSelection.ENEMY_MINIONS);
	}
	
	@Override
	public int getTypeId() {
		return 336;
	}
}

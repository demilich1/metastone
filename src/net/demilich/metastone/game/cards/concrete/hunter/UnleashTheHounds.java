package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.custom.UnleashTheHoundsSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class UnleashTheHounds extends SpellCard {

	public UnleashTheHounds() {
		super("Unleash the Hounds", Rarity.COMMON, HeroClass.HUNTER, 3);
		setDescription("For each enemy minion, summon a 1/1 Hound with Charge.");
		//TODO implement as summon spell with IValueProvider
		setSpell(UnleashTheHoundsSpell.create());
		setTargetRequirement(TargetSelection.NONE);
	}
	
	@Override
	public int getTypeId() {
		return 50;
	}
	
}

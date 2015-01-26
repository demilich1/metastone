package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.custom.TrackingSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Tracking extends SpellCard {

	public Tracking() {
		super("Tracking", Rarity.FREE, HeroClass.HUNTER, 1);
		setDescription("Look at the top three cards of your deck. Draw one and discard the others.");
		setSpell(TrackingSpell.create());
		setTargetRequirement(TargetSelection.NONE);
	}
	
	@Override
	public int getTypeId() {
		return 48;
	}
	
	
}

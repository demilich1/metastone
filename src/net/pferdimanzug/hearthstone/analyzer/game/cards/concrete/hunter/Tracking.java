package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.TrackingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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

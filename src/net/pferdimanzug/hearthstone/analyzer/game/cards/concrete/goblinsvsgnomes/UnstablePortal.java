package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.UnstablePortalSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class UnstablePortal extends SpellCard {

	public UnstablePortal() {
		super("Unstable Portal", Rarity.RARE, HeroClass.MAGE, 2);
		setDescription("Add a random minion to your hand. It costs (3) less.");
		
		setSpell(UnstablePortalSpell.create());
		setTargetRequirement(TargetSelection.NONE);
	}

}

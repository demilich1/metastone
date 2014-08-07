package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.BrawlSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Brawl extends SpellCard {

	public Brawl() {
		super("Brawl", Rarity.EPIC, HeroClass.WARRIOR, 5);
		setDescription("Destroy all minions except one.  (chosen randomly)");

		//TODO: check if this spell can be played with 1 minion on the board
		setSpell(BrawlSpell.create());
		setPredefinedTarget(EntityReference.ALL_MINIONS);
		setTargetRequirement(TargetSelection.NONE);

	}

	@Override
	public int getTypeId() {
		return 363;
	}

	
}

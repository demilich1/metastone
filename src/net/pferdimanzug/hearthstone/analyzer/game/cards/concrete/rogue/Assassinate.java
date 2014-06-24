package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Assassinate extends SpellCard {

	public Assassinate() {
		super("Assassinate", Rarity.FREE, HeroClass.ROGUE, 5);
		setDescription("Destroy an enemy minion.");
		setTargetRequirement(TargetSelection.ENEMY_MINIONS);
		setSpell(new DestroySpell());
	}
	
}

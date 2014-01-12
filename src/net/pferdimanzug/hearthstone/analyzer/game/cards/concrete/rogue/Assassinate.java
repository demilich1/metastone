package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Assassinate extends SpellCard {

	public Assassinate() {
		super("Assasinate", Rarity.FREE, HeroClass.ROGUE, 5);
		setTargetRequirement(TargetSelection.ENEMY_MINIONS);
		setSpell(new DestroySpell());
	}
	
}

package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Fireball extends SpellCard {
	
	public Fireball() {
		super("Fireball", Rarity.FREE, HeroClass.MAGE, 4);
		setDescription("Deal $6 damage.");
		setTargetRequirement(TargetSelection.ANY);
		setSpell(new DamageSpell(6));
	}



	@Override
	public int getTypeId() {
		return 59;
	}
}

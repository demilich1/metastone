package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ArcaneShot extends SpellCard {

	public ArcaneShot() {
		super("Arcane Shot", Rarity.FREE, HeroClass.HUNTER, 1);
		setDescription("Deal $2 damage.");
		setTargetRequirement(TargetSelection.ANY);
		setSpell(DamageSpell.create(2));
	}

	@Override
	public int getTypeId() {
		return 27;
	}
}

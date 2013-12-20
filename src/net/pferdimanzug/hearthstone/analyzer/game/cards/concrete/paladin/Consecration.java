package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AreaDamageSpell;

public class Consecration extends SpellCard {

	public Consecration() {
		super("Consecration", Rarity.FREE, HeroClass.PALADIN, 4);
		setSpell(new AreaDamageSpell(2, TargetSelection.ENEMY_CHARACTERS));
		setTargetRequirement(TargetSelection.NONE);
	}

}

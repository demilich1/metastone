package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Consecration extends SpellCard {

	public Consecration() {
		super("Consecration", Rarity.FREE, HeroClass.PALADIN, 4);
		setDescription("Deal $2 damage to all enemies.");
		setSpell(new DamageSpell(2));
		setPredefinedTarget(EntityReference.ENEMY_CHARACTERS);
		setTargetRequirement(TargetSelection.NONE);
	}

}

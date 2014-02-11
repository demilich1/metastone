package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ArcaneMissiles extends SpellCard {

	public ArcaneMissiles() {
		super("Arcane Missiles", Rarity.FREE, HeroClass.MAGE, 1);
		setDescription("Deal $3 damage randomly split among enemy characters.");
		setTargetRequirement(TargetSelection.NONE);
		setSpell(new DamageRandomSpell(1, 3));
		setPredefinedTarget(EntityReference.ENEMY_CHARACTERS);
	}
}

package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ArcaneExplosion extends SpellCard {

	public ArcaneExplosion() {
		super("Arcane Explosion", Rarity.FREE, HeroClass.MAGE, 2);
		setDescription("Deal $1 damage to all enemy minions.");
		setTargetRequirement(TargetSelection.NONE);
		setSpell(new DamageSpell(1));
		setPredefinedTarget(EntityReference.ENEMY_MINIONS);
	}

	@Override
	public int getTypeId() {
		return 51;
	}
}

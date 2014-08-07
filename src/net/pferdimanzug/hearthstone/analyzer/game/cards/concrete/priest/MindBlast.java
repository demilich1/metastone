package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class MindBlast extends SpellCard {
	
	public static final int DAMAGE = 5;

	public MindBlast() {
		super("Mind Blast", Rarity.FREE, HeroClass.PRIEST, 2);
		setDescription("Deal $5 damage to the enemy hero.");
		setSpell(DamageSpell.create(DAMAGE));
		setTargetRequirement(TargetSelection.NONE);
		setPredefinedTarget(EntityReference.ENEMY_HERO);
	}



	@Override
	public int getTypeId() {
		return 270;
	}
}

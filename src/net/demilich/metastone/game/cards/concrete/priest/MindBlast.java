package net.demilich.metastone.game.cards.concrete.priest;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

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

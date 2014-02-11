package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Whirlwind extends SpellCard {

	public Whirlwind() {
		super("Whirlwind", Rarity.FREE, HeroClass.WARRIOR, 1);
		setDescription("Deal $1 damage to ALL minions.");
		setSpell(new DamageSpell(1));
		setTargetRequirement(TargetSelection.NONE);
		setPredefinedTarget(EntityReference.ALL_MINIONS);
	}

}

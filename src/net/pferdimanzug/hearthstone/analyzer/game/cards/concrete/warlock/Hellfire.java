package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Hellfire extends SpellCard {

	public Hellfire() {
		super("Hellfire", Rarity.FREE, HeroClass.WARLOCK, 4);
		setDescription("Deal $3 damage to ALL characters.");
		setTargetRequirement(TargetSelection.NONE);
		setSpell(DamageSpell.create(3));
		setPredefinedTarget(EntityReference.ALL_CHARACTERS);
	}



	@Override
	public int getTypeId() {
		return 343;
	}
}

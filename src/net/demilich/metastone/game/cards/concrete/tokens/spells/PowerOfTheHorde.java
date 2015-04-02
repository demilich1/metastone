package net.demilich.metastone.game.cards.concrete.tokens.spells;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.targeting.TargetSelection;

public class PowerOfTheHorde extends SpellCard {

	public PowerOfTheHorde() {
		super("Power of the Horde", Rarity.FREE, HeroClass.ANY, 4);
		setDescription("Summon a random Horde Warrior.");

		//setSpell(SummonRandomSpell.create(new FrostwolfGrunt(), new TaurenWarrior(), new SenjinShieldmasta(), new ThrallmarFarseer(),
		//		new SilvermoonGuardian(), new CairneBloodhoof()));
		setTargetRequirement(TargetSelection.NONE);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 467;
	}
}

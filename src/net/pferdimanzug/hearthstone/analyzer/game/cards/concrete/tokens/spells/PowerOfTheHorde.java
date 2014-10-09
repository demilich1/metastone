package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.spells;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.CairneBloodhoof;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.FrostwolfGrunt;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.SenjinShieldmasta;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.SilvermoonGuardian;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.TaurenWarrior;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.ThrallmarFarseer;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class PowerOfTheHorde extends SpellCard {

	public PowerOfTheHorde() {
		super("Power of the Horde", Rarity.FREE, HeroClass.ANY, 4);
		setDescription("Summon a random Horde Warrior.");

		setSpell(SummonRandomSpell.create(new FrostwolfGrunt(), new TaurenWarrior(), new SenjinShieldmasta(), new ThrallmarFarseer(),
				new SilvermoonGuardian(), new CairneBloodhoof()));
		setTargetRequirement(TargetSelection.NONE);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 467;
	}
}

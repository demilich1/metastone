package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.hunter.Huffer;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.hunter.Leokk;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.hunter.Misha;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class AnimalCompanion extends SpellCard {

	public AnimalCompanion() {
		super("Animal Companion", Rarity.FREE, HeroClass.HUNTER, 3);
		setDescription("Summon a random Beast Companion.");
		setSpell(SummonRandomSpell.create(new Huffer(), new Misha(), new Leokk()));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 26;
	}
}

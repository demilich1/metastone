package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.cards.concrete.tokens.hunter.Huffer;
import net.demilich.metastone.game.cards.concrete.tokens.hunter.Leokk;
import net.demilich.metastone.game.cards.concrete.tokens.hunter.Misha;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.SummonRandomSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

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

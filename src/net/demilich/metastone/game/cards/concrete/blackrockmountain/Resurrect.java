package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.custom.ResurrectSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Resurrect extends SpellCard {

	public Resurrect() {
		super("Resurrect", Rarity.RARE, HeroClass.PRIEST, 2);
		setDescription("Summon a random friendly minion that died this game.");

		setSpell(ResurrectSpell.create());
		setTargetRequirement(TargetSelection.NONE);
	}



	@Override
	public int getTypeId() {
		return 642;
	}
}

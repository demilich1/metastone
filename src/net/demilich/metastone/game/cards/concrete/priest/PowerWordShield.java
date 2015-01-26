package net.demilich.metastone.game.cards.concrete.priest;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class PowerWordShield extends SpellCard {

	public PowerWordShield() {
		super("Power Word: Shield", Rarity.FREE, HeroClass.PRIEST, 1);
		setDescription("Draw a card.");
		setSpell(MetaSpell.create(BuffSpell.create(0, 2), DrawCardSpell.create()));
		setTargetRequirement(TargetSelection.MINIONS);
	}



	@Override
	public int getTypeId() {
		return 275;
	}
}

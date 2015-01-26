package net.demilich.metastone.game.cards.concrete.rogue;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.custom.BetrayalSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Betrayal extends SpellCard {
	
	public Betrayal() {
		super("Betrayal", Rarity.COMMON, HeroClass.ROGUE, 2);
		setDescription("An enemy minion deals its damage to the minions next to it.");
		setSpell(BetrayalSpell.create());
		setTargetRequirement(TargetSelection.ENEMY_MINIONS);
	}

	@Override
	public int getTypeId() {
		return 287;
	}
	
}



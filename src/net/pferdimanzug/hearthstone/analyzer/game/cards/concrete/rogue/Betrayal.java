package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.BetrayalSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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



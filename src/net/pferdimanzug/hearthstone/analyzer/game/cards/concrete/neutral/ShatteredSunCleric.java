package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ShatteredSunCleric extends MinionCard{

	public ShatteredSunCleric() {
		super("Shattered Sun Cleric", 3, 2, Rarity.FREE, HeroClass.ANY, 3);
		setDescription("Battlecry: Give a friendly minion +1/+1");
	}

	@Override
	public Minion summon() {
		Minion shatteredSunCleric = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new BuffSpell(1, 1), TargetSelection.FRIENDLY_MINIONS);
		shatteredSunCleric.setBattlecry(battlecry);
		return shatteredSunCleric;
	}
}

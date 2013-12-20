package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;

public class ShatteredSunCleric extends MinionCard{

	public ShatteredSunCleric() {
		super("Shattered Sun Cleric", Rarity.FREE, HeroClass.ANY, 3);
	}

	@Override
	public Minion summon() {
		Minion shatteredSunCleric = createMinion(3, 2);
		Battlecry battlecry = Battlecry.createBattlecry(new BuffSpell(1, 1), TargetSelection.FRIENDLY_MINIONS);
		shatteredSunCleric.setTag(GameTag.BATTLECRY, battlecry);
		return shatteredSunCleric;
	}
}

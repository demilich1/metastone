package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class ShatteredSunCleric extends MinionCard{

	public ShatteredSunCleric() {
		super("Shattered Sun Cleric", 3, 2, Rarity.FREE, HeroClass.ANY, 3);
		setDescription("Battlecry: Give a friendly minion +1/+1");
	}

	@Override
	public int getTypeId() {
		return 196;
	}


	@Override
	public Minion summon() {
		Minion shatteredSunCleric = createMinion();
		BattlecryAction battlecry = BattlecryAction.createBattlecry(BuffSpell.create(1, 1), TargetSelection.FRIENDLY_MINIONS);
		shatteredSunCleric.setBattlecry(battlecry);
		return shatteredSunCleric;
	}
}

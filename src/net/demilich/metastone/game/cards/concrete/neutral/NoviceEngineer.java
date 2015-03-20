package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class NoviceEngineer extends MinionCard {

	public NoviceEngineer() {
		super("Novice Engineer", 1, 1, Rarity.FREE, HeroClass.ANY, 2);
		setDescription("Battlecry: Draw a card.");
	}

	@Override
	public int getTypeId() {
		return 176;
	}


	@Override
	public Minion summon() {
		Minion noviceEngineer = createMinion();
		BattlecryAction battlecry = BattlecryAction.createBattlecry(DrawCardSpell.create(1), TargetSelection.NONE);
		noviceEngineer.setBattlecry(battlecry);
		return noviceEngineer;
	}
}

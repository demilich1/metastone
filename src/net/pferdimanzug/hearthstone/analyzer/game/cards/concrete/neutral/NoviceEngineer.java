package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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
		Battlecry battlecry = Battlecry.createBattlecry(new DrawCardSpell(1), TargetSelection.NONE);
		noviceEngineer.setBattlecry(battlecry);
		return noviceEngineer;
	}
}

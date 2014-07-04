package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;

public class ColdlightOracle extends MinionCard {

	public ColdlightOracle() {
		super("Coldlight Oracle", 2, 2, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("Battlecry: Each player draws 2 cards.");
		setRace(Race.MURLOC);
	}

	@Override
	public Minion summon() {
		Minion coldlightOracle = createMinion();
		coldlightOracle.setBattlecry(Battlecry.createBattlecry(new DrawCardSpell(2, TargetPlayer.BOTH)));
		return coldlightOracle;
	}

}

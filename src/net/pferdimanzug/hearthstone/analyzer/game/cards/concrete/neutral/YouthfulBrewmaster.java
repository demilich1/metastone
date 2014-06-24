package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReturnMinionToHandSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class YouthfulBrewmaster extends MinionCard {

	public YouthfulBrewmaster() {
		super("Youthful Brewmaster", 3, 2, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Battlecry: Return a friendly minion from the battlefield to your hand.");
	}

	@Override
	public Minion summon() {
		Battlecry battlecry = Battlecry.createBattlecry(new ReturnMinionToHandSpell(), TargetSelection.FRIENDLY_MINIONS);
		Minion youthfulBrewmaster = createMinion();
		youthfulBrewmaster.setBattlecry(battlecry);
		return youthfulBrewmaster;
	}

}

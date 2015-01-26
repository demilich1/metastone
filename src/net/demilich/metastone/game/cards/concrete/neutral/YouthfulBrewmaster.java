package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.ReturnMinionToHandSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class YouthfulBrewmaster extends MinionCard {

	public YouthfulBrewmaster() {
		super("Youthful Brewmaster", 3, 2, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Battlecry: Return a friendly minion from the battlefield to your hand.");
	}

	@Override
	public int getTypeId() {
		return 232;
	}



	@Override
	public Minion summon() {
		Battlecry battlecry = Battlecry.createBattlecry(ReturnMinionToHandSpell.create(), TargetSelection.FRIENDLY_MINIONS);
		Minion youthfulBrewmaster = createMinion();
		youthfulBrewmaster.setBattlecry(battlecry);
		return youthfulBrewmaster;
	}
}

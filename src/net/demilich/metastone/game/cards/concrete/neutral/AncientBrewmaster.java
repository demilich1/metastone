package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.ReturnMinionToHandSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class AncientBrewmaster extends MinionCard {

	public AncientBrewmaster() {
		super("Ancient Brewmaster", 5, 4, Rarity.COMMON, HeroClass.ANY, 4);
		setDescription("Battlecry: Return a friendly minion from the battlefield to your hand.");
	}

	@Override
	public int getTypeId() {
		return 83;
	}
	
	



	@Override
	public Minion summon() {
		Minion ancientBrewmaster = createMinion();
		BattlecryAction battlecry = BattlecryAction.createBattlecry(ReturnMinionToHandSpell.create(), TargetSelection.FRIENDLY_MINIONS);
		ancientBrewmaster.setBattlecry(battlecry);
		return ancientBrewmaster;
	}
}

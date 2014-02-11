package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReturnMinionToHandSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class AncientBrewmaster extends MinionCard {

	public AncientBrewmaster() {
		super("Ancient Brewmaster", 5, 4, Rarity.COMMON, HeroClass.ANY, 4);
		setDescription("Battlecry: Return a friendly minion from the battlefield to your hand.");
	}

	@Override
	public Minion summon() {
		Minion ancientBrewmaster = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new ReturnMinionToHandSpell(), TargetSelection.FRIENDLY_MINIONS);
		ancientBrewmaster.setTag(GameTag.BATTLECRY, battlecry);
		return ancientBrewmaster;
	}
	
	

}

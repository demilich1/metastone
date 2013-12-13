package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReturnMinionToHandSpell;

public class AncientBrewmaster extends MinionCard {

	public AncientBrewmaster() {
		super("Ancient Brewmaster", Rarity.COMMON, HeroClass.ANY, 4);
	}

	@Override
	public Minion summon() {
		Minion ancientBrewmaster = createMinion(5, 4);
		Battlecry battlecry = Battlecry.createBattlecry(new ReturnMinionToHandSpell(), TargetRequirement.FRIENDLY_MINIONS);
		ancientBrewmaster.setTag(GameTag.BATTLECRY, battlecry);
		return ancientBrewmaster;
	}
	
	

}

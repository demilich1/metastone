package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class AncientBrewmaster extends MinionCard {

	public AncientBrewmaster() {
		super("Ancient Brewmaster", Rarity.COMMON, HeroClass.ANY, 4);
	}

	@Override
	public Minion summon() {
		Minion ancientBrewmaster = createMinion(5, 4);
		ancientBrewmaster.setTag(GameTag.BATTLECRY, new BattlecryAncientBrewmaster());
		return ancientBrewmaster;
	}
	
	private class BattlecryAncientBrewmaster extends Battlecry {
		
		public BattlecryAncientBrewmaster() {
			setTargetRequirement(TargetRequirement.OWN_MINIONS);
			setEffectHint(EffectHint.UNKNOWN);
		}

		@Override
		public void execute(GameContext context, Player player) {
			Minion minion = (Minion) getTarget();
			player.getMinions().remove(minion);
			player.getHand().add(minion.getSourceCard());
		}
		
	}

}

package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Crush extends SpellCard {

	public Crush() {
		super("Crush", Rarity.EPIC, HeroClass.WARRIOR, 7);
		setDescription("Destroy a minion. If you have a damaged minion, this costs (4) less.");
		
		setSpell(DestroySpell.create());
		setTargetRequirement(TargetSelection.MINIONS);
	}
	
	@Override
	public int getManaCost(GameContext context, Player player) {
		int discount = 0;
		for (Minion minion : player.getMinions()) {
			if (minion.isWounded()) {
				discount = 4;
				break;
			}
		}
		return super.getManaCost(context, player) - discount;
	}



	@Override
	public int getTypeId() {
		return 604;
	}
}

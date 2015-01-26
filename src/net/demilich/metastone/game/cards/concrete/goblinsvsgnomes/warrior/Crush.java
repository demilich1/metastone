package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warrior;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.targeting.TargetSelection;

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

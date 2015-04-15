package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.spareparts;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.SwapAttackAndHpSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class ReversingSwitch extends SpellCard {

	public ReversingSwitch() {
		super("Reversing Switch", Rarity.FREE, HeroClass.ANY, 1);
		setDescription("Swap a minion's Attack and Health");
		
		setSpell(SwapAttackAndHpSpell.create());
		setTargetRequirement(TargetSelection.MINIONS);
		
		setCollectible(false);
	}



	@Override
	public int getTypeId() {
		return 586;
	}
}

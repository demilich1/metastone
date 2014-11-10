package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.spareparts;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.SwapAttackAndHpSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ReversingSwitch extends SpellCard {

	public ReversingSwitch() {
		super("Reversing Switch", Rarity.FREE, HeroClass.ANY, 1);
		setDescription("Swap a minion's Attack and Health");
		
		setSpell(SwapAttackAndHpSpell.create());
		setTargetRequirement(TargetSelection.MINIONS);
		
		setCollectible(false);
	}

}

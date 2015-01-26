package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.costmodifier.MinionCostModifier;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.TargetPlayer;

public class ManaWraith extends MinionCard {

	public ManaWraith() {
		super("Mana Wraith", 2, 2, Rarity.RARE, HeroClass.ANY, 2);
		setDescription("ALL minions cost (1) more.");
	}

	@Override
	public int getTypeId() {
		return 163;
	}


	@Override
	public Minion summon() {
		Minion manaWraith = createMinion();
		MinionCostModifier minionCostModifier = new MinionCostModifier(1);
		minionCostModifier.setTargetPlayer(TargetPlayer.BOTH);
		manaWraith.setCardCostModifier(minionCostModifier);
		return manaWraith;
	}
}

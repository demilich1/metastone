package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.TargetPlayer;

public class NerubarWeblord extends MinionCard {

	public NerubarWeblord() {
		super("Nerub'ar Weblord", 1, 4, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Minions with Battlecry cost (2) more.");
	}

	@Override
	public int getTypeId() {
		return 408;
	}

	@Override
	public Minion summon() {
		Minion nerubarWeblord = createMinion();
//		MinionCostModifier costModifier = new MinionCostModifier(2);
//		costModifier.setTargetPlayer(TargetPlayer.BOTH);
//		costModifier.setRequiredTag(GameTag.BATTLECRY);
//		nerubarWeblord.setCardCostModifier(costModifier);
		return nerubarWeblord;
	}
}

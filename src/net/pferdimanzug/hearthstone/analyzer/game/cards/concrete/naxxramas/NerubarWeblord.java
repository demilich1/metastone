package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.costmodifier.MinionCostModifier;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;

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
		MinionCostModifier costModifier = new MinionCostModifier(2);
		costModifier.setTargetPlayer(TargetPlayer.BOTH);
		costModifier.setRequiredTag(GameTag.BATTLECRY);
		nerubarWeblord.setCardCostModifier(costModifier);
		return nerubarWeblord;
	}
}

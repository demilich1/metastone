package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReturnMinionToHandSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Kidnapper extends MinionCard {

	public Kidnapper() {
		super("Kidnapper", 5, 3, Rarity.EPIC, HeroClass.ROGUE, 6);
		setDescription("Combo: Return a minion to its owner's hand.");
	}

	@Override
	public int getTypeId() {
		return 297;
	}



	@Override
	public Minion summon() {
		Minion kidnapper = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(ReturnMinionToHandSpell.create(), TargetSelection.MINIONS);
		battlecry.setCondition((context, player) -> player.getHero().hasTag(GameTag.COMBO));
		kidnapper.setBattlecry(battlecry);
		return kidnapper;
	}
}

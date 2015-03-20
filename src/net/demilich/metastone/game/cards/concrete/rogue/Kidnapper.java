package net.demilich.metastone.game.cards.concrete.rogue;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.ReturnMinionToHandSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

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
		BattlecryAction battlecry = BattlecryAction.createBattlecry(ReturnMinionToHandSpell.create(), TargetSelection.MINIONS);
		battlecry.setCondition((context, player) -> player.getHero().hasStatus(GameTag.COMBO));
		kidnapper.setBattlecry(battlecry);
		return kidnapper;
	}
}

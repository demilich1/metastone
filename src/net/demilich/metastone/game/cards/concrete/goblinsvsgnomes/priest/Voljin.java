package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.priest;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.custom.VoljinSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Voljin extends MinionCard {

	public Voljin() {
		super("Voljin", 6, 2, Rarity.LEGENDARY, HeroClass.PRIEST, 5);
		setDescription("Battlecry: Swap Health with another minion.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 566;
	}



	@Override
	public Minion summon() {
		Minion voljin = createMinion();
		BattlecryAction battlecry = BattlecryAction.createBattlecry(VoljinSpell.create(), TargetSelection.MINIONS);
		voljin.setBattlecry(battlecry);
		return voljin;
	}
}

package net.demilich.metastone.game.cards;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.actions.PlayMinionCardAction;
import net.demilich.metastone.game.cards.desc.ChooseBattlecryCardDesc;
import net.demilich.metastone.game.spells.desc.BattlecryDesc;

public class ChooseBattlecryCard extends MinionCard implements IChooseOneCard {

	private final BattlecryDesc battlecryOption1;
	private final BattlecryDesc battlecryOption2;

	public ChooseBattlecryCard(ChooseBattlecryCardDesc desc) {
		super(desc);
		this.battlecryOption1 = desc.option1;
		this.battlecryOption2 = desc.option2;
		setAttribute(Attribute.CHOOSE_ONE);
	}

	private String getAction1Suffix() {
		return battlecryOption1.description;
	}

	private String getAction2Suffix() {
		return battlecryOption2.description;
	}

	private BattlecryAction getBattlecry1() {
		return BattlecryAction.createBattlecry(battlecryOption1.spell, battlecryOption1.getTargetSelection());
	}

	private BattlecryAction getBattlecry2() {
		return BattlecryAction.createBattlecry(battlecryOption2.spell, battlecryOption2.getTargetSelection());
	}

	@Override
	public PlayCardAction playOption1() {
		PlayCardAction option1 = new PlayMinionCardAction(getCardReference(), getBattlecry1());
		option1.setActionSuffix(getAction1Suffix());
		option1.setGroupIndex(0);
		return option1;
	}

	@Override
	public PlayCardAction playOption2() {
		PlayCardAction option2 = new PlayMinionCardAction(getCardReference(), getBattlecry2());
		option2.setActionSuffix(getAction2Suffix());
		option2.setGroupIndex(1);
		return option2;
	}

}

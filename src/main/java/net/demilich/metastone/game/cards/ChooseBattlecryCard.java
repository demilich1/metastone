package net.demilich.metastone.game.cards;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.actions.PlayMinionCardAction;
import net.demilich.metastone.game.cards.desc.ChooseBattlecryCardDesc;
import net.demilich.metastone.game.spells.desc.BattlecryDesc;

public class ChooseBattlecryCard extends MinionCard implements IChooseOneCard {

	private final BattlecryDesc[] battlecryOptions;

	public ChooseBattlecryCard(ChooseBattlecryCardDesc desc) {
		super(desc);
		this.battlecryOptions = desc.options;
		setAttribute(Attribute.CHOOSE_ONE);
	}

	@Override
	public PlayCardAction[] playOptions() {
		PlayCardAction[] actions = new PlayCardAction[battlecryOptions.length];
		for (int i = 0; i < battlecryOptions.length; i++) {
			BattlecryDesc battlecryOption = battlecryOptions[i];
			BattlecryAction battlecry = BattlecryAction.createBattlecry(battlecryOption.spell, battlecryOption.getTargetSelection());
			PlayCardAction option = new PlayMinionCardAction(getCardReference(), battlecry);
			option.setActionSuffix(battlecryOption.description);
			option.setGroupIndex(i);
			actions[i] = option;
		}
		return actions;
	}

}

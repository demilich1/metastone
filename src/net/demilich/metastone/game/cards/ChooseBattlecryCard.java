package net.demilich.metastone.game.cards;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.actions.PlayMinionCardAction;
import net.demilich.metastone.game.entities.heroes.HeroClass;

public abstract class ChooseBattlecryCard extends MinionCard implements IChooseOneCard {

	public ChooseBattlecryCard(String name, int baseAttack, int baseHp, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, baseAttack, baseHp, rarity, classRestriction, manaCost);
		setTag(GameTag.CHOOSE_ONE);
	}

	protected abstract String getAction1Suffix();

	protected abstract String getAction2Suffix();

	protected abstract BattlecryAction getBattlecry1();

	protected abstract BattlecryAction getBattlecry2();

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

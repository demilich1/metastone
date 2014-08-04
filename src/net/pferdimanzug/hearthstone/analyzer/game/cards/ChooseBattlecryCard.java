package net.pferdimanzug.hearthstone.analyzer.game.cards;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayMinionCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;

public abstract class ChooseBattlecryCard extends MinionCard implements IChooseOneCard {

	public ChooseBattlecryCard(String name, int baseAttack, int baseHp, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, baseAttack, baseHp, rarity, classRestriction, manaCost);
		setTag(GameTag.CHOOSE_ONE);
		setTag(GameTag.BATTLECRY);
	}

	protected abstract String getAction1Suffix();

	protected abstract String getAction2Suffix();

	protected abstract Battlecry getBattlecry1();

	protected abstract Battlecry getBattlecry2();

	@Override
	public PlayCardAction playOption1() {
		PlayCardAction option1 = new PlayMinionCardAction(getCardReference(), getBattlecry1());
		option1.setActionSuffix(getAction1Suffix());
		return option1;
	}

	@Override
	public PlayCardAction playOption2() {
		PlayCardAction option2 = new PlayMinionCardAction(getCardReference(), getBattlecry2());
		option2.setActionSuffix(getAction2Suffix());
		return option2;
	}

}

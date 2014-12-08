package net.pferdimanzug.hearthstone.analyzer.game.cards;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayMinionCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public abstract class MinionCard extends Card {

	public MinionCard(String name, int baseAttack, int baseHp, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, CardType.MINION, rarity, classRestriction, manaCost);
		setTag(GameTag.BASE_ATTACK, baseAttack);
		setTag(GameTag.BASE_HP, baseHp);
	}

	protected Minion createMinion(GameTag... tags) {
		Minion minion = new Minion(this);
		minion.setBaseAttack(getBaseAttack());
		minion.setTag(GameTag.ATTACK_BONUS, getTagValue(GameTag.ATTACK_BONUS));
		minion.setBaseHp(getBaseHp());
		for (GameTag gameTag : tags) {
			minion.setTag(gameTag);
		}
		return minion;
	}
	
	public int getAttack() {
		return getBaseAttack() + getTagValue(GameTag.ATTACK_BONUS);
	}
	
	public int getBaseAttack() {
		return getTagValue(GameTag.BASE_ATTACK);
	}

	public int getBaseHp() {
		return getTagValue(GameTag.BASE_HP);
	}
	
	@Override
	public PlayCardAction play() {
		return new PlayMinionCardAction(getCardReference());
	}

	public void setRace(Race race) {
		setTag(GameTag.RACE, race);
	}

	public abstract Minion summon();

}

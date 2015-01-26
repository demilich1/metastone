package net.demilich.metastone.game.cards;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.actions.PlayMinionCardAction;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public abstract class MinionCard extends Card {

	public MinionCard(String name, int baseAttack, int baseHp, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, CardType.MINION, rarity, classRestriction, manaCost);
		setTag(GameTag.BASE_ATTACK, baseAttack);
		setTag(GameTag.BASE_HP, baseHp);
	}

	protected Minion createMinion(GameTag... tags) {
		Minion minion = new Minion(this);
		for (GameTag gameTag : tags) {
			minion.setTag(gameTag);
		}
		minion.setBaseAttack(getBaseAttack());
		minion.setTag(GameTag.ATTACK_BONUS, getTagValue(GameTag.ATTACK_BONUS));
		minion.setBaseHp(getBaseHp());
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

package net.pferdimanzug.hearthstone.analyzer.game.cards;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public abstract class MinionCard extends Card {

	public MinionCard(String name, int baseAttack, int baseHp, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, CardType.MINION, rarity, classRestriction, manaCost);
		setTag(GameTag.BASE_ATTACK, baseAttack);
		setTag(GameTag.BASE_HP, baseHp);
	}

	protected Minion createMinion(GameTag... tags) {
		Minion minion = new Minion(this);
		minion.setBaseAttack(getBaseAttack());
		minion.setBaseHp(getBaseHp());
		for (GameTag gameTag : tags) {
			minion.setTag(gameTag);
		}
		return minion;
	}
	
	public int getBaseAttack() {
		return getTagValue(GameTag.BASE_ATTACK);
	}

	public int getBaseHp() {
		return getTagValue(GameTag.BASE_HP);
	}
	
	public void setRace(Race race) {
		setTag(GameTag.RACE, race);
	}

	@Override
	public PlayCardAction play() {
		return new PlayCardAction(this) {
			{
				setTargetRequirement(TargetSelection.FRIENDLY_MINIONS);
				setActionType(ActionType.SUMMON);
			}

			@Override
			protected void play(GameContext context, int playerId) {
				Actor nextTo = (Actor) (getTargetKey() != null ? context.resolveSingleTarget(playerId, getTargetKey()) : null);
				context.getLogic().summon(playerId, summon(), MinionCard.this, nextTo, true);
			}
		};

	}

	public abstract Minion summon();

}

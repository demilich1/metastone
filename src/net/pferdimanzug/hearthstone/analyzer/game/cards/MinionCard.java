package net.pferdimanzug.hearthstone.analyzer.game.cards;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public abstract class MinionCard extends Card {

	private final int baseAttack;
	private final int baseHp;

	public MinionCard(String name, int baseAttack, int baseHp, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, CardType.MINION, rarity, classRestriction, manaCost);
		this.baseAttack = baseAttack;
		this.baseHp = baseHp;
	}

	protected Minion createMinion(GameTag... tags) {
		return createMinion(Race.NONE, tags);
	}

	protected Minion createMinion(Race race, GameTag... tags) {
		Minion minion = new Minion(this);
		minion.setBaseAttack(baseAttack);
		minion.setBaseHp(baseHp);
		minion.setRace(race);
		for (GameTag gameTag : tags) {
			minion.setTag(gameTag);
		}
		return minion;
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
				Entity nextTo = getTargetKey() != null ? context.resolveSingleTarget(playerId, getTargetKey()) : null;
				context.getLogic().summon(playerId, summon(), nextTo);
			}
		};

	}

	public abstract Minion summon();

	public int getBaseAttack() {
		return baseAttack;
	}

	public int getBaseHp() {
		return baseHp;
	}

}

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

	public MinionCard(String name, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, CardType.MINION, rarity, classRestriction, manaCost);
	}

	protected Minion createMinion(int baseAttack, int baseHp, GameTag... tags) {
		return createMinion(baseAttack, baseHp, Race.NONE, tags);
	}

	protected Minion createMinion(int baseAttack, int baseHp, Race race, GameTag... tags) {
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

}

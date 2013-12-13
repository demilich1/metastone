package net.pferdimanzug.hearthstone.analyzer.game.cards.spells;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;

public class RandomSpellDamageCard extends SpellCard {

	private final int damage;
	private final int iterations;

	public RandomSpellDamageCard(String name, Rarity rarity, HeroClass classRestriction, int manaCost, int damage, int iterations) {
		super(name, rarity, classRestriction, manaCost);
		this.damage = damage;
		this.iterations = iterations;
	}

	@Override
	public PlayCardAction play() {
		return new PlayCardAction(this) {
			{
				setTargetRequirement(TargetRequirement.NONE);
				setActionType(ActionType.SPELL);
				setEffectHint(EffectHint.NEGATIVE);
			}
			
			@Override
			protected void cast(GameContext context, Player player) {
				Player opponent = context.getOpponent(player);
				for (int i = 0; i < iterations; i++) {
					doDamage(context, opponent, damage);
				}
			}
			
			private void doDamage(GameContext context, Player opponent, int damage) {
				List<Entity> enemyCharacters = opponent.getCharacters();
				Entity randomTarget = enemyCharacters.get(ThreadLocalRandom.current().nextInt(enemyCharacters.size()));
				context.getLogic().damage(randomTarget, damage);
			}
		};
	}

}

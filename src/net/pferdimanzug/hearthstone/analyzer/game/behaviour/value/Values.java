package net.pferdimanzug.hearthstone.analyzer.game.behaviour.value;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

class Values {

	public static float getHeroDamageValue(GameContext context, Hero target, int damage) {
		if (target.hasStatus(GameTag.IMMUNE)) {
			return 0;
		}
		if (target.getEffectiveHp() <= damage) {
			return 100000;
		}
		return damage;
	}

	public static float getMinionCardValue(MinionCard minionCard) {
		return minionCard.getBaseManaCost();
	}
	
	public static float getMinionValue(Minion minion) {
		return minion.getSourceCard().getBaseManaCost();
	}
	
	public static float getSilenceScore(GameContext context, Minion minion) {
		float score = 0;
		score += minion.getTagValue(GameTag.ATTACK) - minion.getTagValue(GameTag.BASE_ATTACK);
		score += minion.getTagValue(GameTag.HP_BONUS) > 0 ? 1 : 0;
		score += minion.hasStatus(GameTag.WINDFURY) ? 2 : 0;
		score += minion.hasStatus(GameTag.DIVINE_SHIELD) ? 2 : 0;
		score += minion.hasTag(GameTag.DEATHRATTLES) ? 2 : 0;
		score += minion.hasSpellTrigger() ? 2 : 0;
		score += minion.hasStatus(GameTag.FROZEN) ? -2 : 0;
		score += minion.hasStatus(GameTag.TAUNT) ? 1 : 0;
		score += minion.hasStatus(GameTag.UNTARGETABLE_BY_SPELLS) ? 2 : 0;
		return score;
	}
	
	public static int signBeneficial(Entity entity, int playerId) {
		return -signHarmful(entity, playerId);
	}
	
	public static int signHarmful(Entity entity, int playerId) {
		return entity.getOwner() == playerId ? -1 : 1;
	}
	
	public static final int DRAW_CARD_VALUE = 2;

}

package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.events.DamageEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEventListener;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class GurubashiBerserker extends MinionCard {
	
	public static final int BASE_ATTACK = 2;
	public static final int ATTACK_BONUS = 3;

	public GurubashiBerserker() {
		super("Gurubashi Berserker", Rarity.FREE, HeroClass.ANY, 5);
	}

	@Override
	public Minion summon() {
		return new GurubashiBerserkerMinion(this);
	}
	
	private class GurubashiBerserkerMinion extends Minion implements IGameEventListener {

		public GurubashiBerserkerMinion(MinionCard sourceCard) {
			super(sourceCard);
			setBaseStats(BASE_ATTACK, 7);
		}
		
		@Override
		public void onGameEvent(IGameEvent event) {
			DamageEvent damageEvent = (DamageEvent) event;
			if (damageEvent.getVictim() != this) {
				return;
			}
			modifyTag(GameTag.ATTACK_BONUS, ATTACK_BONUS);
		}

		@Override
		public GameEventType interestedIn() {
			return GameEventType.DAMAGE;
		}

	}

}

package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.events.DamageEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEventListener;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class AcolyteOfPain extends MinionCard {

	public AcolyteOfPain() {
		super("Acolyte of Pain", Rarity.COMMON, HeroClass.ANY, 3);
	}

	@Override
	public Minion summon() {
		return new AcolyteOfPainMinion(this);
	}
	
	private class AcolyteOfPainMinion extends Minion implements IGameEventListener {

		public AcolyteOfPainMinion(MinionCard source) {
			super(source);
			setBaseStats(1, 3);
		}

		@Override
		public void onGameEvent(IGameEvent event) {
			DamageEvent damageEvent = (DamageEvent) event;
			if (damageEvent.getVictim() != this) {
				return;
			}
			event.getGameContext().getLogic().drawCard(getOwner());
		}

		@Override
		public GameEventType interestedIn() {
			return GameEventType.DAMAGE;
		}
		
	}

}

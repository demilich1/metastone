package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.HealEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEventListener;

public class NorthshireCleric extends MinionCard {

	public NorthshireCleric() {
		super("Northshire Cleric", Rarity.FREE, HeroClass.PRIEST, 1);
	}

	@Override
	public Minion summon() {
		return new NorthshireClericMinion(this);
	}
	
	private class NorthshireClericMinion extends Minion implements IGameEventListener {

		public NorthshireClericMinion(MinionCard sourceCard) {
			super(sourceCard);
			setBaseStats(1, 3);
		}

		@Override
		public void onGameEvent(IGameEvent event) {
			HealEvent healEvent = (HealEvent) event;
			if (healEvent.getTarget().getEntityType() != EntityType.MINION) {
				return;
			}
			event.getGameContext().getLogic().drawCard(getOwner());
		}

		@Override
		public GameEventType interestedIn() {
			return GameEventType.HEAL;
		}
		
	}

}

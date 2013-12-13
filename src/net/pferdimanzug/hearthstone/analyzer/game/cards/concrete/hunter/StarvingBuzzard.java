package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEventListener;
import net.pferdimanzug.hearthstone.analyzer.game.events.SummonEvent;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Race;

public class StarvingBuzzard extends MinionCard {

	public StarvingBuzzard() {
		super("Starving Buzzard", Rarity.FREE, HeroClass.HUNTER, 2);
	}

	@Override
	public Minion summon() {
		return new StarvingBuzzardMinion(this);
	}
	
	private class StarvingBuzzardMinion extends Minion implements IGameEventListener {

		public StarvingBuzzardMinion(MinionCard sourceCard) {
			super(sourceCard);
			setBaseStats(2, 2);
			setRace(Race.BEAST);
		}

		@Override
		public void onGameEvent(IGameEvent event) {
			if (event.getEventType() != GameEventType.SUMMON) {
				return;
			}
			
			SummonEvent summonEvent = (SummonEvent) event;
			if (summonEvent.getMinion().getRace() == Race.BEAST) {
				event.getGameContext().getLogic().drawCard(getOwner());
			}
		}

		@Override
		public GameEventType interestedIn() {
			return GameEventType.SUMMON;
		}
		
	}

}

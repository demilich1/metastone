package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEventListener;
import net.pferdimanzug.hearthstone.analyzer.game.events.TurnEndEvent;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class AbusiveSergeant extends MinionCard {
	
	private static final int ATTACK_BONUS = 2;

	public AbusiveSergeant() {
		super("Abusive Sergeant", Rarity.COMMON, HeroClass.ANY, 1);
	}

	@Override
	public Minion summon() {
		Minion abusiveSergeant = createMinion(2, 1);
		abusiveSergeant.setTag(GameTag.BATTLECRY, new AbusiveSergeantBattlecry());
		return null;
	}
	
	private class AbusiveSergeantBattlecry extends Battlecry {
		
		public AbusiveSergeantBattlecry() {
			setTargetRequirement(TargetRequirement.OWN_MINIONS);
		}

		@Override
		public void execute(GameContext context, Player player) {
			getTarget().modifyTag(GameTag.ATTACK_BONUS, ATTACK_BONUS);
			context.getEventManager().registerGameEventListener(new EndBuff(getTarget()));
		}

		
	}
	
	private class EndBuff implements IGameEventListener {
		
		private Entity target;

		public EndBuff(Entity target) {
			this.target = target;
		}

		@Override
		public void onGameEvent(IGameEvent event) {
			TurnEndEvent turnEndEvent = (TurnEndEvent) event;
			if (turnEndEvent.getPlayer() != target.getOwner()) {
				return;
			}
			target.modifyTag(GameTag.ATTACK_BONUS, -ATTACK_BONUS);
			turnEndEvent.getGameContext().getEventManager().removeGameEventListener(this);
		}

		@Override
		public GameEventType interestedIn() {
			return GameEventType.TURN_END;
		}
		
	}

}

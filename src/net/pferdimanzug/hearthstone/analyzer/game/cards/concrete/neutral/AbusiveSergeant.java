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
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;

public class AbusiveSergeant extends MinionCard {
	
	private static final int ATTACK_BONUS = 2;

	public AbusiveSergeant() {
		super("Abusive Sergeant", Rarity.COMMON, HeroClass.ANY, 1);
	}

	@Override
	public Minion summon() {
		Minion abusiveSergeant = createMinion(2, 1);
		Battlecry battlecryAbusive = Battlecry.createBattlecry(new AbusiveSergeantSpell(), TargetRequirement.FRIENDLY_MINIONS);
		abusiveSergeant.setTag(GameTag.BATTLECRY, battlecryAbusive);
		return null;
	}
	
	private class AbusiveSergeantSpell extends BuffSpell {
		
		public AbusiveSergeantSpell() {
			super(ATTACK_BONUS, 0);
		}

		@Override
		public void cast(GameContext context, Player player, Entity target) {
			super.cast(context, player, target);
			context.getEventManager().registerGameEventListener(new EndBuff(target));
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

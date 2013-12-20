package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEventListener;
import net.pferdimanzug.hearthstone.analyzer.game.events.PhysicalAttackEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ISpell;

public class BlessingOfWisdom extends SpellCard {

	public BlessingOfWisdom() {
		super("Blessing of Wisdom", Rarity.COMMON, HeroClass.PALADIN, 1);
		setSpell(new BlessingOfWisdomSpell());
		setTargetRequirement(TargetRequirement.MINIONS);
	}
	
	private class BlessingOfWisdomSpell implements ISpell, IGameEventListener {

		private Entity target;

		@Override
		public void onGameEvent(IGameEvent event) {
			PhysicalAttackEvent physicalAttackEvent = (PhysicalAttackEvent) event;
			if (physicalAttackEvent.getAttacker() == target) {
				event.getGameContext().getLogic().drawCard(target.getOwner());
			}
		}

		@Override
		public GameEventType interestedIn() {
			return GameEventType.PHYSICAL_ATTACK;
		}

		@Override
		public void cast(GameContext context, Player player, Entity target) {
			this.target = target;
			context.getEventManager().registerGameEventListener(this);
		}
		
	}

}

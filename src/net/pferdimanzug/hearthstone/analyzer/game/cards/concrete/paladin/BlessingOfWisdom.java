package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.PhysicalAttackEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AddSpellTriggerSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.IGameEventTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class BlessingOfWisdom extends SpellCard {

	public BlessingOfWisdom() {
		super("Blessing of Wisdom", Rarity.COMMON, HeroClass.PALADIN, 1);
		SpellTrigger trigger = new SpellTrigger(new BlessingOfWisdomTrigger(), new DrawCardSpell(1));
		setSpell(new AddSpellTriggerSpell(trigger));
		setTargetRequirement(TargetSelection.MINIONS);
	}
	
	private class BlessingOfWisdomTrigger implements IGameEventTrigger {

		@Override
		public GameEventType interestedIn() {
			return GameEventType.PHYSICAL_ATTACK;
		}

		@Override
		public Entity getTarget(GameContext context, Entity host) {
			return null;
		}

		@Override
		public boolean fire(IGameEvent event, Entity host) {
			PhysicalAttackEvent physicalAttackEvent = (PhysicalAttackEvent) event;
			return physicalAttackEvent.getAttacker() == host;
		}

	}

}

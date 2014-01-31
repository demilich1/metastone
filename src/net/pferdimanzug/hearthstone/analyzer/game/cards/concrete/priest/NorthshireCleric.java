package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.HealEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.GameEventTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class NorthshireCleric extends MinionCard {

	private class NorthshireClericTrigger extends GameEventTrigger {

		@Override
		public boolean fire(IGameEvent event, Entity host) {
			HealEvent healEvent = (HealEvent) event;
			return healEvent.getTarget().getEntityType() != EntityType.MINION;
		}

		@Override
		public GameEventType interestedIn() {
			return GameEventType.HEAL;
		}

				
	}

	public NorthshireCleric() {
		super("Northshire Cleric", Rarity.FREE, HeroClass.PRIEST, 1);
	}
	
	@Override
	public Minion summon() {
		Minion northshireCleric = createMinion(1, 3);
		SpellTrigger trigger = new SpellTrigger(new NorthshireClericTrigger(), new DrawCardSpell());
		northshireCleric.setSpellTrigger(trigger);
		return northshireCleric;
	}

}

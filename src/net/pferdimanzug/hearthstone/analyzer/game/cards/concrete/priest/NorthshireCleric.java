package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.HealEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.GameEventTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class NorthshireCleric extends MinionCard {

	public NorthshireCleric() {
		super("Northshire Cleric", 1, 3, Rarity.FREE, HeroClass.PRIEST, 1);
		setDescription("Whenever a minion is healed, draw a card.");
	}

	@Override
	public int getTypeId() {
		return 274;
	}
	
	@Override
	public Minion summon() {
		Minion northshireCleric = createMinion();
		SpellTrigger trigger = new SpellTrigger(new NorthshireClericTrigger(), new DrawCardSpell());
		northshireCleric.setSpellTrigger(trigger);
		return northshireCleric;
	}



	private class NorthshireClericTrigger extends GameEventTrigger {

		@Override
		public boolean fire(GameEvent event, Entity host) {
			HealEvent healEvent = (HealEvent) event;
			return healEvent.getTarget().getEntityType() == EntityType.MINION;
		}

		@Override
		public GameEventType interestedIn() {
			return GameEventType.HEAL;
		}

				
	}
}

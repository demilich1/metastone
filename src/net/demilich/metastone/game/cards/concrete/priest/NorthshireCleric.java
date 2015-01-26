package net.demilich.metastone.game.cards.concrete.priest;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.HealEvent;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.trigger.GameEventTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

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
		SpellTrigger trigger = new SpellTrigger(new NorthshireClericTrigger(), DrawCardSpell.create());
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

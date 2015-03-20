package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.SummonEvent;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.MinionCardPlayedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class Hobgoblin extends MinionCard {

	public Hobgoblin() {
		super("Hobgoblin", 2, 3, Rarity.EPIC, HeroClass.ANY, 3);
		setDescription("Whenever you play a 1-Attack minion, give it +2/+2.");
	}

	@Override
	public int getTypeId() {
		return 522;
	}
	
	@Override
	public Minion summon() {
		Minion hobgoblin = createMinion();
		SpellDesc buff = BuffSpell.create(EntityReference.EVENT_TARGET, +2, +2);
		SpellTrigger trigger = new SpellTrigger(new HobgoblinTrigger(), buff);
		hobgoblin.setSpellTrigger(trigger);
		return hobgoblin;
	}

	private class HobgoblinTrigger extends MinionCardPlayedTrigger {
		
		@Override
		public boolean fire(GameEvent event, Entity host) {
			if (!super.fire(event, host)) {
				return false;
			}
			
			SummonEvent summonEvent = (SummonEvent) event;
			if (summonEvent.getMinion().getOwner() != getOwner()) {
				return false;
			}
			
			return summonEvent.getMinion().getAttack() == 1;
		}
		
	}
}

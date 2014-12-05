package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.SummonEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionCardPlayedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Hobgoblin extends MinionCard {

	public Hobgoblin() {
		super("Hobgoblin", 2, 3, Rarity.EPIC, HeroClass.ANY, 3);
		setDescription("Whenever you play a 1-Attack minion, give it +2/+2.");
	}

	@Override
	public Minion summon() {
		Minion hobgoblin = createMinion();
		SpellDesc buff = BuffSpell.create(+2, +2);
		buff.setTarget(EntityReference.EVENT_TARGET);
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

package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warlock;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.HeroDamagedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class FloatingWatcher extends MinionCard {

	public FloatingWatcher() {
		super("Floating Watcher", 4, 4, Rarity.COMMON, HeroClass.WARLOCK, 5);
		setDescription("Whenever your hero takes damage on your turn, gain +2/+2.");
		setRace(Race.DEMON);
	}

	@Override
	public int getTypeId() {
		return 599;
	}
	
	@Override
	public Minion summon() {
		Minion floatingWatcher = createMinion();
		SpellDesc buffSpell = BuffSpell.create(+2, +2);
		buffSpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new FloatingWatcherTrigger(), buffSpell);
		floatingWatcher.setSpellTrigger(trigger);
		return floatingWatcher;
	}



	private class FloatingWatcherTrigger extends HeroDamagedTrigger {
		
		
		@Override
		public boolean fire(GameEvent event, Entity host) {
			if (!super.fire(event, host)) {
				return false;
			}
			return event.getGameContext().getActivePlayerId() == getOwner();
		}
	}
}

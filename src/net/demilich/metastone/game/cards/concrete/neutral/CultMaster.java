package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.KillEvent;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.trigger.GameEventTrigger;
import net.demilich.metastone.game.spells.trigger.MinionDeathTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class CultMaster extends MinionCard {
	
	public CultMaster() {
		super("Cult Master", 4, 2, Rarity.COMMON, HeroClass.ANY, 4);
		setDescription("Whenever one of your other minions dies, draw a card.");
	}

	@Override
	public int getTypeId() {
		return 110;
	}

	@Override
	public Minion summon() {
		GameEventTrigger minionDieTrigger = new CultMasterTrigger();
		SpellTrigger trigger = new SpellTrigger(minionDieTrigger, DrawCardSpell.create());
		Minion cultMaster = createMinion();
		cultMaster.setSpellTrigger(trigger);
		return cultMaster;
	}


	private class CultMasterTrigger extends MinionDeathTrigger {

		@Override
		public boolean fire(GameEvent event, Entity host) {
			Actor cultMaster = (Actor) host;
			if (cultMaster.isDead()) {
				return false;
			}
			
			KillEvent killEvent = (KillEvent) event;
			// not a minion
			if (killEvent.getVictim().getEntityType() != EntityType.MINION) {
				return false;
			}
			// not a friendly minion
			if (killEvent.getVictim().getOwner() != host.getOwner()) {
				return false;
			}
			
			// card says 'when one of your OTHER minion dies'
			return killEvent.getVictim() != host;
		}
		
	}
}

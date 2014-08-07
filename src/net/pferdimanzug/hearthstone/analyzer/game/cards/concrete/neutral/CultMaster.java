package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.KillEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.GameEventTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionDeathTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

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

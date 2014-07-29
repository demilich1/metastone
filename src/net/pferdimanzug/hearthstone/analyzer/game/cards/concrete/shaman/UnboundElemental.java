package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.OverloadEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.GameEventTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class UnboundElemental extends MinionCard {

	public UnboundElemental() {
		super("Unbound Elemental", 2, 4, Rarity.COMMON, HeroClass.SHAMAN, 3);
		setDescription("Whenever you play a card with Overload, gain +1/+1.");
	}

	@Override
	public int getTypeId() {
		return 331;
	}
	
	@Override
	public Minion summon() {
		Minion unboundElemental =createMinion();
		Spell buffSpell = new BuffSpell(1, 1);
		buffSpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new OverloadTrigger(), buffSpell);
		unboundElemental.setSpellTrigger(trigger);
		return unboundElemental;
	}



	private class OverloadTrigger extends GameEventTrigger {

		@Override
		public boolean fire(GameEvent event, Entity host) {
			OverloadEvent overloadEvent = (OverloadEvent) event;
			return overloadEvent.getPlayerId() == host.getOwner();
		}

		@Override
		public GameEventType interestedIn() {
			return GameEventType.OVERLOAD;
		}
		
	}
}

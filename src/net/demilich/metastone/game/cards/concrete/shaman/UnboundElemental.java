package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.OverloadEvent;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.GameEventTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

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
		SpellDesc buffSpell = BuffSpell.create(1, 1);
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

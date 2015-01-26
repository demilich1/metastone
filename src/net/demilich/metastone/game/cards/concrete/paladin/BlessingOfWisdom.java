package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.PhysicalAttackEvent;
import net.demilich.metastone.game.spells.AddSpellTriggerSpell;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.trigger.GameEventTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.TargetSelection;

public class BlessingOfWisdom extends SpellCard {

	public BlessingOfWisdom() {
		super("Blessing of Wisdom", Rarity.COMMON, HeroClass.PALADIN, 1);
		setDescription("Choose a minion. Whenever it attacks, draw a card.");
		SpellTrigger trigger = new SpellTrigger(new BlessingOfWisdomTrigger(), DrawCardSpell.create());
		setSpell(AddSpellTriggerSpell.create(trigger));
		setTargetRequirement(TargetSelection.MINIONS);
	}
	
	@Override
	public int getTypeId() {
		return 240;
	}

	private class BlessingOfWisdomTrigger extends GameEventTrigger {

		@Override
		public boolean fire(GameEvent event, Entity host) {
			PhysicalAttackEvent physicalAttackEvent = (PhysicalAttackEvent) event;
			return physicalAttackEvent.getAttacker() == host;
		}

		@Override
		public GameEventType interestedIn() {
			return GameEventType.PHYSICAL_ATTACK;
		}

	}
}

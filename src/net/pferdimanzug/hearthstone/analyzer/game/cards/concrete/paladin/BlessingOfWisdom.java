package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.PhysicalAttackEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AddSpellTriggerSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.GameEventTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class BlessingOfWisdom extends SpellCard {

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
	
	public BlessingOfWisdom() {
		super("Blessing of Wisdom", Rarity.COMMON, HeroClass.PALADIN, 1);
		setDescription("Choose a minion. Whenever it attacks, draw a card.");
		SpellTrigger trigger = new SpellTrigger(new BlessingOfWisdomTrigger(), new DrawCardSpell());
		setSpell(new AddSpellTriggerSpell(trigger));
		setTargetRequirement(TargetSelection.MINIONS);
	}



	@Override
	public int getTypeId() {
		return 240;
	}
}

package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.SummonEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.GameEventTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class StarvingBuzzard extends MinionCard {

	private class StarvingBuzzardTrigger extends GameEventTrigger {

		@Override
		public boolean fire(IGameEvent event, Actor host) {
			SummonEvent summonEvent = (SummonEvent) event;
			if (summonEvent.getMinion().getOwner() != host.getOwner()) {
				return false;
			}
			return summonEvent.getMinion().getRace() == Race.BEAST;
		}

		@Override
		public GameEventType interestedIn() {
			return GameEventType.SUMMON;
		}
	}

	public StarvingBuzzard() {
		super("Starving Buzzard", 2, 1, Rarity.FREE, HeroClass.HUNTER, 2);
		setDescription("Whenever you summon a Beast, draw a card.");
	}
	
	@Override
	public Minion summon() {
		Minion starvingBuzzard = createMinion(Race.BEAST);
		SpellTrigger trigger = new SpellTrigger(new StarvingBuzzardTrigger(), new DrawCardSpell());
		starvingBuzzard.setSpellTrigger(trigger);
		return starvingBuzzard;
	}
	
	

}

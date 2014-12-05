package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.SpellCastedEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReceiveRandomRaceCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellCastedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class Gazlowe extends MinionCard {

	public Gazlowe() {
		super("Gazlowe", 3, 6, Rarity.LEGENDARY, HeroClass.ANY, 6);
		setDescription("Whenever you cast a 1-mana spell, add a random Mech to your hand.");
	}

	@Override
	public int getTypeId() {
		return 516;
	}
	
	@Override
	public Minion summon() {
		Minion gazlowe = createMinion();
		SpellDesc getRandomMech = ReceiveRandomRaceCardSpell.create(Race.MECH, 1);
		SpellTrigger trigger = new SpellTrigger(new GazloweTrigger(), getRandomMech);
		gazlowe.setSpellTrigger(trigger);
		return gazlowe;
	}



	private class GazloweTrigger extends SpellCastedTrigger {

		public GazloweTrigger() {
			super(TargetPlayer.SELF);
		}
		
		@Override
		public boolean fire(GameEvent event, Entity host) {
			if (!super.fire(event, host)) {
				return false;
			}
			SpellCastedEvent spellCastedEvent = (SpellCastedEvent) event;
			return spellCastedEvent.getSourceCard().getBaseManaCost() == 1;
		}
		
	}
}

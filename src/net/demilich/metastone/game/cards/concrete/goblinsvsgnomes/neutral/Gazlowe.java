package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.SpellCastedEvent;
import net.demilich.metastone.game.spells.ReceiveRandomRaceCardSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellCastedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

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
			// Question: How does Gazlowe work with Sorcerer's Apprentice? Will Ice Lance trigger it or Frostbolt?
			// Answer: In example: A zero mana Ice Lance will not trigger Gazlowe, but a 1 mana Frostbolt will.
			// Source: Blue post
			Player player = event.getGameContext().getPlayer(spellCastedEvent.getPlayerId());
			int realManaCost = event.getGameContext().getLogic().getModifiedManaCost(player, spellCastedEvent.getSourceCard());
			return realManaCost == 1;
		}

	}
}

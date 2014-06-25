package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class TotemicCall extends HeroPower {
	
	private class HealingTotem extends MinionCard {

		public HealingTotem() {
			super(HEALING_TOTEM_NAME, 0, 2, Rarity.FREE, HeroClass.SHAMAN, 1);
			setCollectible(false);
			setTag(GameTag.RACE, Race.TOTEM);
		}

		@Override
		public Minion summon() {
			Minion healingTotem = createMinion();
			Spell healSpell = new HealingSpell(1);
			healSpell.setTarget(EntityReference.FRIENDLY_MINIONS);
			SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), healSpell);
			healingTotem.setSpellTrigger(trigger);
			return healingTotem;
		}
		
	}
	private class SearingTotem extends MinionCard {

		public SearingTotem() {
			super(SEARING_TOTEM_NAME, 1, 1, Rarity.FREE, HeroClass.SHAMAN, 1);
			setCollectible(false);
			setTag(GameTag.RACE, Race.TOTEM);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}

		
	}
	private class StoneclawTotem extends MinionCard {

		public StoneclawTotem() {
			super(STONECLAW_TOTEM_NAME, 0, 2, Rarity.FREE, HeroClass.SHAMAN, 1);
			setCollectible(false);
			setTag(GameTag.RACE, Race.TOTEM);
		}

		@Override
		public Minion summon() {
			return createMinion(GameTag.TAUNT);
		}

		
	}
	private class TotemicCallSpell extends Spell {

		private boolean alreadyOnBoard(List<Minion> minions, String minionName) {
			for (Entity minion : minions) {
				if (minion.getName().equals(minionName)) {
					return true;
				}
			}
			return false;
		}
		

		private List<Minion> getTotems() {
			List<Minion> minions = new ArrayList<Minion>(4);
			minions.add(new HealingTotem().summon());
			minions.add(new StoneclawTotem().summon());
			minions.add(new SearingTotem().summon());
			minions.add(new WrathOfAirTotem().summon());
			return minions;
		}

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			List<Minion> availableTotems = new ArrayList<Minion>();
			for (Minion totem : getTotems()) {
				if (!alreadyOnBoard(player.getMinions(), totem.getName())) {
					availableTotems.add(totem);
				}
			}

			Minion randomTotem = availableTotems.get(ThreadLocalRandom.current().nextInt(availableTotems.size()));
			context.getLogic().summon(player.getId(), randomTotem, TotemicCall.this, null, true);
		}
		
	}

	private class WrathOfAirTotem extends MinionCard {

		public WrathOfAirTotem() {
			super(WRATH_OF_AIR_TOTEM_NAME, 0, 2, Rarity.FREE, HeroClass.SHAMAN, 1);
			setCollectible(false);
			setTag(GameTag.RACE, Race.TOTEM);
		}

		@Override
		public Minion summon() {
			Minion wrathOfAirTotem = createMinion();
			wrathOfAirTotem.setTag(GameTag.SPELL_POWER, 1);
			return wrathOfAirTotem;
		}
	}
	
	private final static String HEALING_TOTEM_NAME = "Healing Totem";
	private final static String STONECLAW_TOTEM_NAME = "Stoneclaw Totem";
	private final static String WRATH_OF_AIR_TOTEM_NAME = "Wrath of Air Totem";
	private final static String SEARING_TOTEM_NAME = "Searing Totem";

	public TotemicCall() {
		super("Totemic Call", HeroClass.SHAMAN);
		setTargetRequirement(TargetSelection.NONE);
		setSpell(new TotemicCallSpell());
	}

	@Override
	public boolean canBeCast(GameContext context, Player player) {
		if (player.getMinions().size() < 4) {
			return true;
		}
		List<String> availableTotems = new ArrayList<String>();
		availableTotems.add(HEALING_TOTEM_NAME);
		availableTotems.add(SEARING_TOTEM_NAME);
		availableTotems.add(STONECLAW_TOTEM_NAME);
		availableTotems.add(WRATH_OF_AIR_TOTEM_NAME);
		for(Minion minion : player.getMinions()) {
			availableTotems.remove(minion.getName());
		}
		return !availableTotems.isEmpty();
	}

}

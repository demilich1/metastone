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
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class TotemicCall extends HeroPower {
	
	private class HealingTotem extends MinionCard {

		public HealingTotem() {
			super(HEALING_TOTEM_NAME, Rarity.FREE, HeroClass.SHAMAN, 1);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			Minion healingTotem = createMinion(0, 2, Race.TOTEM);
			//TODO: implement healing
			return healingTotem;
		}
		
	}
	private class SearingTotem extends MinionCard {

		public SearingTotem() {
			super(SEARING_TOTEM_NAME, Rarity.FREE, HeroClass.SHAMAN, 1);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion(1, 1, Race.TOTEM);
		}

		
	}
	private class StoneclawTotem extends MinionCard {

		public StoneclawTotem() {
			super(STONECLAW_TOTEM_NAME, Rarity.FREE, HeroClass.SHAMAN, 1);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion(0, 2, Race.TOTEM, GameTag.TAUNT);
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
		

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			List<Minion> availableTotems = new ArrayList<Minion>();
			for (Minion totem : getTotems()) {
				if (!alreadyOnBoard(player.getMinions(), totem.getName())) {
					availableTotems.add(totem);
				}
			}

			Minion randomTotem = availableTotems.get(ThreadLocalRandom.current().nextInt(availableTotems.size()));
			context.getLogic().summon(player, randomTotem, null);
		}

		private List<Minion> getTotems() {
			List<Minion> minions = new ArrayList<Minion>(4);
			minions.add(new HealingTotem().summon());
			minions.add(new StoneclawTotem().summon());
			minions.add(new SearingTotem().summon());
			minions.add(new WrathOfAirTotem().summon());
			return minions;
		}
		
	}

	private class WrathOfAirTotem extends MinionCard {

		public WrathOfAirTotem() {
			super(WRATH_OF_AIR_TOTEM_NAME, Rarity.FREE, HeroClass.SHAMAN, 1);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			Minion wrathOfAirTotem = createMinion(0, 2, Race.TOTEM);
			wrathOfAirTotem.setTag(GameTag.SPELL_POWER, 1);
			return wrathOfAirTotem;
		}
	}
	
	private final static String HEALING_TOTEM_NAME = "Healing Totem";
	private final static String STONECLAW_TOTEM_NAME = "Stoneclaw Totem";
	private final static String WRATH_OF_AIR_TOTEM_NAME = "Wrath of Air Totem";
	private final static String SEARING_TOTEM_NAME = "Searing Totem";

	public TotemicCall() {
		super("Totemic Call");
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

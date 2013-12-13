package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.HeroPowerAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Race;

public class TotemicCall extends HeroPower {

	public TotemicCall() {
		super("Totemic Call");
	}

	@Override
	public PlayCardAction play() {
		return new HeroPowerAction(this) {

			@Override
			protected void cast(GameContext context, Player player) {
				List<Minion> availableTotems = new ArrayList<Minion>();
				for (Minion totem : getTotems()) {
					if (!alreadyOnBoard(player.getMinions(), totem.getClass())) {
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

			private boolean alreadyOnBoard(List<Minion> minions, Class<? extends Minion> minionClass) {
				for (Entity minion : minions) {
					if (minion.getClass() == minionClass) {
						return true;
					}
				}
				return false;
			}
		};
	}

	private class HealingTotem extends MinionCard {


		public HealingTotem() {
			super("Healing Totem", Rarity.FREE, HeroClass.SHAMAN, 1);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			Minion healingTotem = createMinion(0, 3, Race.TOTEM);
			//TODO: implement healing
			return healingTotem;
		}
		
	}

	private class StoneclawTotem extends MinionCard {

		public StoneclawTotem() {
			super("Stoneclaw Totem", Rarity.FREE, HeroClass.SHAMAN, 1);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion(0, 2, Race.TOTEM, GameTag.TAUNT);
		}

		
	}

	private class SearingTotem extends MinionCard {

		public SearingTotem() {
			super("Searing Totem", Rarity.FREE, HeroClass.SHAMAN, 1);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion(1, 1, Race.TOTEM);
		}

		
	}

	private class WrathOfAirTotem extends MinionCard {

		public WrathOfAirTotem() {
			super("Wrath of Air Totem", Rarity.FREE, HeroClass.SHAMAN, 1);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			Minion wrathOfAirTotem = createMinion(0, 2, Race.TOTEM);
			wrathOfAirTotem.setTag(GameTag.SPELL_POWER, 1);
			return wrathOfAirTotem;
		}

		
	}

}

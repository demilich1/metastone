package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.UniqueMinion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class TotemicCallSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(TotemicCallSpell.class);
		return desc;
	}
	
	private boolean alreadyOnBoard(List<Minion> minions, UniqueMinion uniqueMinion) {
		for (Entity minion : minions) {
			if (minion.getTag(GameTag.UNIQUE_MINION) == uniqueMinion) {
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
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		List<Minion> availableTotems = new ArrayList<Minion>();
		for (Minion totem : getTotems()) {
			if (!alreadyOnBoard(player.getMinions(), (UniqueMinion) totem.getTag(GameTag.UNIQUE_MINION))) {
				availableTotems.add(totem);
			}
		}

		Minion randomTotem = availableTotems.get(ThreadLocalRandom.current().nextInt(availableTotems.size()));
		context.getLogic().summon(player.getId(), randomTotem, null, null, true);
	}
	
	private class HealingTotem extends MinionCard {

		public HealingTotem() {
			super("Healing Totem", 0, 2, Rarity.FREE, HeroClass.SHAMAN, 1);
			setCollectible(false);
			setRace(Race.TOTEM);
		}

		@Override
		public Minion summon() {
			Minion healingTotem = createMinion();
			healingTotem.setTag(GameTag.UNIQUE_MINION, UniqueMinion.HEALING_TOTEM);
			SpellDesc healSpell = HealingSpell.create(1);
			healSpell.setTarget(EntityReference.FRIENDLY_MINIONS);
			SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), healSpell);
			healingTotem.setSpellTrigger(trigger);
			return healingTotem;
		}
		
	}
	
	private class SearingTotem extends MinionCard {

		public SearingTotem() {
			super("Searing Totem", 1, 1, Rarity.FREE, HeroClass.SHAMAN, 1);
			setCollectible(false);
			setRace(Race.TOTEM);
		}

		@Override
		public Minion summon() {
			Minion searingTotem = createMinion();
			searingTotem.setTag(GameTag.UNIQUE_MINION, UniqueMinion.SEARING_TOTEM);
			return searingTotem;
		}
		
	}
	private class StoneclawTotem extends MinionCard {

		public StoneclawTotem() {
			super("Stoneclaw Totem", 0, 2, Rarity.FREE, HeroClass.SHAMAN, 1);
			setCollectible(false);
			setRace(Race.TOTEM);
		}

		@Override
		public Minion summon() {
			Minion stoneclawTotem = createMinion(GameTag.TAUNT);
			stoneclawTotem.setTag(GameTag.UNIQUE_MINION, UniqueMinion.STONECLAW_TOTEM);
			return stoneclawTotem;
		}
	}


	private class WrathOfAirTotem extends MinionCard {

		public WrathOfAirTotem() {
			super("Wrath of Air Totem", 0, 2, Rarity.FREE, HeroClass.SHAMAN, 1);
			setCollectible(false);
			setRace(Race.TOTEM);
		}

		@Override
		public Minion summon() {
			Minion wrathOfAirTotem = createMinion();
			wrathOfAirTotem.setTag(GameTag.UNIQUE_MINION, UniqueMinion.WRATH_OF_AIR_TOTEM);
			wrathOfAirTotem.setTag(GameTag.SPELL_POWER, 1);
			return wrathOfAirTotem;
		}
	}
	
}

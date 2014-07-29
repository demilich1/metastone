package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.aura.BuffAura;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class AnimalCompanion extends SpellCard {

	public AnimalCompanion() {
		super("Animal Companion", Rarity.FREE, HeroClass.HUNTER, 3);
		setDescription("Summon a random Beast Companion.");
		setSpell(new SummonRandomSpell(new Huffer(), new Misha(), new Leokk()));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 26;
	}
	
	private class Huffer extends MinionCard {

		public Huffer() {
			super("Huffer", 4, 2, Rarity.FREE, HeroClass.HUNTER, 3);
			setDescription("Charge");
			setRace(Race.BEAST);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion(GameTag.CHARGE);
		}
		
	}
	
	private class Leokk extends MinionCard {

		public Leokk() {
			super("Leokk", 2, 4, Rarity.FREE, HeroClass.HUNTER, 3);
			setDescription("Other friendly minions have +1 Attack.");
			setRace(Race.BEAST);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			Minion leokk = createMinion();
			Aura leokkAura = new BuffAura(1, 0, EntityReference.FRIENDLY_MINIONS);
			leokk.setSpellTrigger(leokkAura);
			return leokk;
		}
		
	}



	private class Misha extends MinionCard {

		public Misha() {
			super("Misha", 4, 4, Rarity.FREE, HeroClass.HUNTER, 3);
			setRace(Race.BEAST);
			setDescription("Taunt");
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion(GameTag.TAUNT);
		}
		
	}
}

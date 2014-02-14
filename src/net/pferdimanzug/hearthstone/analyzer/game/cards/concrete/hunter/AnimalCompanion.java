package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class AnimalCompanion extends SpellCard {

	private class Huffer extends MinionCard {

		public Huffer() {
			super("Huffer", 4, 2, Rarity.FREE, HeroClass.HUNTER, 3);
			setDescription("Charge");
			setTag(GameTag.RACE, Race.BEAST);
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
			setTag(GameTag.RACE, Race.BEAST);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			Minion leokk = createMinion();
			leokk.setAura(new LeokkAura(leokk));
			return leokk;
		}
		
	}
	
	private class LeokkAura extends Aura {

		public LeokkAura(Actor source) {
			super(source);
		}

		@Override
		public boolean affects(Actor entity) {
			if (entity == getSource()) {
				return false;
			} else if (entity.getOwner() != getSource().getOwner()) {
				return false;
			}
			return entity.getEntityType() == EntityType.MINION;
		}

		@Override
		protected void onApply(Actor entity) {
			entity.modifyTag(GameTag.ATTACK_BONUS, +1);
		}

		@Override
		protected void onRemove(Actor entity) {
			entity.modifyTag(GameTag.ATTACK_BONUS, -1);
		}

	}
	
	private class Misha extends MinionCard {

		public Misha() {
			super("Misha", 4, 4, Rarity.FREE, HeroClass.HUNTER, 3);
			setTag(GameTag.RACE, Race.BEAST);
			setDescription("Taunt");
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion(GameTag.TAUNT);
		}
		
	}
	
	public AnimalCompanion() {
		super("Animal Companion", Rarity.FREE, HeroClass.HUNTER, 3);
		setDescription("Summon a random Beast Companion.");
		setSpell(new SummonRandomSpell(new Huffer(), new Misha(), new Leokk()));
		setTargetRequirement(TargetSelection.NONE);
	}

}

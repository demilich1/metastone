package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonRandomSpell;

public class AnimalCompanion extends SpellCard {

	private class Huffer extends MinionCard {

		public Huffer() {
			super("Huffer", Rarity.FREE, HeroClass.HUNTER, 3);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion(4, 2, Race.BEAST, GameTag.CHARGE);
		}
		
	}

	private class Leokk extends MinionCard {

		public Leokk() {
			super("Leokk", Rarity.FREE, HeroClass.HUNTER, 3);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			Minion leokk = createMinion(2, 4, Race.BEAST);
			leokk.setAura(new LeokkAura(leokk));
			return leokk;
		}
		
	}
	
	private class LeokkAura extends Aura {

		public LeokkAura(Entity source) {
			super(source);
		}

		@Override
		public boolean affects(Entity entity) {
			if (entity == getSource()) {
				return false;
			} else if (entity.getOwner() != getSource().getOwner()) {
				return false;
			}
			return entity.getEntityType() == EntityType.MINION;
		}

		@Override
		protected void onApply(Entity entity) {
			entity.modifyTag(GameTag.ATTACK_BONUS, +1);
		}

		@Override
		protected void onRemove(Entity entity) {
			entity.modifyTag(GameTag.ATTACK_BONUS, -1);
		}

	}
	
	private class Misha extends MinionCard {

		public Misha() {
			super("Misha", Rarity.FREE, HeroClass.HUNTER, 3);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion(4, 4, Race.BEAST, GameTag.TAUNT);
		}
		
	}
	
	public AnimalCompanion() {
		super("Animal Companion", Rarity.FREE, HeroClass.HUNTER, 3);
		setSpell(new SummonRandomSpell(new Huffer(), new Misha(), new Leokk()));
	}

}

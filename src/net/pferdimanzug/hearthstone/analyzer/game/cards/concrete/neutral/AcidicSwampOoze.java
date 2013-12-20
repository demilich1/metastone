package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroyWeaponSpell;

public class AcidicSwampOoze extends MinionCard {

	public AcidicSwampOoze(String name, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, rarity, classRestriction, manaCost);
	}

	@Override
	public Minion summon() {
		Minion acidicSwampOoze = createMinion(3, 2);
		acidicSwampOoze.setTag(GameTag.BATTLECRY, new BattlecryDestroyWeapon());
		return acidicSwampOoze;
	}
	
	private class BattlecryDestroyWeapon extends Battlecry {

		public BattlecryDestroyWeapon() {
			super(new DestroyWeaponSpell());
			setTargetRequirement(TargetRequirement.ENEMY_HERO);
		}

		@Override
		public boolean canBeExecutedOn(Entity entity) {
			if (entity.getEntityType() != EntityType.HERO) {
				return false;
			}
			Hero hero = (Hero) entity;
			return hero.getWeapon() != null;
		}
		
		
	}

}

package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.PhysicalAttackEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetHealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.IGameEventTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class TruesilverChampion extends WeaponCard {

	public TruesilverChampion() {
		super("Truesilver Champion", Rarity.FREE, HeroClass.PALADIN, 4);
	}

	@Override
	public Weapon getWeapon() {
		Weapon trueSilverChampion = createWeapon(4, 2);
		SpellTrigger trigger = new SpellTrigger(new TruesilverChampionWeaponTrigger(), new SingleTargetHealingSpell(2));
		trueSilverChampion.addSpellTrigger(trigger);
		return trueSilverChampion;
	}
	
	private class TruesilverChampionWeaponTrigger implements IGameEventTrigger {
		
		
		@Override
		public boolean fire(IGameEvent event, Entity host) {
			PhysicalAttackEvent physicalAttackEvent = (PhysicalAttackEvent) event;
			if (physicalAttackEvent.getAttacker().getEntityType() != EntityType.HERO) {
				return false;
			}
			Hero hero = (Hero) physicalAttackEvent.getAttacker();
			return hero.getWeapon() == host;
		}

		@Override
		public GameEventType interestedIn() {
			return GameEventType.PHYSICAL_ATTACK;
		}

		@Override
		public Entity getTarget(GameContext context, Entity host) {
			return host.getOwner().getHero();
		}
		
	}

}

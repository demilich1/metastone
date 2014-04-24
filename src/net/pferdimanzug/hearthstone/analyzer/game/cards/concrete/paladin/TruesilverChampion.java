package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.PhysicalAttackEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.GameEventTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class TruesilverChampion extends WeaponCard {

	private class TruesilverChampionWeaponTrigger extends GameEventTrigger {
		
		@Override
		public boolean fire(GameEvent event, Actor host) {
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
		
	}

	public TruesilverChampion() {
		super("Truesilver Champion", Rarity.FREE, HeroClass.PALADIN, 4);
		setDescription("Whenever your hero attacks, restore #2 Health to it.");
	}
	
	@Override
	public Weapon getWeapon() {
		Weapon trueSilverChampion = createWeapon(4, 2);
		Spell healHero = new HealingSpell(2);
		healHero.setTarget(EntityReference.FRIENDLY_HERO);
		SpellTrigger trigger = new SpellTrigger(new TruesilverChampionWeaponTrigger(), healHero);
		trueSilverChampion.setSpellTrigger(trigger);
		return trueSilverChampion;
	}

}

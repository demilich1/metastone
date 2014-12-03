package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.Environment;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.MisdirectSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TargetAcquisitionTrigger;

public class OgreWarmaul extends WeaponCard {

	public OgreWarmaul() {
		super("Ogre Warmaul", Rarity.COMMON, HeroClass.WARRIOR, 3);
		setDescription("50% chance to attack the wrong enemy.");
	}

	@Override
	public Weapon getWeapon() {
		Weapon ogreWarmaul = createWeapon(4, 2);
		SpellDesc fumble = MisdirectSpell.create();
		SpellTrigger trigger = new SpellTrigger(new OgreWarmaulTrigger(), fumble);
		ogreWarmaul.setSpellTrigger(trigger);
		return ogreWarmaul;
	}

	private class OgreWarmaulTrigger extends TargetAcquisitionTrigger {

		public OgreWarmaulTrigger() {
			super(ActionType.PHYSICAL_ATTACK);
		}

		@Override
		public boolean fire(GameEvent event, Entity host) {
			if (!super.fire(event, host)) {
				return false;
			}
			Entity attacker = (Entity) event.getGameContext().getEnvironment().get(Environment.ATTACKER);
			if (attacker != null && attacker.getEntityType() != EntityType.HERO) {
				return false;
			}

			Hero hero = (Hero)attacker;
			if (hero.getWeapon() != host) {
				return false;
			}
			// this trigger only sometimes fires
			return event.getGameContext().getLogic().randomBool();
		}
	}
}

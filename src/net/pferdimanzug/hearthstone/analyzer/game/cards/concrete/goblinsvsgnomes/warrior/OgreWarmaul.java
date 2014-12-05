package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.WeaponDestroyedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class OgreWarmaul extends WeaponCard {

	public OgreWarmaul() {
		super("Ogre Warmaul", Rarity.COMMON, HeroClass.WARRIOR, 3);
		setDescription("50% chance to attack the wrong enemy.");
	}

	@Override
	public int getTypeId() {
		return 606;
	}



	@Override
	public Weapon getWeapon() {
		Weapon ogreWarmaul = createWeapon(4, 2);
		SpellDesc fumble = ApplyTagSpell.create(GameTag.FUMBLE, new WeaponDestroyedTrigger());
		fumble.setTarget(EntityReference.FRIENDLY_HERO);
		ogreWarmaul.setBattlecry(Battlecry.createBattlecry(fumble));
		return ogreWarmaul;
	}
}

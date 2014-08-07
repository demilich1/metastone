package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ChangeDurabilitySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SecretRevealedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class EaglehornBow extends WeaponCard {

	public EaglehornBow() {
		super("Eaglehorn Bow", Rarity.RARE, HeroClass.HUNTER, 3);
		setDescription("Whenever a Secret is revealed, gain +1 Durability.");
	}

	@Override
	public int getTypeId() {
		return 30;
	}

	@Override
	public Weapon getWeapon() {
		Weapon eaglehornBow = createWeapon(3, 2);
		SpellDesc increaseDurabilitySpell = ChangeDurabilitySpell.create(+1);
		increaseDurabilitySpell.setTarget(EntityReference.FRIENDLY_HERO);
		eaglehornBow.setSpellTrigger(new SpellTrigger(new SecretRevealedTrigger(), increaseDurabilitySpell));
		return eaglehornBow;
	}
}

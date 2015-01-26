package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.spells.ChangeDurabilitySpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SecretRevealedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

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

package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.ChangeDurabilitySpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.MinionSummonedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class SwordOfJustice extends WeaponCard {

	public SwordOfJustice() {
		super("Sword of Justice", Rarity.EPIC, HeroClass.PALADIN, 3);
		setDescription("Whenever you summon a minion, give it +1/+1 and this loses 1 Durability.");
	}

	@Override
	public int getTypeId() {
		return 256;
	}

	@Override
	public Weapon getWeapon() {
		Weapon swordOfJustice = createWeapon(1, 5);
		SpellDesc buffSpell = BuffSpell.create(1, 1);
		buffSpell.setTarget(EntityReference.EVENT_TARGET);
		SpellDesc loseDurability = ChangeDurabilitySpell.create(-1);
		loseDurability.setTarget(EntityReference.FRIENDLY_HERO);
		SpellDesc swordOfJusticeSpell = MetaSpell.create(buffSpell, loseDurability);
		SpellTrigger trigger = new SpellTrigger(new MinionSummonedTrigger(TargetPlayer.SELF), swordOfJusticeSpell);
		swordOfJustice.setSpellTrigger(trigger);
		return swordOfJustice;
	}
}

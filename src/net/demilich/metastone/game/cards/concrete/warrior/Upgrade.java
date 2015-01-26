package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.cards.concrete.tokens.weapons.HeavyAxe;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.BuffWeaponSpell;
import net.demilich.metastone.game.spells.EitherOrSpell;
import net.demilich.metastone.game.spells.EquipWeaponSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Upgrade extends SpellCard {

	public Upgrade() {
		super("Upgrade!", Rarity.RARE, HeroClass.WARRIOR, 1);
		setDescription("If you have a weapon, give it +1/+1.  Otherwise equip a 1/3 weapon.");

		SpellDesc equipWeapon = EquipWeaponSpell.create(new HeavyAxe());
		SpellDesc buffWeapon = BuffWeaponSpell.create(1, 1);
		SpellDesc upgrade = EitherOrSpell.create(equipWeapon, buffWeapon, (context, player, target) -> player.getHero().getWeapon() == null);
		setSpell(upgrade);
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 381;
	}

	
}

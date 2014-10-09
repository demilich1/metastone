package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.weapons.HeavyAxe;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffWeaponSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.EitherOrSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.EquipWeaponSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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

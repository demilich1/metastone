package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.hunter;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class Glaivezooka extends WeaponCard {

	public Glaivezooka() {
		super("Glaivezooka", Rarity.COMMON, HeroClass.HUNTER, 2);
		setDescription("Battlecry: Give a random friendly minion +1 Attack.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 488;
	}

	@Override
	public Weapon getWeapon() {
		Weapon weapon = createWeapon(2, 2);
		SpellDesc randomBuffSpell = BuffSpell.create(EntityReference.FRIENDLY_MINIONS, +1, 0, true);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(randomBuffSpell);
		weapon.setBattlecry(battlecry);
		return weapon;
	}
}

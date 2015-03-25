package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.spells.AddAttributeSpell;
import net.demilich.metastone.game.spells.RemoveAttributeSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class Doomhammer extends WeaponCard {

	public Doomhammer() {
		super("Doomhammer", Rarity.EPIC, HeroClass.SHAMAN, 5);
		setDescription("Windfury, Overload: (2)");
		setTag(GameTag.OVERLOAD, 2);
	}

	@Override
	public int getTypeId() {
		return 313;
	}

	@Override
	public Weapon getWeapon() {
		Weapon doomhammer = new Weapon(this, 2, 8) {

			@Override
			public void onEquip(GameContext context, Player player) {
				SpellDesc windfury = AddAttributeSpell.create(GameTag.WINDFURY);
				context.getLogic().castSpell(player.getId(), windfury, getReference(), EntityReference.FRIENDLY_HERO);
			}

			@Override
			public void onUnequip(GameContext context, Player player) {
				SpellDesc removeWindfury = RemoveAttributeSpell.create(GameTag.WINDFURY);
				context.getLogic().castSpell(player.getId(), removeWindfury, getReference(), EntityReference.FRIENDLY_HERO);
			}

		};
		return doomhammer;
	}
}

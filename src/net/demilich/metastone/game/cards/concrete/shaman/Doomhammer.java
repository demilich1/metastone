package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.spells.WindfurySpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.RemoveWindfurySpell;
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
				SpellDesc windfury = WindfurySpell.create();
				windfury.setSourceEntity(getReference());
				windfury.setTarget(EntityReference.FRIENDLY_HERO);
				context.getLogic().castSpell(player.getId(), windfury);
			}

			@Override
			public void onUnequip(GameContext context, Player player) {
				SpellDesc removeWindfury = RemoveWindfurySpell.create();
				removeWindfury.setSourceEntity(getReference());
				removeWindfury.setTarget(EntityReference.FRIENDLY_HERO);
				context.getLogic().castSpell(player.getId(), removeWindfury);
			}

		};
		return doomhammer;
	}
}

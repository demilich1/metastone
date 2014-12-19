package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.WindfurySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.RemoveWindfurySpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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

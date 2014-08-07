package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.WindfurySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.WeaponDestroyedTrigger;
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
		Weapon doomhammer = createWeapon(2, 8);
		SpellDesc windfury = WindfurySpell.create(new WeaponDestroyedTrigger());
		windfury.setTarget(EntityReference.FRIENDLY_HERO);
		doomhammer.setBattlecry(Battlecry.createBattlecry(windfury));
		return doomhammer;
	}
}

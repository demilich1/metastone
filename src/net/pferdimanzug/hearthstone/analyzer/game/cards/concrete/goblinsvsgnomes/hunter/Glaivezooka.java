package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
		SpellDesc randomBuffSpell = BuffSpell.create(+1);
		randomBuffSpell.setTarget(EntityReference.FRIENDLY_MINIONS);
		randomBuffSpell.pickRandomTarget(true);
		Battlecry battlecry = Battlecry.createBattlecry(randomBuffSpell);
		weapon.setBattlecry(battlecry);
		return weapon;
	}
}

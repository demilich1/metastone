package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ComboSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class PerditionsBlade extends WeaponCard {

	public PerditionsBlade() {
		super("Perdition's Blade", Rarity.RARE, HeroClass.ROGUE, 3);
		setDescription("Battlecry: Deal 1 damage. Combo: Deal 2 instead.");
	}

	@Override
	public int getTypeId() {
		return 300;
	}

	@Override
	public Weapon getWeapon() {
		Weapon perditionsBlade = createWeapon(2, 2);
		SpellDesc noCombo = DamageSpell.create(1);
		SpellDesc combo = DamageSpell.create(2);
		Battlecry battlecry = Battlecry.createBattlecry(ComboSpell.create(noCombo, combo), TargetSelection.ANY);
		perditionsBlade.setBattlecry(battlecry);
		return perditionsBlade;
	}
}

package net.demilich.metastone.game.cards.concrete.rogue;

import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.spells.ComboSpell;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

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
		BattlecryAction battlecry = BattlecryAction.createBattlecry(ComboSpell.create(noCombo, combo), TargetSelection.ANY);
		perditionsBlade.setBattlecry(battlecry);
		return perditionsBlade;
	}
}

package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.rogue;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.BuffWeaponSpell;
import net.demilich.metastone.game.spells.ComboSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class TinkersSharpswordOil extends SpellCard {

	public TinkersSharpswordOil() {
		super("Tinker's Sharpsword Oil", Rarity.COMMON, HeroClass.ROGUE, 4);
		setDescription("Give your weapon +3 Attack. Combo: Give a random friendly minion +3 Attack.");

		SpellDesc buffWeapon = BuffWeaponSpell.create(+3);
		SpellDesc buffRandomMinion = BuffSpell.create(EntityReference.FRIENDLY_MINIONS, +3, 0, true);

		SpellDesc comboSpell = MetaSpell.create(EntityReference.NONE, buffWeapon, buffRandomMinion);

		setSpell(ComboSpell.create(buffWeapon, comboSpell));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 573;
	}
}

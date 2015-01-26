package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.TemporaryAttackSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class RockbiterWeapon extends SpellCard {

	private static final int ATTACK_BONUS = 3;

	public RockbiterWeapon() {
		super("Rockbiter Weapon", Rarity.FREE, HeroClass.SHAMAN, 1);
		setDescription("Give a friendly character +3 Attack this turn.");
		SpellDesc buff = TemporaryAttackSpell.create(+ATTACK_BONUS);
		setSpell(buff);
		setTargetRequirement(TargetSelection.FRIENDLY_CHARACTERS);
	}

	@Override
	public int getTypeId() {
		return 328;
	}
}

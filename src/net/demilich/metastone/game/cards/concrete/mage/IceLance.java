package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.EitherOrSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;


public class IceLance extends SpellCard {

	public IceLance() {
		super("Ice Lance", Rarity.COMMON, HeroClass.MAGE, 1);
		setDescription("Freeze a character. If it was already Frozen, deal $4 damage instead.");
		SpellDesc damageSpell = DamageSpell.create(4);
		SpellDesc freezeSpell = ApplyTagSpell.create(GameTag.FROZEN);
		setSpell(EitherOrSpell.create(damageSpell, freezeSpell, (context, player, target) -> target.hasStatus(GameTag.FROZEN)));
		setTargetRequirement(TargetSelection.ANY);
	}

	@Override
	public int getTypeId() {
		return 65;
	}
}

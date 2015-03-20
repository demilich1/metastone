package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.TemporaryAttackSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Bloodlust extends SpellCard {

	private static final int ATTACK_BONUS = 3;
	
	public Bloodlust() {
		super("Bloodlust", Rarity.FREE, HeroClass.SHAMAN, 5);
		setDescription("Give your minions +3 Attack this turn.");
		SpellDesc buff = TemporaryAttackSpell.create(EntityReference.FRIENDLY_MINIONS, +ATTACK_BONUS);
		setSpell(buff);
		setTargetRequirement(TargetSelection.NONE);
	}
	
	@Override
	public int getTypeId() {
		return 312;
	}
}

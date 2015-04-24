package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Revenge extends SpellCard {

	public Revenge() {
		super("Revenge", Rarity.RARE, HeroClass.WARRIOR, 2);
		setDescription("Deal 1 damage to all minions. If you have 12 or less Health, deal 3 damage instead.");
		// (context, player, target) -> player.getHero().getHp() > 12 ? 1 :3
		setSpell(DamageSpell.create(EntityReference.ALL_MINIONS, null) );
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 643;
	}
}

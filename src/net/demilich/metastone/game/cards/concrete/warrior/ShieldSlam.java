package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class ShieldSlam extends SpellCard {

	public ShieldSlam() {
		super("Shield Slam", Rarity.EPIC, HeroClass.WARRIOR, 1);
		setDescription("Deal 1 damage to a minion for each Armor you have.");
		
		SpellDesc damageSpell = DamageSpell.create((context, player, target) -> player.getHero().getArmor());
		setSpell(damageSpell);
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public int getTypeId() {
		return 379;
	}
}

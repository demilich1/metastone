package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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

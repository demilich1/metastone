package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.AdjacentMetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ExplosiveShot extends SpellCard {

	public ExplosiveShot() {
		super("Explosive Shot", Rarity.RARE, HeroClass.HUNTER, 5);
		setDescription("Deal $5 damage to a minion and $2 damage to adjacent ones.");
		SpellDesc primary = DamageSpell.create(5);
		SpellDesc secondary = DamageSpell.create(2);
		setSpell(AdjacentMetaSpell.create(primary, secondary));
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public int getTypeId() {
		return 31;
	}

	
}

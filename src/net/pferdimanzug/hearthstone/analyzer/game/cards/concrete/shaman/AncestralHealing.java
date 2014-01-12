package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class AncestralHealing extends SpellCard {

	public AncestralHealing() {
		super("Ancestral Healing", Rarity.FREE, HeroClass.SHAMAN, 0);
		setTargetRequirement(TargetSelection.MINIONS);
		// implemented 'heal to full' as very large number. Change this if any problems arise
		setSpell(new MetaSpell(new HealingSpell(9999), new ApplyTagSpell(GameTag.TAUNT)));
	}

}

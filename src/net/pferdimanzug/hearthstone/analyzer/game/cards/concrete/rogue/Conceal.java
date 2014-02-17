package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Conceal extends SpellCard {

	public Conceal() {
		super("Conceal", Rarity.COMMON, HeroClass.ROGUE, 1);
		setDescription("Give your minions Stealth until your next turn.");
		Spell stealth = new ApplyTagSpell(GameTag.STEALTHED, true);
		setSpell(stealth);
		setPredefinedTarget(EntityReference.FRIENDLY_MINIONS);
		setTargetRequirement(TargetSelection.NONE);
	}

}

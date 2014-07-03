package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class SiphonSoul extends SpellCard {

	public SiphonSoul() {
		super("Siphon Soul", Rarity.RARE, HeroClass.WARLOCK, 6);
		setDescription("Destroy a minion. Restore 3 Health to your hero.");

		Spell destroySpell = new DestroySpell();
		Spell healingSpell = new HealingSpell(3);
		healingSpell.setTarget(EntityReference.FRIENDLY_HERO);
		setSpell(new MetaSpell(destroySpell, healingSpell));

		setTargetRequirement(TargetSelection.MINIONS);
	}

}

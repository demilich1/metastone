package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndAndControlSecretTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Illuminator extends MinionCard {

	public Illuminator() {
		super("Illuminator", 2, 4, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("If you control a Secret at the end of your turn, restore 4 health to your hero.");
	}

	@Override
	public Minion summon() {
		Minion illuminator = createMinion();
		SpellDesc healHeroSpell = HealingSpell.create(4);
		healHeroSpell.setTarget(EntityReference.FRIENDLY_HERO);
		SpellTrigger trigger = new SpellTrigger(new TurnEndAndControlSecretTrigger(), healHeroSpell);
		illuminator.setSpellTrigger(trigger);
		return illuminator;
	}

}

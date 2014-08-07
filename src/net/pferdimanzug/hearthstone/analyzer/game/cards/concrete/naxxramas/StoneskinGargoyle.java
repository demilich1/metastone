package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class StoneskinGargoyle extends MinionCard {

	public StoneskinGargoyle() {
		super("Stoneskin Gargoyle", 1, 4, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("At the start of your turn, restore this minion to full Health.");
	}

	@Override
	public int getTypeId() {
		return 400;
	}

	@Override
	public Minion summon() {
		Minion stoneskinGargoyle = createMinion();
		SpellDesc healSelf = HealingSpell.create(99);
		healSelf.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new TurnStartTrigger(), healSelf);
		stoneskinGargoyle.setSpellTrigger(trigger);
		return stoneskinGargoyle;
	}
}

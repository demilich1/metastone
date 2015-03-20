package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndAndControlSecretTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class Illuminator extends MinionCard {

	public Illuminator() {
		super("Illuminator", 2, 4, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("If you control a Secret at the end of your turn, restore 4 health to your hero.");
	}

	@Override
	public int getTypeId() {
		return 523;
	}

	@Override
	public Minion summon() {
		Minion illuminator = createMinion();
		SpellDesc healHeroSpell = HealingSpell.create(EntityReference.FRIENDLY_HERO, 4);
		SpellTrigger trigger = new SpellTrigger(new TurnEndAndControlSecretTrigger(), healHeroSpell);
		illuminator.setSpellTrigger(trigger);
		return illuminator;
	}
}

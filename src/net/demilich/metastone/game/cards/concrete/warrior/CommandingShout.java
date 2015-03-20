package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class CommandingShout extends SpellCard {

	public CommandingShout() {
		super("Commanding Shout", Rarity.RARE, HeroClass.WARRIOR, 2);
		setDescription("Your minions can't be reduced below 1 Health this turn. Draw a card.");
		
		SpellDesc commanding = ApplyTagSpell.create(EntityReference.FRIENDLY_CHARACTERS, GameTag.CANNOT_REDUCE_HP_BELOW_1);
		setSpell(MetaSpell.create(DrawCardSpell.create(), commanding));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 366;
	}
}

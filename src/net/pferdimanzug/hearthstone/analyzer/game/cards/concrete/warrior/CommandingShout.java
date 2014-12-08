package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class CommandingShout extends SpellCard {

	public CommandingShout() {
		super("Commanding Shout", Rarity.RARE, HeroClass.WARRIOR, 2);
		setDescription("Your minions can't be reduced below 1 Health this turn. Draw a card.");
		
		SpellDesc commanding = ApplyTagSpell.create(GameTag.CANNOT_REDUCE_HP_BELOW_1);
		commanding.setTarget(EntityReference.FRIENDLY_CHARACTERS);
		setSpell(MetaSpell.create(DrawCardSpell.create(), commanding));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 366;
	}
}

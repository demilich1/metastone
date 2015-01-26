package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DrawCardAndDoSomethingSpell;
import net.demilich.metastone.game.spells.ManaCostCardProcessor;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class FarSight extends SpellCard {

	public FarSight() {
		super("Far Sight", Rarity.EPIC, HeroClass.SHAMAN, 3);
		setDescription("Draw a card. That card costs (3) less.");

		SpellDesc desc = DrawCardAndDoSomethingSpell.create(SpellUtils::drawFromDeck, new ManaCostCardProcessor(-3));
		setSpell(desc);
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 317;
	}

}

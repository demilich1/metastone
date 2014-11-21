package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardAndDoSomethingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ManaCostCardProcessor;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SpellUtils;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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

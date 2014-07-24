package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.costmodifier.OneTurnCostModifier;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AddCostModifierSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Preparation extends SpellCard {

	public Preparation() {
		super("Preparation", Rarity.EPIC, HeroClass.ROGUE, 0);
		setDescription("The next spell you cast this turn costs (3) less.");

		OneTurnCostModifier costModifier = new OneTurnCostModifier(CardType.SPELL, -3, true);
		Spell spellCostReduce = new AddCostModifierSpell(costModifier);
		spellCostReduce.setTarget(EntityReference.FRIENDLY_HERO);
		setSpell(spellCostReduce);
		setPredefinedTarget(EntityReference.FRIENDLY_HERO);
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 301;
	}
}

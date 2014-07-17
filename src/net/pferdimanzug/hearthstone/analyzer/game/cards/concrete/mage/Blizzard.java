package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Blizzard extends SpellCard {

	public Blizzard() {
		super("Blizzard", Rarity.RARE, HeroClass.MAGE, 6);
		setDescription("Deal $2 damage to all enemy minions and Freeze them.");

		Spell blizzardSpell = new MetaSpell(new DamageSpell(2), new ApplyTagSpell(GameTag.FROZEN, new TurnStartTrigger()));
		blizzardSpell.setTarget(EntityReference.ENEMY_MINIONS);
		setSpell(blizzardSpell);
		setTargetRequirement(TargetSelection.NONE);
	}



	@Override
	public int getTypeId() {
		return 55;
	}
}
